package emx.solar.pack.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Helper {

	//protected static Log log = Log.getInstance(Helper.class);
	
	private final static char[] invalidCharacters = {'\\', '/', ':', '*', '?', '"', '<', '>', '|', '"'};

	/** D�finition du format pour une date dd/MM/yyyy */
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/** D�finition du format pour une date dd/MM */
	protected static final String jourMoisFormatDef = "dd/MM";	
	protected static  final SimpleDateFormat jourMoisFormat = new SimpleDateFormat(jourMoisFormatDef);	
	
	/** D�finition du format pour une ann� yyyy */
	protected static  final SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");

	/** D�finition du format pour un moi MMMM */
	protected static  final SimpleDateFormat dateFormatMois = new SimpleDateFormat("MMMM");
	
	/** D�finition du format pour un moi MMMM */
	protected static  final SimpleDateFormat dateFormatAnneeMois = new SimpleDateFormat("yyMM");	

	/** D�finition du format pour un moi MM */
	protected static  final SimpleDateFormat dateFormatMoisInt = new SimpleDateFormat("MM");

	
	/** D�finition du format pour une semaine w */
	protected static  final SimpleDateFormat dateFormatSemaine = new SimpleDateFormat("w");	
   
	/** d�finition du format pour une Date plus heure */
	protected static  final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	/** d�finition du format pour une Date plus heure minute seconde */
	protected static  final SimpleDateFormat dateTimeFullFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/** D�finition du format pour un moi MM */
	protected static  final SimpleDateFormat dateFormatMoisChiffre = new SimpleDateFormat("MM");
	
	/** D�finition du format pour une date yyMMdd */
	protected static final SimpleDateFormat dateFormatMailRobot = new SimpleDateFormat("yyyyMMdd");
	
	/** D�finition du format pour une date yyMMdd */
	protected static final SimpleDateFormat dateFormatPdf = new SimpleDateFormat("yyyy-MM-dd");
	
	protected static final SimpleDateFormat dateFormatJourDeLaSemaine = new SimpleDateFormat("EEEE");
	
	/**
	 *  d�finition du format heure:min
	 */
	protected static  final SimpleDateFormat heureMinFormat = new SimpleDateFormat("HH:mm");

	
	public static String toString(Date uneDate) {
		return (uneDate!=null)? dateFormat.format( uneDate ) : "";
	}
	
	public static String toStringTime(Date uneDate) {
		return (uneDate!=null)? dateTimeFormat.format( uneDate ) : "";
	}	
	
	public static String toString(Integer valeur) {
		return (valeur!=null)? valeur.toString() : "";
	}	
	
	public static String toString(Number valeur) {
		String retour  = (valeur!=null)? valeur.toString() : "";
		if(!"".equals(retour)){
			return new java.math.BigDecimal(valeur.floatValue()).stripTrailingZeros().toPlainString();
		}
		return retour;
	}
	
	/**
	 * Renvoit l'ann�e
	 * @param uneDate
	 * @return Integer
	 */
	public static Integer toYear(Date uneDate) {
		return new Integer((uneDate!=null)? dateFormatYear.format( uneDate ) : "0");
	}
	
	/**
	 * Renvoit le nom du mois en lettres � partir de la date donn�e
	 * @param uneDate
	 * @return
	 */
	public static String toMonth(Date uneDate) {
		return (uneDate!=null)? dateFormatMois.format( uneDate ) : "";
	}
	
	/** transforme une date en dd/mm*/
	public static String toStringJourMois(Date uneDate) {
		return (uneDate!=null)? jourMoisFormat.format( uneDate ) : "";
	}	

	/**
	 * Renvoit le num�ro du mois  � partir de la date donn�e
	 * @param uneDate
	 * @return
	 */
	public static Integer toMonthInt(Date uneDate) {
		if(uneDate==null) {
			return 0;
		}
		return Helper.toInteger(dateFormatMoisInt.format( uneDate ));
	}
		
	/**
	 * Renvoit le num�ro de la semaine  � partir de la date donn�e
	 * @param uneDate
	 * @return
	 */
	public static Integer toWeekNumberInt(Date uneDate) {
		if(uneDate==null) {
			return 0;
		}
		return Helper.toInteger(dateFormatSemaine.format( uneDate ));
	}
	
	
	/**
     * @param <E>
     * @param <E>
     * @param coll
     * @param methodName: le nom de la m�thode � appeler
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * 
     * 
     */
    public static  <E> Map<Object,E> collectionToMap( Collection<E> coll, String methodName ) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
    	Map<Object,E> retour = new LinkedHashMap<Object,E>();
    	if(coll==null || coll.isEmpty()) {
    		return retour;
    	}
    	
    	if(methodName==null) {
    		throw new IllegalArgumentException("methodName can't be null");
    	}
    	
        Method keyFromValueMethod = null;
        for( E elem : coll ) {
            if( keyFromValueMethod == null ) {
                keyFromValueMethod = elem.getClass().getMethod( methodName, new Class[] {} );
            }
            Object key = keyFromValueMethod.invoke( elem, new Object[] {} );
            retour.put( key, elem );
        }
        
        return retour;
    }  
	
	/**
	 * Renvoit le numero de semaine � partir de la date donn�e
	 * @param uneDate
	 * @return
	 */
	public static Integer toWeek(Date uneDate) {
		if(uneDate==null) {
			return 1;
		}
		return Helper.toInteger(dateFormatSemaine.format( uneDate ));
	}	
	
	/**
	 * Renvoit le jour de la semaine de la date
	 * @param uneDate
	 * @return
	 */
	public static String toDayOfWeek(Date uneDate) {
		if(uneDate==null) {
			return null;
		}
		return dateFormatJourDeLaSemaine.format(uneDate);
	}

	/**
	 * Representation d'un boolean en string
	 * @param bool
	 * @return "true ou "false"
	 */
	public static String toString(boolean bool) {
		return (bool)? "true" : "false";
	}

	/**
	 * Retourne la chaine trim�e si elle n'est ni vide ni null
	 * @param chaine
	 * @return String
	 */
	public static String trim(String chaine) {
		if (!nullOuVide(chaine))
			chaine = chaine.trim();
		return chaine;
	}
    
	/**
	 * Enleve un an � la date donn�e
	 * @param date la date d'origine
	 * @return Date
	 */
    public static Date enleveAn(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		return calendar.getTime();
    }
    
	/**
	 * Transforme la chaine de caract�res donn�es en Integer
	 * @param nombre - le nombre � transformer
	 * @return null si la chaine est vide ou null ou au mauvais format
	 */
    public static Integer toInteger(String nombre) {
    	if(nullSiVide(nombre)==null)
    		return null;
    		
    	Integer retour = null;
    	
    	if(Helper.nullSiVide(nombre)==null) {
    		return null;
    	}
    		
		try {
			retour = new Integer(nombre);
			
		}catch(NumberFormatException e) {
			//log.warn(e);
			return null;
		} 
		return retour;
    	
    }
    
	/**
	 * Retourne null si la chaine est vide
	 * @param chaine
	 * @return String
	 */
	public static String nullSiVide(String chaine) {
		if(chaine==null || chaine.trim().length()==0) {
			return null;
		}
		return chaine.trim();
	}
	
	
	/**
	 * Retourne true si la chaine est vide ou null
	 * @param chaine
	 * @return String
	 */
	public static boolean nullOuVide(String chaine) {
		return(chaine==null || chaine.trim().length()==0);
	}
	
	/**
	 * Retourne vide si la chaine est null
	 * sinon retourne la chaine
	 * @param chaine
	 * @return String
	 */
	public static String videSiNull(String chaine) {
		if(chaine==null)
			return "";
		return chaine;
	}
	
	/**
	 * Retourne vide si l'integer est null
	 * sinon retourne la repr�sentation String de l'int
	 * @param integer
	 * @return String
	 */
	public static String videSiNull(Integer integer) {
		if(integer==null)
			return "";
		return String.valueOf(integer);
	}
	
	/**
	 * Retourne vide si l'integer est null
	 * sinon retourne la repr�sentation String du float
	 * @param float
	 * @return String
	 */
	public static String videSiNull(Float valeur) {
		if(valeur==null)
			return "";
		return String.valueOf(Helper.arrondi(valeur));
	}
	
	/**
	 * Retourne vide si le Double est null
	 * sinon retourne la repr�sentation String du float
	 * @param Double
	 * @return String
	 */
	public static String videSiNull(Double valeur) {
		if(valeur==null)
			return "";
		return String.valueOf(valeur);
	}	


	/**
	 * Returns the dateFormat.
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * Returns the dateTimeFormat.
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateTimeFormat() {
		return dateTimeFormat;
	}
	
	/**
	 * Lit le contenu d'un fichier et le transforme en byte[]
	 * @param file
	 * @return byte[]
	 * @throws IOException
	 */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large "+file.getName());
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

	/**
	 * Arrondi un nombre flottatn � 2 chiffres apres la virgule
	 * @param valeur
	 * @return float
	 */
	public static double arrondi(double nombre) {
		return arrondi(nombre, 2);
	}
	
	/**
	 * Arrondi un nombre flottant � 2 chiffres apres la virgule
	 * @param valeur
	 * @return float
	 */
	public static float arrondi(float nombre) {
		return new Float(arrondi(new Double(nombre), 2));
	}
	
	/**
	 * Arrondi un nombre flottatn � "pr�cision" chiffres apres la virgule
	 * @param valeur
	 * @return float
	 */
	public static double arrondi(double nombre, int precision) {
		if(nombre==0) {
			return 0;
		}
		
		double multiple= Math.pow(10,precision);
		double arrondi = Math.round(nombre *multiple);
		arrondi/=multiple;

		
		return arrondi;
	}
	




	/**
	 * Construit une string � partir de la chaine liste donn�es en ajoutant le s�parateur donn�
	 * entre chaque �l�ment (<br> ou autre selon mode extraction xls ou pas)
	 * 
	 * compris avant la valeur de insecable
	 * @param liste
	 * @param insecable
	 * @return String
	 */
	public static String construitString(List<?> liste, String separateur) {
		int cptr=0;
		StringBuffer buffer = new StringBuffer();
		for(Object chaine : liste) {
			if(chaine!=null){
				if(cptr!=0){
					buffer.append(separateur);
				}
				buffer.append(chaine);			
				cptr++;	
			}			
		}
		
		return buffer.toString();
	}
	
	/**
	 * Supprime le dernier caract�re d'un StringBuilder
	 * (utilis� dans les listes de chaine s�par�es par des ",")
	 */
	public static String removeLastCaractere(StringBuilder stringBuilder){
		String chaine = stringBuilder.toString();
		if(chaine.length()>0){
			return chaine.substring(0, chaine.length()-1);
		}
		return "";
	}
	
	/**
	 * Transforme une liste de valeurs s�par�es pas des ";"
	 * en collection
	 * @param str
	 * @return Collection, vide si aucune �lement
	 */
	public static Collection<String> getListFromString (String str)
	{
		Collection<String> liste = new ArrayList<String> ();
		
		if (Helper.nullOuVide(str))
			return liste;
		
		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ";");
			
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if(!Helper.nullOuVide(token)) {
				liste.add(token);
			}
		}		
		return liste;
	}
	
	/**
	 * Transforme une liste de valeurs s�par�es pas des ";"
	 * en collection
	 * @param str
	 * @return Collection, vide si aucune �lement
	 */
	public static Collection<Integer> getListIntegerFromString (String str)
	{
		Collection<Integer> liste = new HashSet<Integer> ();
		
		if (Helper.nullOuVide(str))
			return liste;
		
		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ";");
			
		while (st.hasMoreTokens()) 
		{
			String token = st.nextToken();
			if(!Helper.nullOuVide(token)) {
				liste.add(Helper.toInteger(token));
			}
		}		
		return liste;
	}
	
	/**
	 * Transforme une liste de valeurs s�par�es pas des ";"
	 * en collection
	 * @param str
	 * @return Collection, vide si aucune �lement
	 */
	public static List<String> getListFromString (String str, String separateur)
	{
		List<String> liste = new ArrayList<String> ();
		
		if (Helper.nullOuVide(str))
			return liste;
		
		java.util.StringTokenizer st = new java.util.StringTokenizer(str, separateur);
			
		while (st.hasMoreTokens()) 
		{
			String token = st.nextToken();
			if(!Helper.nullOuVide(token)) {
				liste.add(token);
			}
		}		
		return liste;
	}	
	
	 	
	
	
	/*
	private static StringBuilder buildUploadDirectory(MetDocument document) {
		
		StringBuilder chemin = getUploadRoot();
		chemin.append(document.getDocumentFolder());
		
		File repertoire = new File (String.valueOf(chemin));
		if(!repertoire.exists()){
	          repertoire.mkdirs();
		}
		return chemin;
	}


	public static StringBuilder getUploadRoot() {
		StringBuilder chemin = new StringBuilder();		
		String path = System.getProperty("uploadRoot");
		
		if(path!=null) {
			chemin.append(path);
			if(!path.endsWith(File.separator))
				chemin.append(File.separator);			
		}
		return chemin;
	}
	
	 */
	
	/**
	 * Renvoit le chemin vers le r�pertoire d'upload de fichier
	 * pass� en propri�t� systeme.
	 * Si le chemin ne termine par par File.separator, ce caract�re
	 * est ajout� � la fin.
	 * @param servlet
	 * @return StringBuffer
	 */
	public static StringBuilder getUploadDirectory(Date dateFichier) {
		
		StringBuilder chemin = new StringBuilder();		
		String path = System.getProperty("uploadRoot");
		
		if(path!=null) {
			chemin.append(path);
			if(!path.endsWith(File.separator))
				chemin.append(File.separator);			
		}
		chemin.append("metro");
		chemin.append(File.separator);
				
		if(dateFichier!=null) {
			/** Chemin avec ann�e et mois : ".../YYYY/mm/" */
			String annee = String.valueOf(Helper.toYear(dateFichier));
			String mois = String.valueOf(Helper.toMonthChiffre(dateFichier));
			
			chemin.append(File.separator);
			chemin.append(annee);
			chemin.append(File.separator);
			chemin.append(mois);
			chemin.append(File.separator);
		}
		
		/** Cr�ation des nouveaux dossiers */
		File repertoire = new File (String.valueOf(chemin));
		if(!repertoire.exists()){
	          repertoire.mkdirs();
		}
		return chemin;
	}
	
   /**
    *  Renvoie la valeur de l'enum correspondante � la valeur pass�e en param�tre
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Enum<?> toEnum(String valeur, Class monEnum) {
    	Enum<?> retour = null;
    	try{
    		retour = Enum.valueOf(monEnum,valeur);
    	}catch(Exception e ){
    		//
    	}
    	return retour;
    }
	
	/**
	 * Renvoit le nom du mois en chiffres � partir de la date donn�e
	 * @param uneDate
	 * @return
	 */
	public static String toMonthChiffre(Date uneDate) {
		return (uneDate!=null)? dateFormatMoisChiffre.format( uneDate ) : "";
	}
	
	/**
	 * Force la premi�re lettre de la chaine en majuscule
	 */
	public static void ucFirst(StringBuffer resultat,String chaine){		
		resultat.append(chaine.substring(0, 1).toUpperCase());
		resultat.append(chaine.substring(1));			
	}
	
	/**
	 * Force la premi�re lettre de la chaine en majuscule
	 */
	public static String ucFirst(String chaine){			
		return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);				
	}
	

	
		
	/**
	 * Retourne le nombre d'�l�ments d'un objet castable en liste
	 */
	public static Integer getNombreElement(Object obj){		
	   return (obj!=null)  ?  ((List<?>)obj).size() : new Integer(0) ;
	} 
	
	/**
	 * Retourne le nom d'une m�thode d'un attribut
	 * @deprecated
	 */
	public static String getMethodName(String attribut){		
		StringBuffer retour = new StringBuffer();
		retour.append("get");
		ucFirst(retour,attribut);	
		return retour.toString();
	}
	
	/**
	 *  Test si une chaine est un entier
	 */
	public static boolean isInteger(String input){
	   try{
	      Integer.parseInt( input );
	      return true;
	   }catch(Exception e){
	      return false;
	   }
	}
	
    /** remplace tous les caract�res douteux par un _ pour �viter de bloquer le renommage du fichier */
    public static String cleanFileName(String fileName)
    {
        // Create a SAFE name. Replace any forbidden characters by _
        // Forbidden chars are: \ / : * ? " < > | 
        Pattern scramblePattern = Pattern.compile("[\\\\/:\\*\\?\"<>|]");
        Matcher scrambler = scramblePattern.matcher(fileName);
        return scrambler.replaceAll("_");
    }
    
    
    /**
     * Parse the input using a NumberFormatter.  If the number cannot be parsed, the error key
     * <em>number.invalidNumber</em> will be added to the errors.
     */
    public static Number toNumber(String input) {
    	if(Helper.nullOuVide(input)) {
    		return null;
    	}
    	input=input.trim();
    	if(!"0".equals(input) && !input.startsWith("0.") && input.startsWith("0")) {
    		//ca commence par un zero mais ce n'est ni un 0. ni un 0
    		return null;
    	}
    	input = input.replace("%", "");
        ParsePosition pp = new ParsePosition(0);
        for (NumberFormat format : getNumberFormats()) {
            pp.setIndex(0);
            Number number = format.parse(input, pp);
            if (number != null && input.length() == pp.getIndex()) return number;
        }
        return null;
    }
    
	private final static NumberFormat[] getNumberFormats() {
        NumberFormat[] formats =  { NumberFormat.getInstance(Locale.FRANCE), NumberFormat.getInstance(Locale.ENGLISH) };
        for (NumberFormat format : formats) {
            ((DecimalFormat) format).setParseBigDecimal(true);
            //((DecimalFormat) format).setGroupingUsed(false);
        }
        return formats;
    }    
	
	
	public static boolean validationEmail(String email) {
		if (!email.contains("@")) {
			return false;
		}
		return true;
	}
	
	public static boolean isNotEmpty(Collection<?> list) {
		return list != null && !list.isEmpty();
	}
	
	/**
	 * Construit une Collection<Integer> issue de la valeur du cookie (;pk1;;pk2;;...;)
	 * @param cookieValeur
	 * @return
	 */
	public static Collection<Integer> getCollectionFromPanierCookie(String cookieValeur){
		Collection<Integer> panierIds = new HashSet<Integer>();
		if(Helper.nullOuVide(cookieValeur)){
			return panierIds;
		}
		cookieValeur = cookieValeur.replaceAll(";;", ",");
		cookieValeur = cookieValeur.replaceAll(";", "");
		
		if(! Helper.nullOuVide(cookieValeur)){
			String[] tableauIds =  cookieValeur.split(",");
			panierIds.clear();
			for(String id : tableauIds){
				if(!Helper.nullOuVide(id)){
					panierIds.add(Helper.toInteger(id));
				}
			}
		}
		return panierIds;		
	}
	
	public static void iterableToList(Iterable source, List result) {
		source.forEach(result::add);
	}

}
