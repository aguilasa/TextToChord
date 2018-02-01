/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package br.com.isaguiar;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static br.com.isaguiar.Utils.*;
import static br.com.isaguiar.Consts.*;
import org.apache.commons.io.FileUtils;

/**
 * @author Ingmar.Aguiar
 */
public class ProcessFile {

	private static final String LINE_BREAK = "<br>";
	private File file;
	private String tone;
	private List<String> processed = new ArrayList<>();
	private String outPath = "";

	public ProcessFile() {

	}

	public ProcessFile(String outPath) {
		this.outPath = outPath;
	}

	public void process(File file) {
		try {
			this.file = file;
			List<String> lines = FileUtils.readLines(file, Charset.forName("ISO_8859_1"));
			processTone();
			processLines(lines);
			saveFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void saveFile() {
		try {
			String fileName = getOutPath().concat(file.getName());
			FileUtils.writeLines(new File(fileName), processed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processTone() {
		System.out.println(file.getName());
		String[] split = file.getName().split("_|\\.");
		tone = split[1];
	}

	private void processLines(List<String> lines) {
		processIntro(lines);
		processChords(lines);
	}

	private void processIntro(List<String> lines) {
		String intro = lines.remove(0);
		Set<String> lineChords = getIntroChords(intro);
		Set<FromTo> fromToChords = getFromToChords(lineChords, tone);
		for (FromTo fromTo : fromToChords) {
			intro = intro.replace(fromTo.getFrom(), fromTo.getTo());
		}
		processed.add(intro.concat(LINE_BREAK));
	}

	private void processChords(List<String> lines) {
		for (String line : lines) {
			line = line.trim();

			if (!line.isEmpty()) {
				if (isLineOfChords(line)) {
					Set<String> lineChords = getLineChords(line);
					Set<FromTo> fromToChords = getFromToChords(lineChords, tone);

					for (FromTo fromTo : fromToChords) {
						line = line.replace(fromTo.getFrom(), fromTo.getTo());
					}
				}
			}
			processed.add(line.concat(LINE_BREAK));
		}
		System.out.println(processed);
	}

	public void writeProcessed() {
		if (!processed.isEmpty()) {
			try {
				FileUtils.writeLines(file, processed);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private String getOutPath() {
		return outPath.isEmpty() ? PATH_FILES.concat("\\out\\") : outPath;
	}

}
