package gti310.tp4;

import Model.ColorSpaceValues;

// colorSpaceUtils
/***
 * Classe qui s'occupe de convertir d'un espace de couleur à un autre [RGB-YCbCr] selon
 * la norme T-REC-T.871-201105-I!!PDF-E.pdf, Il y aura une perte de precision +/- 1 sur certaines des valeurs lors de la conversion.
 * @author Manuel Nero, Antoine de Chassey
 */
public class convertColorSpace {
	/***
	 * Convertion d'une image RGB vers YCbCr 4:4:4. 
	 * les valeurs vont de 0 a 255
	 * @param rgb L'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^2)
	 */
	public static void convertRGBtoYCbCr(ColorSpaceValues rgb) {
		int height = rgb.getImgHeight();
		int width = rgb.getImgWidth();
		
		int R;
		int G;
		int B;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				R = rgb.getColorValue(Main.R, i, j);
				G = rgb.getColorValue(Main.G, i, j);
				B = rgb.getColorValue(Main.B, i, j);

				rgb.setColorValue(Main.R, i, j, (int) Math.min(Math.max(0, Math.round(0.299 * R + 0.587 * G + 0.114 * B)), 255));
				rgb.setColorValue(Main.G, i, j, (int) Math.min(Math.max(0, Math.round(-0.1687 * R + (-0.3313 * G) + 0.5 * B + 128 )), 255));
				rgb.setColorValue(Main.B, i, j, (int) Math.min(Math.max(0, Math.round(0.5 * R + (-0.4187 * G) + (-0.0813 * B + 128) )), 255));
			}
		}
	}
	
	/***
	 * Convertion d'une image YCbCr vers RGB 4:4:4. 
	 * les valeurs vont de 0 a 255
	 * @param YCbCr L'instance contenant les valeurs sur les espaces couleurs.
	 * O(N^2)
	 */
	public static void convertYCbCrToRGB(ColorSpaceValues YCbCr) {
		int height = YCbCr.getImgHeight();
		int width = YCbCr.getImgWidth();

		int Y;
		int Cr;
		int Cb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Y  = YCbCr.getColorValue(Main.Y, i, j);
				Cb = YCbCr.getColorValue(Main.Cb, i, j) - 128; 
				Cr = YCbCr.getColorValue(Main.Cr, i, j) - 128;

				YCbCr.setColorValue(Main.R, i, j, (int) Math.min(Math.max(0, Math.round(Y + 1.402 * Cr)), 255));
				YCbCr.setColorValue(Main.G, i, j, (int) Math.min(Math.max(0, Math.round(Y - 0.3441 * Cb - 0.7141 * Cr)), 255));
				YCbCr.setColorValue(Main.B, i, j, (int) Math.min(Math.max(0, Math.round(Y + 1.772 * Cb)), 255));
			}
		}
	}
}
