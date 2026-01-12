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

    private final String[] layout = { //Constante que permite establecer el tablero posteriormente
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

    public Tablero() {//constructor de tablero que no requiere ningun input de parámetros. Cuando se invoca se genera una matriz de ancho y alto iguales al 
                       // de la constante layout. Despues identifica las casillas y coloca muros, jugadores...etc.
        int alto  = layout.length;
        int ancho = layout[0].length();
        celdas = new Celda[alto][ancho];//creación del objeto (matriz) que va a almacenar los movimientos y posiciones de enemigos, jugadores, y el estado de las demas varibles)

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                char c = layout[i].charAt(j);//Tomando el layout inicial como plantilla, rellena la matriz Celdas con las posiciones iniciales de todos los jugadores, enemigos y demás)
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

    public boolean esValido(int fila, int col) {//Comprobador de movimientos: Checkea una posición y comprueba que se encuentre dentro de la matriz y 
                                                //que la celda no se encuentre ocupada por un enemigo.
        if (fila < 0 || fila >= getAlto() || col < 0 || col >= getAncho() || celdas[fila][col].getTipo()==ENEMIGO) {
            return false;
        }
        return celdas[fila][col].getTipo()!= MURO;
    }

    public void imprimir(Jugador jugador, Enemigo[] enemigos) {//Imprime las posiciones, las cuales NO ESTAN ALMACENADAS EN TABLERO sino que se encuentran almacenadas
                                                               //En el propio objeto. Ej: Jugador tiene los parametros Fila y Columna, y por tanto la posición del jugador
                                                               //SE EXTRAE DE DICHOS PARAMETROS DEL OBJETO, NO DEL TABLERO.
        for (int i = 0; i < getAlto(); i++) {
            for (int j = 0; j < getAncho(); j++) {
                // Pacman
                if (i == jugador.getFila() && j == jugador.getColumna()) { //Primero comprueba si el jugador se encuentra en esta columna a través de los 
                    System.out.print(JUGADOR_COLOR + JUGADOR + RESET);    //atributos del objeto jugador.
                    continue;
                }

                // Enemigos
                boolean esEnemigo = false;
                for (Enemigo e : enemigos) { //Mismo proceso para enemigos: Comprueba parametros del objeto Enemigo, NO CHECKEA NINGUN TABLERO.
                    if (e.getFila() == i && e.getColumna() == j) {
                        System.out.print(ENEMIGO_COLOR + ENEMIGO + RESET);
                        esEnemigo = true;
                        break;
                    }
                }
                if (esEnemigo) { //Si ha encontrado un enemigo salta al siguiente bucle para no comprobar puntos y etc.
                    continue;
                }

                // Elementos del tablero
                char tipo = celdas[i][j].getTipo(); //Sin embargo, para los puntos y los muros si que comprueba el tablero.
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

    public boolean todosPuntosComidos() {//Checkea todo el tablero generado y si no encuentra puntos determina que todos han sido comidos.
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
