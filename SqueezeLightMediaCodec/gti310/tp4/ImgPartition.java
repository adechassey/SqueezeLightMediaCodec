package gti310.tp4;

import Model.ColorSpaceValues;

/***
 * Classe qui s'occupe de partionner une image en blocs ou bien de reconstruire une image à partir de blocs.
 * @author Manuel Nero, Antoine de Chassey
 */
public class ImgPartition {
	/***
	 * Partionner l'image pour obtenir des blocs.
	 * @param YCbCr L'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^3)
	 */
	public static void partitionImage(ColorSpaceValues YCbCr) {
		YCbCr.initializeBlocs();
		doTreatments(YCbCr, true);
	}
	
	/*
	 * 
	 * 
	 */
	/***
	 * Reconstruire une image à partir de blocs.
	 * @param YCbCr L'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^3)
	 */
	public static void mergeImageFromBlocs(ColorSpaceValues YCbCr) {
		YCbCr.initializeColorValues();
		doTreatments(YCbCr, false);
	}
	
	/***
	 * Partionner / reconstruit une image.
	 * @param csv L'instance contenant les valeurs sur les espaces couleurs.
	 * @param isPartitionOperation Indique si on partionne/reconstruit une image.
	 * O(N^3)
	 */
	private static void doTreatments(ColorSpaceValues csv, boolean isPartitionOperation) {
		int nbBlocParLignes = csv.getImgWidth() / Main.BLOCK_SIZE;
		int nbBlocParColonnes = csv.getImgHeight() / Main.BLOCK_SIZE;
		int nbTotalBlocs = nbBlocParLignes * nbBlocParColonnes;
		
		for (int noBloc = 0; noBloc < nbTotalBlocs; ++noBloc) {
			// Determination position de lecture pour l'image.
			int imgHeightPos = (int) (Math.floor(noBloc / nbBlocParLignes) * Main.BLOCK_SIZE);
			int imgInitialWidthPos = noBloc + 1 == nbBlocParColonnes ? (noBloc * Main.BLOCK_SIZE) : (noBloc % nbBlocParColonnes) * Main.BLOCK_SIZE;
			
			// Creation bloc 8x8.
			for (int iBlocHeight = 0; iBlocHeight < Main.BLOCK_SIZE; iBlocHeight++) {
				int imgWidthPos = imgInitialWidthPos;
				
				for(int jBlocWidth = 0; jBlocWidth < Main.BLOCK_SIZE; jBlocWidth++) {
					if (isPartitionOperation) {
						// On construit les blocs à partir de l'image.
						csv.setBlocColorValue(noBloc, Main.Y,  iBlocHeight, jBlocWidth, csv.getColorValue(Main.Y,  imgHeightPos,  imgWidthPos));
						csv.setBlocColorValue(noBloc, Main.Cb, iBlocHeight, jBlocWidth, csv.getColorValue(Main.Cb, imgHeightPos, imgWidthPos));
						csv.setBlocColorValue(noBloc, Main.Cr, iBlocHeight, jBlocWidth, csv.getColorValue(Main.Cr, imgHeightPos, imgWidthPos));
					}
					else {
						// On reforme l'image à partir des blocs.
						csv.setColorValue(Main.Y,  imgHeightPos, imgWidthPos, csv.getBlocColorValue(noBloc, Main.Y,  iBlocHeight, jBlocWidth));
						csv.setColorValue(Main.Cb, imgHeightPos, imgWidthPos, csv.getBlocColorValue(noBloc, Main.Cb, iBlocHeight, jBlocWidth));
						csv.setColorValue(Main.Cr, imgHeightPos, imgWidthPos, csv.getBlocColorValue(noBloc, Main.Cr, iBlocHeight, jBlocWidth));
					}
					
					imgWidthPos++;
				}
				
				imgHeightPos++;
			}
		}
	}
}
