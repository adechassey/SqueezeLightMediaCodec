package gti310.tp4;

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
	 * 
	 * @param blocs
	 * @param facteurQualite
	 * @return
	 * O(N^3)
	 */
	public static int[][][][] applyQuantification(int [][][][] blocs, int facteurQualite) {
		// Si facteur de qualite == 100 on passe .
		if (facteurQualite == 100) return blocs;
		
		int nbBlocs = blocs.length;
		int height = blocs[0][Main.Y].length;
		int width = blocs[0][Main.Y][0].length;
		int[][][][] resultat = new int[nbBlocs][Main.COLOR_SPACE_SIZE][height][width];

		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for (int u = 0; u < height; ++u) {
				for (int v = 0; v < width; ++v) {
					// TODO one function instead of 3 lines
					double alpha = getAlphaValue(facteurQualite);
					resultat[indexBloc][Main.Y][u][v]  = (int) Math.round(blocs[indexBloc][Main.Y][u][v] / (alpha * TABLE_QY[u][v]));
					resultat[indexBloc][Main.Cb][u][v] = (int) Math.round(blocs[indexBloc][Main.Cb][u][v] / (alpha * TABLE_QCbCr[u][v]));
					resultat[indexBloc][Main.Cr][u][v] = (int) Math.round(blocs[indexBloc][Main.Cr][u][v] / (alpha * TABLE_QCbCr[u][v]));
				}
			}
		}
		
		return resultat;
	}

	/***
	 * 
	 * @param blocs
	 * @param facteurQualite
	 * @return
	 * O(N^3)
	 */
	public static int[][][][] dequantification(int [][][][] blocs, int facteurQualite) {
		// Si facteur de qualite == 100 on passe .
		if (facteurQualite == 100) return blocs;
		
		int nbBlocs = blocs.length;
		int height = blocs[0][Main.Y].length;
		int width = blocs[0][Main.Y][0].length;
		int[][][][] resultat = new int[nbBlocs][Main.COLOR_SPACE_SIZE][height][width];

		for (int indexBloc = 0; indexBloc < nbBlocs; ++indexBloc) {
			for (int u = 0; u < height; ++u) {
				for (int v = 0; v < width; ++v) {
					// TODO one function instead of 3 lines
					double alpha = getAlphaValue(facteurQualite);
					// TODO one function for both quantification / dequantification (same code basically).
					resultat[indexBloc][Main.Y][u][v]  = (int) Math.round(blocs[indexBloc][Main.Y][u][v] * (alpha * TABLE_QY[u][v]));
					resultat[indexBloc][Main.Cb][u][v] = (int) Math.round(blocs[indexBloc][Main.Cb][u][v] * (alpha * TABLE_QCbCr[u][v]));
					resultat[indexBloc][Main.Cr][u][v] = (int) Math.round(blocs[indexBloc][Main.Cr][u][v] * (alpha * TABLE_QCbCr[u][v]));
				}
			}
		}
		
		return resultat;
	}
	
	/***
	 * 
	 * @param facteurQualite
	 * @return
	 * O(1)
	 */
	private static double getAlphaValue(int facteurQualite) {
		// TODO ou ? conditionnel
		double value = 0;
		
		// voir double dans equation , conversion explicite
		if (facteurQualite >= 1 && facteurQualite <= 50) {
			value = (double)(50 / facteurQualite);
		}
		else if (facteurQualite <= 99)
		{
			value = (double)(200 - 2 * facteurQualite) / 100;
		}
		else 
		{
			//erreur 
		}
		
		return value;
	}
}
