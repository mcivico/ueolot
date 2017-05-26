package ueolot.com.ueolot.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ueolot.com.ueolot.Model.Notificacio;
import ueolot.com.ueolot.R;
import ueolot.com.ueolot.helper.UEOlot_helper;


/**
 * Created by mcivico on 20/12/2016.
 */

public class Adapter_Notificacions extends ArrayAdapter<Notificacio> {

    class Vista{
        private TextView data, contingut;
    }

    private ArrayList<Notificacio> dades;

    public Adapter_Notificacions(Activity context, ArrayList<Notificacio> dades){
        super(context, R.layout.fragment_adapter_notificacions,dades);
        this.dades = dades;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View element = convertView;
        Vista vista;

        if(element == null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            element = inflater.inflate(R.layout.fragment_adapter_notificacions,null);
            vista = new Vista();
            vista.data = (TextView)element.findViewById(R.id.data_notificacions);
            vista.contingut = (TextView)element.findViewById(R.id.contingut_notificacions);
            element.setTag(vista);
        }else{
            vista = (Vista)element.getTag();
        }

        vista.data.setText(UEOlot_helper.formatDateTime(dades.get(position).fecha));
        vista.contingut.setText(dades.get(position).descripcion);

        return element;
    }


}
