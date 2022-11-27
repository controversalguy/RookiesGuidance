package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

public class RegistoState extends RookiesGuidanceStateAdapter{
    public RegistoState(RookiesGuidanceContext context, GestaoData data) {
        super(context, data);
    }
}
