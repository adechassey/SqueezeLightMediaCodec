package gti310.tp4;

import Model.ColorSpaceValues;

/***
 * 
 * @author 
 *
 */
public class ImgPartition {
	/***
	 * Partionner l'image pour obtenir des blocs 8x8
	 * @param YCbCr
	 * @return
	 * O(N^3)
	 */
	public static void partitionImage(ColorSpaceValues YCbCr) {
		YCbCr.initializeBlocs();
		doTreatments(YCbCr, true);
	}
	
	/*
	 * 
	 * O(N^3)
	 */
	public static void mergeImageFromBlocs(ColorSpaceValues YCbCr) {
		YCbCr.initializeColorValues();
		doTreatments(YCbCr, false);
	}
	
	/*
	 * O(N^3)
	 */
	private static void doTreatments(ColorSpaceValues csv, boolean isPartitionOperation) {
		// TODO rapport fin de TEST : Nous avons creer les partitions de l'image en images separer pour analyser le resultat.
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
