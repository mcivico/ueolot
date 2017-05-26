package ueolot.com.ueolot.Connexio;

import java.util.ArrayList;

import ueolot.com.ueolot.Model.Player;

/**
 * Created by m_civico on 15/12/2016.
 */

public class PrimerEquip_Response {
    public ArrayList<Player> jugadors;
    public ArrayList<Player> tecnics;

    public ArrayList<Player> getJugadors(){
        jugadors.addAll(tecnics);
        return jugadors;
    }
}
