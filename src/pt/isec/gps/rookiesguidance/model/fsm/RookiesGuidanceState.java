package pt.isec.gps.rookiesguidance.model.fsm;

import pt.isec.gps.rookiesguidance.model.data.GestaoData;

public enum RookiesGuidanceState {
    AUTENTICADOR,REGISTO,LOGIN;
//,ESTUDANTE,GESTOR
    IRookiesGuidanceState createState(RookiesGuidanceContext context, GestaoData data) {
        return  switch (this) {
            case REGISTO -> new RegistoState(context,data);
            case LOGIN -> new LoginState(context,data);
            case AUTENTICADOR -> new AutenticadorState(context, data);
        };
    }
}
