package Model;

import gti310.tp4.Main;

/***
 * Classe qui converse les tableaux de valeurs nécessaires et les attributs de base (height, width, nombre de blocs) pour une image.
 * Les valeurs peuvent représenter diverse catégorie d'information (RGC, YCbCr, DCT, quantification values, ...)
 * L'idée d'utiliser cette classe est de centraliser les conteneurs de valeurs à cet endroit et d'offrir les fonctions nécessaires 
 * pour le stockage et la manipulations.
 *  
 * @author Manuel Nero, Antoine de Chassey
 */
public class ColorSpaceValues {
	private final int UNKNOWN_COLOR_VALUE = -1;
	
	private int[][][] colorValues = null; 		  // Tableau de dimension 3 contenant les valeurs nécessaires résultant des différentes opérations.
	private int[][][][] blocsColorValues = null;  // Tableau de dimension 4.
	private int imgHeight = 0;
	private int imgWidth = 0;
	private int colorSpaces = Main.COLOR_SPACE_SIZE;
	
	public int getColorSpaces() { return colorSpaces; }
	public int getImgHeight() { return imgHeight; }
	public int getImgWidth() { return imgWidth; }
	
	/***
	 * Retour le nombre de blocs qui constitue une image.
	 * @return Le nombre de blocs.
	 * O(1)
	 */
	public int getNbBlocs() { 
		int nbBlocParLignes = getImgWidth() / Main.BLOCK_SIZE;
		int nbBlocParColonnes = getImgHeight() / Main.BLOCK_SIZE;
		return nbBlocParLignes * nbBlocParColonnes;
	}

	public int getblocHeight() { return Main.BLOCK_SIZE; }
	public int getblocWidth() { return Main.BLOCK_SIZE; }

	public void setBlocsColorValues (int[][][][] values) { blocsColorValues = values; }
	public void setImgHeight(int height) { imgHeight = height; }
	public void setImgWidth(int width) { imgWidth = width; }

	public boolean isColorValuesNull() { return colorValues == null; }

	/***
	 * Initialise le conteneur de valeurs pour les espaces de couleurs.
	 */
	public void initializeColorValues() {
		colorValues = new int[getColorSpaces()][getImgHeight()][getImgWidth()];
	}

	/***
	 * Initialise le conteneur de valeurs contenir le train de bit par espace de couleurs et blocs.
	 */
	public void initializeZigZagValuesHolder() {
		colorValues = new int[getNbBlocs()][getColorSpaces()][getblocHeight() * getImgWidth()];
	}

	/***
	 * Initialise le conteneur de valeurs pour les espaces de couleurs par blocs.
	 */
	public void initializeBlocs() {
		blocsColorValues = new int[getNbBlocs()][getColorSpaces()][getblocHeight()][getblocWidth()];
	}
	
	/***
	 * S'occupe d'obtenir un tableau de valeurs représentant une image
	 * Obtient du même coup la hauteur et largeur de l'image.
	 * @param values Les valeurs pour une image pour les espaces de couleur.
	 */
	public void setColorValues(int[][][] values) { 
		colorValues = values; 
		resetAttributes();
		
		if (values != null && values.length > 0) {
			colorSpaces = values.length;
			imgHeight = values[0].length;
			imgWidth = values[0][0].length;
		}
	}
	
	public void setColorValues(int noBloc, int colorSpace, int[] values) { colorValues[noBloc][colorSpace] = values; }
	
	/***
	 * Reinitialisation des attributs pour une image.
	 */
	private void resetAttributes() {
		colorSpaces = Main.COLOR_SPACE_SIZE;
		imgHeight = 0;
		imgWidth = 0;
	}

	public int[][][] getColorValues() { return this.colorValues; }
	
	public void setColorValue(int colorSpace, int posHeight, int posWidth, int value) {
		if (colorValues != null) colorValues[colorSpace][posHeight][posWidth] = value;
	}
	public int getColorValue(int colorSpace, int posHeight, int posWidth) {
		return colorValues == null ? UNKNOWN_COLOR_VALUE : colorValues[colorSpace][posHeight][posWidth];
	}

	public void setBlocSpaceColorValues(int indexBloc, int colorSpace, int[][] values) {
		if (blocsColorValues != null) blocsColorValues[indexBloc][colorSpace] = values;
	}
	public int[][] getBlocSpaceColorValues(int noBloc, int colorSpace) {
		return blocsColorValues == null ? null : blocsColorValues[noBloc][colorSpace];
	}

	public void setSpaceColorValues(int indexBloc, int colorSpace, int[] values) {
		if (colorValues != null) colorValues[indexBloc][colorSpace] = values;
	}
	public int[] getSpaceColorValues(int indexBloc, int colorSpace) {
		return colorValues == null ? null : colorValues[indexBloc][colorSpace];
	}

	public void setBlocColorValue(int noBloc, int colorSpace, int posHeight, int posWidth, int value) {
		if (blocsColorValues != null) blocsColorValues[noBloc][colorSpace][posHeight][posWidth] = value;
	}
	public int getBlocColorValue(int noBloc, int colorSpace, int posHeight, int posWidth) {
		return blocsColorValues == null ? UNKNOWN_COLOR_VALUE : blocsColorValues[noBloc][colorSpace][posHeight][posWidth];
	}
}
