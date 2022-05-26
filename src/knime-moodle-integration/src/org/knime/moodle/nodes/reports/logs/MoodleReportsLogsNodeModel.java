package org.knime.moodle.nodes.reports.logs;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.container.CloseableRowIterator;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.streamable.DataTableRowInput;
import org.knime.moodle.internals.connection.MoodleConnection;
import org.knime.moodle.internals.connection.MoodleConnectionPortObject;
import org.knime.moodle.nodes.connector.MoodleConnectorNodeModel;

import es.ubu.lsi.ubumonitor.controllers.load.Connection;
import es.ubu.lsi.ubumonitor.controllers.load.Constants;
import es.ubu.lsi.ubumonitor.controllers.load.PopulateCourse;
import es.ubu.lsi.ubumonitor.model.Course;
import es.ubu.lsi.ubumonitor.model.DataBase;

//import es.ubu.lsi.ubumonitor.model.LogLine;
//import es.ubu.lsi.ubumonitor.model.Logs;
import org.knime.moodle.internals.logs.LogLine;
import org.knime.moodle.internals.logs.Logs;
import org.knime.moodle.internals.logs.MoodleLogCreator;

import es.ubu.lsi.ubumonitor.model.Role;
import es.ubu.lsi.ubumonitor.util.UtilMethods;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification.Classification;
import es.ubu.lsi.ubumonitor.webservice.api.core.enrol.CoreEnrolGetEnrolledUsers;
import es.ubu.lsi.ubumonitor.webservice.api.core.enrol.CoreEnrolGetUsersCourses;
import es.ubu.lsi.ubumonitor.webservice.webservices.WebService;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This is an example implementation of the node model of the
 * "MoodleCourses" node.
 * 
 * This example node performs simple number formatting
 * ({@link String#format(String, Object...)}) using a user defined format string
 * on all double columns of its input table.
 *
 * @author Fran Gil
 */
public class MoodleReportsLogsNodeModel extends NodeModel {
    
    /**
	 * The logger is used to print info/warning/error messages to the KNIME console
	 * and to the KNIME log file. Retrieve it via 'NodeLogger.getLogger' providing
	 * the class of this node model.
	 */
	private static final NodeLogger LOGGER = NodeLogger.getLogger(MoodleReportsLogsNodeModel.class);

	
	NodeLogger logger=NodeLogger.getLogger("Moodle Integration");
	
	
	/**
	 * The settings key to retrieve and store settings shared between node dialog
	 * and node model. In this case, the key for the number format String that
	 * should be entered by the user in the dialog.
	 */
	private static final String KEY_NUMBER_FOMAT = "number_format";

	/**
	 * The default number format String. This default will round to three decimal
	 * places. For an explanation of the format String specification please refer to
	 * https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
	 */
	private static final String DEFAULT_NUMBER_FORMAT = "%.3f";

	
	private static DataBase database;
	
	
	/**
	 * The settings model to manage the shared settings. This model will hold the
	 * value entered by the user in the dialog and will update once the user changes
	 * the value. Furthermore, it provides methods to easily load and save the value
	 * to and from the shared settings (see:
	 * <br>
	 * {@link #loadValidatedSettingsFrom(NodeSettingsRO)},
	 * {@link #saveSettingsTo(NodeSettingsWO)}). 
	 * <br>
	 * Here, we use a SettingsModelString as the number format is a String. 
	 * There are models for all common data types. Also have a look at the comments 
	 * in the constructor of the {@link MoodleReportsLogsNodeDialog} as the settings 
	 * models are also used to create simple dialogs.
	 */
	private final SettingsModelString m_numberFormatSettings = createNumberFormatSettingsModel();

	/**
	 * Constructor for the node model.
	 */
	protected MoodleReportsLogsNodeModel() {
        super(new PortType[]{MoodleConnectionPortObject.TYPE, BufferedDataTable.TYPE},
		      new PortType[]{MoodleConnectionPortObject.TYPE, BufferedDataTable.TYPE});
	}

	/**
	 * A convenience method to create a new settings model used for the number
	 * format String. This method will also be used in the {@link MoodleReportsLogsNodeDialog}. 
	 * The settings model will sync via the above defined key.
	 * 
	 * @return a new SettingsModelString with the key for the number format String
	 */
	static SettingsModelString createNumberFormatSettingsModel() {
		return new SettingsModelString(KEY_NUMBER_FOMAT, DEFAULT_NUMBER_FORMAT);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected PortObject[] execute(final PortObject[] inObjects, final ExecutionContext exec)
			throws Exception {
		/*
		 * The functionality of the node is implemented in the execute method. This
		 * implementation will format each double column of the input table using a user
		 * provided format String. The output will be one String column for each double
		 * column of the input containing the formatted number from the input table. For
		 * simplicity, all other columns are ignored in this example.
		 * 
		 * Some example log output. This will be printed to the KNIME console and KNIME
		 * log.
		 */

		
		// Puerto in 0: Moodle Connection
		MoodleConnection moodleConnection = ((MoodleConnectionPortObject)inObjects[0]).getMoodleConnection();
		
		
		
		WebService webService = moodleConnection.getWebService();
		

		// datos de entrada  (obtiene id de  cursos)
		// Puerto in 1: Tabla
		BufferedDataTable inTable = (BufferedDataTable)inObjects[1];
		DataTableSpec inSpec = inTable.getDataTableSpec();
		
		CloseableRowIterator rowIterator = inTable.iterator();
				
		int currentRowCounter = 0;		
		
		List<Integer> coursesID = new ArrayList<Integer>();
		
		while (rowIterator.hasNext()) {
			DataRow currentRow = rowIterator.next();
			IntCell CourseIDCell = (IntCell)currentRow.getCell(0);
			coursesID.add(CourseIDCell.getIntValue());
		}
		
		logger.warn("Arra courses" + coursesID.toString());
		
		DataColumnSpec[] cols = new DataColumnSpec[15];
		
		cols[0] = new DataColumnSpecCreator("courseid", IntCell.TYPE).createSpec();
		cols[1] = new DataColumnSpecCreator("time", StringCell.TYPE).createSpec();
		cols[2] = new DataColumnSpecCreator("component", StringCell.TYPE).createSpec();
		cols[3] = new DataColumnSpecCreator("eventName", StringCell.TYPE).createSpec();
		cols[4] = new DataColumnSpecCreator("origin", StringCell.TYPE).createSpec();
		cols[5] = new DataColumnSpecCreator("IPAddress", StringCell.TYPE).createSpec();
		cols[6] = new DataColumnSpecCreator("course", IntCell.TYPE).createSpec();
		cols[7] = new DataColumnSpecCreator("user", IntCell.TYPE).createSpec();
		cols[8] = new DataColumnSpecCreator("targetUser", IntCell.TYPE).createSpec();
		cols[9] = new DataColumnSpecCreator("targetCourse", IntCell.TYPE).createSpec();
		cols[10] = new DataColumnSpecCreator("module", IntCell.TYPE).createSpec();
		cols[11] = new DataColumnSpecCreator("section", IntCell.TYPE).createSpec();
		cols[12] = new DataColumnSpecCreator("item", IntCell.TYPE).createSpec();
		cols[13] = new DataColumnSpecCreator("description", StringCell.TYPE).createSpec();
		cols[14] = new DataColumnSpecCreator("descriptionMapped", IntCell.TYPE).createSpec();
		
		DataTableSpec outputSpec = new DataTableSpec(cols); 
	    BufferedDataContainer container = exec.createDataContainer(outputSpec);
	    
	    // TODO: Leer courseid desde tabla cursos 
	    // a) Columna directa courseid
	    // b) (columna en settings)
	    	    
	    
		int rowCounter = 0;
		for(int j = 0; j < coursesID.size(); ++j ) {		
		
		    int courseid = coursesID.get(j); 	
		
		    
		    logger.warn("courseid " + courseid);
		    
		    // Obtener logs curso
		    		    
//		    "http://192.168.1.20/report/log/index.php?download=json&id=657&chooselog=1&logreader=logstore_standard&sesskey=q9sSEyjGWG"
	
			RequestBody formBody = new FormBody.Builder()
					.add("download", "csv")
					.add("lang", "en")
					.add("id", String.valueOf(courseid))
					.add("chooselog", "1")
					.add("logreader", "logstore_standard")
					.build();

			String url = moodleConnection.getHostname() + "/report/log/index.php";			
			Response response = Connection.getResponse(new Request.Builder().url(url)
							.post(formBody)
							.addHeader("Cookie", "MoodleSession=" + moodleConnection.getMoodleSession())
							.build());	    
	    
			//String html = response.body().string();
		    
			//LogCreator.setDateTimeFormatter(ZoneId.systemDefault());
			//Logs logs = new Logs(ZoneId.systemDefault());
			
			//logger.warn("zoneid" + ZoneId.systemDefault().toString());
			
			
			//LogCreator.parserResponse(logs, response.body().charStream());

			Reader reader = response.body().charStream();
						
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
			
			LinkedList<LogLine> logs = new LinkedList<>();
			Set<String> headers = csvParser.getHeaderMap().keySet();
			
			//logger.warn("csv headers" + headers.toString());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss Z");
			
			
			for (CSVRecord csvRecord : csvParser) {
				
				//logger.warn("csv record" + csvRecord.toString());
				
				MoodleLogCreator.setDateTimeFormatter(ZoneId.systemDefault());
				
				LogLine logLine = MoodleLogCreator.createLog(headers, csvRecord);
     			logs.addFirst(logLine);
     			
     			//logger.warn("log line" + logLine.toString());
     			
     			     			
     			 DataCell[] cells = new DataCell[] { 
     		       	new IntCell(courseid), 
     		        new StringCell(logLine.getTime().format(formatter)),
     		        new StringCell(logLine.getComponent().toString()),
     		        new StringCell(logLine.getEventName().toString()),
     		        new StringCell(logLine.getOrigin().toString()),
     		        new StringCell(logLine.getIPAddress().toString()),
     		        new IntCell(logLine.getCourse()),
     		        new IntCell(logLine.getUser()),
     		        new IntCell(logLine.getTargetUser()),
     		        new IntCell(logLine.getTargetCourse()),
     		        new IntCell(logLine.getModule()),
     		        new IntCell(logLine.getSection()),
     		        new IntCell(logLine.getItem()),
     		        new StringCell(logLine.getDescription()),
     		        new IntCell(logLine.getDescriptionMapped())
     		     };
     				    
     			 
     		 	 DataRow row = new DefaultRow(new RowKey("Row" + rowCounter++), cells);
     		     container.addRowToTable(row);
     			
			}
						
			//logger.warn("reports logs" + logs.toString());

			
		}    
		    
		container.close();
		BufferedDataTable out = container.getTable();
		return new PortObject[] { (MoodleConnectionPortObject)inObjects[0], out };
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PortObjectSpec[] configure(final PortObjectSpec[] inSpecs) throws InvalidSettingsException {

	
		DataColumnSpec[] cols = new DataColumnSpec[15];
		
		cols[0] = new DataColumnSpecCreator("courseid", IntCell.TYPE).createSpec();
		cols[1] = new DataColumnSpecCreator("time", StringCell.TYPE).createSpec();
		cols[2] = new DataColumnSpecCreator("component", StringCell.TYPE).createSpec();
		cols[3] = new DataColumnSpecCreator("eventName", StringCell.TYPE).createSpec();
		cols[4] = new DataColumnSpecCreator("origin", StringCell.TYPE).createSpec();
		cols[5] = new DataColumnSpecCreator("IPAddress", StringCell.TYPE).createSpec();
		cols[6] = new DataColumnSpecCreator("course", IntCell.TYPE).createSpec();
		cols[7] = new DataColumnSpecCreator("user", IntCell.TYPE).createSpec();
		cols[8] = new DataColumnSpecCreator("targetUser", IntCell.TYPE).createSpec();
		cols[9] = new DataColumnSpecCreator("targetCourse", IntCell.TYPE).createSpec();
		cols[10] = new DataColumnSpecCreator("module", IntCell.TYPE).createSpec();
		cols[11] = new DataColumnSpecCreator("section", IntCell.TYPE).createSpec();
		cols[12] = new DataColumnSpecCreator("item", IntCell.TYPE).createSpec();
		cols[13] = new DataColumnSpecCreator("description", StringCell.TYPE).createSpec();
		cols[14] = new DataColumnSpecCreator("descriptionMapped", IntCell.TYPE).createSpec();
		
		DataTableSpec outputSpec = new DataTableSpec(cols); 
		
		return new PortObjectSpec[]{ inSpecs[0], outputSpec };
		
	}

	/**
	 * Creates the output table spec from the input spec. For each double column in
	 * the input, one String column will be created containing the formatted double
	 * value as String.
	 * 
	 * @param inputTableSpec
	 * @return
	 */
	private DataTableSpec createOutputSpec(DataTableSpec inputTableSpec) {
		List<DataColumnSpec> newColumnSpecs = new ArrayList<>();
		// Iterate over the input column specs
		for (int i = 0; i < inputTableSpec.getNumColumns(); i++) {
			DataColumnSpec columnSpec = inputTableSpec.getColumnSpec(i);
			/*
			 * If the column is a double column (hence there are double cells), we create a
			 * new DataColumnSpec with column type String and a new column name. Here, we
			 * wrap the original column name with 'Formatted(...)'.
			 */
			if (columnSpec.getType().getCellClass().equals(DoubleCell.class)) {
				String newName = "Formatted(" + columnSpec.getName() + ")";
				DataColumnSpecCreator specCreator = new DataColumnSpecCreator(newName, StringCell.TYPE);
				newColumnSpecs.add(specCreator.createSpec());
			}
		}

		// Create and return a new DataTableSpec from the list of DataColumnSpecs.
		DataColumnSpec[] newColumnSpecsArray = newColumnSpecs.toArray(new DataColumnSpec[newColumnSpecs.size()]);
		return new DataTableSpec(newColumnSpecsArray);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {
		/*
		 * Save user settings to the NodeSettings object. SettingsModels already know how to
		 * save them self to a NodeSettings object by calling the below method. In general,
		 * the NodeSettings object is just a key-value store and has methods to write
		 * all common data types. Hence, you can easily write your settings manually.
		 * See the methods of the NodeSettingsWO.
		 */
		m_numberFormatSettings.saveSettingsTo(settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
		/*
		 * Load (valid) settings from the NodeSettings object. It can be safely assumed that
		 * the settings are validated by the method below.
		 * 
		 * The SettingsModel will handle the loading. After this call, the current value
		 * (from the view) can be retrieved from the settings model.
		 */
		m_numberFormatSettings.loadSettingsFrom(settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
		/*
		 * Check if the settings could be applied to our model e.g. if the user provided
		 * format String is empty. In this case we do not need to check as this is
		 * already handled in the dialog. Do not actually set any values of any member
		 * variables.
		 */
		m_numberFormatSettings.validateSettings(settings);
	}

	@Override
	protected void loadInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		/*
		 * Advanced method, usually left empty. Everything that is
		 * handed to the output ports is loaded automatically (data returned by the execute
		 * method, models loaded in loadModelContent, and user settings set through
		 * loadSettingsFrom - is all taken care of). Only load the internals
		 * that need to be restored (e.g. data used by the views).
		 */
	}

	@Override
	protected void saveInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		/*
		 * Advanced method, usually left empty. Everything
		 * written to the output ports is saved automatically (data returned by the execute
		 * method, models saved in the saveModelContent, and user settings saved through
		 * saveSettingsTo - is all taken care of). Save only the internals
		 * that need to be preserved (e.g. data used by the views).
		 */
	}

	@Override
	protected void reset() {
		/*
		 * Code executed on a reset of the node. Models built during execute are cleared
		 * and the data handled in loadInternals/saveInternals will be erased.
		 */
	}
}

