/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package br.com.isaguiar;

import static br.com.isaguiar.Consts.PATH_FILES;

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

		ProcessFile process2 = new ProcessFile();
		process2.process(new File(path.concat("\\Graça soberana;Paulo César Baruk;F;f.txt")));

		StringBuilder sb = new StringBuilder();
		int id = 0;
		Collection<File> listFiles = FileUtils.listFiles(new File(path), new String[] { "txt" }, false);
		for (File file : listFiles) {
			ProcessFile process = new ProcessFile(++id, path.concat("\\out\\"));
			process.saveText = false;
			process.saveJson = true;
			process.process(file);
			sb.append(process.getJson().toString(4));
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
