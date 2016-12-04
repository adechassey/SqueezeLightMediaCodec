package gti310.tp4;

import Model.ColorSpaceValues;

// colorSpaceUtils
public class convertColorSpace {
	// TODO :Rapport voir norme ITU-R BT.601 recommendation pour rounding des valeurs lors de convertion.
	//       comment on test: utilisant 1 pixel de couleur voir resultat vice-versa
	//       comparaison matrice entiere par la suite.
	// TODO: pour le rapport: math.floor pour obtenir une valeur appropriee plus exacte.  ou bien voir formule : http://www.itu.int/rec/T-REC-T.871
	/***
	 * Convertion d'une image RGB YCbCr 4:4:4. 
	 * les valeurs vont de 0 a 255
	 * @param rgb information 
	 * @return
	 * 
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

				// TODO Restruturation des array dans ColorSpaceValues evite de manipuler plusieurs tableau de dimension de taille de l'image.
				// TODO mentionne norme de la formule.
				// TODO mentionne rapport maintenir double vs int  , perte de precision. le reste des operations decouleur des valeurs sur les couleurs/
				rgb.setColorValue(Main.R, i, j, (int) Math.min(Math.max(0, Math.round(0.299 * R + 0.587 * G + 0.114 * B)), 255));
				rgb.setColorValue(Main.G, i, j, (int) Math.min(Math.max(0, Math.round(-0.1687 * R + (-0.3313 * G) + 0.5 * B + 128 )), 255));
				rgb.setColorValue(Main.B, i, j, (int) Math.min(Math.max(0, Math.round(0.5 * R + (-0.4187 * G) + (-0.0813 * B + 128) )), 255));
			}
		}
	}
	
	/***
	 * valeurs vont de 0 a 255
	 * @param YCbCr
	 * @return
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
				
				// T-REC-T.871-201105-I!!PDF-E.pdf
				// Il y aura une perte de precision +/- 1 sur valeurs de RGB
			}
		}
	}
}
