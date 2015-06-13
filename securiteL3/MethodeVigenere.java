package securiteL3;

public class MethodeVigenere implements ChiffreDechiffre{
	
	private int cle[];

	private int position;

	public MethodeVigenere(String motcle){
		initCle(motcle);
	}

	public char chiffrer(char c){
		if(position >= cle.length)
			position = 0;

		int newChar = (int)(c - 'a') + cle[position];
		position++;

		if(newChar > 25)
			return (char)('a' + (char)newChar%26);
		return (char)('a' + (char)newChar);
	}

	public char dechiffrer(char c){
		if(position >= cle.length)
			position = 0;

		int newChar = (int)(c - 'a') - cle[position];
		position++;

		if(newChar < 0)
			return (char)((int)'z' + newChar%26 +1);
		return (char)((int)'a' + newChar);
	}

	private void initCle(String motcle){
		position = 0;
		cle = new int[motcle.length()];

		for(int i = 0; i < cle.length; i++)
			cle[i] = (int) (motcle.charAt(i) - 'a');
	}

	/**
	 * Verifie si le mot-cle est valide pour la methode de Vigenere
	 * @param  s le mot-cle
	 */
	public static boolean isValidKey(String s){
		return s != null && s.length() > 0;
	}
}