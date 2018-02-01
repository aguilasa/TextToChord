/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package br.com.isaguiar;

import static br.com.isaguiar.Consts.PATH_FILES;

import java.io.File;
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

		Collection<File> listFiles = FileUtils.listFiles(new File(path), new String[] { "txt" }, false);
		for (File file : listFiles) {
			ProcessFile process = new ProcessFile(path.concat("\\out\\"));
			process.process(file);
		}

		// ProcessFile process = new ProcessFile();
		// process.process(new File(PATH_FILES.concat("\\Você pode ter_A.txt")));

	}

}
