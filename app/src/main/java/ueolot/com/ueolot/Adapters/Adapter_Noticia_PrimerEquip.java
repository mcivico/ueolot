package ueolot.com.ueolot.Adapters;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ueolot.com.ueolot.Model.Noticia;
import ueolot.com.ueolot.R;

public class Adapter_Noticia_PrimerEquip extends ArrayAdapter<Noticia> {

    class Vista{
        private TextView titol;
        private ImageView foto;
    }

    private ArrayList<Noticia> dades;
    int width;
    Display display;

    public Adapter_Noticia_PrimerEquip(Activity context, ArrayList<Noticia> dades){
        super(context, R.layout.fragment_adapter_noticies_primer_equip,dades);
        this.dades = dades;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View element = convertView;
        Vista vista;

        if(element == null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            element = inflater.inflate(R.layout.fragment_adapter_noticies_primer_equip,null);
            vista = new Vista();
            display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;

            vista.foto = (ImageView)element.findViewById(R.id.imgNoticies1E);
            vista.titol = (TextView)element.findViewById(R.id.txtTitol1E);

            element.setTag(vista);
        }else{
            vista = (Vista)element.getTag();
        }

        vista.titol.setText(dades.get(position).getTitle());

        //Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()+"-768x403.jpg").into(vista.foto);
        Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()).fit().centerCrop().into(vista.foto);
        /*if(vista.foto.getDrawable() == null){
            Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()+".jpg").into(vista.foto);
        }*/
        String a = "";
        return element;
    }




}
