package Entity;

import gta_java.GamePanel;
import gta_java.KeyHandler;
import gta_java.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        // Centrar al jugador exactamente en el centro de la pantalla
        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;

        setDefaultValues();
        getPlayerImage();

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 12;
        solidArea.width = 18;
        solidArea.height = 18;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up    = setup("up");
        down  = setup("down");
        left  = setup("left");
        right = setup("right");
    }

    // Carga y escala la imagen al tamaño del tile
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
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
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // Asignar dirección según tecla presionada
            if      (keyH.upPressed)    direction = "up";
            else if (keyH.downPressed)  direction = "down";
            else if (keyH.leftPressed)  direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // Comprobar colisión antes de mover
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Mover solo si no hay colisión
            if (!collisionOn) {
                switch (direction) {
                    case "up":    worldY -= speed; break;
                    case "down":  worldY += speed; break;
                    case "left":  worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }


        }

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Ray":
                    gp.playSE(0);
                    int speedBoost = 2;
                    speed += speedBoost;
                    gp.obj[i] = null;
                    gp.ui.showMessage("¡VELOCIDAD AUMENTADA!");

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            speed -= speedBoost;
                            gp.ui.showMessage("Efecto terminado");
                            timer.cancel();
                        }
                    }, 5000);
                    break;

                case "Goal":
                    gp.gameState = gp.winState;
                    break;
            }
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

        int carWidth  = gp.tileSize / 2;
        int carHeight = gp.tileSize / 2;

        // Convierte coordenadas del mundo a coordenadas de pantalla usando la cámara
        int x = worldX - gp.cameraX;
        int y = worldY - gp.cameraY;

        if (image != null) {
            g2.drawImage(image, x, y, carWidth, carHeight, null);
        }
    }
}