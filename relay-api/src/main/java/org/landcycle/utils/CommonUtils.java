package org.landcycle.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.landcycle.json.AsiaMapper;

/**
 * Base Asia Common utility class
 * 
 */
public class CommonUtils {

	/**
	 * numero di default di decimali in un bigint
	 */
	public final static int DEFAULT_CURRENCY_DECIMALS = 2;

	/**
	 * singleton
	 */
	private static ObjectMapper mapper = new ObjectMapper();
	private static ObjectMapper mapper1 = new ObjectMapper();

	/**
	 * metodo che ritorna una stringa URL-DECODED.
	 * 
	 * @param paramValue
	 * @return
	 */
	public static String urlDecode(String paramValue) {
		if (paramValue == null)
			return null;
		try {
			return URLDecoder.decode(paramValue, "UTF-8");
		} catch (Exception e) {
			return paramValue;
		}
	}

	public static String urlDecodeUnsafe(String paramValue) throws UnsupportedEncodingException {
		if (paramValue == null)
			return null;
		return URLDecoder.decode(paramValue, "UTF-8");
	}

	/**
	 * metodo singleton per la creazione di un ObjectMapper Jackson
	 * 
	 * @return
	 */
	public static ObjectMapper getJsonMapper() {
		if (mapper != null)
			return mapper;
		synchronized (CommonUtils.class) {
			if (mapper != null)
				return mapper;
			mapper = new AsiaMapper();
		}
		return mapper;
	}

	/**
	 * imposta un nuovo ObjectMapper di Jackson
	 * 
	 * @param newMapper
	 * @see CommonUtils#toJsonString(Object)
	 */
	public static synchronized void setJsonMapper(ObjectMapper newMapper) {
		mapper = newMapper;
	}

	/**
	 * Utility per toString
	 * 
	 * @param o
	 * @return
	 */
	public static String bean2string(Object o) {
		String result = "";
		try {
			result = toJsonString(o);
		} catch (Exception e) {
			// ****************************************************************************
			// DO NOTHING
			// ****************************************************************************
		}
		return result;
	}

	/**
	 * 
	 * @param o
	 * @param ignorableFieldNames
	 * @return
	 */
	public static String bean2string(Object o, String[] ignorableFieldNames) {
		String result = "";
		try {
			result = toJsonString(o, ignorableFieldNames);
		} catch (Exception e) {
			// ****************************************************************************
			// DO NOTHING
			// ****************************************************************************
		}
		return result;
	}

	

	/**
	 * converte un oggetto generico in JSON, utilizzando il mapper ritornato da {@link #getJsonMapper()}
	 * 
	 * @param obj
	 *            l'oggetto da serializzare
	 * @return il risultato della serializzazione
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		String jjson = getJsonMapper().writeValueAsString(obj);
		return jjson;
	}

	/**
     * 
     * @param obj
     * @param ignorableFieldNames
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static String toJsonString(Object obj, String[] ignorableFieldNames) throws JsonGenerationException, JsonMappingException, IOException {
        FilterProvider filters = new SimpleFilterProvider().addFilter("rootFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
        mapper1.setFilters(filters);
        mapper1.getSerializationConfig().addMixInAnnotations(Object.class, PropertyFilterMixIn.class);
        return mapper1.writeValueAsString(obj);
    }
     
    @JsonFilter("rootFilter")
    class PropertyFilterMixIn
    {
 
    }


	/**
	 * converte una stringa json nei valori dei campi di un oggetto vuoto passato come parametro
	 * 
	 * @param emptyValue
	 * @param text
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void fromJsonString(Object emptyValue, String text) throws JsonGenerationException, JsonMappingException, IOException {
		if (emptyValue == null)
			throw new IllegalArgumentException("emptyValue cannot be null");
		ObjectReader reader = getJsonMapper().readerForUpdating(emptyValue);
		reader.readValue(text);
	}

	/**
	 * 
	 * @param emptyValue
	 * @param file
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void fromJsonFile(Object emptyValue, File file) throws JsonGenerationException, JsonMappingException, IOException {
		if (emptyValue == null)
			throw new IllegalArgumentException("emptyValue cannot be null");
		ObjectReader reader = getJsonMapper().readerForUpdating(emptyValue);
		reader.readValue(file);
	}

	/**
	 * 
	 * @param emptyValue
	 * @param is
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void fromJsonInputStream(Object emptyValue, InputStream is) throws JsonGenerationException, JsonMappingException, IOException {
		if (emptyValue == null)
			throw new IllegalArgumentException("emptyValue cannot be null");
		ObjectReader reader = getJsonMapper().readerForUpdating(emptyValue);
		reader.readValue(is);
	}

	/**
	 * utilizzato per creare manualmente un array JSON a partire da un array di stringe gia' formattate come JSON
	 * 
	 * @param jsonStrings
	 *            ogni singola stringa e' la rappresentazione di un oggetto in JSON
	 * @return una stringa unica rappresentante l'arrays
	 */
	public static String concatenateJsonArray(String[] jsonStrings) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < jsonStrings.length; i++) {
			@SuppressWarnings("unused")
			String obj = jsonStrings[i];
			if (i > 0)
				sb.append(",");
			sb.append(jsonStrings[i]);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Formatta java.Date una data
	 * 
	 * @param strDate
	 *            dt input
	 * @param format
	 *            formato data
	 * @return data output
	 * @throws Exception
	 *             eccezione
	 */
	public static Date getDate(String strDate, String format) throws Exception {
		Date date;
		try {
			// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);

			date = df.parse(strDate, pos);
			// Check all possible things that signal a parsing error
			if ((date == null) || (pos.getErrorIndex() != -1)) {
				throw new Exception("Data non valida");
			}
		} catch (Exception e) {
			throw new Exception("Data non valida");
		}
		return date;
	}

	/**
	 * Ritorna una data formattata a partire dai millisecondi
	 * 
	 * @param timemillis
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getFormatDate(long timemillis, String format) throws Exception {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date(timemillis));
	}

	/**
	 * Metodo Standard per la conversione da Stringa a Currency con 2 decimali
	 * 
	 * @param currency
	 *            Stirnga da convertire
	 * @return BigDecimal
	 */
	public static BigDecimal stringToCurrency(String currency) {
		return stringToCurrency(currency, DEFAULT_CURRENCY_DECIMALS);
	}

	/**
	 * Metodo Standard per la conversione da Stringa a Currency
	 * 
	 * @param currency
	 *            Stirnga da convertire
	 * @param decimals
	 *            numero di decimali
	 * @return BigDecimal
	 */
	public static BigDecimal stringToCurrency(String currency, int decimals) {
		if (currency == null || StringUtils.isEmpty(currency))
			return null;
		BigDecimal ret = null;
		try {
			ret = new BigDecimal(currency).setScale(decimals, RoundingMode.HALF_EVEN);
		} catch (NumberFormatException e) {
			String msg = String.format("BigDecimal(value='%s', decimals=%d", currency, decimals);
			throw new NumberFormatException(msg);
		}
		return ret;
	}

	/**
	 * 
	 * @param currency
	 * @return
	 */
	public static BigDecimal doubleToCurrency(Double currency) {
		return doubleToCurrency(currency, 2);
	}

	/**
	 * 
	 * @param currency
	 * @param decimals
	 * @return
	 */
	public static BigDecimal doubleToCurrency(Double currency, int decimals) {
		if (currency == null)
			return null;
		BigDecimal ret = null;
		ret = new BigDecimal(currency).setScale(decimals, RoundingMode.HALF_EVEN);
		return ret;
	}

	/**
	 * 
	 * @param currency
	 * @return
	 */
	public static BigDecimal doubleToCurrency(Float currency) {
		return floatToCurrency(currency, 2);
	}

	/**
	 * 
	 * @param currency
	 * @param decimals
	 * @return
	 */
	public static BigDecimal floatToCurrency(Float currency, int decimals) {
		if (currency == null)
			return null;
		BigDecimal ret = null;
		ret = new BigDecimal(currency).setScale(decimals, RoundingMode.HALF_EVEN);
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String currencyToString(BigDecimal value) {
		return currencyToString(value, DEFAULT_CURRENCY_DECIMALS);
	}

	/**
	 * 
	 * @param value
	 * @param decimals
	 * @return
	 */
	public static String currencyToString(BigDecimal value, int decimals) {
		if (value == null)
			return null;
		return value.setScale(decimals, RoundingMode.HALF_EVEN).toPlainString();
	}

	/**
	 * converte un array in lista
	 * 
	 * @param entities
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] entities) {
		if (entities == null || entities.length < 1) {
			return null;
		}
		List<T> ret = new ArrayList<T>();
		for (int i = 0; i < entities.length; i++) {
			ret.add(entities[i]);
		}
		return ret;
	}

	/**
	 * convert a list to an array
	 * 
	 * @param entities
	 *            a list of results (obtained from an ORM, for example )
	 * @return null if entities is empty, a full array otherwise
	 */
	public static <T> T[] listToArray(List<T> entities) {
		return listToArray(null, entities, true);
	}

	/**
	 * convert a list to an array
	 * 
	 * @param myType
	 *            the class type of the array: if null, the class is guessed from the first record in the entities list
	 * @param entities
	 *            the list of entities
	 * @param returnNullIfEmpty
	 *            applied when entities has no elements or is null.<BR>
	 *            if false, it returns an empty array, if true it returns null
	 * @return an array of the type specified or guessed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] listToArray(Class<T> myType, List<T> entities, boolean returnNullIfEmpty) {
		Class<T> finalType = myType;
		if (entities != null && entities.size() > 0) {
			if (myType == null) {
				finalType = (Class<T>) entities.get(0).getClass();
			}
			T[] reals = createEmptyArray(finalType, entities.size());
			for (int i = 0; i < reals.length; i++) {
				reals[i] = entities.get(i);
			}
			return reals;
		} else {
			if (myType == null || returnNullIfEmpty) {
				return null;
			} else {
				T[] reals = (T[]) createEmptyArray(myType, 0);
				return reals;
			}
		}
	}

	public static <T> void sortAndRemoveDuplicateSlow(List<T> list, Comparator<? super T> comp) {
		if (list == null)
			return;
		Collections.sort(list, comp);
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		for (T t : list) {
			set.add(t);
		}
		list.clear();
		list.addAll(set);
	}

	/**
	 * Crea un array di oggetti di tipo passato in input
	 * 
	 * @param clazz
	 * @param size
	 * @return
	 */
	public static <T> T[] createEmptyArray(Class<T> clazz, int size) {
		@SuppressWarnings("unchecked")
		T[] reals = (T[]) Array.newInstance(clazz, size);
		return reals;
	}

	/**
	 * 
	 * @param centesimi
	 *            un Long espresso in numero di centesimi (ovvero euro * 100)
	 * @return un valore in valuta compatibile con i centesimi (ovver centesimi/100)
	 */
	public static BigDecimal centsToCurrency(Long centesimi) {
		if (centesimi == null)
			return null;
		BigDecimal ret = new BigDecimal(centesimi).setScale(2);
		ret = ret.scaleByPowerOfTen(-2);
		return ret;
	}

	/**
	 * Metodo di utiliti per formattare in maniera univoca l'identificativo della sessione
	 * 
	 * @param sessionId
	 * @param username
	 * @return
	 */
	public static String formatKeyLog(String sessionId, String username) {
		return new StringBuilder(sessionId).append("-").append(username).toString();
	}

	/**
	 * Calcola il primo giorno del mese a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getFirstDateInMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		int firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola il primo giorno del mese successivo a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getFirstDateNextMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		c.add(Calendar.MONTH, 1);
		int firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola l'ultimo giorno del mese a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getLastDateInMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		int firstDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola l'ultimo giorno del mese successivo a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getLastDateNextMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		c.add(Calendar.MONTH, 1);
		int firstDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola il primo giorno del mese precedente a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getFirstDatePreviousMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		c.add(Calendar.MONTH, -1);
		int firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola l'ultimo giorno del mese precedente a partire da una data passata o se non viene passato nulla calcola la data da sistema
	 * 
	 * @param timemillis
	 *            (Data passata in millisecondi)
	 * @return Data calcolata in formato java.util.date
	 * @throws Exception
	 */
	public static Date getLastDatePreviousMonth(Long timemillis) throws Exception {
		if (timemillis == null)
			timemillis = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timemillis);
		c.add(Calendar.MONTH, -1);
		int firstDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar tmp = Calendar.getInstance();
		tmp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), firstDay);
		Date ret = new Date();
		ret.setTime(tmp.getTimeInMillis());
		return ret;
	}

	/**
	 * Calcola id sessione qarc
	 * 
	 * @param qarc
	 * @return id sessione
	 */
	public static long getSessionIdFromQarc(String qarc) {
		String cleanQarc = qarc.substring(6, qarc.indexOf("z"));
		StringBuilder buildSession = new StringBuilder();
		for (int i = 0; i < cleanQarc.length(); i++) {
			if (i % 2 != 0) {
				buildSession.append(cleanQarc.charAt(i));
			}
		}
		return Long.parseLong(buildSession.toString());
	}
}
