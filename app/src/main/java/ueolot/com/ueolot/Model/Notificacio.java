package ueolot.com.ueolot.Model;

import java.io.Serializable;

/**
 * Created by mcivico on 20/12/2016.
 */

public class Notificacio implements Serializable {

    public String pk, fecha, descripcion;

    public Notificacio(String pk, String fecha, String descripcion) {
        this.pk = pk;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
}
