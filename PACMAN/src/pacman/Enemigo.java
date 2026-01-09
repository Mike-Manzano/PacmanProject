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
    private Tablero tablero;

    public Enemigo(Tablero tablero, int fila, int columna) {
        this.tablero = tablero;
        this.fila = fila;
        this.columna = columna;
    }

    
    
    //Comentario de prueba jaja
    public void moverAleatorio() {
        String dirs = "wasd";
        
        int nuevaFila;
        int nuevaCol;
        do{
            
        
        char dir = dirs.charAt((int)(Math.random() * 4));

        int df = 0;
        int dc = 0;

        switch (dir) {
            case 'w': df = -1; break;
            case 's': df =  1; break;
            case 'a': dc = -1; break;
            case 'd': dc =  1; break;
        }

        nuevaFila = fila + df;
        nuevaCol  = columna + dc;

        if (tablero.esValido(nuevaFila, nuevaCol)) {
            fila = nuevaFila;
            columna = nuevaCol;
        }
            
        }while(!tablero.esValido(nuevaFila, nuevaCol));
    }
    

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getFila()     { return fila; }
    public int getColumna()  { return columna; }
}
