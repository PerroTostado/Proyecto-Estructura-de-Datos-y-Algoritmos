
package Grafo;

import Tile.RoadTile;
import Tile.Tile;
import Tile.TileManager;
import java.util.*;

public class DiGrafo {
  
    private Nodo[][] nodos;

    private int filas;
    private int columnas;

    public DiGrafo(int filas, int columnas) {

        this.filas = filas;
        this.columnas = columnas;

        nodos = new Nodo[filas][columnas];
    }

    

    public List<Nodo> buscarCaminoAStar(int startRow, int startCol, int targetRow, int targetCol) {
        // Resetear nodos antes de empezar
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                if (nodos[r][c] != null) {
                    nodos[r][c].parent = null;
                    nodos[r][c].gCost = 999999; // Infinito
                }
            }
        }

        Nodo startNode = nodos[startRow][startCol];
        Nodo targetNode = nodos[targetRow][targetCol];

        if (startNode == null || targetNode == null) return null;

        PriorityQueue<Nodo> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        Set<Nodo> closedList = new HashSet<>();

        startNode.gCost = 0;
        startNode.hCost = Math.abs(startRow - targetRow) + Math.abs(startCol - targetCol);
        startNode.calcularFCost();
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Nodo current = openList.poll();

            if (current == targetNode) {
                return reconstruirCamino(targetNode);
            }

            closedList.add(current);

            for (Arista arista : current.getVecinos()) {
                Nodo vecino = arista.getDestino();

                if (closedList.contains(vecino)) continue;

                int nuevoGCost = current.gCost + arista.getPeso();

                if (nuevoGCost < vecino.gCost) {
                    vecino.parent = current;
                    vecino.gCost = nuevoGCost;
                    vecino.hCost = Math.abs(vecino.getRow() - targetRow) + Math.abs(vecino.getCol() - targetCol);
                    vecino.calcularFCost();

                    if (!openList.contains(vecino)) {
                        openList.add(vecino);
                    }
                }
            }
        }
        return null;
    }


    private int calcularDistanciaManhattan(Nodo a, Nodo b) {
        // Usamos Manhattan porque en tu juego el movimiento es ortogonal (arriba, abajo, izq, der)
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }

    private List<Nodo> reconstruirCamino(Nodo targetNode) {
        List<Nodo> camino = new ArrayList<>();
        Nodo actual = targetNode;
        while (actual != null) {
            camino.add(0, actual);
            actual = actual.parent;
        }
        return camino;
    }

    // Generar el grafo automáticamente
    public void generar(TileManager tm) {
        int[][] mapa = tm.mapTileNum;

        // PASO 1: Crear nodos para tiles transitables

        for(int row = 0; row < filas; row++) {
            for(int col = 0; col < columnas; col++) {
                
                // Obtenemos el número del tile del mapa
                int tileNum = mapa[col][row];
                System.out.println("Procesando Fila: " + row + " Col: " + col + " Tile ID: " + tileNum);

                // SEGURIDAD 1: Verificar que el tile existe en el catálogo de TileManager
                if(tm.tile[tileNum] == null) {
                    // Si el tile es nulo, no creamos nodo y pasamos al siguiente
                    continue; 
                }

                // SEGURIDAD 2: Solo si el tile NO tiene colisión, creamos un Nodo
                if(!tm.tile[tileNum].collision) {
                    nodos[row][col] = new Nodo(row, col);
                }
            }
        }

        // PASO 2: Crear aristas
        for(int row = 0; row < filas; row++) {
            for(int col = 0; col < columnas; col++) {
                
                Nodo actual = nodos[row][col];
                if(actual == null) continue; // Si es una pared, no tiene aristas

                int tileNum = mapa[col][row];
                Tile tileActual = tm.tile[tileNum];

                // VALIDACIÓN DE SEGURIDAD
                if(tileActual == null) continue;

                if(tileActual instanceof RoadTile) {
                    RoadTile road = (RoadTile) tileActual;
                    // Verificamos que la lista de direcciones no sea nula
                    if(road.direcciones != null) {
                        for(Direction dir : road.direcciones) {
                            switch(dir) {
                                case UP:    conectar(actual, row - 1, col); break;
                                case DOWN:  conectar(actual, row + 1, col); break;
                                case LEFT:  conectar(actual, row, col - 1); break;
                                case RIGHT: conectar(actual, row, col + 1); break;
                            }
                        }
                    }
                } else {
                    // Si es un tile normal transitable (ej. pasto), conectamos 4 direcciones
                    conectar(actual, row - 1, col);
                    conectar(actual, row + 1, col);
                    conectar(actual, row, col - 1);
                    conectar(actual, row, col + 1);
                }
            }
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
