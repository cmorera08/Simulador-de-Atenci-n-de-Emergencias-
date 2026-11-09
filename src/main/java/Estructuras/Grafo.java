/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import modelo.Intersección;
import modelo.Calle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Clarissa
 */
public class Grafo {
    private Map<Intersección, List<Calle>> listaAdyacencia;
    
    public Grafo(){
        this.listaAdyacencia = new HashMap<>();
    }
    
    public void agregarIntersección(Intersección intersección){
        if(!listaAdyacencia.containsKey(intersección)){
            listaAdyacencia.put(intersección, new LinkedList<>());
        }
    }
    
    public void agregarCalle(Intersección origen, Intersección destino, double pesoRuta, boolean esDirigida){
        agregarIntersección(origen);
        agregarIntersección(destino);
        
        listaAdyacencia.get(origen).add(new Calle(destino, pesoRuta, esDirigida));
        if(!esDirigida){
            listaAdyacencia.get(destino).add(new Calle(origen, pesoRuta, false));
        }
    }
    
    public Map<Intersección, List<Calle>> listaAdyacencia(){
        return listaAdyacencia;
    }
}
