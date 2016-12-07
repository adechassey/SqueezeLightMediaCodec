package gti310.tp4;

/***
 * Classe qui s'occupe de l'opération RLC.
 * @author Manuel Nero, Antoine de Chassey
 */
public class RLC {
	/***
	 * Exécute l'opération de RLC.
	 * @param zigzag les données sous forme de train de bits pour les espaces de couleurs et les blocs.
	 * O(N^2)
	 */
	public static void applyRLC(int[][][] zigzag) {
		int runlength = 0;
		int value = 0;
		// O(Main.COLOR_SPACE_SIZE -> 3)
		for (int indexColorSpace = 0; indexColorSpace < Main.COLOR_SPACE_SIZE; ++indexColorSpace) {
			// O(N)
			for (int i = 0; i < zigzag.length; i++) {
				// O(N)
				for (int valueIndex = 1; valueIndex < Main.BLOCK_SIZE * Main.BLOCK_SIZE; ++valueIndex) {
					value = zigzag[i][indexColorSpace][valueIndex];
					if (value == 0 && valueIndex < Main.BLOCK_SIZE * Main.BLOCK_SIZE - 1) 
						++runlength;
					else
					{
						Entropy.writeAC(runlength, value);
						runlength = 0;
					}
				}
			}
		}
	}

	/***
	 * Exécute l'opération de iRLC.
	 * @param blocs les données sous forme de train de bits pour les espaces de couleurs et les blocs.
	 * O(N^3) 
	 */
	public static void inverseRLC(int[][][] blocs) {
		int vecteurSize = Main.BLOCK_SIZE * Main.BLOCK_SIZE;
		
		// O(Main.COLOR_SPACE_SIZE -> 3)
		for (int indexColorSpace = 0; indexColorSpace < Main.COLOR_SPACE_SIZE; ++indexColorSpace) {
			// O(N)
			for (int i = 0; i < blocs.length; ++i) {
				// O(Main.BLOCK_SIZE * Main.BLOCK_SIZE -> N)
				for (int valueIndex = 1; valueIndex < vecteurSize; valueIndex++) {
					int[] pair = Entropy.readAC();
					
					// Suite de zeros + valeur
					if (pair[0] != 0) 
					{
						// O(N)
						for (int zeroCpt = 0; zeroCpt < pair[0]; ++zeroCpt) {
							blocs[i][indexColorSpace][valueIndex] = 0;
							valueIndex++;
						}
						
					}
					// EOB, ajoute une suite de zeros jusqu'a la fin de la grandeur du vecteur - 1.
					else if (pair[0] == 0 && pair[1] == 0) 
					{
						blocs[i][indexColorSpace][valueIndex] = 0;
						
						// O(N)
						for (int indexEOB = valueIndex; indexEOB < vecteurSize - 1; indexEOB++, valueIndex++) {
							blocs[i][indexColorSpace][indexEOB] = 0;
						}
					}

					// si EOB sera 0, sinon valeur AC
					blocs[i][indexColorSpace][valueIndex] = pair[1];
				}
			}
		}
	}
}
