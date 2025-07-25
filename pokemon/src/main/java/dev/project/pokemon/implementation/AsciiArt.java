package dev.project.pokemon.implementation;

public class AsciiArt {
	public static String getStartMenu() {
//		Using ANSI Shadow from https://www.asciiart.eu/text-to-ascii-art
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐                                                                 ▌
				▐ ██████╗  ██████╗ ██╗  ██╗███████╗███╗   ███╗ ██████╗ ███╗   ██╗ ▌
				▐ ██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝████╗ ████║██╔═══██╗████╗  ██║ ▌
				▐ ██████╔╝██║   ██║█████╔╝ █████╗  ██╔████╔██║██║   ██║██╔██╗ ██║ ▌
				▐ ██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██║╚██╔╝██║██║   ██║██║╚██╗██║ ▌
				▐ ██║     ╚██████╔╝██║  ██╗███████╗██║ ╚═╝ ██║╚██████╔╝██║ ╚████║ ▌
				▐ ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝ ▌
				▐                                                                 ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				
				 P - [ Start Game ]
				 Q - [ Quit ]
				""";
	}
	public static String getPauseMenu() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐        Pause           ▌
				▐  P - [ Resume ]     	 ▌
				▐  L - [ Pokemon List ]  ▌
				▐  I - [ Inventory ]  	 ▌
				▐  Q - [ Quit ]          ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				""";
	}
	public static String getPokemonEncounter() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐                                                                                ▌
				▐  ░█▀█░█▀█░█░█░█▀▀░█▄█░█▀█░█▀█░░░█▀▀░█▀█░█▀▀░█▀█░█░█░█▀█░▀█▀░█▀▀░█▀▄░█▀▀░█▀▄░█  ▌
				▐  ░█▀▀░█░█░█▀▄░█▀▀░█░█░█░█░█░█░░░█▀▀░█░█░█░░░█░█░█░█░█░█░░█░░█▀▀░█▀▄░█▀▀░█░█░▀  ▌
				▐  ░▀░░░▀▀▀░▀░▀░▀▀▀░▀░▀░▀▀▀░▀░▀░░░▀▀▀░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀░▀▀▀░▀▀░░▀  ▌
				▐                                                                                ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				
				C - [ Catch ]
				F - [ Fight ]
				Q - [ Leave ]
				""";
	}
	public static String getCatchFailed() {
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐                                                  ▌
				▐  ░█▀▀░█▀█░▀█▀░█▀▀░█░█░░░█▀▀░█▀█░▀█▀░█░░░█▀▀░█▀▄  ▌
				▐  ░█░░░█▀█░░█░░█░░░█▀█░░░█▀▀░█▀█░░█░░█░░░█▀▀░█░█  ▌
				▐  ░▀▀▀░▀░▀░░▀░░▀▀▀░▀░▀░░░▀░░░▀░▀░▀▀▀░▀▀▀░▀▀▀░▀▀░  ▌
				▐                                                  ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				
				F - [ Fight ]
				Q - [ Leave ]
				""";
	}
	public static String getDefeatedMenu() {
//		Using Pagga Font from https://www.asciiart.eu/text-to-ascii-art
		return """
				▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
				▐                            ▌
				▐  ░█▀▄░█▀▀░█▀▀░█▀▀░█▀█░▀█▀  ▌
				▐  ░█░█░█▀▀░█▀▀░█▀▀░█▀█░░█░  ▌
				▐  ░▀▀░░▀▀▀░▀░░░▀▀▀░▀░▀░░▀░  ▌
				▐                            ▌
				▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
				""";
	}
}