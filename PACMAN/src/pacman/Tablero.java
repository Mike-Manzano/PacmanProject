/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author WEB1-20
 */
public class Tablero {
    public static final char MURO    = 'X';
    public static final char PUNTO   = '.';
    public static final char VACIO   = ' ';
    public static final char JUGADOR = 'P';
    public static final char ENEMIGO = 'E';

    // Colores ANSI
    public static final String RESET          = "\u001B[0m";
    public static final String MURO_COLOR     = "\u001B[34m";  // Azul
    public static final String PUNTO_COLOR    = "\u001B[33m";  // Amarillo
    public static final String JUGADOR_COLOR  = "\u001B[32m";  // Verde
    public static final String ENEMIGO_COLOR  = "\u001B[31m";  // Rojo

    private Celda[][] celdas;

    private final String[] layout = {
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXX",
        "X............XX............X",
        "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
        "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
        "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
        "X.............E............X",
        "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
        "X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
        "X......XX....XX....XX......X",
        "XXXXXX.XXXXX.XX.XXXXX.XXXXXX",
        "XXXXXX.XXXXX.XX.XXXXX.XXXXXX",
        "XXXXXX.XX...E..E...XX.XXXXXX",
        "XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
        "XXXXXX.XX.X......X.XX.XXXXXX",
        "..........X......X..........",
        "XXXXXX.XX.X......X.XX.XXXXXX",
        "XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
        "XXXXXX.XX..........XX.XXXXXX",
        "XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
        "XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
        "X............XX............X",
        "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
        "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
        "X...XX.......P..........XX...X",
        "XXX.XX.XX.XXXXXXXX.XX.XX.XXX",
        "XXX.XX.XX.XXXXXXXX.XX.XX.XXX",
        "X......XX....XX....XX......X",
        "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
        "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
        "X..........................X",
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXX"
        
        
        
    
    };

    public Tablero() {
        int alto  = layout.length;
        int ancho = layout[0].length();
        celdas = new Celda[alto][ancho];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                char c = layout[i].charAt(j);
                if (c == MURO || c == PUNTO || c == VACIO || c == JUGADOR || c == ENEMIGO) {
                    celdas[i][j] = new Celda(c);
                } else {
                    celdas[i][j] = new Celda(PUNTO);
                }
            }
        }
    }

    public int getAlto() {
        return celdas.length;
    }

    public int getAncho() {
        return celdas[0].length;
    }

    public Celda getCelda(int fila, int col) {
        if (fila >= 0 && fila < getAlto() && col >= 0 && col < getAncho()) {
            return celdas[fila][col];
        }
        return null;
    }

    public boolean esValido(int fila, int col) {
        if (fila < 0 || fila >= getAlto() || col < 0 || col >= getAncho() || celdas[fila][col].getTipo()==ENEMIGO) {
            return false;
        }
        return celdas[fila][col].getTipo()!= MURO;
    }

    public void imprimir(Jugador jugador, Enemigo[] enemigos) {
        for (int i = 0; i < getAlto(); i++) {
            for (int j = 0; j < getAncho(); j++) {
                // Pacman
                if (i == jugador.getFila() && j == jugador.getColumna()) {
                    System.out.print(JUGADOR_COLOR + JUGADOR + RESET);
                    continue;
                }

                // Enemigos
                boolean esEnemigo = false;
                for (Enemigo e : enemigos) {
                    if (e.getFila() == i && e.getColumna() == j) {
                        System.out.print(ENEMIGO_COLOR + ENEMIGO + RESET);
                        esEnemigo = true;
                        break;
                    }
                }
                if (esEnemigo) {
                    continue;
                }

                // Elementos del tablero
                char tipo = celdas[i][j].getTipo();
                String color = "";

                if (tipo == MURO) {
                    color = MURO_COLOR;
                } else if (tipo == PUNTO) {
                    color = PUNTO_COLOR;
                } else {
                    color = RESET;
                }

                System.out.print(color + tipo + RESET);
            }
            System.out.println();
        }
    }

    public boolean todosPuntosComidos() {
        for (int i = 0; i < getAlto(); i++) {
            for (int j = 0; j < getAncho(); j++) {
                if (celdas[i][j].getTipo() == PUNTO) {
                    return false;
                }
            }
        }
        return true;
    }
}
