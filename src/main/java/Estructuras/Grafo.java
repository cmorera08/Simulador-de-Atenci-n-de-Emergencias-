/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Modelo.Interseccion;
import Modelo.Calle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Clarissa
 */
public class Grafo {
    private Map<Interseccion, List<Calle>> listaAdyacencia;
    
    public Grafo(){
        this.listaAdyacencia = new HashMap<>();
    }
    
    public void agregarIntersección(Interseccion interseccion){
        if(!listaAdyacencia.containsKey(interseccion)){
            listaAdyacencia.put(interseccion, new LinkedList<>());
        }
    }
    
    public void agregarCalle(Interseccion origen, Interseccion destino, double pesoRuta, boolean esDirigida){
        agregarIntersección(origen);
        agregarIntersección(destino);
        
        listaAdyacencia.get(origen).add(new Calle(destino, pesoRuta, esDirigida));
        if(!esDirigida){
            listaAdyacencia.get(destino).add(new Calle(origen, pesoRuta, false));
        }
    }
    
    public Map<Interseccion, List<Calle>> listaAdyacencia(){
        return listaAdyacencia;
    }
}
