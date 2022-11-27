package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

import static pt.isec.gps.rookiesguidance.model.fsm.RookiesGuidanceState.AUTENTICADOR;

public class RookiesGuidanceContext {
    private IRookiesGuidanceState state;
    GestaoData data;

    public RookiesGuidanceContext() {
        data = new GestaoData();
        state = AUTENTICADOR.createState(this,data);
    }
}
