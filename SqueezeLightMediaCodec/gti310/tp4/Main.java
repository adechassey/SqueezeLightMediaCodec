/******************************************************
Cours:   GTI310
Session: A2016
Groupe:  01
Projet: Laboratoire #4
�tudiant(e)s: Manuel Nero, Antoine de Chassey 
              
Professeur : Francis Cardinal
Nom du fichier: [xxxxxx]
Date cr��: 2016-11-16
*******************************************************/   
package gti310.tp4;

import java.util.Arrays;

/**
 * The Main class is where the different functions are called to either encode
 * a PPM file to the Squeeze-Light format or to decode a Squeeze-Ligth image
 * into PPM format. It is the implementation of the simplified JPEG block 
 * diagrams.
 * 
 * @author Fran�ois Caron
 */
public class Main {

	/*
	 * The entire application assumes that the blocks are 8x8 squares.
	 */
	public static final int BLOCK_SIZE = 8;
	
	/*
	 * The number of dimensions in the color spaces.
	 */
	public static final int COLOR_SPACE_SIZE = 3;
	
	/*
	 * The RGB color space.
	 */
	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;
	
	/*
	 * The YCbCr color space.
	 */
	public static final int Y = 0;
	public static final int Cb = 1;
	public static final int Cr = 2;
	
	/**
	 * The application's entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Squeeze Light Media Codec !");
		
		// TODO : try catch
		if (args.length == 3) {
			System.out.println("Encoding started...");
			// Point d'entr�e pour l'encodage
			String fileToEncode = args[0].trim();
			String compressFileName = args[1].trim();
			int facteurQualite = Integer.parseInt(args[2].trim());
			
			int[][][] rgb = PPMReaderWriter.readPPMFile(fileToEncode);
			
			// Conversion RGB � YCbCr
			int[][][] YCbCr = convertColorSpace.convertRGBtoYCbCr(rgb);
			
			// TODO conserver attributs hauteur , largeur a un endroit accessible, on y accede assez souvent a cette info.
			int height = YCbCr[Main.Y].length;
			int width = YCbCr[Main.Y][0].length;
			
			// DCT
			// Obtenir les blocs 8x8
			int[][][][] blocs = ImgPartition.partitionImage(YCbCr);
			
			 //* @param facteurQualite  0 � 100 de la plus basse qualit� (0) � qualit� sans perte (100).
			
			// test blocks render
			int[][] bloc = new int[8][8];
			bloc[0][0] = 200;
			bloc[0][1] = 202;
			bloc[0][2] = 189;
			bloc[0][3] = 188;
			bloc[0][4] = 189;
			bloc[0][5] = 175;
			bloc[0][6] = 175;
			bloc[0][7] = 175;

			bloc[1][0] = 200;
			bloc[1][1] = 203;
			bloc[1][2] = 198;
			bloc[1][3] = 188;
			bloc[1][4] = 189;
			bloc[1][5] = 182;
			bloc[1][6] = 178;
			bloc[1][7] = 175;
			
			bloc[2][0] = 203;
			bloc[2][1] = 200;
			bloc[2][2] = 200;
			bloc[2][3] = 195;
			bloc[2][4] = 200;
			bloc[2][5] = 187;
			bloc[2][6] = 185;
			bloc[2][7] = 175;

			bloc[3][0] = 200;
			bloc[3][1] = 200;
			bloc[3][2] = 200;
			bloc[3][3] = 200;
			bloc[3][4] = 197;
			bloc[3][5] = 187;
			bloc[3][6] = 187;
			bloc[3][7] = 187;

			bloc[4][0] = 200;
			bloc[4][1] = 205;
			bloc[4][2] = 200;
			bloc[4][3] = 200;
			bloc[4][4] = 195;
			bloc[4][5] = 188;
			bloc[4][6] = 187;
			bloc[4][7] = 175;

			bloc[5][0] = 200;
			bloc[5][1] = 200;
			bloc[5][2] = 200;
			bloc[5][3] = 200;
			bloc[5][4] = 200;
			bloc[5][5] = 190;
			bloc[5][6] = 187;
			bloc[5][7] = 175;

			bloc[6][0] = 205;
			bloc[6][1] = 200;
			bloc[6][2] = 199;
			bloc[6][3] = 200;
			bloc[6][4] = 191;
			bloc[6][5] = 187;
			bloc[6][6] = 187;
			bloc[6][7] = 175;

			bloc[7][0] = 210;
			bloc[7][1] = 200;	
			bloc[7][2] = 200;
			bloc[7][3] = 200;
			bloc[7][4] = 188;
			bloc[7][5] = 185;
			bloc[7][6] = 187;
			bloc[7][7] = 186;

			for (int i = 0; i < blocs[0][Main.Y].length; ++i )
				for (int j = 0; j < blocs[0][Main.Y][0].length; ++j ) 
					blocs[0][Main.Y][i][j] = bloc[i][j];

		int[][][][] blocsDCT = DCT.applyDCT(blocs);
	    
		int[][][][] quantified = Quantification.applyQuantification(blocsDCT, facteurQualite);
		
		int[][][] zigzag = new int[quantified.length][Main.COLOR_SPACE_SIZE][Main.BLOCK_SIZE * Main.BLOCK_SIZE]; 
		for (int i = 0; i < quantified.length; i++) {
			zigzag[i][Main.Y] = ZigZag.doZipZag(quantified[0][Main.Y]);
			zigzag[i][Main.Cb] = ZigZag.doZipZag(quantified[0][Main.Cb]);
			zigzag[i][Main.Cr] = ZigZag.doZipZag(quantified[0][Main.Cr]);
		}
		
		// TODO : Traitement des coefficients DC
		
		int[][][][] deZigzag = new int[zigzag.length][Main.COLOR_SPACE_SIZE][Main.BLOCK_SIZE][Main.BLOCK_SIZE]; 
		for (int i = 0; i < zigzag.length; i++) {
			deZigzag[i][Main.Y] =  ZigZag.inverseZipZag(zigzag[i][Main.Y]);
			deZigzag[i][Main.Cb] = ZigZag.inverseZipZag(zigzag[i][Main.Cb]);
			deZigzag[i][Main.Cr] = ZigZag.inverseZipZag(zigzag[i][Main.Cr]);
		}
		
		int[][] deZigZag = ZigZag.inverseZipZag(zigzag);
		
		int[][][][] dequantified = Quantification.dequantification(quantified, facteurQualite);
		
		int[][][][] blocsConvertFromDCT = DCT.inverseDCT(dequantified);
		
		String s = "sssss";
		
			/*
			for (int i = 0; i < blocs.length; ++i) {
				int[][][] rgbFromYCBCr = convertColorSpace.convertYCbCrToRGB(blocs[i]);
				PPMReaderWriter.writePPMFile("lena_part" + i + ".ppm", rgbFromYCBCr);
			}
			*/

			// test new create image from blocks
			//int[][][] YCbCr2 = ImgPartition.mergeImageFromBlocs(blocs, height, width);
			//int[][][] rgbFromYCBCr = convertColorSpace.convertYCbCrToRGB(YCbCr2);
			//PPMReaderWriter.writePPMFile("lena_merge.ppm", rgbFromYCBCr);
			
			
			// Quantification 
			
			// ZigZag
			
			// DPCM sur le DC (premier)
			
			// RLC sur les AC (le reste)
			
			// Codage entropique


			
			

			System.out.println("Encoding completed...");
			
		}
		else if (args.length == 2) {
			System.out.println("Decoding started...");
			// Point d'entr�e pour le d�codage

			System.out.println("Decoding completed...");
		}
		else { 
			System.err.println("erreur lecture args");
		}
		
			
	}
}
