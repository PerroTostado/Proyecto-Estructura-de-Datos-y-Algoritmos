package Entity;

import gta_java.GamePanel;
import gta_java.KeyHandler;
import gta_java.UtilityTool; // Importamos la herramienta de escalado

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

import java.util.Timer;
import java.util.TimerTask;


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

        // Definimos el área de colisión (x, y, ancho, alto)
        solidArea = new Rectangle(); // Ajusta estos valores según el tamaño del sprite y la parte que colisione
        solidArea.x = 8;
        solidArea.y = 12;
        solidArea.width = 18;
        solidArea.height = 18;
        

        solidAreaDefaultX = solidArea.x; 
        solidAreaDefaultY = solidArea.y;
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
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            
            // 1. SOLO asignamos la dirección (eliminamos los worldX/Y -= speed de aquí)
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // 2. COMPROBAMOS LA COLISIÓN
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            // 3. SI NO HAY COLISIÓN, RECIÉN MOVEMOS AL JUGADOR
            if(collisionOn == false) {
                switch(direction) {
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
        if(i != 999) {
            String objectName = gp.obj[i].name;

            switch(objectName) {

                case "Ray":
                    gp.playSE(0); // Reproducimos el sonido del power-up

                    int speedBoost = 2;
                    speed += speedBoost; // Aumenta la velocidad
                    gp.obj[i] = null; // Elimina el objeto del mapa
                    System.out.println("¡Velocidad aumentada por 5 segundos!");

                    // Creamos un temporizador para revertir el efecto
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            speed -= speedBoost; // Restaura la velocidad original
                            System.out.println("El efecto de velocidad ha terminado.");
                            timer.cancel(); // Finaliza el timer
                        }
                    }, 5000); // 5000 milisegundos = 5 segundos
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

        // Como la imagen ya viene escalada de setup(), no pasamos el tamaño aquí
        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize/2, gp.tileSize/2, null);
        }
    }
}