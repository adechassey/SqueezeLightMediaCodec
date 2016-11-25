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
		
		// TODO : try catch
		if (args.length == 3) {
			System.out.println("Encoding started...");
			// Point d'entrée pour l'encodage
			String fileToEncode = args[0].trim();
			String compressFileName = args[1].trim();
			int facteurQualite = Integer.parseInt(args[2].trim());
			
			int[][][] rgb = PPMReaderWriter.readPPMFile(fileToEncode);
			
			// Conversion RGB à YCbCr
			int[][][] YCbCr = convertColorSpace.convertRGBtoYCbCr(rgb);
			
			// TODO conserver attributs hauteur , largeur a un endroit accessible, on y accede assez souvent a cette info.
			int height = YCbCr[Main.Y].length;
			int width = YCbCr[Main.Y][0].length;
			
			// DCT
			// Obtenir les blocs 8x8
			int[][][][] blocs = ImgPartition.partitionImage(YCbCr);
			int[][][] YCbCr2 = ImgPartition.mergeImageFromBlocs(blocs, height, width);
			//DCT.applyDCT(bloc, facteurQualite);
			
			// test blocks render
			/*
			for (int i = 0; i < blocs.length; ++i) {
				int[][][] rgbFromYCBCr = convertColorSpace.convertYCbCrToRGB(blocs[i]);
				PPMReaderWriter.writePPMFile("lena_part" + i + ".ppm", rgbFromYCBCr);
			}
			*/
			
			int[][][] rgbFromYCBCr = convertColorSpace.convertYCbCrToRGB(YCbCr2);
			PPMReaderWriter.writePPMFile("lena_merge.ppm", rgbFromYCBCr);
			
			
			// Quantification 
			
			// ZigZag
			
			// DPCM sur le DC (premier)
			
			// RLC sur les AC (le reste)
			
			// Codage entropique


			
			

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
