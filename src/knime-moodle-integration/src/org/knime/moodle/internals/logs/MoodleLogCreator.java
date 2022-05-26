package org.knime.moodle.internals.logs;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.knime.core.node.NodeLogger;

// import org.knime.moodle.util.log.logtypes.LogTypes;
// import org.knime.moodle.util.log.logtypes.ReferencesLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.ubumonitor.controllers.Controller;
// import es.ubu.lsi.ubumonitor.model.Component;
// import es.ubu.lsi.ubumonitor.model.Event;
//import es.ubu.lsi.ubumonitor.model.LogLine;
//import es.ubu.lsi.ubumonitor.model.Logs;
import es.ubu.lsi.ubumonitor.model.Origin;

import org.knime.moodle.internals.logs.Logs;
import org.knime.moodle.internals.logs.Component;
import org.knime.moodle.internals.logs.Event;
import org.knime.moodle.internals.logs.LogDescription;
import org.knime.moodle.internals.logs.LogLine;


import es.ubu.lsi.ubumonitor.util.I18n;
import okhttp3.Response;


public class MoodleLogCreator {

	static NodeLogger logger=NodeLogger.getLogger("Moodle Integration");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MoodleLogCreator.class);
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yy, kk:mm");

	private static final Set<String> NOT_AVAIBLE_COMPONENTS = new TreeSet<>();
	private static final Set<String> NOT_AVAIBLE_EVENTS = new TreeSet<>();
	
	private static final Pattern INTEGER_PATTERN = Pattern
			.compile("(['\"])(?<idQuote>-?\\d+)\\1|[^'\"](?<idNoQuote>\\d+)[^'\"]");

	public static final String TIME = "Time";
	public static final String USER_FULL_NAME = "User full name";
	public static final String AFFECTED_USER = "Affected user";
	public static final String EVENT_CONTEXT = "Event context";
	public static final String COMPONENT = "Component";
	public static final String EVENT_NAME = "Event name";
	public static final String DESCRIPTION = "Description";
	public static final String ORIGIN = "Origin";
	public static final String IP_ADRESS = "IP address";

	private static final Set<String> ALL_COLUMNS = new HashSet<>(Arrays.asList(TIME, USER_FULL_NAME, AFFECTED_USER,
			EVENT_CONTEXT, COMPONENT, EVENT_NAME, DESCRIPTION, ORIGIN, IP_ADRESS));

	/**
	 * Cambia la zona horia del formateador de tiempo
	 * 
	 * @param zoneId zona horaria
	 */
	public static void setDateTimeFormatter(ZoneId zoneId) {
		dateTimeFormatter = dateTimeFormatter.withZone(zoneId);
	}



	public static void parserResponse(Logs logs, Reader reader) throws IOException {

		try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
			List<LogLine> logList = MoodleLogCreator.createLogs(csvParser);
			logs.addAll(logList);
		}

	}

	/**
	 * Crea todos los logs la lista
	 * 
	 * @param allLogs los logs listados en mapas con clave la columna del logline
	 * @return los logs creados
	 */
	private static List<LogLine> createLogs(CSVParser csvParser) {
		LinkedList<LogLine> logs = new LinkedList<>();
		Set<String> headers = csvParser.getHeaderMap()
				.keySet();
		LOGGER.info("Los nombres de las columnas del csv son: {}", headers);
		if (!headers.isEmpty() && headers.stream()
				.noneMatch(ALL_COLUMNS::contains)) {
			throw new IllegalArgumentException(I18n.get("error.logsEnglish"));
		}
		for (CSVRecord csvRecord : csvParser) {
			LogLine logLine = createLog(headers, csvRecord);

			logs.addFirst(logLine);

		}

		return logs;
	}

	/**
	 * Crea un log y añade los atributos de las columnas y a que van asociado
	 * (usuario, course module etc)
	 * 
	 * @param mapLog mapa con clave las columnas de la linea de log
	 * @return logline con atributos
	 */
	public static LogLine createLog(Set<String> headers, CSVRecord csvRecord) {

		LogLine log = new LogLine();
		Component component;
		if (headers.contains(MoodleLogCreator.COMPONENT)) {
			component = Component.get(csvRecord.get(MoodleLogCreator.COMPONENT));
			if (component == Component.COMPONENT_NOT_AVAILABLE) {
				NOT_AVAIBLE_COMPONENTS.add(csvRecord.get(MoodleLogCreator.COMPONENT));
			}
		} else {
			component = Component.COMPONENT_NOT_AVAILABLE;
		}
		log.setComponent(component);

		Event event;
		if (headers.contains(MoodleLogCreator.EVENT_NAME)) {
			event = Event.get(csvRecord.get(MoodleLogCreator.EVENT_NAME));
			if (event == Event.EVENT_NOT_AVAILABLE) {
				NOT_AVAIBLE_EVENTS.add(csvRecord.get(MoodleLogCreator.EVENT_NAME));
			}
		} else {
			event = Event.EVENT_NOT_AVAILABLE;
		}
		log.setEventName(event);

		if (headers.contains(MoodleLogCreator.DESCRIPTION)) {
			String description = csvRecord.get(MoodleLogCreator.DESCRIPTION);
			List<Integer> ids = getIdsInDescription(description);
			
			logger.warn("LOG CREATOR: descripcion " + description);
			logger.warn("LOG CREATOR: ids " + ids.toString());
			
			
			
			// nuevo sistema de identificación de log LogParameters
			
			
			try {
				//ReferencesLog referencesLog = LogTypes.getReferenceLog(component, event);
				
				
				LogParameters[] logParameters = LogDescription.getLogParamtersList(component, event);
				
				
				logger.warn("LOG PARAMETES " + logParameters.toString());
				
				
				int descriptionMapped = 0;
				
				for (int i=0; i< logParameters.length; i++) 
				{ 
					logger.warn("LOG PARAMETES " + i + ": " + logParameters[i].getName());
					
					if(logParameters[i] != LogParameters.EMPTY) {
						
						descriptionMapped = 1;						
						logger.warn("LOG PARAMETES " + i + ": " + ids.get(i));
						
						logger.warn("REFLECT: " + logParameters[i].getName());
												
						// Invoca al método adecuado de forma dinámica (Reflection)
						Method method = LogLine.class.getDeclaredMethod(logParameters[i].getName(), Integer.TYPE);
						
						logger.warn("REFLECT " + method.toString());
						
						method.invoke(log, ids.get(i));

						logger.warn("REFLECT " + log.getUser());
						logger.warn("REFLECT " + log.getTargetUser());

					}
					
				}
				
				log.setDescriptionMapped(descriptionMapped);				
				
				
								
				// logger.warn("LOG CREATOR: referencesLog class " + referencesLog.getClass());
				
				// logger.warn("LOG CREATOR: referencesLog1 " + referencesLog.toString());
				
				// referencesLog.setLogReferencesAttributes(log, ids);
				
				// logger.warn("LOG CREATOR: referencesLog2 " + log.toString());
				
				/*if (headers.contains(MoodleLogCreator.USER_FULL_NAME) && log.getUser() != null && log.getUser()
						.getFullName() == null) {
					log.getUser()
							.setFullName(csvRecord.get(MoodleLogCreator.USER_FULL_NAME));
				}*/
			} catch (Exception e) {
				LOGGER.error("Problema en linea de log: " + csvRecord + " usando el gestor: con los ids:" + ids, e);
			}
			
			log.setDescription(description);
			
		}

		//considering Time column always first
		String time = csvRecord.get(0);

		ZonedDateTime zdt = ZonedDateTime.parse(time, dateTimeFormatter);
		log.setTime(zdt);

		if (headers.contains(MoodleLogCreator.ORIGIN)) {
			
		
			log.setOrigin(Origin.get(csvRecord.get(MoodleLogCreator.ORIGIN)));

		}

		if (headers.contains(MoodleLogCreator.IP_ADRESS)) {
			log.setIPAddress(csvRecord.get(MoodleLogCreator.IP_ADRESS));
		}

		return log;
	}

	/**
	 * Devuelve los componentes que no existen en el enum {@link model.Component} al
	 * parsear logs
	 * 
	 * @return los componentes que no existen en el enum {@link model.Component}
	 */
	public static Set<String> getNotAvaibleComponents() {
		return NOT_AVAIBLE_COMPONENTS;
	}

	/**
	 * Devuelve los eventos que no existen en el enum {@link model.Event} al parsear
	 * los logs
	 * 
	 * @return eventos que no existen en el enum {@link model.Event}
	 */
	public static Set<String> getNotAvaibleEvents() {
		return NOT_AVAIBLE_EVENTS;
	}

	/**
	 * Busca los integer de la Descripción de una linea de log
	 * 
	 * @param description de la columna decripción
	 * @return lista de integer encontrado en la descripción
	 */
	private static List<Integer> getIdsInDescription(String description) {
		Matcher m = INTEGER_PATTERN.matcher(description);
		List<Integer> list = new ArrayList<>();
		while (m.find()) {
			String integer = null;
			if (m.group("idQuote") != null) { // si el id esta entre comillas simples o dobles
				integer = m.group("idQuote");
			} else if (m.group("idNoQuote") != null) { // si el id no esta entre comillas
				integer = m.group("idNoQuote");
			}

			if (integer != null) {
				try {
					int value = Integer.parseInt(integer);
					list.add(value);
				}catch (Exception e){
					LOGGER.warn("No se ha podido convertir a integer el registro.", e);
				}
				
			}
		}
		return list;
	}

	private MoodleLogCreator() {
		throw new UnsupportedOperationException();
	}

}
