/******************************************************
Cours:   GTI310
Session: A2016
Groupe:  01
Projet: Laboratoire #4
Étudiant(e)s: Manuel Nero, Antoine de Chassey 
              
Professeur : Francis Cardinal
Nom du fichier: SqueezeLightMediaCodec
Date créé: 2016-12-07
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
	
	/*
	 * File type extensions
	 */
	public static final String IMAGE_FILE_EXTENSION = ".ppm";
	public static final String COMPRESSED_FILE_EXTENSION = ".szl";
	
	/*
	 * Liste des messages d'erreur et de validation
	 */
	private final static String ERRMSG_FACTEUR_QUALITE_VALUERANGE = "Le facteur de qualité doit être une valeur numérique comprisse entre 1 (pire qualité) et 100 (meilleur qualité).";
	private final static String ERRMSG_ARGS = "Le nombre d'arguments passés au programme est insuffisant.\n" + 
											  String.format("Encodage fichier image %s vers %s : <Nom de fichier en entrée> <Nom de fichier en sortie> <Facteur de qualité entre 1 et 100>.\n", 
											  IMAGE_FILE_EXTENSION, COMPRESSED_FILE_EXTENSION) +
											  String.format("Décodage fichier image %s vers %s : <Nom de fichier compressé> <Nom de fichier en sortie>", 
											  COMPRESSED_FILE_EXTENSION, IMAGE_FILE_EXTENSION);
	
	/**
	 * The application's entry point.
	 * 
	 * @param args
	 * O(?)
	 */
	public static void main(String[] args) {
		System.out.println("Squeeze Light Media Codec !");
	
		if (args.length >= 3) {
			// Point d'entrée pour l'encodage.
			System.out.println("Encoding started...");
			
			if (!Validations.facteurQualiteEstValide(args[2].trim()))
			{
				System.err.println(ERRMSG_FACTEUR_QUALITE_VALUERANGE);
				System.exit(1);
			}
					
			String imgFileName = Validations.validerNomFichierExtension(args[0].trim(), Main.IMAGE_FILE_EXTENSION);
			String outputFileName = Validations.validerNomFichierExtension(args[1].trim(), Main.COMPRESSED_FILE_EXTENSION);
			
			Encodeur encodeur = new Encodeur(imgFileName, outputFileName, Integer.parseInt(args[2].trim()) );
			
			if (encodeur.encoder()) {
				System.out.println("Encodage terminé !");
			}
			
		}
		else if (args.length == 2) {
			// Point d'entrée pour le décodage.
			System.out.println("Decoding started...");
		
			String fileToDecode =   Validations.validerNomFichierExtension(args[0].trim(), Main.COMPRESSED_FILE_EXTENSION);
			String outputFileName = Validations.validerNomFichierExtension(args[1].trim(), Main.IMAGE_FILE_EXTENSION);
			
			Decodeur decodeur = new Decodeur(fileToDecode, outputFileName);
			if (decodeur.decoder()) {
				System.out.println("Décodage terminé !");
			}
		}
		else { 
			System.err.println(ERRMSG_ARGS);
		}
	}

	/***
	 * Pour des fins de tests seulement. Pour TODO pdf du cours  avec facteur qualite 50. 
	 * Attributation de valeurs de Y prédéterminer pour un bloc.
	 * @param blocs
	 * O(N^2)
	 */
	private static void applyPredeterminedTestBlocValues(int[][][][] blocs) {
		int[][] bloc = new int[8][8];							
		bloc[0][0] = 200;	bloc[1][0] = 200;	bloc[2][0] = 203;	bloc[3][0] = 200;	bloc[4][0] = 200;	bloc[5][0] = 200;	bloc[6][0] = 205;	bloc[7][0] = 210;
		bloc[0][1] = 202;	bloc[1][1] = 203;	bloc[2][1] = 200;	bloc[3][1] = 200;	bloc[4][1] = 205;	bloc[5][1] = 200;	bloc[6][1] = 200;	bloc[7][1] = 200;
		bloc[0][2] = 189;	bloc[1][2] = 198;	bloc[2][2] = 200;	bloc[3][2] = 200;	bloc[4][2] = 200;	bloc[5][2] = 200;	bloc[6][2] = 199;	bloc[7][2] = 200;
		bloc[0][3] = 188;	bloc[1][3] = 188;	bloc[2][3] = 195;	bloc[3][3] = 200;	bloc[4][3] = 200;	bloc[5][3] = 200;	bloc[6][3] = 200;	bloc[7][3] = 200;
		bloc[0][4] = 189;	bloc[1][4] = 189;	bloc[2][4] = 200;	bloc[3][4] = 197;	bloc[4][4] = 195;	bloc[5][4] = 200;	bloc[6][4] = 191;	bloc[7][4] = 188;
		bloc[0][5] = 175;	bloc[1][5] = 182;	bloc[2][5] = 187;	bloc[3][5] = 187;	bloc[4][5] = 188;	bloc[5][5] = 190;	bloc[6][5] = 187;	bloc[7][5] = 185;
		bloc[0][6] = 175;	bloc[1][6] = 178;	bloc[2][6] = 185;	bloc[3][6] = 187;	bloc[4][6] = 187;	bloc[5][6] = 187;	bloc[6][6] = 187;	bloc[7][6] = 187;
		bloc[0][7] = 175;	bloc[1][7] = 175;	bloc[2][7] = 175;	bloc[3][7] = 187;	bloc[4][7] = 175;	bloc[5][7] = 175;	bloc[6][7] = 175;	bloc[7][7] = 186;

		for (int i = 0; i < blocs[0][Main.Y].length; ++i )
			for (int j = 0; j < blocs[0][Main.Y][0].length; ++j ) 
				blocs[0][Main.Y][i][j] = bloc[i][j];
	}

	/***
	 * Application du filtre gri sur l'ensemble des blocs pour la conservation des tons de gris seulement (canal Y).
	 * @param les blocs
	 * O(N^3)
	 */
	private static void applyGrayfilter(int[][][][] blocs) {
		for (int i = 0; i < blocs.length; i++) {
			for (int u = 0; u < blocs[i][Main.Cb].length; u++) {
				for (int v = 0; v < blocs[i][Main.Cb][0].length; v++) {
					blocs[i][Main.Cb][u][v] = 128;
					blocs[i][Main.Cr][u][v] = 128;
				}
			}
		}
	}
}
