/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Modelo.Emergencia;

/**
 *
 * @author Clarissa
 */
public class ListaCircularDoble {
    private NodoLista cabeza;
    private int tamaño;

    public ListaCircularDoble() {
        this.cabeza = null;
        this.tamaño = 0;
    }


    public boolean estaVacia() {
        return cabeza == null;
    }


    public void insertar(Emergencia emergencia) {
        NodoLista nuevoNodo = new NodoLista(emergencia);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);
        } else {
            NodoLista cola = cabeza.getAnterior();
            
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
        }
        tamaño++;
    }


    public Emergencia obtenerActual() {
        if (estaVacia()) return null;
        return cabeza.getEmergencia();
    }


    public void avanzar() {
        if (!estaVacia()) {
            cabeza = cabeza.getSiguiente();
        }
    }


    public void retroceder() {
        if (!estaVacia()) {
            cabeza = cabeza.getAnterior();
        }
    }

    public int getTamanio() {
        return tamaño;
    }
}