package com.example.migueltrabalho.Modelo;

import java.io.Serializable;

public class Computador implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long _id;
    private String processador;
    private String memoria;
    private String armazenamento;
    private byte [] imagemAtributoModelo;

    public byte[] getImagemAtributoModelo() {
        return imagemAtributoModelo;
    }

    public void setImagemAtributoModelo(byte[] imagemAtributoModelo) {
        this.imagemAtributoModelo = imagemAtributoModelo;
    }

    @Override
    public String toString() {
        return processador;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}

