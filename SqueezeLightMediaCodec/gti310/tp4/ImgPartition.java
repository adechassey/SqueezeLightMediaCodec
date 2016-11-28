package gti310.tp4;

/***
 * 
 * @author 
 *
 */
public class ImgPartition {
	/***
	 * 
	 * @param YCbCr
	 * @return
	 * O(N^3)
	 */
	public static int[][][][] partitionImage(int[][][] YCbCr) {
		// TODO centralise ces info, utilise dans reverse.
		int height = YCbCr[Main.Y].length;
		int width = YCbCr[Main.Y][0].length;
		int nbBlocParLignes = width / Main.BLOCK_SIZE;
		int nbBlocParColonnes = height / Main.BLOCK_SIZE;
		int nbTotalBlocs = nbBlocParLignes * nbBlocParColonnes;
		int[][][][] blocs = new int[nbTotalBlocs][Main.COLOR_SPACE_SIZE][Main.BLOCK_SIZE][Main.BLOCK_SIZE];
		
		doTreatments(YCbCr, blocs, height, width, true);
		
		return blocs;
	}
	
	/*
	 * 
	 * O(N^3)
	 */
	public static int[][][] mergeImageFromBlocs(int[][][][] blocs, int height, int width) {
		// TODO function to get YCbCr instance.
		int[][][] YCbCr = new int[Main.COLOR_SPACE_SIZE][height][width];
		doTreatments(YCbCr, blocs, height, width, false);
		return YCbCr;
	}
	
	/*
	 * O(N^3)
	 */
	private static void doTreatments(int[][][] YCbCr, int[][][][] blocs, int height, int width, boolean isPartitionOperation) {
		// for each bloc de 8x8, appliquer DCT sur Y.
		// On valide pas, mais il y aura toujours une image avec multiple de 8x8.
		// O(N^3)
		// Report bloc 1 par 1 , ou bien construire l'ensemble des blocs

		// Report : [choix design] lecture i et j total et determiner le bloc u et v et attribue les bons index 
				//  		[choix design] lecture par u, v, j, i et attribue les bons index.
				// 			considerer la iDCT dans choix de design , O(N^4) vs O(N^2) 
				//   		comments stockage dans blocs sont faits. -> array ou bitstream
				// fin de TEST : Nous avons creer les partitions de l'image en images separer pour analyser le resultat.
		int nbBlocParLignes = width / Main.BLOCK_SIZE;
		int nbBlocParColonnes = height / Main.BLOCK_SIZE;
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
						blocs[noBloc][Main.Y][iBlocHeight][jBlocWidth] =  YCbCr[Main.Y][imgHeightPos][imgWidthPos];
						blocs[noBloc][Main.Cb][iBlocHeight][jBlocWidth] =  YCbCr[Main.Cb][imgHeightPos][imgWidthPos];
						blocs[noBloc][Main.Cr][iBlocHeight][jBlocWidth] =  YCbCr[Main.Cr][imgHeightPos][imgWidthPos];
					}
					else {
						// On reforme l'image à partir des blocs.
						YCbCr[Main.Y][imgHeightPos][imgWidthPos] = blocs[noBloc][Main.Y][iBlocHeight][jBlocWidth];
						YCbCr[Main.Cb][imgHeightPos][imgWidthPos] = blocs[noBloc][Main.Cb][iBlocHeight][jBlocWidth];
						YCbCr[Main.Cr][imgHeightPos][imgWidthPos] = blocs[noBloc][Main.Cr][iBlocHeight][jBlocWidth];
					}
					
					imgWidthPos++;
				}
				
				imgHeightPos++;
			}
		}

	}
}
