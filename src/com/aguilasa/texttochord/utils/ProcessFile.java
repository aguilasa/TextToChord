/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package com.aguilasa.texttochord.utils;

import com.aguilasa.texttochord.model.FromTo;
import com.aguilasa.texttochord.model.SongFileChords;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.aguilasa.texttochord.model.Consts.PATH_FILES;
import static com.aguilasa.texttochord.utils.Utils.*;

/**
 * @author Ingmar.Aguiar
 */
public class ProcessFile {

	private static final String LINE_BREAK = "<br>";
	private File file;
	private String tone;
	private String name;
	private String artist;
	private String minor;
	private final List<String> processed = new ArrayList<>();
	private final StringBuilder processedSb = new StringBuilder();
	private String outPath = "";
	public boolean saveText = true;
	public boolean saveJson = true;

	public ProcessFile() {

	}

	public ProcessFile(String outPath) {
		this.outPath = outPath;
	}

	public void process(File file) {
		try {
			this.file = file;
			List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
			processName(lines);
			processLines(lines);
			if (saveText) {
				saveFile();
			}
			if (saveJson) {
				saveFileJson();
			}
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

	private void saveFileJson() {
        String fileName = getOutPath().concat(file.getName()).replace(".txt", ".json");
        getSongFileChords().saveToFile(fileName, 4);
    }

	public SongFileChords getSongFileChords() {
		return SongFileChords.builder()
				.id(UUID.randomUUID())
				.name(name)
				.artist(artist)
				.tone(tone)
				.original(tone)
				.minor(minor)
				.chords(processedSb.toString())
				.build();
	}

	private void processName(List<String> lines) {
		System.out.println(file.getName());
		name = lines.remove(0);
		artist = lines.remove(0);
		tone = lines.remove(0);
		minor = lines.remove(0).trim().equalsIgnoreCase("t") ? "m" : "";
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
		String line = intro.concat(LINE_BREAK);
		processed.add(line);
		processedSb.append(line);
	}

	private void processChords(List<String> lines) {
		for (String line : lines) {
			//line = line.trim();

			if (!line.isEmpty()) {
				if (isLineOfChords(line)) {
					Set<String> lineChords = getLineChords(line);
					Set<FromTo> fromToChords = getFromToChords(lineChords, tone);

					for (FromTo fromTo : fromToChords) {
						line = line.replace(fromTo.getFrom(), fromTo.getTo());
					}
				}
			}
			String processedLine = line.concat(LINE_BREAK);
			processed.add(processedLine);
			processedSb.append(processedLine);
		}
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
