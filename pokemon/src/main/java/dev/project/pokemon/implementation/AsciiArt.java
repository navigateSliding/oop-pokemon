package dev.project.pokemon.implementation;

public class AsciiArt {
	public String getStartMenu() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐        Pause         ▌
				▐  P - [ Start Game ]  ▌
				▐  Q - [ Quit ]        ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				""";
	}
	public String getPauseMenu() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐        Pause           ▌
				▐  P - [ Resume ]     	 ▌
				▐  L - [ Pokemon List ]  ▌
				▐  Q - [ Quit ]          ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				""";
	}
	public String getDefeatedMenu() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐                                      ▌
				▐ ░█▀▀░█▀█░█▄█░█▀▀░░░█▀█░█░█░█▀▀░█▀▄░█ ▌
				▐ ░█░█░█▀█░█░█░█▀▀░░░█░█░▀▄▀░█▀▀░█▀▄░▀ ▌
				▐ ░▀▀▀░▀░▀░▀░▀░▀▀▀░░░▀▀▀░░▀░░▀▀▀░▀░▀░▀ ▌
				▐                                      ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				""";
//		Using Pagga Font from https://www.asciiart.eu/text-to-ascii-art
	}
	public String getPokemonEncounter() {
		return """
				
				""";
	}
}