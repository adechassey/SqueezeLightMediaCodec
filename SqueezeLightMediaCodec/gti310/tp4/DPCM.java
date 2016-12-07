package gti310.tp4;

/***
 * Classe qui s'occupe de l'opération DPCM et iDPCM
 * @author Manuel Nero, Antoine de Chassey
 */
public class DPCM {
	/***
	 * Opération DPCM
	 * @param zigzag les données sous forme de train de bits pour les espaces de couleurs et les blocs.
	 * O(N^2)
	 */
	public static void applyDPCM(int[][][] zigzag) {
		int previousDC = 0;
		for (int indexColorSpace = 0; indexColorSpace < Main.COLOR_SPACE_SIZE; ++indexColorSpace) {
			for (int i = 0; i < zigzag.length; i++) { 
				Entropy.writeDC(zigzag[i][indexColorSpace][0] - previousDC); // DPCM - previous DC value
				previousDC = zigzag[i][indexColorSpace][0];
			}
		}
	}
	
	/***
	 * Opération iDPCM
	 * @param blocs les données sous forme de train de bits pour les espaces de couleurs et les blocs.
	 * O(N^2)
	 */
	public static void inverseDPCM(int[][][] blocs) {
		int previousDC = 0;
		int valueDC = 0;
		
		for (int indexColorSpace = 0; indexColorSpace < Main.COLOR_SPACE_SIZE; ++indexColorSpace) {
			for (int i = 0; i < blocs.length; ++i) {
				valueDC = Entropy.readDC() + previousDC; // DPCM + previous DC value
				blocs[i][indexColorSpace][0] = valueDC;
				previousDC = valueDC;
			}
		}
	}
}
