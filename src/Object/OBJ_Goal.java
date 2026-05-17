package Object;

import java.awt.Color;
import java.awt.Graphics2D;
import gta_java.GamePanel;

public class OBJ_Goal extends SuperObject {

    public OBJ_Goal(GamePanel gp) {
        name = "Goal";
        collision = false;

        // Dibuja un rectángulo amarillo como placeholder hasta tener sprite
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
            gp.tileSize, gp.tileSize, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = img.createGraphics();
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, gp.tileSize, gp.tileSize);
        g.setColor(Color.BLACK);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        g.drawString("META", 4, gp.tileSize / 2 + 8);
        g.dispose();

        image = img;

        // Área de colisión cubre todo el tile
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
    }
}