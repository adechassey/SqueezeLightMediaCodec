package gti310.tp4;

/**
 * Classe utilitaire qui s'occupe de validation des informations passer comme arguments au programme et s'occupe de modifier ceux ci pour 
 * qu'ils soient conforme si il y a lieu.
 * @author 
 *
 */
public class Validations {
	private final static int FACTEUR_QUALITE_MINVALUE = 1;
	private final static int FACTEUR_QUALITE_MAXVALUE = 1000;

	/*
	 * O(1)
	 */
	/***
	 * Indique si le facteur de qualité est valide.
	 * @param facteurQualite La valeur du facteur de qualité.
	 * @return valide ou non.
	 */
	public static boolean facteurQualiteEstValide(String facteurQualite) {
		boolean estValide = true;
		int valeurFacteurQualite = 0;
		
		try {
			valeurFacteurQualite = Integer.parseInt(facteurQualite);
			estValide = facteurQualiteEstValide(valeurFacteurQualite);
		}
		catch (NumberFormatException e) {
			estValide = false;
		}

		return estValide;
	}

	/*
	 * O(1)
	 */
	/***
	 * Indique si le facteur de qualité est compris dans un intervalle numérique.
	 * @param facteurQualite La valeur pour le facteur de qualité
	 * @return Si le facteur de qualité est dans l'intervalle numérique spécifié.
	 */
	public static boolean facteurQualiteEstValide(int facteurQualite) {
		return !(facteurQualite < FACTEUR_QUALITE_MINVALUE || facteurQualite > FACTEUR_QUALITE_MAXVALUE);
	}

	/***
	 * Vérifie qu'un fichier comporte bien l'extension spécifié sinon l'ajoute.
	 * @param fileName Le nom du fichier
	 * @param ext l'extension du fichier
	 * @return Le nom du fichier avec son extension.
	 * O(1)
	 */
	public static String validerNomFichierExtension(String fileName, String ext) {
		// 
		int indexFileExtLookup = fileName.length() - ext.length();
		return indexFileExtLookup < 0 ? fileName + ext : !fileName.substring(indexFileExtLookup).equals(ext) ? fileName + ext : fileName;
	}
}
