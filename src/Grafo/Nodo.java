
package Grafo;

import java.util.ArrayList;

public class Nodo {
    int row;
    int col;
    ArrayList<Arista> vecinos;

    // Campos para A*
    public Nodo parent;
    public int gCost; // Distancia desde el inicio
    public int hCost; // Distancia al objetivo (Heurística)
    public int fCost; // Suma de G + H

    public Nodo(int row, int col) {
        this.row = row;
        this.col = col;
        vecinos = new ArrayList<>();
    }

    public void calcularFCost() {
        this.fCost = gCost + hCost;
    }
    
    // Agregar conexión
    public void agregarArista(Nodo destino, int peso) {
        vecinos.add( new Arista(this, destino, peso));
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ArrayList<Arista> getVecinos() {
        return vecinos;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

