package org.knime.moodle.nodes.connector;

import org.knime.core.node.defaultnodesettings.SettingsModelPassword;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * The class storing the configuration for the Moodle Connector node
 *
 * @author Fran Gil
 *
 */
public class MoodleConnectorNodeSettingsModel {

    private MoodleConnectorNodeSettingsModel() {
        // Utility class
    }

    protected static SettingsModelString createHostnameModel() {
        return new SettingsModelString("m_hostname", "");
    }

    protected static SettingsModelString createUsernameModel() {
        return new SettingsModelString("m_username", "");
    }

    protected static SettingsModelString createPasswordModel() {
        return new SettingsModelString("m_password", "");
    }

}
