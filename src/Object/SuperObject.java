package Object;
 
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import gta_java.GamePanel;
 
public class SuperObject {
 
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
 
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
 
    public void draw(Graphics2D g2, GamePanel gp) {
        // Convierte coordenadas del mundo a coordenadas de pantalla usando la cámara
        int screenX = worldX - gp.cameraX;
        int screenY = worldY - gp.cameraY;
 
        // Solo dibuja si está dentro de la pantalla visible
        if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
            screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
 
            g2.drawImage(image, screenX, screenY, null);
        }
    }
}