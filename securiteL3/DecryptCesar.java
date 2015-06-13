package securiteL3;

import java.io.BufferedReader;
import java.util.ArrayList;

public class DecryptCesar{
	

	/**
	 * Recherche le bon decalage par methode brute
	 * @param  buffer buffer d'un fichier
	 * @return        le bon decalage ou -1 sinon
	 */
	public static int methodeBrute(BufferedReader buffer){
		ArrayList<String> mots = Decrypt.getMots(buffer);
		
		for(int i = 0; i < 26; i++){
			if(testDecalage(mots, i))
				return i;
		}

		return -1;
	}


	/**
	 * Recherche le bon decalage par methode de frequence
	 * @param  buffer buffer d'un fichier
	 * @return        le bon decalage ou -1 sinon
	 */
	public static int methodeFrequence(BufferedReader buffer){
		ArrayList<String> mots = Decrypt.getMots(buffer);
		int[] compt = new int[26];
		char[] freq = new char[26];

		// Calcul du nombre d'occurence de chaque lettre du texte
		for(String mot : mots){
			for(int k = 0; k < mot.length(); k++){
				int c = (int)(mot.charAt(k) - 'a');
				compt[c]++;
			}
		}

		// Creer le tableau des frequences par ordre decroissant a partir du tableau d'occurence
		for(int i = 0; i < 26; i++){
			int max = getMaxFromTab(compt);
			compt[max] = -1;
			freq[i] = (char)((int)'a' + max);
		}

		for(int i = 0; i < freq.length; i++){
			char c = freq[i];
			for(int j = 0; j < Decrypt.frequences.length; j++){
				char d = Decrypt.frequences[j];

				int decal = (d - c)%26;
				if(decal < 0)
					decal += 26;

				if(testDecalage(mots, decal))
					return decal;
			}
		}

		return -1;
	}

	/**
	 * Recherche le bon decalage par methode d'un mot connu
	 * @param  buffer buffer d'un fichier
	 * @param  clef   le mot connu
	 * @return        le bon decalage ou -1 sinon
	 */
	public static int methodeMot(BufferedReader buffer, String clef){
		ArrayList<String> mots = Decrypt.getMots(buffer);

		for(String mot : mots){
			if(mot.length() == clef.length()){
				int decal = -1;
				boolean valide = true;
				for(int i = 0; i < mot.length(); i++){
					int v = (mot.charAt(i) - clef.charAt(i))%26;
					if(v < 0)
						v += 26;

					if(decal == -1)
						decal = v;
					else{
						if(v != decal){
							valide = false;
							break;
						}
					}
				}

				if(valide && testDecalage(mots, decal))
					return decal;
			}
		}

		return -1;
	}

	/**
	 * Test si le decalage donné est valide pour l'ensemble des mots d'une liste
	 * @param  mots     Liste de mots a tester
	 * @param  decalage Decalage
	 * @return          boolean
	 */
	private static boolean testDecalage(ArrayList<String> mots, int decalage){
		String motDecoder;
		MethodeCesar methode = new MethodeCesar(decalage);

		for(String mot : mots){
			motDecoder = "";
			for(int k = 0; k < mot.length(); k++)
				motDecoder += methode.dechiffrer(mot.charAt(k));

			// System.out.println("MOT "+decalage+" : "+mot+" => "+motDecoder);

			if(!Lexique.exist(motDecoder))
				return false;
		}
		return true;
	}

	/**
	 * Recupère l'index du maximum du tableau
	 * @param  tab le tableau
	 * @return     l'index maximum
	 */
	private static int getMaxFromTab(int[] tab){
		int max = 0;

		for(int i = 0; i < tab.length; i++){
			if(tab[i] > tab[max])
				max = i;
		}
		return max;
	}
}