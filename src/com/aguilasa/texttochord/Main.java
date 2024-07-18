/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package com.aguilasa.texttochord;

import static com.aguilasa.texttochord.Consts.PATH_FILES;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * @author Ingmar.Aguiar
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String path = args.length > 0 ? args[0] : PATH_FILES;

//		ProcessFile process2 = new ProcessFile();
//		process2.process(new File(path.concat("\\Gra�a soberana;Paulo C�sar Baruk;F;f.txt")));

		StringBuilder sb = new StringBuilder();
		Collection<File> listFiles = FileUtils.listFiles(new File(path), new String[] { "txt" }, false);
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
			FileUtils.write(new File(path.concat("\\saida.json")), sb.toString(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
