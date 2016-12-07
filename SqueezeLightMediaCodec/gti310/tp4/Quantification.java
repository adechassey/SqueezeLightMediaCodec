package gti310.tp4;

import Model.ColorSpaceValues;

/***
 * Classe utilisée pour les opérations de quantification et déquantification
 * @author Manuel Nero, Antoine de Chassey
 */
public class Quantification {
	public static final int[][] TABLE_QY = {
			{ 16, 11, 10, 16,  24,  40,  51,  61 },
			{ 12, 12, 14, 19,  26,  58,  60,  55 },
			{ 14, 13, 16, 24,  40,  57,  69,  56 },
			{ 14, 17, 22, 29,  51,  87,  80,  62 },
			{ 18, 22, 37, 56,  68, 109, 103,  77 },
			{ 24, 35, 55, 64,  81, 104, 113,  92 },
			{ 49, 64, 78, 87, 103, 121, 120, 101 },
			{ 72, 92, 95, 98, 112, 100, 103,  99 }
	};

	public static final int[][] TABLE_QCbCr = {
			{ 17, 40, 40, 95,  95,  95,  95,  95 },
			{ 40, 40, 40, 95,  95,  95,  95,  95 },
			{ 40, 40, 40, 95,  95,  95,  95,  95 },
			{ 40, 40, 95, 95,  95,  95,  95,  95 },
			{ 95, 95, 95, 95,  95,  95,  95,  95 },
			{ 95, 95, 95, 95,  95,  95,  95,  95 },
			{ 95, 95, 95, 95,  95,  95,  95,  95 },
			{ 95, 95, 95, 95,  95,  95,  95,  95 }	
	};

	/***
	 * Appelle doTreatment pour exécuter une quantification.
	 * @param blocs L'instance contenant les valeurs sur les espaces couleurs.
	 * @param facteurQualite Le facteur de qualité
	 * O(N^3) via doTreatment
	 */
	public static void applyQuantification(ColorSpaceValues blocs, int facteurQualite) {
		doTreatment(blocs, facteurQualite, false);
	}

	/***
	 * Appelle doTreatment pour exécuter une déquantification.
	 * @param blocs L'instance contenant les valeurs sur les espaces couleurs.
	 * @param facteurQualite Le facteur de qualité
	 * O(N^3) via doTreatment
	 */
	public static void dequantification(ColorSpaceValues blocs, int facteurQualite) {
		doTreatment(blocs, facteurQualite, true);
	}
	
	/***
	 * Exécute l'opération de quantification/déquantification.
	 * @param blocs L'instance contenant les valeurs sur les espaces couleurs.
	 * @param facteurQualite Le facteur de qualité
	 * @param dequantification Indique si on procède à une opération de déquantification.
	 * @return
	 * O(N^3)
	 */
	private static void doTreatment(ColorSpaceValues blocs, int facteurQualite, boolean dequantification) {
		// Si facteur de qualite de 100 on passe.
		if (facteurQualite == 100) return;

		int nbBlocs = blocs.getNbBlocs();
		int height = blocs.getblocHeight();
		int width = blocs.getblocWidth();

		int[][][] valeursBloc = new int[blocs.getColorSpaces()][height][width];
		
		int valueY = 0;
		int valueCb = 0;
		int valueCr = 0;
		double alpha = getAlphaValue(facteurQualite);
		
		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for (int u = 0; u < height; ++u) {
				for (int v = 0; v < width; ++v) {
					if (!dequantification) {
						valueY  = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Y, u, v)  / (alpha * TABLE_QY[u][v]));
						valueCb = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Cb, u, v) / (alpha * TABLE_QCbCr[u][v]));
						valueCr = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Cr, u, v) / (alpha * TABLE_QCbCr[u][v]));
					}
					else {
						valueY  = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Y, u, v)  * (alpha * TABLE_QY[u][v]));
						valueCb = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Cb, u, v) * (alpha * TABLE_QCbCr[u][v]));
						valueCr = (int) Math.round(blocs.getBlocColorValue(indexBloc, Main.Cr, u, v) * (alpha * TABLE_QCbCr[u][v]));
					}
					
					valeursBloc[Main.Y][u][v]  = valueY;
					valeursBloc[Main.Cb][u][v] = valueCb;
					valeursBloc[Main.Cr][u][v] = valueCr;
				}
			}
			
			// On affecte les valeurs DCT/iDCT pour le bloc courant.
			blocs.setBlocSpaceColorValues(indexBloc, Main.Y, valeursBloc[Main.Y]);
			blocs.setBlocSpaceColorValues(indexBloc, Main.Cb, valeursBloc[Main.Cb]);
			blocs.setBlocSpaceColorValues(indexBloc, Main.Cr, valeursBloc[Main.Cr]);
			valeursBloc = new int[blocs.getColorSpaces()][height][width];
		}
	}
	
	/***
	 * Calcul de la valeur alpha.
	 * @param facteurQualite Le facteur de qualité.
	 * @return La valeur alpha. 
	 * O(1)
	 */
	private static double getAlphaValue(int facteurQualite) {
		double value = 0;
		
		if (facteurQualite >= 1 && facteurQualite <= 50) {
			value = (double)(50 / facteurQualite);
		}
		else if (facteurQualite <= 99)
		{
			value = (double)(200 - 2 * facteurQualite) / 100;
		}
		
		return value;
	}
}
