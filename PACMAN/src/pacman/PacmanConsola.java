
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
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                String[] cmd = {
                    "cmd", "/c", "echo off & for /f \"tokens=*\" %i in ('powershell -Command \"Add-Type -MemberDefinition '\\''[DllImport(\\\"kernel32.dll\\\", SetLastError=true)] public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int mode); [DllImport(\\\"kernel32.dll\\\")] public static extern IntPtr GetStdHandle(int nStdHandle);'\\'' -and \"[console]::GetStdHandle(-11) | ForEach { [console]::SetConsoleMode($_, 0x0004 + ($_.mode -band 0x0004)) }\" ')"
                };
                new ProcessBuilder(cmd).start().waitFor();
                System.out.print("\033[0m");
            } catch (Exception e) {
                System.err.println("No se pudo activar ANSI automáticamente: " + e.getMessage());
            }
        }
        // ================================================================

        Tablero tablero = new Tablero();
        Scanner sc = new Scanner(System.in);

        // Buscar posiciones iniciales de Pacman y enemigos
        int pacmanFila = -1;
        int pacmanCol = -1;
        int[] enemigoFila = new int[10];   // Máximo 10 enemigos (más que suficiente)
        int[] enemigoCol = new int[10];
        int numEnemigos = 0;

        for (int i = 0; i < tablero.getAlto(); i++) {
            for (int j = 0; j < tablero.getAncho(); j++) {
                char c = tablero.getCelda(i, j).getTipo();

                if (c == 'P') {
                    pacmanFila = i;
                    pacmanCol = j;
                    tablero.getCelda(i, j).setTipo(Tablero.VACIO);
                } else if (c == 'E') {
                    if (numEnemigos < enemigoFila.length) {
                        enemigoFila[numEnemigos] = i;
                        enemigoCol[numEnemigos] = j;
                        numEnemigos++;
                        tablero.getCelda(i, j).setTipo(Tablero.PUNTO);
                    }
                }
            }
        }

        // Crear jugador y enemigos
        Jugador jugador = new Jugador(tablero, pacmanFila, pacmanCol);
        Enemigo[] enemigos = new Enemigo[numEnemigos];
        for (int i = 0; i < numEnemigos; i++) {
            enemigos[i] = new Enemigo(tablero, enemigoFila[i], enemigoCol[i]);
        }

        boolean gameOver = false;

        while (!gameOver) {
            tablero.imprimir(jugador, enemigos);
            System.out.println("Puntaje: " + jugador.getPuntaje());
            System.out.print("Dirección (w/a/s/d) o 'q' para salir: ");
            
            
            String input = sc.next().toLowerCase().trim();

            if (input.equals("q")) {
                System.out.println("¡Juego terminado por el jugador!");
                break;
            }

            if (input.length() > 0) {
                char dir = input.charAt(0);
                if ("wasd".indexOf(dir) != -1) {

                     jugador.mover(dir);
                    
                    

                    // Mover enemigos
                    
                        
                    
                    for (Enemigo e : enemigos) {
                        e.moverAleatorio();
                    }

                    // Detectar colisión con enemigos
                    for (Enemigo e : enemigos) {
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
