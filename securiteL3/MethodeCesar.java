package securiteL3;

public class MethodeCesar implements ChiffreDechiffre{
	
	
	/**
	 * Valeur du decalage de Cesar
	 */
	private int decalage;

	public MethodeCesar(int d){
		decalage = d;
	}

	public char chiffrer(char c){
		return decaler(c, true);
	}

	public char dechiffrer(char c){
		return decaler(c, false);
	}

	/**
	 * Donne le caractere correspondant au decalage
	 * @param  c    Le caractere a decaler
	 * @param  sens Le sens du decalage : true pour chiffrer, false pour dechiffrer
	 * @return      le caractere decale correspondant
	 */
	private char decaler(char c, boolean sens){
		int pos = (int)(c - 'a');
		if(sens){
			if(pos+decalage > 25){
				return (char)((int)'a' + (pos+decalage)%26);
			}
			return (char)((int)'a' + pos+decalage);
		}else{
			if(pos-decalage < 0){
				return (char)((int)'z' + (pos-decalage)%26 +1);
			}
			return (char)((int)'a' + pos-decalage);
		}
	}
	
	public void setDecalage(int d){
		decalage = d;
	}

	/**
	 * Verifie si le parametre de decalage est valide pour la methode Cesar
	 * @param  i le parametre de decalage
	 */
	public static boolean isValidKey(int i){
		return i >= 0 && i < 26;
	}
}