package com.gprosoft.incrowdapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mensaje {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("autor")
    @Expose
    private String autor;

    @SerializedName("evento")
    @Expose
    private String evento;

    @SerializedName("texto")
    @Expose
    private String texto;

    public Mensaje(String autor, String evento, String texto) {
        this.autor = autor;
        this.evento = evento;
        this.texto = texto;
    }

    public Integer getPk() {
        return id;
    }

    public void setPk(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEvento() { return evento; }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getTexto() { return texto; }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}

