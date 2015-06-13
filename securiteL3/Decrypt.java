package securiteL3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;


public class Decrypt{

	/**
	 * Tableau des frequences par ordre decroissant de la langue francaise
	 */
	public static char frequences[] = {'e', 's', 'a', 'i', 't', 'n', 'r', 'u', 'l', 'o', 'd', 'c', 'p', 'm', 'v', 'q', 'f', 'b', 'g', 'h', 'j', 'x', 'y', 'z', 'w', 'k'};
	
	public static void main(String[] args){
		if(args.length != 2 && args.length != 3 && args.length != 4){
			System.err.println(usage());
			System.exit(1);
		}

		Lexique.init();

		BufferedReader buffer = Util.open(args[1]);
		ChiffreDechiffre methode = null;

		long startTime = System.currentTimeMillis();

		if(args[0].equals("c")){
			if(args[2].equals("1") && args.length == 4){
				int decalage = DecryptCesar.methodeMot(buffer, args[3]);
				if(decalage < 0){
					System.err.println("Aucun décalage n'a été trouvé.");
					System.exit(0);
				}
				System.err.println("TROUVE "+decalage);
				methode = new MethodeCesar(decalage);
			}else if(args[2].equals("2")){
				int decalage = DecryptCesar.methodeFrequence(buffer);
				if(decalage < 0){
					System.err.println("Aucun décalage n'a été trouvé.");
					System.exit(0);
				}
				System.err.println("TROUVE "+decalage);
				methode = new MethodeCesar(decalage);
			}else if(args[2].equals("3")){
				int decalage = DecryptCesar.methodeBrute(buffer);
				if(decalage < 0){
					System.err.println("Aucun décalage n'a été trouvé.");
					System.exit(0);
				}
				System.err.println("TROUVE "+decalage);
				methode = new MethodeCesar(decalage);
			}else{
				System.err.println(usage());
				System.exit(1);	
			}

		}else if(args[0].equals("p") && args.length == 2){
			String clef = DecryptPermutation.methodeFrequence(buffer);
			if(clef == null || clef.length() != 26){
				System.err.println("Aucune permutation valide n'a été trouvé.");
				System.exit(0);
			}
			System.err.println("TROUVE "+clef);
			methode = new MethodePermutation(clef);
		}else if(args[0].equals("v") && args.length == 3){
			int lg = Integer.parseInt(args[2]);
			String clef = DecryptVigenere.methodeBrute(buffer, lg);
			if(clef == null){
				System.err.println("Aucune solution valide n'a été trouvé.");
				System.exit(0);
			}
			System.err.println("TROUVE "+clef);
			methode = new MethodeVigenere(clef);
		}else{
			System.err.println("Methode de decryptage inconnue : "+args[0]);
			System.err.println(usage());
			System.exit(1);
		}

		long endTime = System.currentTimeMillis();

		try{
			buffer.close();
		}catch (IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}

		buffer = Util.open(args[1]);
		ChiffreDechiffreFichier.dechiffre(buffer, methode);
		System.err.println("Temps : "+ (endTime - startTime) + "ms");
	}

	/**
	 * Usage de la ligne de commande
	 * @return usage
	 */
	private static String usage(){
		return "usage: Decrypt [c,p,v] [fichier] [options supplémentaires]";
	}

	/**
	 * Recupere une liste des mots contenu dans un fichier
	 * @param  fichier nom du fichier
	 * @return         liste des mots
	 */
	public static ArrayList<String> getMots(BufferedReader br){
		ArrayList<String> mots = new ArrayList<String>();
		String ligne = null;
		String mot = "";

		try{
			ligne = br.readLine();
		}catch (IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}

		while(ligne != null){
			ligne = Util.replaceAccent(ligne);
			for(int i = 0; i < ligne.length(); i++){
				char c = ligne.charAt(i);
				if(c == ' ' || c == ',' || c == '.' || c == ';' || c == ':'){
					if(mot.length() > 0) mots.add(mot);
					mot = "";
				}else if(i == 0 && mot.length() > 0){
					mots.add(mot);
					mot = ""+c;
				}else
					mot += c;
			}

			try{
				ligne = br.readLine();
			}catch (IOException e){
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}

		return mots;
	}
}