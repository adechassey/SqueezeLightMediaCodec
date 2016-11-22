/******************************************************
Cours:   GTI310
Session: A2016
Groupe:  01
Projet: Laboratoire #4
Étudiant(e)s: Manuel Nero, Antoine de Chassey 
              
Professeur : Francis Cardinal
Nom du fichier: [xxxxxx]
Date créé: 2016-11-16
*******************************************************/   
package gti310.tp4;

import java.util.Arrays;

/**
 * The Main class is where the different functions are called to either encode
 * a PPM file to the Squeeze-Light format or to decode a Squeeze-Ligth image
 * into PPM format. It is the implementation of the simplified JPEG block 
 * diagrams.
 * 
 * @author François Caron
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
	
		PPMReaderWriter ppm = new PPMReaderWriter();
		
		// TODO : try catch
		if (args.length == 3) {
			System.out.println("Encoding started...");
			// Point d'entrée pour l'encodage
			String fileToEncode = args[0].trim();
			String compressFileName = args[1].trim();
			int facteurQualite = Integer.parseInt(args[2].trim());
			
			int[][][] rgb = ppm.readPPMFile(fileToEncode);
			
			// Conversion RGB à YCbCr
			int[][][] YCbCr = convertColorSpace.convertRGBtoYCbCr(rgb);
			
			// DCT
			
			// Quantification 
			
			// ZigZag
			
			// DPCM sur le DC (premier)
			
			// RLC sur les AC (le reste)
			
			// Codage entropique


			// test
			int[][][] rgbFromYCBCr = convertColorSpace.convertYCbCrToRGB(YCbCr);
			//ppm.writePPMFile("lena2.ppm", rgbFromYCBCr);

			System.out.println("Encoding completed...");
			
		}
		else if (args.length == 2) {
			System.out.println("Decoding started...");
			// Point d'entrée pour le décodage

			System.out.println("Decoding completed...");
		}
		else { 
			System.err.println("erreur lecture args");
		}
		
			
	}
}
