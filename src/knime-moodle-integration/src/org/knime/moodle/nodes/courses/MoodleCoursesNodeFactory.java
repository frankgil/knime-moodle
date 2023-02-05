package org.knime.moodle.nodes.courses;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * This is an example implementation of the node factory of the
 * "MoodleCourses" node.
 *
 * @author Fran Gil
 */
public class MoodleCoursesNodeFactory 
        extends NodeFactory<MoodleCoursesNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public MoodleCoursesNodeModel createNodeModel() {
        return new MoodleCoursesNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<MoodleCoursesNodeModel> createNodeView(final int viewIndex,
            final MoodleCoursesNodeModel nodeModel) {
		return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new MoodleCoursesNodeDialog();
    }

}

