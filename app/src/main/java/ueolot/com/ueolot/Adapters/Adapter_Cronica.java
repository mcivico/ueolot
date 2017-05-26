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

import ueolot.com.ueolot.Model.Cronica;
import ueolot.com.ueolot.R;

/**
 * Created by m_civico on 15/12/2016.
 */

public class Adapter_Cronica extends ArrayAdapter<Cronica> {

    class Vista {
        private TextView titol;
        private ImageView foto;
    }

    private ArrayList<Cronica> dades;
    int width;
    Display display;


    public Adapter_Cronica(Activity context, ArrayList<Cronica> dades) {
        super(context, R.layout.fragment_adapter_cronica, dades);
        this.dades = dades;
    }

    public View getView(int position, View convertview, ViewGroup parent){
        View element = convertview;
        Vista vista;

        if(element == null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            element =  inflater.inflate(R.layout.fragment_adapter_cronica,null);
            display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;

            vista = new Vista();
            vista.foto = (ImageView)element.findViewById(R.id.imglay);
            vista.titol = (TextView) element.findViewById(R.id.txttitolay);
            element.setTag(vista);

        }else{
            vista = (Vista)element.getTag();
        }

        vista.titol.setText(dades.get(position).getTitol());
        //Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()+"-768x403.jpg").into(vista.foto);
        Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()).into(vista.foto);
        /*if(vista.foto.getDrawable() == null)
            Picasso.with(this.getContext()).load("http://www.ueolot.com/wp-content/uploads/"+dades.get(position).getUrlimatge()+".jpg").into(vista.foto);
        */

        return element;
    }
}
