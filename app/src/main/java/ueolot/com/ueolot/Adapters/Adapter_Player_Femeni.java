package ueolot.com.ueolot.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ueolot.com.ueolot.Model.Player;
import ueolot.com.ueolot.R;

public class Adapter_Player_Femeni extends ArrayAdapter<Player> {

    class Vista{
        private TextView nom1, posicio1, data1, lloc;
        private ImageView imatge;
    }

    private ArrayList<Player> dades;
    int width;


    public Adapter_Player_Femeni(Activity context, ArrayList<Player> dades){
        super(context, R.layout.fragment_adapter_player_femeni,dades);
        this.dades = dades;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View element = convertView;
        Vista vista;

        if(element == null){
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            element = inflater.inflate(R.layout.fragment_adapter_player,null);
            vista = new Vista();
            vista.imatge = (ImageView)element.findViewById(R.id.imagen_single_post_circuito_persona);
            vista.nom1 = (TextView)element.findViewById(R.id.tv_titulo_single_post_circuito_persona);
            vista.posicio1 = (TextView)element.findViewById(R.id.tv_contenido_single_post_circuito_persona);
            vista.data1 = (TextView)element.findViewById(R.id.tv_neix_single_post_circuito_persona);
            vista.lloc = (TextView)element.findViewById(R.id.tv_lloc_single_post_circuito_persona);
            element.setTag(vista);
        }else{
            vista = (Vista)element.getTag();
        }


        Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(i).getImage()).into(vista.imatge);
        vista.nom1.setText(dades.get(i).getTitle());
        //vista.posicio1.setText(dades.get(i).getPosition());
        //vista.data1.setText(UEOlot_helper.formatDate(dades.get(i).getDate()));
        //vista.lloc.setText(dades.get(i).getCity());

        return element;
    }
}
