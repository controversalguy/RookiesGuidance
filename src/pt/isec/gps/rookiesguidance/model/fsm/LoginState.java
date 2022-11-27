package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

public class LoginState extends RookiesGuidanceStateAdapter{
    public LoginState(RookiesGuidanceContext context, GestaoData data) {
        super(context, data);
    }
}
