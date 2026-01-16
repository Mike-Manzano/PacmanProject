
/*
 * Para activar colores ejecuta el siguiente comando en PowerShell (solo una vez):
 * > Set-ItemProperty HKCU:\Console VirtualTerminalLevel -Type DWORD 1
 * 
 * Alternativamente, el programa intenta activarlo automáticamente.
 */
package pacman;

import java.util.Scanner;

public class PacmanConsola {

    public static void main(String[] args) {
        // === ACTIVAR ANSI AUTOMÁTICAMENTE EN WINDOWS (CMD/PowerShell) ===
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {//Revisa que el sistema sea windows(?
            try {
                String[] cmd = {//Crea un array de String con, parece, comandos...
                    "cmd", "/c", "echo off & for /f \"tokens=*\" %i in ('powershell -Command \"Add-Type -MemberDefinition '\\''[DllImport(\\\"kernel32.dll\\\", SetLastError=true)] public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int mode); [DllImport(\\\"kernel32.dll\\\")] public static extern IntPtr GetStdHandle(int nStdHandle);'\\'' -and \"[console]::GetStdHandle(-11) | ForEach { [console]::SetConsoleMode($_, 0x0004 + ($_.mode -band 0x0004)) }\" ')"
                };
                new ProcessBuilder(cmd).start().waitFor(); //Abre el cmd (?
                System.out.print("\033[0m");
            } catch (Exception e) { //En caso de error
                System.err.println("No se pudo activar ANSI automáticamente: " + e.getMessage());
            }
        }
        // ================================================================

        Tablero tablero = new Tablero(); //Crea objeto de la clase Tablero
        Scanner sc = new Scanner(System.in);

        // Buscar posiciones iniciales de Pacman y enemigos
        int pacmanFila = -1;
        int pacmanCol = -1;
        int[] enemigoFila = new int[10];   // Máximo 10 enemigos (más que suficiente)
        int[] enemigoCol = new int[10];
        int numEnemigos = 0;//Enemigos antes del conteo

        for (int i = 0; i < tablero.getAlto(); i++) {//Revisa la matriz tablero
            for (int j = 0; j < tablero.getAncho(); j++) {
                char c = tablero.getCelda(i, j).getTipo(); // Crea c con el tipo de la celda con indice i, j

                if (c == 'P') {// Si es player
                    pacmanFila = i;
                    pacmanCol = j;
                    tablero.getCelda(i, j).setTipo(Tablero.VACIO);//Elimina el punto, es comido por el jugador
                } else if (c == 'E') {//Si es enemigo
                    if (numEnemigos < enemigoFila.length) {
                        enemigoFila[numEnemigos] = i; //Guarda la fila del enemigo numEnemigos
                        enemigoCol[numEnemigos] = j; //Guarda la columna *******
                        numEnemigos++; //Aumenta el numero
                        tablero.getCelda(i, j).setTipo(Tablero.PUNTO);//Aun que haya enemigo, la celda del tablero tendra un punto
                    }
                }
            }
        }

        // Crear jugador y enemigos
        Jugador jugador = new Jugador(tablero, pacmanFila, pacmanCol); //Crea objeto de Jugador
        Enemigo[] enemigos = new Enemigo[numEnemigos];// crea un array de enemigos
        for (int i = 0; i < numEnemigos; i++) {// como hay multiples enemigos
            enemigos[i] = new Enemigo(tablero, enemigoFila[i], enemigoCol[i]);//crea todos los enemigos
        }

        boolean gameOver = false;//Check para el fin de la partida

        while (!gameOver) {//Bucle principal del juego
            tablero.imprimir(jugador, enemigos);
            System.out.println("Puntaje: " + jugador.getPuntaje());
            System.out.print("Dirección (w/a/s/d) o 'q' para salir: ");

            String input = sc.next().toLowerCase().trim(); // registra el input del user 

            //Revisa los inputs
            if (input.equals("q")) {
                System.out.println("¡Juego terminado por el jugador!");
                break;
            }

            if (input.length() > 0) {//Si hay algun input...
                char dir = input.charAt(0);//Revisa la primera letra del mismo que determinara la direccion
                if ("wasd".indexOf(dir) != -1) {

                    jugador.mover(dir); //Llama a la funcion mover de Jugador
                    
                    //Revision tras el moviemiento del player para evitar atravesar enemigos
                    for (Enemigo e : enemigos) {//TODO metodo de revision colisionEnemigo
                        //Revisa las filas y columnas ACTUALES de enemigos y jugador para ver si chocan
                        if (e.getFila() == jugador.getFila() && e.getColumna() == jugador.getColumna()) {
                            tablero.imprimir(jugador, enemigos);
                            System.out.println("\n¡Un fantasma te atrapó! Game Over.");
                            gameOver = true;
                            break;
                        }
                    }

                    // Mover enemigos
                    for (Enemigo e : enemigos) {//Recorre el array de enemigos 
                        //TODO introducir metodo para revisar los enemigos no atraviesen enemigos
                        e.moverAleatorio(enemigos); //Llama a la funcion mover para cada enemigo
                    }

                    // Detectar colisión con enemigos
                    for (Enemigo e : enemigos) {
                        //Revisa las filas y columnas ACTUALES de enemigos y jugador para ver si chocan
                        if (e.getFila() == jugador.getFila() && e.getColumna() == jugador.getColumna()) {
                            tablero.imprimir(jugador, enemigos);
                            System.out.println("\n¡Un fantasma te atrapó! Game Over.");
                            gameOver = true;
                            break;
                        }
                    }

                    // Victoria
                    if (!gameOver && tablero.todosPuntosComidos()) {
                        tablero.imprimir(jugador, enemigos);
                        System.out.println("\n¡FELICIDADES! ¡Comiste todos los puntos!");
                        gameOver = true;
                    }
                }
            }
        }

        sc.close();
    }
}
