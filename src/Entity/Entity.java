
package Entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY; // Posición en el mapa global
    public int speed;
    public BufferedImage up, down, left, right;
    public String direction;
}