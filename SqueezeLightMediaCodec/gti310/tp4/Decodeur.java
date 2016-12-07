package gti310.tp4;

import Model.ColorSpaceValues;

/**
 * Classe utilisée pour décoder/decompresser un fichier image selon un facteur de qualité.
 * @author Manuel Nero, Antoine de Chassey
 */
public class Decodeur {
	private String fileNameImage; 		// Le nom du fichier image.
	private String fileNameCompression; // Le nom du fichier compressé.
	private int facteurQualite;			// Le facteur de qualité de l'image lors de la compression.

	private final String ERRMSG_LECTURE_FICHIER_COMPRESSE = "Erreur lors de la lecture du fichier image compressé %s. Le fichier est peut-être corrumpu ou invalide.";

	/***
	 * Constructeur
	 * @param fileNameCompression Le nom du fichier image compressé.
	 * @param fileNameImage Le nom du fichier image.
	 */
	public Decodeur(String fileNameCompression, String fileNameImage) {
		this.fileNameImage = fileNameImage;
		this.fileNameCompression = fileNameCompression;
	}

	/**
	 * Décoder un fichier image selon le facteur de qualité obtenu lors de la lecteur du fichier image compressé.
	 * @param intputFile Le fichier .szl en entré
	 * @param outputFile Le fichier .ppm qui sera créer en sortie
	 * O(N^5)
	 */
	public boolean decoder () {
		boolean operationCompleted = false;
		int[] szlBitStream = null;
		
		try {
			szlBitStream = SZLReaderWriter.readSZLFile(fileNameCompression);
		}
		catch (Exception e) {
			System.err.println(String.format(ERRMSG_LECTURE_FICHIER_COMPRESSE, fileNameCompression));
			return false;
		}
		
		if (szlBitStream == null) {
			System.err.println(String.format(ERRMSG_LECTURE_FICHIER_COMPRESSE, 
					           fileNameCompression));
		}
		else {
			ColorSpaceValues oColorValues = new ColorSpaceValues();
			oColorValues.setImgHeight(szlBitStream[0]);
			oColorValues.setImgWidth(szlBitStream[1]);
			int qualityFactor = szlBitStream[3];
			
			oColorValues.initializeBlocs();
			
			// iDC		
			oColorValues.initializeZigZagValuesHolder();
			System.out.println("Obtenir les DC.");
			DPCM.inverseDPCM(oColorValues.getColorValues());
			
			// iAC
			System.out.println("Obtenir les AC.");
			RLC.inverseRLC(oColorValues.getColorValues()); 
			
			// iZigzag
			System.out.println("Inverse opération Zigzag.");
			for (int i = 0; i < oColorValues.getNbBlocs(); i++) {
				oColorValues.setBlocSpaceColorValues(i, Main.Y,  ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Y) ) );
				oColorValues.setBlocSpaceColorValues(i, Main.Cb, ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Cb) ) );
				oColorValues.setBlocSpaceColorValues(i, Main.Cr, ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Cr)) );
			}
		
			// Dequantification
			System.out.println("Déquantification.");
			Quantification.dequantification(oColorValues, qualityFactor);
			
			// iDCT
			System.out.println("iDCT en cours...");
			DCT.inverseDCT(oColorValues);
			System.out.println("iDCT ok!");
			
			// Reconstruire l'image entière à partir des blocs.
			System.out.println("Reconstruction de l'image entière à partir des blocs");
			ImgPartition.mergeImageFromBlocs(oColorValues);
	
			// Conversion YCbCr à RBG
			System.out.println("Conversion de YCbCr vers RBG.");
			convertColorSpace.convertYCbCrToRGB(oColorValues);
			
			// Création du fichier de sortie.
			if (PPMReaderWriter.writePPMFile(fileNameImage, oColorValues.getColorValues())) {
				operationCompleted = true;
			}
		}
	
		return operationCompleted;
	}
}
