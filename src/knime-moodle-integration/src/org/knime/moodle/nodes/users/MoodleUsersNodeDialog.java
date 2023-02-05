package org.knime.moodle.nodes.users;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;

import org.knime.core.node.defaultnodesettings.DialogComponentNumberEdit;
import org.knime.core.node.defaultnodesettings.SettingsModelInteger;


/**
 * Implementation of the node dialog of the "Moodleuser" node.
 * 
 * @author Fran Gil
 */
public class MoodleUsersNodeDialog extends DefaultNodeSettingsPane {

    protected MoodleUsersNodeDialog() {
        super();
		
		SettingsModelBoolean filterStudentsSettings = MoodleUsersNodeModel.createFilterStudentsSettingsModel();
		addDialogComponent(new DialogComponentBoolean(filterStudentsSettings, "Only students"));
		

		SettingsModelBoolean anonymizeSettings = MoodleUsersNodeModel.createAnonymizeSettingsModel();
		addDialogComponent(new DialogComponentBoolean(anonymizeSettings, "Anonymize"));

		SettingsModelInteger randomSeedSettings = MoodleUsersNodeModel.createRandomSeedSettingsModel();
		addDialogComponent(new DialogComponentNumberEdit(randomSeedSettings, "Random Seed"));
		
    }
}

