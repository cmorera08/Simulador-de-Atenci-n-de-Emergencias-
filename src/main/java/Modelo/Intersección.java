/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Clarissa
 */
public class Intersección {
    private String id;
    private boolean haySede;
    private String tipoSede;
    
    public Intersección (String id, boolean haySede, String tipoSede){
        this.id = id;
        this.haySede = haySede;
        this.tipoSede = tipoSede;
    }
    
    public String getid(){
        return id;
    }
    public boolean gethaySede(){
        return haySede;
    }
    public String gettipoSede(){
        return tipoSede;
    }
}
