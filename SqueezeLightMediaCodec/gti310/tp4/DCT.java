package gti310.tp4;

public class DCT {
	/***
	 * 
	 * @param bloc Représente f(i,j)
	 * @return bloc représentant F(u,v)
	 * O(N^5)
	 */
	public static int[][][][] applyDCT(int[][][][] blocs) {
		// Utilisation de double donne valeurs comme dans l'exemple du pdf du cours.
		int nbBlocs = blocs.length;
		// TODO Const 8x8
		int height = blocs[0][Main.Y].length;
		int width = blocs[0][Main.Y][0].length;
		int[][][][] resultat = new int[nbBlocs][Main.COLOR_SPACE_SIZE][height][width];
		
		double valeurDCT_Y = 0;
		double valeurDCT_Cb = 0;
		double valeurDCT_Cr = 0;
		
		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for(int u = 0; u < height; u++) {
				for(int v = 0; v < width; v++) {
			
					for(int i = 0; i < height; i++) {
						for(int j = 0; j < width; j++) {
							// TODO refactor utiliser appel de une fonction seulement au lieu de 3 lignes de code
							valeurDCT_Y += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Y][i][j];
							valeurDCT_Cb += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Cb][i][j];
							valeurDCT_Cr += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Cr][i][j];
						}
					}
					
					valeurDCT_Y  *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
					valeurDCT_Cb *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
					valeurDCT_Cr *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
					
					resultat[indexBloc][Main.Y][u][v] = (int)Math.round (valeurDCT_Y);
					resultat[indexBloc][Main.Cb][u][v] = (int)Math.round (valeurDCT_Cb);
					resultat[indexBloc][Main.Cr][u][v] = (int)Math.round (valeurDCT_Cr);
					
					valeurDCT_Y = 0;
					valeurDCT_Cb = 0;
					valeurDCT_Cr = 0;
				}
			}
		}
		
		return resultat;
	}
	
	public static int[][][][] inverseDCT(int[][][][] blocs) { 
		int nbBlocs = blocs.length;
		int height = blocs[0][Main.Y].length;
		int width = blocs[0][Main.Y][0].length;
		int[][][][] resultat = new int[nbBlocs][Main.COLOR_SPACE_SIZE][height][width];
		
		double valeur_Y = 0;
		double valeur_Cb = 0;
		double valeur_Cr = 0;
		
		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
	
					for(int u = 0; u < height; u++) {
						for(int v = 0; v < width; v++) {
							valeur_Y  += ((valeurCoefficient(u) * valeurCoefficient(v)) / 4) * Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Y][u][v];
							valeur_Cb += ((valeurCoefficient(u) * valeurCoefficient(v)) / 4) * Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Cb][u][v];
							valeur_Cr += ((valeurCoefficient(u) * valeurCoefficient(v)) / 4) * Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs[indexBloc][Main.Cr][u][v];
						}
					}
					
					resultat[indexBloc][Main.Y][i][j] = (int)Math.round (valeur_Y);
					resultat[indexBloc][Main.Cb][i][j] = (int)Math.round (valeur_Cb);
					resultat[indexBloc][Main.Cr][i][j] = (int)Math.round (valeur_Cr);
					
					valeur_Y = 0;
					valeur_Cb = 0;
					valeur_Cr = 0;
				}
			}
		}
		
		return resultat;
	}
	
	private static double valeurCoefficient(int indexUV) {
		return indexUV == 0 ? 1/Math.sqrt(2) : 1;
	}
	
	/*
	public static applyiDCT() {
		
	}
	*/
}
