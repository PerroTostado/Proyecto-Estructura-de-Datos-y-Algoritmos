
package Grafo;

import Tile.Tile;
import Tile.TileManager;

public class DiGrafo {
  
    private Nodo[][] nodos;

    private int filas;
    private int columnas;

    public DiGrafo(int filas, int columnas) {

        this.filas = filas;
        this.columnas = columnas;

        nodos = new Nodo[filas][columnas];
    }

    // Generar el grafo automáticamente
    public void generar(TileManager tm) {

        int[][] mapa = tm.mapTileNum;

        /*
            PASO 1:
            Crear nodos para tiles transitables
        */

        for(int row = 0; row < filas; row++) {

            for(int col = 0; col < columnas; col++) {

                int tileNum = mapa[col][row];

                Tile tile = tm.tile[tileNum];

                // Si NO hay colisión, es transitable
                if(!tile.collision) {

                    nodos[row][col] =
                        new Nodo(row, col);
                }
            }
        }

        /*
            PASO 2:
            Crear aristas dirigidas
        */

        for(int row = 0; row < filas; row++) {

            for(int col = 0; col < columnas; col++) {

                Nodo actual = nodos[row][col];

                if(actual == null) continue;

                int tileNum = mapa[col][row];

                Tile tileActual = tm.tile[tileNum];

                /* ARRIBA
                if(tileActual.direcciones.contains(Direccion.UP)) {

                    conectar(actual, row - 1, col);
                }

                // ABAJO
                if(tileActual.direcciones.contains(Direccion.DOWN)) {

                    conectar(actual, row + 1, col);
                }

                // IZQUIERDA
                if(tileActual.direcciones.contains(Direccion.LEFT)) {

                    conectar(actual, row, col - 1);
                }

                // DERECHA
                if(tileActual.direcciones.contains(Direccion.RIGHT)) {

                    conectar(actual, row, col + 1);

                }
*/            }
        }
    }

    // Crear conexión segura
    private void conectar(
        Nodo origen,
        int rowDestino,
        int colDestino
    ) {

        // Validar límites
        if(rowDestino < 0 || rowDestino >= filas ||
           colDestino < 0 || colDestino >= columnas) {

            return;
        }

        Nodo destino = nodos[rowDestino][colDestino];

        // Si existe nodo destino
        if(destino != null) {

            origen.agregarArista(destino, 1);
        }
    }

    public Nodo getNodo(int row, int col) {

        return nodos[row][col];
    }

    public void imprimir() {

        for(int row = 0; row < filas; row++) {

            for(int col = 0; col < columnas; col++) {

                Nodo n = nodos[row][col];

                if(n != null) {

                    System.out.println(
                        "Nodo " + n
                    );

                    for(Arista a : n.getVecinos()) {

                        System.out.println(
                            "   " + a
                        );
                    }
                }
            }
        }
    }
}
