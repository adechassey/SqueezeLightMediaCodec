package gti310.tp4;

import Model.ColorSpaceValues;

public class DCT {
	/***
	 * 
	 * @param bloc Représente f(i,j)
	 * @return bloc représentant F(u,v)
	 * O(N^5) voir doTreatment
	 */
	public static void applyDCT(ColorSpaceValues blocs) {
		doTreatment(blocs, false);
	}
	
	/***
	 * 
	 * @param blocs
	 * @return
	 * O(N^5) voir doTreatment
	 */
	public static void inverseDCT(ColorSpaceValues blocs) { 
		doTreatment(blocs, true);
	}
	
	/***
	 * 
	 * @param blocs
	 * @param inverseOperation
	 * @return
	 * O(N^5)
	 */
	private static void doTreatment(ColorSpaceValues blocs, boolean inverseOperation) {
		// TODO Const 8x8
		int nbBlocs = blocs.getNbBlocs();
		int height = blocs.getblocHeight();
		int width = blocs.getblocWidth();
		int[][][][] resultat = new int[nbBlocs][blocs.getColorSpaces()][height][width];
		
		double valeur_Y = 0;
		double valeur_Cb = 0;
		double valeur_Cr = 0;
		
		// Dans le cas d'un traitement inverse, en se référant à l'énoncé de laboratoire l'index u est représenté par i et v par j, vice-versa. 
		// On conserve la même notation pour les index que la transformer DCT pour regrouper les deux opération (DCT et iDCT) à l'intérieur de cette même fonction.
		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for(int u = 0; u < height; u++) {
				for(int v = 0; v < width; v++) {
			
					for(int i = 0; i < height; i++) {
						for(int j = 0; j < width; j++) {
							if (!inverseOperation) {
								valeur_Y += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) *  blocs.getBlocColorValue(indexBloc, Main.Y, i, j);
								valeur_Cb += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs.getBlocColorValue(indexBloc, Main.Cb, i, j);
								valeur_Cr += Math.cos( ((2 * i + 1) * u * Math.PI) / 16) * Math.cos( ((2 * j + 1) * v * Math.PI) / 16 ) * blocs.getBlocColorValue(indexBloc, Main.Cr, i, j);
							}
							else {
								valeur_Y  += ((valeurCoefficient(i) * valeurCoefficient(j)) / 4) * Math.cos( ((2 * u + 1) * i * Math.PI) / 16) * Math.cos( ((2 * v + 1) * j * Math.PI) / 16 ) * blocs.getBlocColorValue(indexBloc, Main.Y, i, j);
								valeur_Cb += ((valeurCoefficient(i) * valeurCoefficient(j)) / 4) * Math.cos( ((2 * u + 1) * i * Math.PI) / 16) * Math.cos( ((2 * v + 1) * j * Math.PI) / 16 ) * blocs.getBlocColorValue(indexBloc, Main.Cb, i, j);
								valeur_Cr += ((valeurCoefficient(i) * valeurCoefficient(j)) / 4) * Math.cos( ((2 * u + 1) * i * Math.PI) / 16) * Math.cos( ((2 * v + 1) * j * Math.PI) / 16 ) * blocs.getBlocColorValue(indexBloc, Main.Cr, i, j);
							}
						}
					}
					
					if (!inverseOperation) {
						valeur_Y  *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
						valeur_Cb *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
						valeur_Cr *= ((valeurCoefficient(u) * valeurCoefficient(v)) / 4);
					}
					
					resultat[indexBloc][Main.Y][u][v] = (int)Math.round (valeur_Y);
					resultat[indexBloc][Main.Cb][u][v] = (int)Math.round (valeur_Cb);
					resultat[indexBloc][Main.Cr][u][v] = (int)Math.round (valeur_Cr);
					
					valeur_Y = 0;
					valeur_Cb = 0;
					valeur_Cr = 0;
				}
			}
		}
		
		blocs.setBlocsColorValues(resultat);
	}
	
	/***
	 * 
	 * @param indexUV
	 * @return
	 * O(1)
	 */
	private static double valeurCoefficient(int indexUV) {
		return indexUV == 0 ? 1/Math.sqrt(2) : 1;
	}
}
