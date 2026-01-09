/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author WEB1-20
 */
public class Celda {
    private char tipo;
    
    
    public Celda(char c){
        this.tipo = c;
    }
    
    
    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {    
        this.tipo = tipo;
    }

}
