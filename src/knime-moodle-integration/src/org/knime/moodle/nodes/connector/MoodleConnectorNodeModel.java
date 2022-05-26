package org.knime.moodle.nodes.connector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Collection;



import org.json.JSONArray;
import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.container.CloseableRowIterator;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
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
import org.knime.core.node.defaultnodesettings.SettingsModel;
import org.knime.core.node.defaultnodesettings.SettingsModelPassword;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;

import javafx.stage.Stage;
import javafx.scene.web.WebView;

import es.ubu.lsi.ubumonitor.controllers.Controller;
import es.ubu.lsi.ubumonitor.webservice.webservices.WebService;
import es.ubu.lsi.ubumonitor.model.Course;
import es.ubu.lsi.ubumonitor.model.DataBase;
import es.ubu.lsi.ubumonitor.model.Logs;
import es.ubu.lsi.ubumonitor.controllers.load.DownloadLogController;
import es.ubu.lsi.ubumonitor.controllers.load.LogCreator;
import es.ubu.lsi.ubumonitor.controllers.load.Login;
import es.ubu.lsi.ubumonitor.util.UtilMethods;


import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetCoursesByField;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetEnrolledCoursesByTimelineClassification.Classification;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetRecentCourses;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetUserAdministrationOptions;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseGetUserNavigationOptions;
import es.ubu.lsi.ubumonitor.webservice.api.core.course.CoreCourseSearchCourses;
import es.ubu.lsi.ubumonitor.webservice.api.core.enrol.CoreEnrolGetUsersCourses;
import es.ubu.lsi.ubumonitor.controllers.load.DownloadLogController;
import es.ubu.lsi.ubumonitor.controllers.load.LogCreator;


import es.ubu.lsi.ubumonitor.controllers.load.Connection;
import org.knime.moodle.internals.connection.MoodleConnection;
import org.knime.moodle.internals.connection.MoodleConnectionPortObject;
import org.knime.moodle.internals.connection.MoodleConnectionPortObjectSpec;

import okhttp3.Response;

/**
 * This is an example implementation of the node model of the
 * "MoodleConnector" node.
 * 
 * This example node performs simple number formatting
 * ({@link String#format(String, Object...)}) using a user defined format string
 * on all double columns of its input table.
 *
 * @author Fran Gil
 */
public class MoodleConnectorNodeModel extends NodeModel {
    
	private static final NodeLogger LOGGER = NodeLogger.getLogger(MoodleConnectorNodeModel.class);

	/**
	 * The settings key to retrieve and store settings shared between node dialog
	 * and node model. 
	 */
    private final List<SettingsModel> settingsModels = new ArrayList<>();
	
	/*
    private final SettingsModelString m_hostname =
            MoodleConnectorNodeSettingsModel.createHostnameModel();
	
	private final SettingsModelString m_username =
            MoodleConnectorNodeSettingsModel.createUsernameModel();
	
	private final SettingsModelString m_password =
            MoodleConnectorNodeSettingsModel.createPasswordModel();
	*/
		
	private MoodleConnectorConfiguration m_config = new MoodleConnectorConfiguration();
	
	
	
	/**
	 * Constructor for the node model.
	 */
	protected MoodleConnectorNodeModel() {
		super(new PortType[0], new PortType[]{MoodleConnectionPortObject.TYPE});
    }
		


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
    protected PortObject[] execute(PortObject[] inObjects, ExecutionContext exec) throws Exception {
        return new PortObject[]{
        		
        		new MoodleConnectionPortObject(createSpec())
        		
        		
        		
        		};
    }

	
	private MoodleConnectionPortObjectSpec createSpec() throws InvalidSettingsException {
	        return new MoodleConnectionPortObjectSpec(m_config.createMoodleConnection());
	}

	
	
	
	protected BufferedDataTable[] execute2(final BufferedDataTable[] inData, final ExecutionContext exec)
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
		LOGGER.info("This is an example info.");


		/*
		 * Create the spec of the output table, for each double column of the input
		 * table we will create one formatted String column in the output. See the
		 * javadoc of the "createOutputSpec(...)" for more information.
		 */
		DataTableSpec outputSpec = null;

		/*
		 * The execution context provides storage capacity, in this case a
		 * data container to which we will add rows sequentially. Note, this container
		 * can handle arbitrary big data tables, it will buffer to disc if necessary.
		 * The execution context is provided as an argument to the execute method by the
		 * framework. Have a look at the methods of the "exec". There is a lot of
		 * functionality to create and change data tables.
		 */
		BufferedDataContainer container = exec.createDataContainer(outputSpec);

		

		// Login UBUMonitor
		
		NodeLogger logger=NodeLogger.getLogger("Moodle Integration");
		
		// String USERNAME = "teacher1";
		// String PASSWORD = "Teacher.1";
		// String HOST = "http://192.168.1.20";
		int COURSE_ID = 657;
		WebService webService;
		DataBase dataBase;
		int USERID = 10016;
		
		//Controller CONTROLLER = Controller.getInstance();
		//CONTROLLER.tryLogin(HOST, USERNAME, PASSWORD);	
	
		
		//Login login = new Login(hostNameModel.getStringValue(), usernameModel.getStringValue(), passwordModel.getStringValue());
		//login.tryLogin();
		
		
		//login.normalLogin();
		
		
		//webService = login.getWebService();
				
		//logger.warn("token: " + webService.getToken());
		
		//logger.warn("SessKey: " + webService.getSessKey());
		
		
	// 	logger.warn("Cookies: " + Connection.COOKIE_MANAGER.getCookieStore())
		
			
		//JSONArray userCourses = UtilMethods.getJSONArrayResponse(webService, new CoreEnrolGetUsersCourses(USERID));
		
		//logger.warn("cursos: " + userCourses.toString());
		//logger.warn("cursos total: " + userCourses.length());
		
		
		/*
		MoodleConnection moodleConnection = new MoodleConnection(m_hostname.getStringValue(), m_username.getStringValue(), m_password.getStringValue());
		
		moodleConnection.loginWeb();
				
		logger.warn("Moodle session: " + moodleConnection.getMoodleSession());
		
			*/		
		
		
		
		
		///// LOG
		
		// Course couse = Controller.getInstance().getActualCourse();
		// Course actualCourse = dataBase.getActualCourse();
		// logger.warn("curso actual: " + actualCourse.getFullName());
		
		/*		
		DownloadLogController downloadLogController = LogCreator.download();

		Response response = downloadLogController.downloadLog(false);
		Logs logs = new Logs(downloadLogController.getServerTimeZone());
		LogCreator.parserResponse(logs, response.body()
				.charStream());
		
		logger.warn("logs: " + logs.toString());
		
		*/
		
		/*
		DownloadLogController downloadLogController = LogCreator.download();

		Response response = downloadLogController.downloadLog(false);
		LOGGER.info("Log descargado");
		updateMessage(I18n.get("label.parselog"));
		Logs logs = new Logs(downloadLogController.getServerTimeZone());
		LogCreator.parserResponse(logs, response.body()
				.charStream());
		actualCourse.setLogs(logs);
		
		
		*/
		
		
		
		
		
		
		
	/*	
		CONTROLLER.setDataBase(new DataBase());
		CONTROLLER.setUsername(USERNAME);
		CONTROLLER.setURLHost(new URL(HOST));
		CONTROLLER.setPassword(PASSWORD);
		CONTROLLER.setActualCourse(CONTROLLER.getDataBase()
				.getCourses()
				.getById(COURSE_ID));
		webService = CONTROLLER.getWebService();
		dataBase = CONTROLLER.getDataBase();
		
		// Collection<Course> allCourses = Controller.getInstance().getDataBase().getCourses().getMap().values();
		
		//logger.warn(allCourses.toString());
		
		*/
		/*
		 * Once we are done, we close the container and return its table. Here we need
		 * to return as many tables as we specified in the constructor. This node has
		 * one output, hence return one table (wrapped in an array of tables).
		 */
		container.close();
		BufferedDataTable out = container.getTable();
		return new BufferedDataTable[] { out };
	}


	/**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(NodeSettingsWO settings) {
        m_config.save(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(NodeSettingsRO settings) throws InvalidSettingsException {
        MoodleConnectorConfiguration config = new MoodleConnectorConfiguration();
        config.loadInModel(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(NodeSettingsRO settings) throws InvalidSettingsException {
        MoodleConnectorConfiguration config = new MoodleConnectorConfiguration();
        config.loadInModel(settings);
        m_config = config;
    }

	
	    
	@Override
	protected void loadInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// not needed
	}

	@Override
	protected void saveInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {
		// not needed
	}

	@Override
	protected void reset() {
		// not needed
	}
	
	
    /**
     * {@inheritDoc}
     */
    @Override
    protected PortObjectSpec[] configure(PortObjectSpec[] inSpecs) throws InvalidSettingsException {
        return new PortObjectSpec[]{createSpec()};
    }

}

