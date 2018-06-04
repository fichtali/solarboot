package emx.solar.pack.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import emx.solar.pack.utils.Helper;



public class RequeteHql {
	
	/**
	 * Constantes indiquant l'opérateur
	 */	
	public enum Operateur {
		EGAL,SUP_EGAL,INF_EGAL,INF,SUP,LIKE,COMMENCE_PAR,FINIT_PAR,EGAL_IGNORECASE,IN,IS_NULL,IS_NOT_NULL,NOT_EGAL,
		NOT_IN,IS_EMPTY,SIZE_EGAL,PROPERTY_INF_EGAL,PROPERTY_EGAL, PROPERTY_NOT_EGAL, IS_NOT_EMPTY };
	
	//protected static Log log = Log.getInstance(RequeteHql.class);
	
	
	private static String escape(String valeur) {
		if(valeur.indexOf("%")>=0)
			valeur = valeur.replaceAll("%", "\\\\%");
		if(valeur.indexOf("_")>=0)
			valeur = valeur.replaceAll("_", "\\\\_");
		return valeur;
	}	
	/**
	 * Method Ajoute un crit�re de type String
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereString(Criteria critere, String nom, String valeur, Operateur operateur) {
		if(Helper.nullSiVide(valeur)==null)
			return critere;
		
		return (Criteria)createCritere(critere, nom, valeur, operateur, true);
	}
	
	/**
	 * Method Ajoute un crit�re de type SQL
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereSql(Criteria critere, String sqlString) {

		if(Helper.nullSiVide(sqlString)==null)
			return critere;

		critere.add(Restrictions.sqlRestriction(sqlString));
		return critere;
	}
	

	public static Set<Criterion> createDateCriterion(Set<Criterion> crits, String propertyName, Date beginDate, Date endDate) {
		if (crits == null) {
			crits = new HashSet<Criterion>();
		}
		if (beginDate != null) {
			crits.add(Restrictions.ge(propertyName, beginDate));
		}
		if (endDate != null) {
			crits.add(Restrictions.le(propertyName, endDate));
		}
		return crits;
	}
	
	/**
	 * Cr�e un crition et l'ajoute � la requete si besoin
	 * Etablit une jointure si besoin
	 * @param critere: le critere auquel il faut ajouter le criterion cr��
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param join: tables de jointures �ventuelles
	 * @param add: indique s'il faut ajouter le critere � la recherche
	 * @return le crit�rion si add==false, sinon le crit�re compl�t�
	 */
	private static Object createCritere(
		Criteria critere,
		String nom,
		Object valeur,
		Operateur operateur, boolean add) {
			
		Criterion temp = null;
			
		switch(operateur) {
			case EGAL:
				temp = Restrictions.eq(nom, valeur);
				break;
			case SUP_EGAL:
				temp = Restrictions.ge(nom, valeur);
				break;
			case INF_EGAL:
				temp = Restrictions.le(nom, valeur);
				break;
			case INF:
				temp = Restrictions.lt(nom, valeur);
				break;
			case SUP:
				temp = Restrictions.gt(nom, valeur);
				break;
			case COMMENCE_PAR:
				temp = Restrictions.ilike(nom, escape((String)valeur), MatchMode.START);
				break;
			case FINIT_PAR:
				temp = Restrictions.ilike(nom, escape((String)valeur), MatchMode.END);
				break;
			case LIKE :
				temp = Restrictions.ilike(nom, escape((String)valeur), MatchMode.ANYWHERE);
				break;
			case EGAL_IGNORECASE:
				temp = Restrictions.ilike(nom, escape((String)valeur), MatchMode.EXACT);
				break;
			case IN:
				temp = Restrictions.in(nom, (Collection<?>)valeur);
				break;
			case NOT_IN:
				temp = Restrictions.not(Restrictions.in(nom, (Collection<?>)valeur));
				break;
			case IS_NULL:
				temp = Restrictions.isNull(nom);
				break;
			case IS_NOT_NULL:
				temp = Restrictions.isNotNull(nom);
				break;
			case NOT_EGAL:
				temp = Restrictions.not(Restrictions.eq(nom, valeur));
				break;
			case IS_EMPTY:
				temp = Restrictions.isEmpty(nom);
				break;
			case IS_NOT_EMPTY:
				temp = Restrictions.isNotEmpty(nom);
				break;
			case SIZE_EGAL:
				temp = Restrictions.sizeEq(nom, ((Integer)valeur).intValue());
				break;
			case PROPERTY_INF_EGAL:
				temp = Restrictions.leProperty(nom, (String)valeur);
				break;				
			case PROPERTY_EGAL:
				temp = Restrictions.eqProperty(nom, (String)valeur);
				break;	
			case PROPERTY_NOT_EGAL:
				temp = Restrictions.neProperty(nom, (String)valeur);
				break;	
		}
		if(!add) {
			//log.debug("createCriterion:"+nom+":",valeur,", oper=",operateur);
			return temp;
		}
				
		if(temp!=null) {
			critere.add(temp);
			//log.debug("addCritere:",nom,":",valeur,", oper=",operateur);
		}
			
		
		return critere;	
	}

	/**
	 * Effectue la jointure
	 * @param critere
	 * @param join
	 * @return Criteria
	 * @throws HibernateException
	 */
	public static Criteria doJointure(
		Criteria critere,
		String join) {
			
		try {
			if(join!=null) {
				StringTokenizer tokenizer = new StringTokenizer(join,".");
				while(tokenizer.hasMoreTokens()) {	
					String token = 	tokenizer.nextToken();		
					critere = critere.createCriteria(token, token);
				}
			}
		} catch (HibernateException e){
			//log.error(e);
			//log.error(e);
		}
		return critere;
	}
	
	

	/**
	 * Ajoute un crit�re de type Integer
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereInteger(Criteria critere, String nom, Integer valeur, Operateur operateur) {
		
		if(valeur==null)
			return critere;
			
		return (Criteria)createCritere(critere, nom, valeur, operateur,  true);
	}
	
	/**
	 * Ajoute un crit�re de type Boolean
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereBoolean(Criteria critere, String nom, Boolean valeur) {
		
		if(valeur==null)
			return critere;
			
		return (Criteria)createCritere(critere, nom, valeur, Operateur.EGAL, true);
	}
	
	/**
	 * Ajoute un crit�re de type Float
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereFloat(Criteria critere, String nom, Float valeur, Operateur operateur) {
		
		if(valeur==null)
			return critere;
			
		return (Criteria)createCritere(critere, nom, valeur, operateur, true);
	}
	
	/**
	 * Ajoute un crit�re de type enum
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereEnum(Criteria critere, String nom, Enum<?> valeur, Operateur operateur) {
		
		if(valeur==null)
			return critere;
			
		return (Criteria)createCritere(critere, nom, valeur, operateur, true);
	}	
	
	/**
	 * Ajoute un crit�re de type is Null is addCritere est � true.
	 * Ne fais rien si addCrietere est � false
	 * @param critere
	 * @param nom
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereIsNull(Criteria critere, String nom, boolean addCritere) {	

		if(!addCritere)
			return critere;

		return (Criteria)createCritere(critere, nom, null, Operateur.IS_NULL, true);
	}
	
	/**
	 * Cr�e un crit�ron de type is Null pour faire des OR
	 * @param critere
	 * @param nom
	 * @param la table de jointure si besoin
	 * @return Criterion
	 */
	public static Criterion createCriterionIsNull(String nom) {
		
		return (Criterion)createCritere(null, nom, null, Operateur.IS_NULL, false);
	}
	/**
	 * Cr�e un crit�ron de type is Not Null pour faire des OR
	 * @param critere
	 * @param nom
	 * @param la table de jointure si besoin
	 * @return Criterion
	 */
	public static Criterion createCriterionIsNotNull(String nom) {
		
		return (Criterion)createCritere(null, nom, null, Operateur.IS_NOT_NULL, false);
	}
	
	/**
	 * Ajoute un crit�re de type is Null si le boolean addCritere est true
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereIsNotNull(Criteria critere, String nom, boolean addCritere) {
		
		if(!addCritere)
			return critere;
			
		return (Criteria)createCritere(critere, nom, null,  Operateur.IS_NOT_NULL, true);
	}
	
	/**
	 * Ajoute un crit�re de type isEmpty si le boolean addCritere est true
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereIsEmpty(Criteria critere, String nom, boolean addCritere) {
		
		if(!addCritere)
			return critere;
			
		return (Criteria)createCritere(critere, nom, null,  Operateur.IS_EMPTY, true);
	}
	
	/**
	 * Ajoute un crit�re de type isEmpty si le boolean addCritere est true
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereIsNotEmpty(Criteria critere, String nom, boolean addCritere) {
		
		if(!addCritere)
			return critere;
			
		return (Criteria)createCritere(critere, nom, null,  Operateur.IS_NOT_EMPTY, true);
	}	
	
	/**
	 * Compare la taille de la collection donn�e avec l'int donn�
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereTaille(Criteria critere, String nom, int taille) {
		
		return (Criteria)createCritere(critere, nom, new Integer(taille),  Operateur.SIZE_EGAL, true);
	}
	
	/**
	 * Cr�e un crit�re  sans l'ajouter � la requete
	 * afin de permettre les or ou and
	 * @param nom: nom complet (avec jointure s�par�es par des ".")
	 * @param valeur
	 * @param operateur
	 * @return Criteria
	 */
	public static Criterion createCriterion(String nom, Object valeur,  Operateur operateur) {
		if(valeur==null)
			return null;
			
		return (Criterion)createCritere(null, nom, valeur, operateur, false);
	}

	/**
	 * Ajoute un crit�re de type date.
	 * @param critere
	 * @param nom
	 * @param valeur
	 * @param operateur
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereDate(Criteria critere, String nom, Date valeur,  Operateur operateur) {

		if(valeur==null)
			return critere;

		//Date date = Helper.toDate(valeur);
		return (Criteria)createCritere(critere, nom, valeur, operateur, true);	
		

	}
	
	/**
	 * Ajoute un crit�re de type where truc in (liste).
	 * ---> Si la liste est vide, le crit�re n'est pas ajout�
	 * @param critere
	 * @param nom
	 * @param valeurs: la collection contenant les valeurs
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereIn(Criteria critere, String nom, Collection<?> valeurs) {
		
		if(valeurs==null || valeurs.isEmpty())
			return critere;
			
    	return (Criteria)createCritere(critere, nom, valeurs,  Operateur.IN, true);	
	}
	
	/**
	 * Ajoute un crit�re de type where truc not in (liste).
	 * ---> Si la liste est vide, le crit�re n'est pas ajout�
	 * @param critere
	 * @param nom
	 * @param valeurs: la collection contenant les valeurs
	 * @param la table de jointure si besoin
	 * @return Criteria
	 */
	public static Criteria addCritereNotIn(Criteria critere, String nom, Collection<?> valeurs) {
		
		if(valeurs==null || valeurs.isEmpty())
			return critere;
			
    	return (Criteria)createCritere(critere, nom, valeurs,  Operateur.NOT_IN, true);	
	}

	/**
	 * Calcule la taille d'une collection qui n'est pas encore charg�e sans l'initialiser enti�rement
	 * (�quivalent � un count(*)
	 * @param col
	 * @param s
	 * @return int
	 */
	public static int calculeTaille(Collection<?> col) {
		if(col==null) {
			return 0;
		}
		
		Long taille = null;
		try {
		//	taille =(Long) RequeteHql.getSession().createFilter(col, "select count(*)" ).setFlushMode(FlushMode.MANUAL).uniqueResult();
		} catch (HibernateException e) {
			//log.error(e);
		}
		return (taille!=null ? taille.intValue() : 0);
	}
	
	/**
	 * Calcule la taille d'une collection qui n'est pas encore charg�e sans l'initialiser enti�rement
	 * (�quivalent � un count(*)
	 * @param col
	 * @param s
	 * @return int
	 */
	public static int calculeTaille(Map<?,?> col) {
		if(col==null) {
			return 0;
		}
		
		Long taille = null;
		try {
			//taille =(Long) RequeteHql.getSession().createFilter(col, "select count(*)" ).setFlushMode(FlushMode.MANUAL).uniqueResult();
		} catch (HibernateException e) {
			//log.error(e);
		}
		return (taille!=null ? taille.intValue() : 0);
	}	
	
	public static Criteria createAlias(Map<String, Criteria> aliasMap, Criteria q, String joiture, String alias, Integer joinType) {
		if(aliasMap==null) {
			return null;
		}
		if(aliasMap.get(alias)==null) {
			return aliasMap.put(alias, q.createAlias(joiture, alias, joinType));
		}
		return null;
	}
	
	public static DetachedCriteria createAlias(Map<String, DetachedCriteria> aliasMap, DetachedCriteria q, String joiture, String alias, Integer joinType) {
		if(aliasMap==null) {
			return null;
		}
		if(aliasMap.get(alias)==null) {
			return aliasMap.put(alias, q.createAlias(joiture, alias, joinType));
		}
		return null;
	}
	
	public static Criteria createAlias(Map<String, Criteria> aliasMap, Criteria q, String joiture, String alias, Integer joinType,Criterion c) {
		if(aliasMap==null) {
			return null;
		}
		if(aliasMap.get(alias)==null) {
			return aliasMap.put(alias, q.createAlias(joiture, alias, joinType,c));
		}
		return null;
	}
	
	/**
	 * Ajoute un alias s'il n'existe pas d�j� sur le criteria
	 * (et l'ajoute dans la map s'il n'existait pas pour appel ult�rieur)
	 * @param aliasMap
	 * @param q
	 * @param joiture
	 * @param alias
	 * @return Criteria
	 */
	public static Criteria createAlias(Map<String,Criteria> aliasMap, Criteria q, String joiture, String alias) {
		if(aliasMap.get(alias)==null) {
			return aliasMap.put(alias, q.createAlias(joiture, alias));
		}
		return null;
	}	
	/**
	 * Ajoute un alias s'il n'existe pas d�j� sur le detachedccriteria
	 * (et l'ajoute dans la map s'il n'existait pas pour appel ult�rieur)
	 * @param aliasMap
	 * @param q
	 * @param joiture
	 * @param alias
	 * @return Criteria
	 */
	public static DetachedCriteria createAlias(Map<String,DetachedCriteria> aliasMap, DetachedCriteria d, String joiture, String alias) {
		if(aliasMap.get(alias)==null) {
			return aliasMap.put(alias, d.createAlias(joiture, alias));
		}
		return null;
	}	


	
	

	/**
	 * Ajoute un alias s'il n'existe pas d�j� sur le criteria
	 * (et l'ajoute dans la map s'il n'existait pas pour appel ult�rieur)
	 * @param aliasMap
	 * @param q
	 * @param joiture
	 * @param alias
	 * @return Criteria
	 */
	public static Criteria createMultipleAlias(Map<String,Criteria> aliasMap, Criteria q, String jointures) {
		
		StringTokenizer tokenizer = new StringTokenizer(jointures,".");
		while(tokenizer.hasMoreTokens()) {	
			String jointure = 	tokenizer.nextToken();		
			if(aliasMap.get(jointure)==null) {
				return (Criteria)aliasMap.put(jointure, q.createAlias(jointure, jointure));
			}
		}
		
		return null;
	}

	/** 
	 * Calcul le nombre de lignes d'une requete select avec Criteria
	 * !! les setMaxResults,setFirstResult et order doivent absolument etre positionn�s sur le Criteria apres cet appel !!
	 */
	public static int compteLignes(Criteria q) {
		//log.debug("compteLignes");					
		q.setProjection(Projections.rowCount());
		return ((Number)q.uniqueResult()).intValue();	
	}
	
	/** 
	 * Formate la requete pour la paginatedList : limites et tris
	 */
	public static void putPaginatedListOptions(Criteria q,int numeroPage,int lignesParPages,String triParDefaut,String triColonne, /*SortOrderEnum triDirection,*/boolean isPaginatedContentType) {
		//log.debug("putPaginatedListOptions");
		if(isPaginatedContentType){
			q.setFirstResult((numeroPage-1)*lignesParPages);	
			q.setMaxResults(lignesParPages);
		}
		else {
			q.setMaxResults(5000);
		}
		
		if(Helper.nullOuVide(triColonne) && Helper.nullOuVide(triParDefaut)){
			return;
		}						
		else if(Helper.nullOuVide(triColonne)) {			
			triColonne = triParDefaut;
		}
		
		/*if(triDirection==null) {
			triDirection = SortOrderEnum.ASCENDING;
		}
		if(triDirection.getCode()==2) {
			q.addOrder(Order.asc(triColonne));	
		}*/
		else {
			q.addOrder(Order.desc(triColonne));
		}	
	}
	
	/**
	 * Formate la requete pour la paginatedList : limites et tris
	 * Query q
	 */
	public static void putPaginatedListOptions(Query q, int numeroPage, int lignesParPages, String triParDefaut, String triColonne,
			/*SortOrderEnum triDirection,*/ boolean isPaginatedContentType) {
		//log.debug("putPaginatedListOptions");
		if (isPaginatedContentType) {
			q.setFirstResult((numeroPage - 1) * lignesParPages);
			q.setMaxResults(lignesParPages);
		} else {
			q.setMaxResults(5000);
		}

		if (Helper.nullOuVide(triColonne) && Helper.nullOuVide(triParDefaut)) {
			return;
		} else if (Helper.nullOuVide(triColonne)) {
			triColonne = triParDefaut;
		}

		/*if (triDirection == null) {
			triDirection = SortOrderEnum.ASCENDING;
		}
		if (triDirection.getCode() == 2) {
			q.setParameter("orderBy", triColonne+ " asc");	
			//q. .addOrder(Order.asc(triColonne));
		} else {
			q.setParameter("orderBy", triColonne+ " desc");	
			//q.addOrder(Order.desc(triColonne));
		}*/
	}
	
	/**
	 * 	Ajoute une liste de regroupements
	 */
	public static void ajoutAlias(String[] aliases, Criteria q,
			Map<String, Criteria> aliasMap, Integer typeJointure) {
		String fullAlias = "";
		if(aliases!=null) {
			for(int i=0;i<aliases.length;i++) {
				if(i!=0) {
					fullAlias = aliases[i-1]+".";
				}
				RequeteHql.createAlias(aliasMap, q, fullAlias+aliases[i], aliases[i], typeJointure);
			}
		}		

	}	
	
	

	/**
	 * Cr�er une disjunction et ajoute tous les criteres donn�s (ils doivent tous etre non null)
	 * @param q
	 * @param criteres
	 */
	public static void addCritereOr(Criteria q, Criterion... criteres) {
		Disjunction disjunction = Restrictions.disjunction();
		for(Criterion cri : criteres) {
			if (cri != null)
				disjunction.add(cri);
		}
		q.add(disjunction);		
		
	}
	
	/**
	 * Cr�er une disjunction et ajoute tous les criteres donn�s (ils doivent tous etre non null)
	 * @param q
	 * @param criteres
	 */
	public static void addCritereOr(Criteria q,List<Criterion> criteres) {
		Disjunction disjunction = Restrictions.disjunction();
		for(Criterion cri : criteres) {
			disjunction.add(cri);
		}
		q.add(disjunction);		
		
	}
	
	public static void addCriterions(Set<Criterion> crits, DetachedCriteria criteria) {
		for (Criterion criterion : crits) {
			criteria.add(criterion);
		}
	}



}