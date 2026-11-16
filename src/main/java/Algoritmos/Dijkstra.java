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
        
    public static double calcularCostoRuta(Grafo grafo, List<Interseccion> ruta) {
        if (ruta == null || ruta.size() < 2) return Double.MAX_VALUE;
        double costoTotal = 0.0;
        Map<Interseccion, List<Calle>> adyacencia = grafo.getListaAdyacencia();

        for (int i = 0; i < ruta.size() - 1; i++) {
            Interseccion actual = ruta.get(i);
            Interseccion siguiente = ruta.get(i + 1);
            for (Calle c : adyacencia.get(actual)) {
                if (c.getDestino().equals(siguiente)) {
                    costoTotal += c.getPesoRuta();
                    break;
                }
            }
        }
        return costoTotal;
    }

    
    public static Interseccion encontrarSedeMasCercana(Grafo grafo, Interseccion destino, String tipoSedeRequerida) {
        Interseccion mejorSede = null;
        double menorCosto = Double.MAX_VALUE;

        for (Interseccion nodo : grafo.getListaAdyacencia().keySet()) {
            if (nodo.isHaySede() && nodo.getTipoSede().equalsIgnoreCase(tipoSedeRequerida)) {
                List<Interseccion> ruta = calcularRutaMasCortas(grafo, nodo, destino);
                if (!ruta.isEmpty()) {
                    double costo = calcularCostoRuta(grafo, ruta);
                    if (costo < menorCosto) {
                        menorCosto = costo;
                        mejorSede = nodo;
                    }
                }
            }
        }
        return mejorSede;
    }
}
