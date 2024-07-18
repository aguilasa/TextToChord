/*
 * Created on 2 de fev de 2017.
 *
 * Copyright 2017 Senior Ltda. All rights reserved.
 */
package com.aguilasa.texttochord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ingmar.Aguiar
 */
public class Consts {

	private static final String[][] SEQUENCES_CHORDS = {
			{ "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B" },
			{ "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B", "C" },
			{ "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B", "C", "C#" },
			{ "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B", "C", "C#", "D" },
			{ "E", "F", "F#", "G", "G#", "A", "Bb", "B", "C", "C#", "D", "Eb" },
			{ "F", "F#", "G", "G#", "A", "Bb", "B", "C", "C#", "D", "Eb", "E" },
			{ "F#", "G", "G#", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F" },
			{ "G", "G#", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#" },
			{ "G#", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G" },
			{ "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#" },
			{ "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A" },
			{ "B", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb" } };
	public static final Map<String, List<String>> SEQUENCES = new HashMap<>();
	public static final List<String> BASIC_CHORDS = Arrays.asList(new String[] { "C", "D", "E", "F", "G", "A", "B" });
	public static final List<String> CHORDS = Arrays
			.asList("C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B");
	public static final String PATH_FILES = "/home/ingmar/github/congresso-2020/res/cifras/texto";
	public static final String[] MARKERS = new String[] { "Solo", "Solo:", "1x", "2x", "3x", "4x", "5x", "6x", "7x",
			"8x", "9x", "10x", "Passagem:", "|:", ":|", "Final|:", "Interl�dio:", "Final:" };

	private static final void loadSequences() {
		for (String[] sequences : SEQUENCES_CHORDS) {
			List<String> list = new LinkedList<>();
			for (String sequence : sequences) {
				list.add(sequence);
			}
			SEQUENCES.put(list.get(0), list);
		}
	}

	static {
		loadSequences();
	}

}
