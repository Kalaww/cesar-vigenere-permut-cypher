package securiteL3;

import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.Character;
import java.lang.Exception;

public class MethodePermutation implements ChiffreDechiffre{
	
	
	private HashMap<Character, Character> permutations;

	public MethodePermutation(String s){
		initPermutations(s);
	}

	private void initPermutations(String s){
		permutations = new HashMap<Character, Character>();
		char a = 'a';
		for(int i = 0; i < s.length(); i++){
			permutations.put(new Character(a), new Character(s.charAt(i)));
			a++;
		}
	}

	public char chiffrer(char c){
		return permutations.get(new Character(c)).charValue();
	}

	public char dechiffrer(char c) throws Exception{
		for(Entry<Character, Character> entry : permutations.entrySet()){
			if(entry.getValue().charValue() == c)
				return entry.getKey().charValue();
		}

		throw new Exception("Aucune permutation pour le caractere "+c);
	}

	

	/**
	 * Verifie si le parametre de permutation est valide pour la methode avec permutation
	 * @param  i le parametre de permutation
	 */
	public static boolean isValidKey(String s){
		return s != null && s.length() == 26;
	}
}