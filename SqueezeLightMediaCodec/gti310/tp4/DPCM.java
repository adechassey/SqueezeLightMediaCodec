package gti310.tp4;

public class DPCM {
	
	/***
	 * O(N^2)
	 */
	public static void applyDPCM(int[][][] zigzag) {
		int previousDC = 0;
		for (int indexColorSpace = 0; indexColorSpace < Main.COLOR_SPACE_SIZE; ++indexColorSpace) {
			for (int i = 0; i < zigzag.length; i++) {
				Entropy.writeDC(zigzag[i][indexColorSpace][0] - previousDC);
				previousDC = zigzag[i][indexColorSpace][0];
			}
		}
	}
	
	/***
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
