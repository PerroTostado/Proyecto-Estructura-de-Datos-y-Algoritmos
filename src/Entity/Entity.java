
package Entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
public class Entity {
    public int worldX, worldY; // Posición en el mapa global
    public int speed;
    
    // Área de colisión invisible
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public BufferedImage up, down, left, right;
    public String direction;
}