/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Clarissa
 */
public class Emergencia {
    private String id;
    private String tipo;
    private String ubicacion;
    private String sedeSalida;
    private String sedeLlegada;
    private String estado;

    
    public Emergencia(String id, String tipo, String ubicacion, String sedeSalida, String sedeLlegada, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.sedeSalida = sedeSalida;
        this.sedeLlegada = sedeLlegada;
        this.estado = estado;
    }

    
    public String getId(){
        return id;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getUbicacion(){
        return ubicacion;
    }
    
    public String getSedeSalida(){
        return sedeSalida;
    }
    
    public String getSedeLlegada(){
        return sedeLlegada;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
}