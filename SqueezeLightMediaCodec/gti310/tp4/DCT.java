package gti310.tp4;

import Model.ColorSpaceValues;

/***
 * Classe qui s'occupe du traitement DCT et iDCT.
 * @author Manuel Nero, Antoine de Chassey
 */
public class DCT {
	/***
	 * S'occupe d'appeller la fonction doTreatment pour exécuter l'opération DCT.
	 * @param blocs Représente f(i,j), l'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^5) voir doTreatment
	 */
	public static void applyDCT(ColorSpaceValues blocs) {
		doTreatment(blocs, false);
	}
	
	/***
	 * S'occupe d'appeller la fonction doTreatment pour exécuter l'opération iDCT.
	 * @param blocs Représente F(u,v), l'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^5) voir doTreatment
	 */
	public static void inverseDCT(ColorSpaceValues blocs) { 
		doTreatment(blocs, true);
	}
	
	/***
	 * S'occupe d'exécuter le traitement DCT ou iDCT.
	 * @param blocs Représente f(i,j), l'instance contenant les valeurs sur les espaces couleurs.
	 * @param inverseOperation Indique si on application l'opération DCT ou iDCT.
	 * O(N^5)
	 */
	private static void doTreatment(ColorSpaceValues blocs, boolean inverseOperation) {
		int nbBlocs = blocs.getNbBlocs();
		int height = blocs.getblocHeight();
		int width = blocs.getblocWidth();
		int[][][] valeursBloc = new int[blocs.getColorSpaces()][height][width];
		
		double valeur_Y = 0;
		double valeur_Cb = 0;
		double valeur_Cr = 0;
		
		// Dans le cas d'un traitement inverse, en se référant à l'énoncé de laboratoire l'index u est représenté par i et v par j, vice-versa. 
		// On conserve la même notation pour les index que la transformer DCT en regroupant les deux opération (DCT et iDCT) à l'intérieur de cette même fonction.
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
					
					valeursBloc[Main.Y][u][v] = (int)Math.round (valeur_Y);
					valeursBloc[Main.Cb][u][v] = (int)Math.round (valeur_Cb);
					valeursBloc[Main.Cr][u][v] = (int)Math.round (valeur_Cr);
					
					valeur_Y = 0;
					valeur_Cb = 0;
					valeur_Cr = 0;
				}
			}
			
			// On affecte les valeurs DCT/iDCT pour le bloc courrant en évitant de créer un tableau temporaire pour contenir les valeurs.
			blocs.setBlocSpaceColorValues(indexBloc, Main.Y, valeursBloc[Main.Y]);
			blocs.setBlocSpaceColorValues(indexBloc, Main.Cb, valeursBloc[Main.Cb]);
			blocs.setBlocSpaceColorValues(indexBloc, Main.Cr, valeursBloc[Main.Cr]);
			valeursBloc = new int[blocs.getColorSpaces()][height][width];
		}
	}
	
	/***
	 * Obtenir la valeur du coefficient.
	 * @param indexUV La position de l'index U ou V.
	 * @return La valeur du coefficient.
	 * O(1)
	 */
	private static double valeurCoefficient(int indexUV) {
		return indexUV == 0 ? 1/Math.sqrt(2) : 1;
	}
}
