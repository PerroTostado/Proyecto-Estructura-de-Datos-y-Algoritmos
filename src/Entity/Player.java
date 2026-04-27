package Entity;

import gta_java.GamePanel;
import gta_java.KeyHandler;
import gta_java.UtilityTool; // Importamos la herramienta de escalado

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp; // Solo declaras la variable, NO le pongas "= new GamePanel()"
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Centrar al jugador en la pantalla
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // Posición inicial en el mapa del mundo (ejemplo: baldosa 23, 21)
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        // Usamos el método setup para cargar y escalar cada imagen
        up = setup("up");
        down = setup("down");
        left = setup("left");
        right = setup("right");
    }

    // Método helper para evitar que el programa truene si no encuentra la imagen
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            // La ruta comienza con / para indicar la raíz de la carpeta src
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));

            if (image == null) {
                System.out.println("No se pudo encontrar la imagen en: /res/player/" + imageName + ".png");
            } else {
                image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        // Movimiento básico basado en KeyHandler
        if (keyH.upPressed) {
            direction = "up";
            worldY -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            worldY += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            worldX -= speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":    image = up;    break;
            case "down":  image = down;  break;
            case "left":  image = left;  break;
            case "right": image = right; break;
        }

        // Como la imagen ya viene escalada de setup(), no pasamos el tamaño aquí
        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}