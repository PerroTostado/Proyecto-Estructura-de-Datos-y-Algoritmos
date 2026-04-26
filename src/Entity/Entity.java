
package Entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    
    // Solo una imagen por dirección
    public BufferedImage up, down, left, right;
    public String direction;
}