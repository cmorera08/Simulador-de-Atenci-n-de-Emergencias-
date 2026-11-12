/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Clarissa
 */
public class Calle {
    private Interseccion destino;
    private double pesoRuta;
    private boolean esDirigida;
    
    public Calle (Interseccion destino, double pesoRuta, boolean esDirigida){
        this.destino = destino;
        this.pesoRuta = pesoRuta;
        this.esDirigida = esDirigida;
    }
    
    public Interseccion getdestino(){
        return destino;
    }
    public double getpesoRuta(){
        return pesoRuta;
    }
    public boolean getesDirigida(){
        return esDirigida;
    }
    
}
