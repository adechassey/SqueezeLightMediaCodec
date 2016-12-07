package gti310.tp4;

import Model.ColorSpaceValues;

/**
 * Classe utilisée pour encoder/compresser un fichier image selon un facteur de qualité.
 * @author Manuel Nero, Antoine de Chassey
 */
public class Encodeur {
	private String fileNameImage; 		// Le nom du fichier image.
	private String fileNameCompression; // Le nom du fichier compressé.
	private int facteurQualite;			// Le facteur de qualité de l'image lors de la compression.
	
	private final String ERRMSG_LECTURE_FICHIER_IMAGE = "Erreur lors de la lecture du fichier image %s. Le fichier est peut-être corrumpu ou invalide.";
	
	/***
	 * Constructeur
	 * @param fileNameImage Le nom du fichier image.
	 * @param fileNameCompression Le nom du fichier image compressé.
	 * @param facteurQualite Le facteur de qualité de la compression.
	 */
	public Encodeur(String fileNameImage, String fileNameCompression, int facteurQualite) {
		this.fileNameImage = fileNameImage;
		this.fileNameCompression = fileNameCompression;
		this.facteurQualite = facteurQualite;
	}

	/**
	 * Encodage d'un fichier image selon le facteur de qualité.
	 * O(N^5)
	 */
	public boolean encoder() {
		boolean operationCompleted = false;
		ColorSpaceValues oColorValues = new ColorSpaceValues();
		
		try {
			oColorValues.setColorValues(PPMReaderWriter.readPPMFile(fileNameImage));
		}
		catch (Exception e) {
			System.err.println(String.format(ERRMSG_LECTURE_FICHIER_IMAGE, fileNameImage));
			return false;
		}
		
		if (oColorValues.isColorValuesNull()) {
			System.err.println(String.format(ERRMSG_LECTURE_FICHIER_IMAGE, fileNameImage));
		}
		else { 
			// Conversion RGB à YCbCr
			System.out.println("Conversion de RGB à YCbCr.");
			convertColorSpace.convertRGBtoYCbCr(oColorValues);
			
			// Obtenir blocs [BLOCK_SIZE][BLOCK_SIZE]
			System.out.println("Partionnement de l'image en blocs.");
			ImgPartition.partitionImage(oColorValues);
			
			// Lignes suivantes en commentaires pour fins de tests uniquement.
			//applyGrayfilter(blocs);
			//applyPredeterminedTestBlocValues(oColorValues);

			// DCT
			System.out.println("DCT en cours...");
			DCT.applyDCT(oColorValues);
			System.out.println("DCT ok!");
		    
			// Quantification	
			System.out.println("Quantification.");
			Quantification.applyQuantification(oColorValues, facteurQualite);
			
			// Zigzag
			System.out.println("Zigzag.");
			oColorValues.initializeZigZagValuesHolder();
			for (int i = 0; i < oColorValues.getNbBlocs(); i++) {
				oColorValues.setSpaceColorValues(i, Main.Y, ZigZag.doZipZag(oColorValues.getBlocSpaceColorValues(i, Main.Y)));
				oColorValues.setSpaceColorValues(i, Main.Cb, ZigZag.doZipZag(oColorValues.getBlocSpaceColorValues(i, Main.Cb)));
				oColorValues.setSpaceColorValues(i, Main.Cr, ZigZag.doZipZag(oColorValues.getBlocSpaceColorValues(i, Main.Cr)));
			}
			
			// Prepare stream for DPCM, RLC operation 
			Entropy.loadBitstream(Entropy.getBitstream());
			
			// DC
			System.out.println("Obtenir les DC.");
			DPCM.applyDPCM(oColorValues.getColorValues());
	
			// AC
			System.out.println("Obtenir les AC.");
			RLC.applyRLC(oColorValues.getColorValues());

			// Write to output file.
			System.out.println(String.format("Création du fichier compressé %s.", fileNameCompression));
			SZLReaderWriter.writeSZLFile(fileNameCompression, oColorValues.getImgHeight(), oColorValues.getImgWidth(), facteurQualite);
			operationCompleted = true;
		}

		return operationCompleted;
	}	
	
	/***
	 * Pour des fins de tests seulement. 
	 * Permet de suivre l'exemple du pdf du cours GTI310-12-NormesCompressionImages.pdf (12-10) avec facteur qualite 50 (valeur alpha = 1). 
	 * Attributation de valeurs luminance Y prédéterminer pour 1 bloc uniquement.
	 * @param blocs
	 * O(N^2)
	 */
	private static void applyPredeterminedTestBlocValues(ColorSpaceValues blocs) {
		int[][] bloc = new int[Main.BLOCK_SIZE][Main.BLOCK_SIZE];							
		bloc[0][0] = 200;	bloc[1][0] = 200;	bloc[2][0] = 203;	bloc[3][0] = 200;	bloc[4][0] = 200;	bloc[5][0] = 200;	bloc[6][0] = 205;	bloc[7][0] = 210;
		bloc[0][1] = 202;	bloc[1][1] = 203;	bloc[2][1] = 200;	bloc[3][1] = 200;	bloc[4][1] = 205;	bloc[5][1] = 200;	bloc[6][1] = 200;	bloc[7][1] = 200;
		bloc[0][2] = 189;	bloc[1][2] = 198;	bloc[2][2] = 200;	bloc[3][2] = 200;	bloc[4][2] = 200;	bloc[5][2] = 200;	bloc[6][2] = 199;	bloc[7][2] = 200;
		bloc[0][3] = 188;	bloc[1][3] = 188;	bloc[2][3] = 195;	bloc[3][3] = 200;	bloc[4][3] = 200;	bloc[5][3] = 200;	bloc[6][3] = 200;	bloc[7][3] = 200;
		bloc[0][4] = 189;	bloc[1][4] = 189;	bloc[2][4] = 200;	bloc[3][4] = 197;	bloc[4][4] = 195;	bloc[5][4] = 200;	bloc[6][4] = 191;	bloc[7][4] = 188;
		bloc[0][5] = 175;	bloc[1][5] = 182;	bloc[2][5] = 187;	bloc[3][5] = 187;	bloc[4][5] = 188;	bloc[5][5] = 190;	bloc[6][5] = 187;	bloc[7][5] = 185;
		bloc[0][6] = 175;	bloc[1][6] = 178;	bloc[2][6] = 185;	bloc[3][6] = 187;	bloc[4][6] = 187;	bloc[5][6] = 187;	bloc[6][6] = 187;	bloc[7][6] = 187;
		bloc[0][7] = 175;	bloc[1][7] = 175;	bloc[2][7] = 175;	bloc[3][7] = 187;	bloc[4][7] = 175;	bloc[5][7] = 175;	bloc[6][7] = 175;	bloc[7][7] = 186;

		for (int i = 0; i < Main.BLOCK_SIZE; ++i )
			for (int j = 0; j < Main.BLOCK_SIZE; ++j ) 
				blocs.setBlocColorValue(0, Main.Y, i, j, bloc[i][j]);
	}

	/***
	 * Application du filtre gris sur l'ensemble des blocs pour la conservation des tons de gris seulement (canal de luminance Y).
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
