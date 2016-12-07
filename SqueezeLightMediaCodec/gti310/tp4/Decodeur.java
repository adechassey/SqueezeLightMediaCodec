package gti310.tp4;

import Model.ColorSpaceValues;

/**
 * Classe utilis�e pour d�coder/decompresser un fichier image selon un facteur de qualit�.
 * @author Manuel Nero, Antoine de Chassey
 */
public class Decodeur {
	private String fileNameImage; 		// Le nom du fichier image.
	private String fileNameCompression; // Le nom du fichier compress�.
	private int facteurQualite;			// Le facteur de qualit� de l'image lors de la compression.

	private final String ERRMSG_LECTURE_FICHIER_COMPRESSE = "Erreur lors de la lecture du fichier image compress� %s. Le fichier est peut-�tre corrumpu ou invalide.";

	/***
	 * Constructeur
	 * @param fileNameCompression Le nom du fichier image compress�.
	 * @param fileNameImage Le nom du fichier image.
	 */
	public Decodeur(String fileNameCompression, String fileNameImage) {
		this.fileNameImage = fileNameImage;
		this.fileNameCompression = fileNameCompression;
	}

	/**
	 * D�coder un fichier image selon le facteur de qualit� obtenu lors de la lecteur du fichier image compress�.
	 * @param intputFile Le fichier .szl en entr�
	 * @param outputFile Le fichier .ppm qui sera cr�er en sortie
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
			System.out.println("Inverse op�ration Zigzag.");
			for (int i = 0; i < oColorValues.getNbBlocs(); i++) {
				oColorValues.setBlocSpaceColorValues(i, Main.Y,  ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Y) ) );
				oColorValues.setBlocSpaceColorValues(i, Main.Cb, ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Cb) ) );
				oColorValues.setBlocSpaceColorValues(i, Main.Cr, ZigZag.inverseZipZag(oColorValues.getSpaceColorValues(i, Main.Cr)) );
			}
		
			// Dequantification
			System.out.println("D�quantification.");
			Quantification.dequantification(oColorValues, qualityFactor);
			
			// iDCT
			System.out.println("iDCT en cours...");
			DCT.inverseDCT(oColorValues);
			System.out.println("iDCT ok!");
			
			// Reconstruire l'image enti�re � partir des blocs.
			System.out.println("Reconstruction de l'image enti�re � partir des blocs");
			ImgPartition.mergeImageFromBlocs(oColorValues);
	
			// Conversion YCbCr � RBG
			System.out.println("Conversion de YCbCr vers RBG.");
			convertColorSpace.convertYCbCrToRGB(oColorValues);
			
			// Cr�ation du fichier de sortie.
			if (PPMReaderWriter.writePPMFile(fileNameImage, oColorValues.getColorValues())) {
				operationCompleted = true;
			}
		}
	
		return operationCompleted;
	}
}
