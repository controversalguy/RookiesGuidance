package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

import java.sql.SQLException;

import static pt.isec.gps.rookiesguidance.model.fsm.RookiesGuidanceState.AUTENTICADOR;

public class RookiesGuidanceContext {
    private IRookiesGuidanceState state;
    GestaoData data;

    public RookiesGuidanceContext() throws SQLException {
        data = new GestaoData();
        state = AUTENTICADOR.createState(this,data);
    }

    public RookiesGuidanceState getState() {
        if (state == null)
            return null;
        return state.getState();
    }

}
