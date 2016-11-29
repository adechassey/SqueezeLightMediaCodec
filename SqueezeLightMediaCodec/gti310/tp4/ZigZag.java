package gti310.tp4;

public class ZigZag {
	/***
	 * 
	 * Code emprunté et adapté de : https://rosettacode.org/wiki/Zig-zag_matrix#Java
	 * @param blocs
	 * @return
	 * O(N) via doTreatment
	 */
	public static int[] doZipZag(int [][] blocs) {
		// TODO return null pour les autres fonctions.
		// code: https://algorithm.yuanbin.me/zh-hans/problem_misc/matrix_zigzag_traversal.html
		if (blocs == null || blocs.length == 0) return null;
		int[] resultat = new int[Main.BLOCK_SIZE * Main.BLOCK_SIZE];
        
		doTreatment(resultat, blocs, false);

		return resultat;
	}
	
	/***
	 * 
	 * @param vecteur
	 * @return
	 * O(N) via doTreatment
	 */
	public static int[][] inverseZipZag(int [] vecteur) {
		if (vecteur == null || vecteur.length == 0) return null;
		int[][] resultat = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];

		doTreatment(vecteur, resultat, true);
		
		return resultat;
	}
	
	/***
	 * 
	 * @param vecteur
	 * @param blocs
	 * @param inverseOperation
	 * O(N)
	 */
	private static void doTreatment(int [] vecteur, int [][] blocs, boolean inverseOperation) {
        int linePos = 1;
        int colPos = 1;
        
        for (int element = 0; element < Main.BLOCK_SIZE * Main.BLOCK_SIZE; element++)
        {
        	if (!inverseOperation) 
        		vecteur[element] = blocs[linePos - 1][colPos - 1];
        	else 
        		blocs[linePos - 1][colPos - 1] = vecteur[element];

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
