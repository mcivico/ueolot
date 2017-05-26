package ueolot.com.ueolot.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ueolot.com.ueolot.Model.Posicio;
import ueolot.com.ueolot.R;


/**
 * Created by m_civico on 04/10/2016. */

public class Adapter_Clasificacio extends BaseAdapter {

    class Vista{
        private TextView pos, equip, pj, pts;
    }

    private Activity context;
    private ArrayList<Posicio> dades;

    public Adapter_Clasificacio(Activity context, ArrayList<Posicio> dades){
        this.context = context;
        this.dades = dades;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Vista holder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.fragment_adapter_classificacio,null);

            holder = new Vista();
            holder.pos = (TextView)convertView.findViewById(R.id.txtPosicio);
            holder.equip = (TextView)convertView.findViewById(R.id.txtEquip);
            holder.pj = (TextView)convertView.findViewById(R.id.txtPartitsJugats);
            holder.pts = (TextView)convertView.findViewById(R.id.txtPunts);
            convertView.setTag(holder);

        } else{
            holder = (Vista)convertView.getTag();
        }

        if(dades.get(position).equip.equals("UE OLOT")){
            holder.pos.setTextSize(17);
            holder.pos.setTextColor(context.getResources().getColor(R.color.colortTítols));
            holder.equip.setTextSize(17);
            holder.equip.setTextColor(context.getResources().getColor(R.color.colortTítols));
            holder.pj.setTextSize(17);
            holder.pj.setTextColor(context.getResources().getColor(R.color.colortTítols));
            holder.pts.setTextSize(17);
            holder.pts.setTextColor(context.getResources().getColor(R.color.colortTítols));
        } else{
            holder.pos.setTextSize(14);
            holder.pos.setTextColor(context.getResources().getColor(R.color.colorNegre));
            holder.equip.setTextSize(14);
            holder.equip.setTextColor(context.getResources().getColor(R.color.colorNegre));
            holder.pj.setTextSize(14);
            holder.pj.setTextColor(context.getResources().getColor(R.color.colorNegre));
            holder.pts.setTextSize(14);
            holder.pts.setTextColor(context.getResources().getColor(R.color.colorNegre));
        }

        String posicio, equip, pjug, punts;
        posicio = dades.get(position).position;
        equip = dades.get(position).equip;
        pjug = dades.get(position).partitsJugats;
        punts = dades.get(position).punts;

        holder.pos.setText(posicio);
        holder.equip.setText(equip);
        holder.pj.setText(pjug);
        holder.pts.setText(punts);
        return convertView;
    }



    @Override
    public int getCount() {
        return this.dades.size();
    }

    @Override
    public Object getItem(int i) {
        return this.dades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
