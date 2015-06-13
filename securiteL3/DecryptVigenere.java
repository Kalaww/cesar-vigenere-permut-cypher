package securiteL3;

import java.io.BufferedReader;
import java.util.ArrayList;

public class DecryptVigenere{


	public static String methodeBrute(BufferedReader buffer, int longClef){
		ArrayList<String> mots = Decrypt.getMots(buffer);

		String aTester = "";

		int cpt = 0;
		for(String mot : mots){
			if(aTester.length() != 0)
				aTester += " ";
			aTester += mot;
			cpt += mot.length();
			if(cpt >= longClef)
				break;
		}

		int[] clef = new int[longClef];

		return decalageRecursif(mots, clef, 0);

	}	

	/**
	 * Recursion sur l'ensemble des cas possibles de lettre a la position index de la clef
	 * @param  mots  la liste des mots
	 * @param  clef  la clef en cours de teste
	 * @param  index l'index courant
	 * @return       le mot clef ou null sinon
	 */
	private static String decalageRecursif(ArrayList<String> mots, int[] clef, int index){
		String motClef = arrayToMot(clef);

		if(testMotClef(mots, motClef))
			return motClef;

		if(index >= clef.length)
			return null;

		for(int i = 0; i < 26; i++){
			clef[index] = i;

			String res = decalageRecursif(mots, clef, index+1);
			if(res != null)
				return res;
		}
		
		return null;
	}

	/**
	 * Convertie un tableau de decalage de Cesar en mot clef de Vigenere
	 * @param  clef le tableau de decalage
	 * @return      le mot clef
	 */
	private static String arrayToMot(int[] clef){
		String s = "";
		for(int i = 0; i < clef.length; i++)
			s += (char)((int)'a'+clef[i]);
		return s;
	}

	/**
	 * Test si le mot clef est valide pour tout les mots encode du texte
	 * @param  mots    liste des mots du texte
	 * @param  motClef mot clef
	 * @return         boolean
	 */
	private static boolean testMotClef(ArrayList<String> mots, String motClef){
		MethodeVigenere methode = new MethodeVigenere(motClef);

		String decoder;
		for(String mot : mots){
			decoder = "";
			for(int i = 0; i < mot.length(); i++)
				decoder += methode.dechiffrer(mot.charAt(i));

			if(!Lexique.exist(decoder))
				return false;
		}

		return true;
	}

}