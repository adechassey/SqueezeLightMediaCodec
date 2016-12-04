package Model;

import gti310.tp4.Main;

public class ColorSpaceValues {
	private final int UNKNOWN_COLOR_VALUE = -1;
	
	private int[][][] colorValues = null; 		 // The matrix is a 3D array: each dimension holds one dimension of the RGB color space.
	private int[][][][] blocsColorValues = null;
	private int imgHeight = 0;
	private int imgWidth = 0;
	private int colorSpaces = Main.COLOR_SPACE_SIZE;
	
	public int getColorSpaces() { return colorSpaces; }
	public int getImgHeight() { return imgHeight; }
	public int getImgWidth() { return imgWidth; }
	
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

	public void initializeColorValues() {
		colorValues = new int[getColorSpaces()][getImgHeight()][getImgWidth()];
	}

	public void initializeZigZagValuesHolder() {
		colorValues = new int[getNbBlocs()][getColorSpaces()][getblocHeight() * getImgWidth()];
	}

	public void initializeBlocs() {
		blocsColorValues = new int[getNbBlocs()][getColorSpaces()][getblocHeight()][getblocWidth()];
	}
	
	public void setColorValues(int[][][] values) { 
		colorValues = values; 
		resetAttributes();
		
		// TODO revoir 
		if (values != null && values.length > 0) {
			colorSpaces = values.length;
			imgHeight = values[0].length;
			imgWidth = values[0][0].length;
		}
	}
	
	public void setColorValues(int noBloc, int colorSpace, int[] values) { colorValues[noBloc][colorSpace] = values; }
	
	private void resetAttributes() {
		colorSpaces = Main.COLOR_SPACE_SIZE;
		imgHeight = 0;
		imgWidth = 0;
	}

	public int[][][] getColorValues() { return this.colorValues; }
	
	public int getColorValue(int colorSpace, int posHeight, int posWidth) {
		return colorValues == null ? UNKNOWN_COLOR_VALUE : colorValues[colorSpace][posHeight][posWidth];
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

	public void setBlocSpaceColorValues(int indexBloc, int colorSpace, int[][] values) {
		if (blocsColorValues != null) blocsColorValues[indexBloc][colorSpace] = values;
	}

	public int getBlocColorValue(int noBloc, int colorSpace, int posHeight, int posWidth) {
		return blocsColorValues == null ? UNKNOWN_COLOR_VALUE : blocsColorValues[noBloc][colorSpace][posHeight][posWidth];
	}

	public void setColorValue(int colorSpace, int posHeight, int posWidth, int value) {
		if (colorValues != null) colorValues[colorSpace][posHeight][posWidth] = value;
	}

	public void setBlocColorValue(int noBloc, int colorSpace, int posHeight, int posWidth, int value) {
		if (blocsColorValues != null) blocsColorValues[noBloc][colorSpace][posHeight][posWidth] = value;
	}
}
