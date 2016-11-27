package gti310.tp4;

public class ZigZag {
	/***
	 * 
	 * Code emprunté et adapté de : https://rosettacode.org/wiki/Zig-zag_matrix#Java
	 * @param blocs
	 * @return
	 * O(N)
	 */
	public static int[] doZipZag(int [][] blocs) {
		// TODO return null pour les autres fonctions.
		// code: https://algorithm.yuanbin.me/zh-hans/problem_misc/matrix_zigzag_traversal.html
		if (blocs == null || blocs.length == 0) return null;
		int[] resultat = new int[Main.BLOCK_SIZE * Main.BLOCK_SIZE];
        
        int linePos = 1;
        int colPos = 1;
        
        for (int element = 0; element < Main.BLOCK_SIZE * Main.BLOCK_SIZE; element++)
        {
        	resultat[element] = blocs[linePos - 1][colPos - 1];

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
		
		return resultat;
	}
	
	/***
	 * 
	 * @param vecteur
	 * @return
	 * O(N)
	 */
	public static int[][] inverseZipZag(int [] vecteur) {
		if (vecteur == null || vecteur.length == 0) return null;
		int[][] resultat = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];
        
        int linePos = 1;
        int colPos = 1;
        
        for (int element = 0; element < Main.BLOCK_SIZE * Main.BLOCK_SIZE; element++)
        {
        	// TODO : regroup 2 function in one... La seule difference est l'inverse de cette ligne.
        	resultat[linePos - 1][colPos - 1] = vecteur[element];

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
		
		return resultat;

	}
	
}
