package com.example.civitets.clases;

import java.io.Serializable;

public class Offers implements Serializable{

    private String imagen;
    private String name;
    private String info;

    public Offers(){
    }

    public Offers(String imagen, String name, String info) {
        this.imagen = imagen;
        this.name = name;
        this.info = info;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
