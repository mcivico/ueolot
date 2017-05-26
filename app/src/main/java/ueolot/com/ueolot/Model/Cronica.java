package ueolot.com.ueolot.Model;

import java.io.Serializable;

/**
 * Created by m_civico on 21/09/2016.
 */
public class Cronica implements Serializable {

    private String id,date, title, content, excerpt,urlimatge;

    public Cronica(String id, String date, String title, String content, String excerpt, String urlimatge){
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.urlimatge = urlimatge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitol() {
        return title;
    }

    public void setTitol(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getUrlimatge() {
        return urlimatge;
    }

    public void setUrlimatge(String urlimatge) {
        this.urlimatge = urlimatge;
    }
}
