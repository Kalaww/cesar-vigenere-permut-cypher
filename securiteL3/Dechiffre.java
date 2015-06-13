package securiteL3;

import java.io.BufferedReader;

public class Dechiffre{

	public static void main(String[] args){
		if(args.length != 3){
			System.err.println(usage());
			System.exit(1);
		}

		ChiffreDechiffre methode = null;
		// Methode Cesar
		if(args[0].equals("c")){
			int k = Integer.parseInt(args[1]);
			if(MethodeCesar.isValidKey(k))
				methode = new MethodeCesar(k);
			else{
				System.err.println("Le decalage "+k+" n'est pas valide");
				System.exit(1);
			}
		}else if(args[0].equals("p")){
			if(MethodePermutation.isValidKey(args[1]))
				methode = new MethodePermutation(args[1]);
			else{
				System.err.println("La clef "+args[1]+" n'est pas valide");
				System.exit(1);
			}
		}else if(args[0].equals("v")){
			if(MethodeVigenere.isValidKey(args[1]))
				methode = new MethodeVigenere(args[1]);
			else{
				System.err.println("La clef "+args[1]+" n'est pas valide");
				System.exit(1);
			}
		}else{
			System.err.println("Methode de dechiffrage inconnue : "+args[0]);
			System.err.println(usage());
			System.exit(1);
		}

		BufferedReader buffer = Util.open(args[2]); 

		try{
			ChiffreDechiffreFichier.dechiffre(buffer, methode);
		}catch (Exception e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private static String usage(){
		return "usage: Dechiffre [c,p,v] [clef] [fichier]";
	}
}