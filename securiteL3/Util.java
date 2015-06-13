package securiteL3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;

public class Util{
	
	/**
	 * Supprime les accents et mets tout en minuscule
	 * @param  s le mot a editer
	 * @return   le nouveau mot
	 */
	public static String replaceAccent(String s){
		s = s.replaceAll("[èéêëÈÉÊË]","e");
		s = s.replaceAll("[ûùÛÙ]","u");
		s = s.replaceAll("[ïîÏÎ]","i");
		s = s.replaceAll("[àâÀÂ]","a");
		s = s.replaceAll("[ôÔ]","o");

		return s.toLowerCase();
	}

	/**
	 * Ouvre un buffer de lecture sur le nom de fichier en argument
	 * @param  name nom de fichier
	 * @return      le buffer de lecture
	 */
	public static BufferedReader open(String name){
		FileReader f = null;
		try{
			f = new FileReader(new File(name));
		}catch(FileNotFoundException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return new BufferedReader(f);
	}
}