package org.knime.moodle.nodes.courses;

import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


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
import org.knime.moodle.internals.connection.MoodleConnection;
import org.knime.moodle.internals.connection.MoodleConnectionPortObject;
import org.knime.moodle.nodes.connector.MoodleConnectorNodeModel;

import es.ubu.lsi.ubumonitor.controllers.load.Constants;
import es.ubu.lsi.ubumonitor.controllers.load.PopulateCourse;
import es.ubu.lsi.ubumonitor.model.Course;
import es.ubu.lsi.ubumonitor.model.DataBase;
import es.ubu.lsi.ubumonitor.util.UtilMethods;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification.Classification;
import es.ubu.lsi.ubumonitor.webservice.api.core.enrol.CoreEnrolGetUsersCourses;
import es.ubu.lsi.ubumonitor.webservice.webservices.WebService;

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
public class MoodleCoursesNodeModel extends NodeModel {
    
    /**
	 * The logger is used to print info/warning/error messages to the KNIME console
	 * and to the KNIME log file. Retrieve it via 'NodeLogger.getLogger' providing
	 * the class of this node model.
	 */
	private static final NodeLogger LOGGER = NodeLogger.getLogger(MoodleCoursesNodeModel.class);

	
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
	 * in the constructor of the {@link MoodleCoursesNodeDialog} as the settings 
	 * models are also used to create simple dialogs.
	 */
	private final SettingsModelString m_numberFormatSettings = createNumberFormatSettingsModel();

	/**
	 * Constructor for the node model.
	 */
	protected MoodleCoursesNodeModel() {
        super(new PortType[]{MoodleConnectionPortObject.TYPE, BufferedDataTable.TYPE},
		      new PortType[]{MoodleConnectionPortObject.TYPE, BufferedDataTable.TYPE});
	}

	/**
	 * A convenience method to create a new settings model used for the number
	 * format String. This method will also be used in the {@link MoodleCoursesNodeDialog}. 
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

		logger.warn("Entra execute");
		
		
		// Puerto in 0: Moodle Connection
		MoodleConnection moodleConnection = ((MoodleConnectionPortObject)inObjects[0]).getMoodleConnection();
		
		
		// probamos si llegan los parámetros necesarios
		
		logger.warn("MoodleConnector token: " + moodleConnection.getWebService().getToken());
		logger.warn("MoodleConnector MoodleSession: " + moodleConnection.getMoodleSession());		
		logger.warn("MoodleConnector Hostname: " + moodleConnection.getHostname());
		logger.warn("MoodleConnector userid: " + moodleConnection.getUserid());
		logger.warn("MoodleConnector webservice token: " + moodleConnection.getWebService().getToken());
		
		WebService webService = moodleConnection.getWebService();
		

		// datos de entrada
		// Puerto in 1: Tabla
				BufferedDataTable inputTable = (BufferedDataTable)inObjects[1];
		
		
		DataColumnSpec[] cols = new DataColumnSpec[10];
		
		cols[0] = new DataColumnSpecCreator("courseid", IntCell.TYPE).createSpec();
	    cols[1] = new DataColumnSpecCreator("shortname", StringCell.TYPE).createSpec();
	    cols[2] = new DataColumnSpecCreator("fullname", StringCell.TYPE).createSpec();
	    cols[3] = new DataColumnSpecCreator("displayname", StringCell.TYPE).createSpec();
	    cols[4] = new DataColumnSpecCreator("enrolledusercount", IntCell.TYPE).createSpec();
	    cols[5] = new DataColumnSpecCreator("idnumber", StringCell.TYPE).createSpec();
	    cols[6] = new DataColumnSpecCreator("visible", IntCell.TYPE).createSpec();
	    cols[7] = new DataColumnSpecCreator("summary", StringCell.TYPE).createSpec();
	    cols[8] = new DataColumnSpecCreator("summaryformat", IntCell.TYPE).createSpec();
	    cols[9] = new DataColumnSpecCreator("format", StringCell.TYPE).createSpec();
		
		
		DataTableSpec outputSpec = new DataTableSpec(cols); 
	    BufferedDataContainer container = exec.createDataContainer(outputSpec);
	    
		
		
		// Obtener cursos
		JSONArray courses = UtilMethods.getJSONArrayResponse(webService, new CoreEnrolGetUsersCourses(moodleConnection.getUserid()));
			
		for (int i = 0; i < courses.length(); ++i) {
			JSONObject course = courses.getJSONObject(i);
			
			DataCell[] cells = new DataCell[] { 
	        	new IntCell(course.getInt("id")), 
	        	new StringCell(course.getString("shortname")),
	        	new StringCell(course.getString("fullname")),
	        	new StringCell(course.getString("displayname")),
	        	new IntCell(course.getInt("enrolledusercount")),
	        	new StringCell(course.getString("idnumber")),
	        	new IntCell(course.getInt("visible")),
	        	new StringCell(course.getString("summary")),
	        	new IntCell(course.getInt("summaryformat")),
	        	new StringCell(course.getString("format"))
	         };
	 	     DataRow row = new DefaultRow(new RowKey("Row" + i), cells);
	         container.addRowToTable(row);
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

		
        DataColumnSpec[] cols = new DataColumnSpec[10];
		
		cols[0] = new DataColumnSpecCreator("courseid", IntCell.TYPE).createSpec();
	    cols[1] = new DataColumnSpecCreator("shortname", StringCell.TYPE).createSpec();
	    cols[2] = new DataColumnSpecCreator("fullname", StringCell.TYPE).createSpec();
	    cols[3] = new DataColumnSpecCreator("displayname", StringCell.TYPE).createSpec();
	    cols[4] = new DataColumnSpecCreator("enrolledusercount", IntCell.TYPE).createSpec();
	    cols[5] = new DataColumnSpecCreator("idnumber", StringCell.TYPE).createSpec();
	    cols[6] = new DataColumnSpecCreator("visible", IntCell.TYPE).createSpec();
	    cols[7] = new DataColumnSpecCreator("summary", StringCell.TYPE).createSpec();
	    cols[8] = new DataColumnSpecCreator("summaryformat", IntCell.TYPE).createSpec();
	    cols[9] = new DataColumnSpecCreator("format", StringCell.TYPE).createSpec();
		
		
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

