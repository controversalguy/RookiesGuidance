package pt.isec.gps.rookiesguidance.model.data;

import pt.isec.gps.rookiesguidance.model.data.infodata.Informacao;

import java.util.ArrayList;

public class GestaoData {
    private Informacao informacoes;
    private ArrayList<Novidade> novidades;
    private ArrayList<Evento> eventos;

    public boolean addlocalA(String local){return informacoes.getLocalAlimentacao().add(local);}
    public boolean addlocalE(String local){return informacoes.getLocalEstudo().add(local);}
//    public boolean removeLocalA(int pos){
//
//    }
//    public boolean removeLocalE(int pos){
//
//    }
//    public boolean addNovidade(Novidade nov){
//        novidades.add(nov);
//    }
//    public boolean remNovidade(Novidade nov){
//        novidades.remove(nov);
//    }
//    public boolean addEvento(Evento ev){
//        eventos.add(ev);
//    }
//    public boolean remEvento(Evento ev){
//        eventos.remove(ev);
//    }

}
