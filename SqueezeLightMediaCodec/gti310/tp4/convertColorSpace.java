package gti310.tp4;

// colorSpaceUtils
public class convertColorSpace {
	// TODO :Rapport voir norme ITU-R BT.601 recommendation pour rounding des valeurs lors de convertion.
	//       comment on test: utilisant 1 pixel de couleur voir resultat vice-versa
	//       comparaison matrice entiere par la suite.
	// TODO: pour le rapport: math.floor pour obtenir une valeur appropriee plus exacte.  ou bien voir formule : http://www.itu.int/rec/T-REC-T.871
	/***
	 * convertion d'une image RGB 4:4:4 en format YCbCr 4:2:0. 
	 * valeurs vont de 0 a 255
	 * @param rgb
	 * @return
	 * 
	 * O(N^2)
	 */
	public static int[][][] convertRGBtoYCbCr(int[][][] rgb) {
		//rgb = new int[Main.COLOR_SPACE_SIZE][1][1]; rgb[Main.R][0][0] = 222; rgb[Main.G][0][0] = 133; rgb[Main.B][0][0] = 108;
		// ou bien float[][][] ou lieu de int , a voir.
 		int[][][] YCbCr = new int[Main.COLOR_SPACE_SIZE][rgb[0].length][rgb[0][0].length];
		
		int height = rgb[Main.R].length;
		int width = rgb[Main.R][0].length;
		int R;
		int G;
		int B;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// TODO 4:2:0  ??
				R = rgb[Main.R][i][j];
				G = rgb[Main.G][i][j];
				B = rgb[Main.B][i][j];

				YCbCr[Main.Y][i][j]  = (int) Math.min(Math.max(0, Math.round(0.299 * R + 0.587 * G + 0.114 * B)), 255);
				YCbCr[Main.Cb][i][j] = (int) Math.min(Math.max(0, Math.round(-0.1687 * R + (-0.3313 * G) + 0.5 * B + 128 )), 255);
				YCbCr[Main.Cr][i][j] = (int) Math.min(Math.max(0, Math.round(0.5 * R + (-0.4187 * G) + (-0.0813 * B + 128) )), 255);
			}
		}
		
		// 4:2:0 -> nearest neighbor approach http://stackoverflow.com/questions/13714232/yuv-image-processing
		// Y = image[ y * w + x];
		// U = image[ w * h + floor(y/2) * (w/2) + floor(x/2) + 1]
		// V = image[ w * h + floor(y/2) * (w/2) + floor(x/2) + 0]
		// 
		// autre info : http://www.forejune.co/jvcompress/chap5.pdf
		// ref git : https://github.com/banctilrobitaille/Convertisseur_QuasiJPEG/blob/master/ColorSpaceConverter/YCBCRConverter.java
		return YCbCr;
	}
	
	/***
	 * valeurs vont de 0 a 255
	 * @param YCbCr
	 * @return
	 * O(N^2)
	 */
	public static int[][][] convertYCbCrToRGB(int[][][] YCbCr) {
		int[][][] rgb = new int[Main.COLOR_SPACE_SIZE][YCbCr[0].length][YCbCr[0][0].length];
		int height = rgb[Main.R].length;
		int width = rgb[Main.R][0].length;

		// TODO 4:2:0  ??
		// Source : https://en.wikipedia.org/wiki/YCbCr - section JPEG conversion
		int Y;
		int Cr;
		int Cb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Y = YCbCr[Main.Y][i][j];
				Cb = YCbCr[Main.Cb][i][j] - 128; 
				Cr = YCbCr[Main.Cr][i][j] - 128;

				rgb[Main.R][i][j] = (int) Math.min(Math.max(0, Math.round(Y + 1.402 * Cr)), 255);
				rgb[Main.G][i][j] = (int) Math.min(Math.max(0, Math.round(Y - 0.3441 * Cb - 0.7141 * Cr)), 255);
				rgb[Main.B][i][j] = (int) Math.min(Math.max(0, Math.round(Y + 1.772 * Cb)), 255);

				/* premiere version
				 * rgb[Main.R][i][j] = (int) Math.floor(Y + 1.402 * Cr);
				 * rgb[Main.G][i][j] = (int) Math.floor(Y - 0.344136 * Cb - 0.714136 * Cr);
				 * rgb[Main.B][i][j] = (int) Math.floor(Y + 1.772 * Cb);
				 **/

				
				// T-REC-T.871-201105-I!!PDF-E.pdf
				// Il y aura une perte de precision +/- 1 sur valeurs de RGB
			}
		}
		
		return rgb;
	}
}
