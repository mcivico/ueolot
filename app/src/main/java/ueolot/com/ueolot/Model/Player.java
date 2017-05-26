package ueolot.com.ueolot.Model;

import java.io.Serializable;

/**
 * Created by m_civico on 15/12/2016.
 */

public class Player implements Serializable {

    public String id, date, title, content, position, city, number, image;

    public Player(String id, String date, String title, String content, String position, String city, String number, String image){
        super();
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.position = position;
        this.city = city;
        this.number = number;
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
