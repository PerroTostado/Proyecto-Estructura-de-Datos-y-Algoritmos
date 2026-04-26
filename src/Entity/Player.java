/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp; // Aquí recibes el panel que ya existe
        this.keyH = keyH;
        
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
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
            y -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
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
            g2.drawImage(image, x, y, null);
        }
    }
}