package org.knime.moodle.nodes.connector;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.ModelContentRO;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.moodle.internals.connection.MoodleConnection;


public class MoodleConnectorConfiguration {

	

	// private static final String CFG_CONSUMER_KEY = "consumerKey";

	private static final String CFG_USERNAME = "m_username";
	
    private static final String CFG_HOSTNAME = "m_hostname";

    private static final String CFG_PASSWORD = "m_password";

    private String m_username;

    private String m_hostname;

    private String m_password;


    NodeLogger logger=NodeLogger.getLogger("Moodle Integration");
    
    
    /**
     * @param settings The settings to load from
     * @throws InvalidSettingsException If the settings are invalid
     */
    public void loadInModel(final NodeSettingsRO settings) throws InvalidSettingsException {
        m_username = settings.getString(CFG_USERNAME);
        
        logger.warn("Variables settings 1: " + m_username);
        
        m_hostname = settings.getString(CFG_HOSTNAME);
        m_password = settings.getString(CFG_PASSWORD, null);
    }

    /**
     * @param settings The settings to load from
     */
    public void loadInDialog(final NodeSettingsRO settings) {
        m_username = settings.getString(CFG_USERNAME, null);
        m_hostname = settings.getString(CFG_HOSTNAME, null);
        m_password = settings.getString(CFG_PASSWORD, null);
        
        logger.warn("Variables settings 2: " + m_username + ' ' + m_password + ' ' + m_hostname);
        
        
    }

    /**
     * @param settings The settings to save to
     */
    public void save(final NodeSettingsWO settings) {
    	
    	logger.warn("Variables settings 3: " + m_username + ' ' + m_password + ' ' + m_hostname);
    	
        settings.addString(CFG_USERNAME, m_username);
        settings.addString(CFG_HOSTNAME, m_hostname);
        settings.addString(CFG_PASSWORD, m_password);
    }

    /**
     * @return A MoodleConnection based on the current settings
     * @throws InvalidSettingsException If the current settings are invalid
     * @throws IOException 
     */
    public MoodleConnection createMoodleConnection() throws InvalidSettingsException {
    	
    	logger.warn("Variables settings 4: " + m_username + ' ' + m_password + ' ' + m_hostname);
        
    	
        if (StringUtils.isBlank(m_username)) {
            throw new InvalidSettingsException("Username is missing");
        }
        if (StringUtils.isBlank(m_hostname)) {
            throw new InvalidSettingsException("Hostname is missing");
        }
        if (StringUtils.isBlank(m_password)) {
            throw new InvalidSettingsException("Password is missing");
        }
        
        //MoodleConnection moodleConnection = new MoodleConnection(m_hostname, m_username, m_password);        
        
        //moodleConnection.loginWeb();        
                
        return new MoodleConnection(m_hostname, m_username, m_password);
    }

    /**
     * @return the consumerKey
     */
    public String getUsername() {
        return m_username;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setUsername(final String username) {
        m_username = username;
    }

    /**
     * @return the consumerSecret
     */
    public String getHostname() {
        return m_hostname;
    }

    /**
     * @param consumerSecret the consumerSecret to set
     */
    public void setHostname(final String hostname) {
        m_hostname = hostname;
    }

    /**
     * @return the accessToken
     */
    public String getPassword() {
        return m_password;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setPassword(final String password) {
        m_password = password;
    }

    
}
