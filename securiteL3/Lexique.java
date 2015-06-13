package securiteL3;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Lexique{

	/**
	 * Racine de l'arbre : les noeuds fils de la racine
	 */
	public static HashMap<Character, Noeud> mots;

	/**
	 * Initialise l'arbre de recherche avec le fichier lexique.txt
	 */
	public static void init(){
		mots = new HashMap<Character, Noeud>();
		BufferedReader br = null;

		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("lexique.txt"), "ISO-8859-1"));
		}catch (FileNotFoundException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}catch (UnsupportedEncodingException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}

		String mot;

		try{
			while((mot = br.readLine()) != null){
				Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
				String t = Normalizer.normalize(mot, Normalizer.Form.NFD);
				mot = pattern.matcher(t).replaceAll("").toLowerCase();
				char debut = mot.charAt(0);

				Noeud n = mots.get(debut);
				if(n == null){
					n = new Noeud();
					mots.put(new Character(debut), n);
				}

				n.add(mot.substring(1));
			}
		}catch (Exception e){
			System.err.println(e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Test un mot est dans l'arbre
	 * @param  s le mot a tester
	 * @return   boolean
	 */
	public static boolean exist(String s){
		if(s.length() == 0)
			return false;

		char k = s.charAt(0);

		if(k == '.'){
			for(Entry<Character, Noeud> entry : mots.entrySet()){
				Noeud n = entry.getValue();
				if(n.exist(s.substring(1)))
					return true;
			}
			return false;
		}else{
			Noeud n = mots.get(k);
			if(n == null)
				return false;

			return n.exist(s.substring(1));
		}
	}
}

/**
 * Structure de noeud pour stocker l'ensemble des mots du lexique
 */
class Noeud{

	/**
	 * Les fils
	 */
	private HashMap<Character, Noeud> fils;

	/**
	 * Vrai si le noeud courant est le chemin vers un mot existant
	 */
	public boolean mot;

	/**
	 * Constructeur
	 */
	public Noeud(){
		fils = new HashMap<Character, Noeud>();
		mot = false;
	}

	/**
	 * Ajoute un mot dans le sous arbre a partir de ce noeud
	 * @param s le mot
	 */
	public void add(String s){
		if(s.length() == 0){
			mot = true;
			return;
		}

		char k = s.charAt(0);

		Noeud n = fils.get(k);
		if(n == null){
			n = new Noeud();
			fils.put(new Character(k), n);
		}

		n.add(s.substring(1));
	}

	/**
	 * Teste si le mot existe dans le sous arbre du noeud
	 * @param  s le mot a tester
	 * @return   boolean
	 */
	public boolean exist(String s){
		if(s.length() == 0)
			return mot;

		char k = s.charAt(0);

		if(k == '.'){
			for(Entry<Character, Noeud> entry : fils.entrySet()){
				Noeud n = entry.getValue();
				if(n.exist(s.substring(1)))
					return true;
			}
			return false;
		}else{
			Noeud n = fils.get(k);
			if(n == null)
				return false;

			return n.exist(s.substring(1));
		}
	}
}