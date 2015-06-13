package securiteL3;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Exception;

public class ChiffreDechiffreFichier{

	public static void chiffre(BufferedReader buffer, ChiffreDechiffre methode){
		operation(buffer, methode, true);
	}

	public static void dechiffre(BufferedReader buffer, ChiffreDechiffre methode){
		operation(buffer, methode, false);
	}

	private static void operation(BufferedReader buffer, ChiffreDechiffre methode, boolean sens){
		String ligne = null;
		String res;

		try{
			ligne = buffer.readLine();
		} catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
		while(ligne != null){
			ligne = Util.replaceAccent(ligne);
			res = "";
			for(int i = 0; i < ligne.length(); i++){
				if(ligne.charAt(i) == ' ')
					res += " ";
				else{
					try{
						if(sens)
							res += methode.chiffrer(ligne.charAt(i));
						else
							res += methode.dechiffrer(ligne.charAt(i));
					}catch(Exception e){
						System.err.println(e.getMessage());
						System.exit(1);
					}
				}
			}

			try{
				ligne = buffer.readLine();
			} catch(IOException e){
				System.err.println(e.getMessage());
				System.exit(1);
			}

			res += "\n";

			System.out.print(res);
		}
	}
}