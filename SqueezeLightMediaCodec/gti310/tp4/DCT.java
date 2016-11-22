package gti310.tp4;

public class DCT {
	/***
	 * 
	 * @param bloc Représente f(i,j)
	 * @param facteurQualite  0 à 100 de la plus basse qualité (0) à qualité sans perte (100).
	 * @return bloc représentant F(u,v)
	 * O(N^4)
	 */
	public static int[][] applyDCT(int[][] bloc, int facteurQualite) {
		// int vs double ?
		int[][] resultat = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];
		double valeurDCT = 0; 
		
		for(int u = 0; u < Main.BLOCK_SIZE; u++) {
			for(int v = 0; v < Main.BLOCK_SIZE; v++) {
		
				for(int i = 0; i < Main.BLOCK_SIZE; i++) {
					for(int j = 0; j < Main.BLOCK_SIZE; j++) {
						valeurDCT += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * bloc[i][j];
					}
				}
				
				valeurDCT *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
				
				// On arrondi ??
				resultat[u][v] = (int)Math.round (valeurDCT);
				valeurDCT = 0;
			}
		}
		
		return resultat;
	}
	
	private static double valeurCoefficient(int indexUV) {
		return indexUV == 0 ? 1/Math.sqrt(2) : 0;
	}
	
	/*
	public static applyiDCT() {
		
	}
	*/
}
