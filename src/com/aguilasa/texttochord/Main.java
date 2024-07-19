package com.aguilasa.texttochord;

import com.aguilasa.texttochord.utils.ProcessFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static com.aguilasa.texttochord.model.Consts.PATH_FILES;

/**
 * @author Ingmar.Aguiar
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String path = args.length > 0 ? args[0] : PATH_FILES;

        StringBuilder sb = new StringBuilder();
        Collection<File> listFiles = FileUtils.listFiles(new File(path), new String[]{"txt"}, false);
        for (File file : listFiles) {
            if (file.getName().equalsIgnoreCase("base.txt")) {
                continue;
            }
            ProcessFile process = new ProcessFile(path.concat("\\out\\"));
            process.saveText = false;
            process.saveJson = true;
            process.process(file);
            sb.append(process.getSongFileChords().toJson(4));
            sb.append(",");
        }

        try {
            FileUtils.write(new File(path.concat("\\saida.json")), sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
