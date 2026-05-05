
package Entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Entity {
    public int worldX, worldY; // Posición en el mapa global
    public int speed;
    
    // Área de colisión invisible
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Área de colisión general
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public BufferedImage up, down, left, right;
    public String direction;

    


    
}