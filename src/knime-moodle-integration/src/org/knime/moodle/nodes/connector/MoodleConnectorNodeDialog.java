package org.knime.moodle.nodes.connector;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.defaultnodesettings.DialogComponentPasswordField;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;


import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.util.KnimeEncryption;

/**
 * This is an example implementation of the node dialog of the
 * "MoodleConnector" node.
 * 
 * @author Fran Gil
 */
// public class MoodleConnectorNodeDialog extends DefaultNodeSettingsPane {

public class MoodleConnectorNodeDialog extends NodeDialogPane{
	
	 private JTextField m_hostname = new JTextField(40);    
	 
	 private JTextField m_username = new JTextField(40);
	    
	 private JPasswordField m_password = new JPasswordField(40);

	 
	 NodeLogger logger=NodeLogger.getLogger("Moodle Integration");

     MoodleConnectorNodeDialog() {
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 5, 5, 5);
	        gbc.anchor = GridBagConstraints.NORTHWEST;
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.weightx = 0;
	        gbc.weighty = 0;
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        panel.add(new JLabel("Host name:"), gbc);
	        gbc.weightx = 1;
	        gbc.gridx++;
	        panel.add(m_hostname, gbc);
	        gbc.weightx = 0;
	        gbc.gridx = 0;
	        gbc.gridy++;
	        panel.add(new JLabel("Username:"), gbc);
	        gbc.weightx = 1;
	        gbc.gridx++;
	        panel.add(m_username, gbc);
	        gbc.weightx = 0;
	        gbc.gridx = 0;
	        gbc.gridy++;
	        panel.add(new JLabel("Password:"), gbc);
	        gbc.weightx = 1;
	        gbc.gridx++;
	        panel.add(m_password, gbc);
	        gbc.weightx = 0;
	        gbc.gridx = 0;
	        gbc.gridy++;
	        addTab("Credentials", panel);
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void loadSettingsFrom(NodeSettingsRO settings, PortObjectSpec[] specs) throws NotConfigurableException {
	        MoodleConnectorConfiguration config = new MoodleConnectorConfiguration();
	        
	        config.loadInDialog(settings);
	        
	        m_username.setText(config.getUsername());
	        m_hostname.setText(config.getHostname());
	        m_password.setText(config.getPassword());
	        
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void saveSettingsTo(NodeSettingsWO settings) throws InvalidSettingsException {
	        MoodleConnectorConfiguration config = new MoodleConnectorConfiguration();
	        
	        config.setUsername(m_username.getText());
	        config.setPassword(m_password.getText());
	        config.setHostname(m_hostname.getText());
			
	        config.save(settings);
	    }	
	
	
	
	
	/*
	
	private final SettingsModelString hostnameModel =
			MoodleConnectorNodeSettingsModel.createHostnameModel();
    private final SettingsModelString m_username =
    		MoodleConnectorNodeSettingsModel.createUsernameModel();
    private final SettingsModelPassword passwordModel =
    		MoodleConnectorNodeSettingsModel.createPasswordModel();

	protected MoodleConnectorNodeDialog() {
       // super();
        
		createNewGroup("Moodle Credentials");
		addDialogComponent(new DialogComponentString(hostnameModel, "Hostname", true, 20));
        addDialogComponent(new DialogComponentString(m_username, "Username", true, 20));
        addDialogComponent(new DialogComponentPasswordField(passwordModel, "Password"));
        closeCurrentGroup();
		
		
    }
	
*/
}

