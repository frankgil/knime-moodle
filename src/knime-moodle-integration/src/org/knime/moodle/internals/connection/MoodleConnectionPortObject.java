package org.knime.moodle.internals.connection;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.ModelContentRO;
import org.knime.core.node.ModelContentWO;
import org.knime.core.node.port.AbstractSimplePortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.PortTypeRegistry;
import org.knime.core.node.util.ViewUtils;



public final class MoodleConnectionPortObject extends AbstractSimplePortObject {
	    public static final class Serializer extends AbstractSimplePortObjectSerializer<MoodleConnectionPortObject> {}

	    private MoodleConnectionPortObjectSpec m_spec;

	    /**
	     * The type of this port.
	     */
	    public static final PortType TYPE =
	        PortTypeRegistry.getInstance().getPortType(MoodleConnectionPortObject.class);

	    /**
	     * Constructor for framework.
	     */
	    public MoodleConnectionPortObject() {
	        // used by framework
	    }

	    /**
	     * @param spec The specification of this port object.
	     */
	    public MoodleConnectionPortObject(final MoodleConnectionPortObjectSpec spec) {
	        m_spec = spec;
	    }

	    /**
	     * @return The contained MoodleConnection object
	     */
	    public MoodleConnection getMoodleConnection() {
	        return m_spec.getMoodleConnection();
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public String getSummary() {
	        return m_spec.getMoodleConnection().toString();
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public PortObjectSpec getSpec() {
	        return m_spec;
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void save(final ModelContentWO model, final ExecutionMonitor exec) throws CanceledExecutionException {
	        // nothing to do
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    protected void load(final ModelContentRO model, final PortObjectSpec spec, final ExecutionMonitor exec)
	            throws InvalidSettingsException, CanceledExecutionException {
	        m_spec = (MoodleConnectionPortObjectSpec)spec;
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public JComponent[] getViews() {
	        String text;
	        if (getMoodleConnection() != null) {
	            text = "<html>" + getMoodleConnection().toString().replace("\n", "<br>") + "</html>";
	        } else {
	            text = "No connection available";
	        }
	        JPanel f = ViewUtils.getInFlowLayout(new JLabel(text));
	        f.setName("Connection");
	        return new JComponent[]{new JScrollPane(f)};
	    }

	}
