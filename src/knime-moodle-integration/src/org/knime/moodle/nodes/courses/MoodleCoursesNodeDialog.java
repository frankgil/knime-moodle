package org.knime.moodle.nodes.courses;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.core.data.StringValue;
import org.knime.core.data.LongValue;


/**
 * Implementation of the node dialog of the "MoodleCourses" node.
 * 
 * @author Fran Gil
 */
public class MoodleCoursesNodeDialog extends DefaultNodeSettingsPane {

    
	protected MoodleCoursesNodeDialog() {
        super();
		
		SettingsModelString categoryColSettings = MoodleCoursesNodeModel.createCategoryColModel();
		addDialogComponent(new DialogComponentColumnNameSelection(categoryColSettings,"Category column: ", 1, false, true, 
				                                                  new Class[]{LongValue.class, StringValue.class}));

		SettingsModelString courseIdColSettings = MoodleCoursesNodeModel.createCourseIdColModel();
		addDialogComponent(new DialogComponentColumnNameSelection(courseIdColSettings,"CourseId column: ", 1, false, true, 
				                                                  new Class[]{LongValue.class, StringValue.class}));
		
		
    }
    
    
    
    
}









