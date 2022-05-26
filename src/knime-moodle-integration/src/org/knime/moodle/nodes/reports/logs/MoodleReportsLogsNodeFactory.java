package org.knime.moodle.nodes.reports.logs;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * This is an example implementation of the node factory of the
 * "MoodleCourses" node.
 *
 * @author Fran Gil
 */
public class MoodleReportsLogsNodeFactory 
        extends NodeFactory<MoodleReportsLogsNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public MoodleReportsLogsNodeModel createNodeModel() {
		// Create and return a new node model.
        return new MoodleReportsLogsNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
		// The number of views the node should have, in this cases there is none.
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<MoodleReportsLogsNodeModel> createNodeView(final int viewIndex,
            final MoodleReportsLogsNodeModel nodeModel) {
		// We return null as this example node does not provide a view. Also see "getNrNodeViews()".
		return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
		// Indication whether the node has a dialog or not.
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
		// This example node has a dialog, hence we create and return it here. Also see "hasDialog()".
        return new MoodleReportsLogsNodeDialog();
    }

}

