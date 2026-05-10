package Tile;
import grafo.Direction;
import java.util.ArrayList;

public class RoadTile extends Tile{
    
    public ArrayList<Direction> direcciones;

    public int costo;

    public RoadTile() {

        direcciones = new ArrayList<>();

        costo = 1;
    }
}
