package Entity;

import gta_java.GamePanel;
import java.util.Random;
import javax.imageio.ImageIO;

public class NPC_Police extends Entity {

    public NPC_Police(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
    }

    public void getImage() {
        try {
            // Asegúrate de tener estas imágenes en tu carpeta res/npc/
            up = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_arr.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_aba.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_der.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_izq.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) { // Cambia de dirección cada 2 segundos
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) direction = "up";
            if (i > 25 && i <= 50) direction = "down";
            if (i > 50 && i <= 75) direction = "left";
            if (i > 75 && i <= 100) direction = "right";

            actionLockCounter = 0;
        }
    }
}