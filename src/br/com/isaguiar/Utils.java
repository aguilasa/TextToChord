/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package br.com.isaguiar;

import static br.com.isaguiar.Consts.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ingmar.Aguiar
 */
public class Utils {

	private static final String TONE_FORMAT = "{%s}";

	public static int getSequenceByTone(String aTone, String aChord) {

		if (aChord.equals("A#")) {
			aChord = "Bb";
		} else if (aChord.equals("Db")) {
			aChord = "C#";
		} else if (aChord.equals("D#")) {
			aChord = "Eb";
		}

		List<String> sequences = SEQUENCES.get(aTone);
		if (sequences != null) {
			return sequences.indexOf(aChord);
		}

		return -1;
	}

	public static boolean inBasicChords(String aChord) {
		for (String c : BASIC_CHORDS) {
			if (c.equals(aChord)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidMarker(String aWord) {
		aWord = aWord.trim();
		for (String c : MARKERS) {
			if (c.trim().equalsIgnoreCase(aWord)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidChord(String aChord) {
		aChord = aChord.trim();
		if (aChord.length() >= 1) {
			if (isSingleChord(aChord)) {
				return inBasicChords(aChord);
			}
			return inBasicChords(aChord.substring(0, 1));
		}

		return false;
	}

	public static boolean isSingleChord(String aChord) {
		aChord = aChord.trim();
		return aChord.length() == 1;
	}

	public static boolean isSharp(String aChord) {
		if (aChord.length() > 1) {
			return aChord.charAt(1) == '#';
		}
		return false;
	}

	public static boolean isFlat(String aChord) {
		if (aChord.length() > 1) {
			return aChord.charAt(1) == 'b';
		}

		return false;
	}

	public static Set<String> getIntroChords(String line) {
		line = line.replace("Intro.:|", "");
		line = line.replace("|", "");
		return getLineChords(line);
	}

	public static Set<String> getLineChords(String line) {
		Set<String> list = new LinkedHashSet<>();

		String[] split = line.split("\\s|\\/|\\(|\\)");
		for (String s : split) {
			if (isValidChord(s)) {
				list.add(s);
			}
		}

		return list;
	}

	public static Set<FromTo> getFromToChords(Set<String> lineChords, String aTone, boolean doOrder) {
		Set<FromTo> fromToChords = new LinkedHashSet<>();

		for (String chord : lineChords) {
			if (!isValidMarker(chord)) {
				FromTo fromTo = processFromTo(chord, aTone);
				fromToChords.add(fromTo);
			}
		}

		if (doOrder) {
			fromToChords = new LinkedHashSet<>(orderFromToList(fromToChords));
		}

		return fromToChords;
	}

	public static Set<FromTo> getFromToChords(Set<String> lineChords, String aTone) {
		return getFromToChords(lineChords, aTone, true);
	}

	public static List<FromTo> orderFromToList(Set<FromTo> fromToList) {
		List<FromTo> list = new ArrayList<FromTo>(fromToList);
		list.sort((f1, f2) -> (Integer.compare(f1.getFrom().length(), f2.getFrom().length()) * -1));
		return list;
	}

	public static FromTo processFromTo(String aChord, String aTone) {
		FromTo fromTo = new FromTo();
		fromTo.setFrom(aChord);

		if (isSingleChord(aChord)) {
			int index = getSequenceByTone(aTone, aChord);
			fromTo.setTo(String.format(TONE_FORMAT, index));
		} else if (isSharp(aChord) || isFlat(aChord)) {
			String chordAux = aChord;
			if (aChord.length() > 2) {
				chordAux = chordAux.substring(0, 2);
			}

			int index = getSequenceByTone(aTone, chordAux);
			String toAux = String.format(TONE_FORMAT, index);
			toAux = aChord.replace(chordAux, toAux);
			fromTo.setTo(toAux);
		} else {
			String chordAux = aChord.substring(0, 1);
			int index = getSequenceByTone(aTone, chordAux);
			String toAux = String.format(TONE_FORMAT, index);
			toAux = aChord.replace(chordAux, toAux);
			fromTo.setTo(toAux);
		}

		fromTo.setTo(String.format("<b>%s</b>", fromTo.getTo()));

		return fromTo;
	}

	public static boolean isLineOfChords(String line) {
		String[] split = line.split("\\s+|\\/|\\(|\\)");
		for (String s : split) {
			if (!s.trim().isEmpty() && !isValidChord(s) && !isValidMarker(s)) {
				return false;
			}
		}

		return true;
	}

}
