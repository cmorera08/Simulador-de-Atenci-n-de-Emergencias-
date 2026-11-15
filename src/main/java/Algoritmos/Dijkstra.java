/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import Estructuras.Grafo;
import Modelo.Interseccion;
import Modelo.Calle;
import java.util.*;
/**
 *
 * @author Clarissa
 */
public class Dijkstra {
    public static List<Interseccion> calcularRutaMasCortas(Grafo grafo, Interseccion inicio, Interseccion fin) {
        
        Map<Interseccion, Double> distancias = new HashMap<>();
        Map<Interseccion, Interseccion> predecesores = new HashMap<>();
        Set<Interseccion> noVisitados = new HashSet<>();
        
        Map<Interseccion, List<Calle>> adyacencia = grafo.getListaAdyacencia();

        for (Interseccion inter : adyacencia.keySet()) {
            distancias.put(inter, Double.MAX_VALUE);
            noVisitados.add(inter);
        }

        
        distancias.put(inicio, 0.0);

        while (!noVisitados.isEmpty()) {
            Interseccion actual = null;
            double menorDistancia = Double.MAX_VALUE;

            for (Interseccion inter : noVisitados) {
                double dist = distancias.get(inter);
                if (dist < menorDistancia) {
                    menorDistancia = dist;
                    actual = inter;
                }
            }

            
            if (actual == null || actual.equals(fin)) break;

            noVisitados.remove(actual);

            for (Calle calle : adyacencia.get(actual)) {
                Interseccion vecino = calle.getDestino();
                if (noVisitados.contains(vecino)) {
                    double nuevaDistancia = distancias.get(actual) + calle.getPesoRuta();
                    if (nuevaDistancia < distancias.get(vecino)) {
                        distancias.put(vecino, nuevaDistancia);
                        predecesores.put(vecino, actual);
                    }
                }
            }
        }


        LinkedList<Interseccion> ruta = new LinkedList<>();
        Interseccion paso = fin;
        if (predecesores.containsKey(paso) || paso.equals(inicio)) {
            while (paso != null) {
                ruta.addFirst(paso);
                paso = predecesores.get(paso);
            }
        }
        return ruta;
    }
}
