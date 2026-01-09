/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author WEB1-20
 */
public class Enemigo {
    private int fila;
    private int columna;
    private Tablero tablero;//Atributo de la clase Tablero en enemigo

    public Enemigo(Tablero tablero, int fila, int columna) {//Constructor de enemigo
        this.tablero = tablero;
        this.fila = fila;
        this.columna = columna;
    }

    public void moverAleatorio() {
        String dirs = "wasd";
        
        int nuevaFila;//fila que ocupara en el futuro
        int nuevaCol;//columna *****
        do{//Selecciona numeros aleatorios dentro del string "wasd" hasta que sean validos
            
        
        char dir = dirs.charAt((int)(Math.random() * 4));//Revisa que caracter hay en el numero indicado

        int df = 0; //direccion de la fila
        int dc = 0; //direccion de la columna

        switch (dir) { //guarda la direccion de la fila y columna en las variables df y dc
            case 'w': df = -1; break;
            case 's': df =  1; break;
            case 'a': dc = -1; break;
            case 'd': dc =  1; break;
        }

        nuevaFila = fila + df; //Establece cual sera la nueva fila, usando la actual +- la direccion
        nuevaCol  = columna + dc;//Establece cual sera la nueva columna, usando la actual +- la direccion

        if (tablero.esValido(nuevaFila, nuevaCol)) {//Llama a la funcion .esValido para revisar la casilla
            fila = nuevaFila; // mueve a nueva fila
            columna = nuevaCol;//mueve a nueva columna
        }
            
        }while(!tablero.esValido(nuevaFila, nuevaCol));
    }
    

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getFila(){ 
        return fila; 
    }
    
    public int getColumna() { 
        return columna; 
    }
}
