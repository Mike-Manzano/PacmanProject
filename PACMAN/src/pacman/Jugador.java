/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author WEB1-20
 */
public class Jugador {
    private int fila;
    private int columna;
    private int puntaje = 0;
    private Tablero tablero;

    public Jugador(Tablero tablero, int fila, int columna) {
        this.tablero = tablero;
        this.fila = fila;
        this.columna = columna;
    }

    public boolean mover(char dir) {
        int df = 0;
        int dc = 0;
        
        
        switch (dir) {
            case 'w': df = -1; break;
            case 's': df =  1; break;
            case 'a': dc = -1; break;
            case 'd': dc =  1; break;
        }

        int nuevaFila = fila + df;
        int nuevaCol  = columna + dc;

        if (tablero.esValido(nuevaFila, nuevaCol)) {
            if (tablero.getCelda(nuevaFila, nuevaCol).getTipo() == Tablero.PUNTO) {
                puntaje++;
                tablero.getCelda(nuevaFila, nuevaCol).setTipo(Tablero.VACIO);
            }
            fila = nuevaFila;
            columna = nuevaCol;
            return true;
        }
        return false;
        
    }

    public int getFila()     { return fila; }
    public int getColumna()  { return columna; }
    public int getPuntaje()  { return puntaje; }
}
