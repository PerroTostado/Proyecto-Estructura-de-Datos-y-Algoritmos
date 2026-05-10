package Entity;

import gta_java.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up, down, left, right;
    public String direction = "down";

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
    // Contador para el movimiento aleatorio
    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {} // Se sobrescribe en las subclases

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (!collisionOn) {
            switch (direction) {
                case "up":    worldY -= speed; break;
                case "down":  worldY += speed; break;
                case "left":  worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        switch (direction) {
            case "up":    image = up;    break;
            case "down":  image = down;  break;
            case "left":  image = left;  break;
            case "right": image = right; break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}