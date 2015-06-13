package securiteL3;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DecryptPermutation{
	
	public static String methodeFrequence(BufferedReader buffer){
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


		HashMap<String, char[]> map = new HashMap<String, char[]>();
		for(String mot : mots){
			char[] array = new char[mot.length()];
			for(int i = 0; i < array.length; i++)
				array[i] = '.';
			map.put(new String(mot), array);
		}

		Stack<Character> pile1 = new Stack<Character>();
		for(int i = freq.length -1; i >= 0; i--)
			pile1.push(freq[i]);

		char[] clef = new char[26];
		for(int i = 0; i < clef.length; i++)
			clef[i] = '.';

		String result = permutation(map, clef, 0, pile1, new Stack<Character>());
		System.err.println("RES "+result);
		for(char c = 'a'; c <= 'z'; c++){
			if(!result.contains(""+c))
				result = result.replaceFirst("\\.", ""+c);
		}

		System.err.println("RES "+result);

		return getPermutationClef(result, "abcdefghijklmnopqrstuvwxyz");

	}

	/**
	 * Permutation recursive qui teste si une permutation d'une lettre est valide en verifiant dans le lexique
	 * @param  map
	 * @param  clef [description]
	 * @param  index etape d'ou en est la recursion dans le tableau des frequences
	 * @param pile1 pile des prochaines lettres a tester
	 * @param pile2 pile des lettres a retester
	 * @return la clef
	 */	
	private static String permutation(HashMap<String, char[]> map, char[] clef, int index, Stack<Character> pile1, Stack<Character> pile2){
		if(pile1.isEmpty()){
			return new String(clef);
		}

		char courant = pile1.pop();
		boolean valide = true;

		//System.err.println("ETAPE "+index+" "+courant+" clef: "+(new String(clef))+" "+pile1+" "+pile2);
		for(String mot : map.keySet()){
			char[] array = map.get(mot);
			for(int i = 0; i < mot.length(); i++){
				if(mot.charAt(i) == courant)
					array[i] = Decrypt.frequences[index];
			}

			//System.err.println(" "+mot+" "+(new String(array)));
			if(!Lexique.exist(new String(array))){
				//System.err.println("Echec "+(new String(array)));
				valide = false;
				break;
			}
		}


		if(!valide){
			for(String mot : map.keySet()){
				char[] array = map.get(mot);
				for(int i = 0; i < array.length; i++){
					if(array[i] == Decrypt.frequences[index])
						array[i] = '.';
				}
			}

			pile2.push(courant);

			return permutation(map, clef, index, pile1, pile2);
		}else{
			clef[courant - 'a'] = Decrypt.frequences[index];
			while(!pile2.isEmpty()){
				pile1.push(pile2.pop());
			}

			return permutation(map, clef, index+1, pile1, pile2);
		}
	}

	/**
	 * Recupere la clef de permutation selon un modele d'ordre alphabetique donne
	 * @param  modelS        modele d'ordre alphabetique
	 * @param  permutationsS la permutation
	 * @return               la nouvelle clef selon l'ordre alphabetique donne
	 */
	private static String getPermutationClef(String modelS, String permutationsS){
		char[] model = modelS.toCharArray();
		char[] permutations = permutationsS.toCharArray();
		char[] res = new char[26];

		for(char i = 'a'; i <= 'z'; i++){
			for(int j = 0; j < model.length; j++){
				if(i == (char)model[j]){
					res[i-'a'] = permutations[j];
				}
			}
		}

		return new String(res);
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