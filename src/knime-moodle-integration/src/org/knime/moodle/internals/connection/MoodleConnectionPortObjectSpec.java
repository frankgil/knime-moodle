package org.knime.moodle.internals.connection;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.ModelContentRO;
import org.knime.core.node.ModelContentWO;
import org.knime.core.node.port.AbstractSimplePortObjectSpec;
import org.knime.core.node.util.ViewUtils;



public final class MoodleConnectionPortObjectSpec extends AbstractSimplePortObjectSpec {
   public static final class Serializer
       extends AbstractSimplePortObjectSpecSerializer<MoodleConnectionPortObjectSpec> { }

   private MoodleConnection m_MoodleConnection;

   /**
    * Constructor for a port object spec that holds no MoodleConnection.
    */
   public MoodleConnectionPortObjectSpec() {
       m_MoodleConnection = null;
   }

   /**
    * @param MoodleConnection The MoodleConnection that will be contained by this port object spec
    */
   public MoodleConnectionPortObjectSpec(final MoodleConnection MoodleConnection) {
       m_MoodleConnection = MoodleConnection;
   }

   /**
    * @return The contained MoodleConnection object
    */
   public MoodleConnection getMoodleConnection() {
       return m_MoodleConnection;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void save(final ModelContentWO model) {
       m_MoodleConnection.save(model);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void load(final ModelContentRO model) throws InvalidSettingsException {
		m_MoodleConnection = MoodleConnection.load(model);
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object ospec) {
       if (this == ospec) {
           return true;
       }
       if (!(ospec instanceof MoodleConnectionPortObjectSpec)) {
           return false;
       }
       MoodleConnectionPortObjectSpec spec = (MoodleConnectionPortObjectSpec)ospec;
       return m_MoodleConnection.equals(spec.m_MoodleConnection);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
       return m_MoodleConnection != null ? m_MoodleConnection.hashCode() : 0;
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
       return new JComponent[]{f};
   }

}
