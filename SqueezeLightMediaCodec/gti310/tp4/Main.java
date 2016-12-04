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
}
