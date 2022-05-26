package org.knime.moodle.nodes.connector;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * Node factory implementation of the "MoodleConnector" node.
 *
 * @author Fran Gil
 */
public class MoodleConnectorNodeFactory 
        extends NodeFactory<MoodleConnectorNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public MoodleConnectorNodeModel createNodeModel() {
        return new MoodleConnectorNodeModel();
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
    public NodeView<MoodleConnectorNodeModel> createNodeView(final int viewIndex,
            final MoodleConnectorNodeModel nodeModel) {
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
        return new MoodleConnectorNodeDialog();
    }

}

