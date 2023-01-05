package com.gprosoft.incrowdapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Evento implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("esPublico")
    @Expose
    private Boolean esPublico;
    @SerializedName("aforo")
    @Expose
    private Integer aforo;
    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("organizador")
    @Expose
    private String organizador;
    @SerializedName("participantes")
    @Expose
    private List<UsuarioModel> participantes;


    public Evento(String nombre, String descripcion, String fecha, String hora, Boolean esPublico,
                  Integer aforo, String categoria, String organizador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.esPublico = esPublico;
        this.aforo = aforo;
        this.categoria = categoria;
        this.organizador = organizador;
    }

    public Integer getPk() {
        return id;
    }

    public void setPk(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() { return hora; }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Boolean getEsPublico() { return esPublico; }

    public void setEsPublico(Boolean esPublico) {
        this.esPublico = esPublico;
    }

    public Integer getAforo() { return aforo; }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getOrganizador() { return organizador; }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public List<UsuarioModel> getParticipantes() { return participantes; }

    public void setParticipantes(List<UsuarioModel> participantes) { this.participantes = participantes; }
}
