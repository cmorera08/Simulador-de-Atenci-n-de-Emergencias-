/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Clarissa
 */
public class Interseccion {
    private String id;
    private boolean haySede;
    private String tipoSede;
    private int x, y;
    private boolean tieneEmergencia;

    
    public Interseccion(String id, boolean haySede, String tipoSede) {
        this.id = id;
        this.haySede = haySede;
        this.tipoSede = tipoSede;
        this.x = x;
        this.y = y;
        this.tieneEmergencia = false;
    }

    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public boolean isHaySede(){
        return haySede;
    }
    
    public boolean getHaySede(){
        return haySede;
    }
    
    public boolean gethaySede(){
        return haySede;
    }

    public String getTipoSede(){
        return tipoSede;
    }
    
    public String gettipoSede(){
        return tipoSede;
    } 

    public int getX() { 
        return x; 
    }
    
    public int getY() { 
        return y; 
    }

    public boolean isTieneEmergencia() { 
        return tieneEmergencia; 
    }
    
    public boolean gettieneEmergencia() { 
        return tieneEmergencia; 
    } // Añadido por compatibilidad con el panel visual

    public void setTieneEmergencia(boolean tieneEmergencia) { 
        this.tieneEmergencia = tieneEmergencia; 
    }
    
    @Override
    public String toString() {
        return id;
    }
}
