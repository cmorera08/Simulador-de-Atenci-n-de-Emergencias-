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
public class NodoLista {
    private Emergencia emergencia;
    private NodoLista siguiente;
    private NodoLista anterior;

    public NodoLista(Emergencia emergencia) {
        this.emergencia = emergencia;
        this.siguiente = null;
        this.anterior = null;
    }

    
    public Emergencia getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
    }

    public NodoLista getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoLista siguiente) {
        this.siguiente = siguiente;
    }

    public NodoLista getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoLista anterior) {
        this.anterior = anterior;
    }
}