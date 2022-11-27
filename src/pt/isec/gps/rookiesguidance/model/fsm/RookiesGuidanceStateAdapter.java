package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

abstract public class RookiesGuidanceStateAdapter implements IRookiesGuidanceState{

    RookiesGuidanceContext context;
    GestaoData data;

    public RookiesGuidanceStateAdapter(RookiesGuidanceContext context, GestaoData data) {
        this.context = context;
        this.data = data;
    }


}
