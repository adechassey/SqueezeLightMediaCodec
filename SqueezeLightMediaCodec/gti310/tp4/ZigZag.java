package gti310.tp4;

/***
 * Classe qui s'occupe de l'opération zigzag/dezigzag sur un bloc.
 * @author Manuel Nero, Antoine de Chassey
 */
public class ZigZag {
	/***
	 * Appelle doTreatment pour exécuter l'opération de zigzag sur un bloc.
	 * @param bloc Valeurs du bloc.
	 * @return le train de bit du bloc.
	 * O(N) via doTreatment
	 */
	public static int[] doZipZag(int [][] bloc) {
		if (bloc == null || bloc.length == 0) return null;
		int[] resultat = new int[Main.BLOCK_SIZE * Main.BLOCK_SIZE];
        
		doTreatment(resultat, bloc, false);

		return resultat;
	}
	
	/***
	 * Appelle doTreatment pour exécuter l'opération de dezigzag sur un bloc.
	 * @param vecteur le train de bit du bloc.
	 * @return Valeurs du bloc.
	 * O(N) via doTreatment
	 */
	public static int[][] inverseZipZag(int [] vecteur) {
		if (vecteur == null || vecteur.length == 0) return null;
		int[][] resultat = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];

		doTreatment(vecteur, resultat, true);
		
		return resultat;
	}
	
	/***
	 * Exécute l'opération de zigzag/dezigzag.
	 * Code emprunté et adapté de : https://rosettacode.org/wiki/Zig-zag_matrix#Java
	 * @param vecteur vecteur le train de bit du bloc.
	 * @param bloc Valeurs du bloc.
	 * @param inverseOperation Indique si il s'agit d'une operation dezigzag. Operation zigzag dans le cas contraire.
	 * O(N)
	 */
	private static void doTreatment(int [] vecteur, int [][] bloc, boolean inverseOperation) {
        int linePos = 1;
        int colPos = 1;
        
        for (int element = 0; element < Main.BLOCK_SIZE * Main.BLOCK_SIZE; element++)
        {
        	if (!inverseOperation) 
        		vecteur[element] = bloc[linePos - 1][colPos - 1];
        	else 
        		bloc[linePos - 1][colPos - 1] = vecteur[element];

        	// On détermine si la position se situe dans une bande paire ou impair.
        	if ((linePos + colPos) % 2 == 0)
        	{
        		// Algorithme pour bande paire. Lecture de diagonale montante.
        		if (colPos < Main.BLOCK_SIZE)
        			colPos++;	// Direction vers la droite.
        		else
        			linePos+= 2;// Direction vers le bas.
		  
        		if (linePos > 1)
        			linePos--; 	// Ajustement vers la ligne du haut pour la lecture. 
        	}
        	else
        	{
        		// Algorithme pour bande impaire. Lecture de diagonale descendante. 
        		if (linePos < Main.BLOCK_SIZE)
        			linePos++;	// Direction vers le bas.
        		else
        			colPos+= 2;	// Direction vers la droite.
        		
        		if (colPos > 1)
        			colPos--; 	// Ajustement vers la colonne de gauche pour la lecture.
        	}
		}
	}
}
