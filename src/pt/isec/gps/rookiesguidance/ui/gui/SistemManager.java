package pt.isec.gps.rookiesguidance.ui.gui;

import pt.isec.gps.rookiesguidance.model.fsm.RookiesGuidanceContext;
import pt.isec.gps.rookiesguidance.model.fsm.RookiesGuidanceState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public class SistemManager {
    private RookiesGuidanceContext fsm;
    PropertyChangeSupport pcs;
    public SistemManager() throws SQLException {
        fsm = new RookiesGuidanceContext();
        pcs = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public RookiesGuidanceState getState() { return fsm.getState(); }
}
