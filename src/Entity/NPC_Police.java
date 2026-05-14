package Entity;

import gta_java.GamePanel;
import java.util.Random;
import javax.imageio.ImageIO;

public class NPC_Police extends Entity {

    public NPC_Police(GamePanel gp) {
        super(gp);
        direction = "left"; // Dirección inicial del NPC
        speed = 3  ; // Velocidad del NPC
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
        
        // Obtenemos el ID del tile donde está parado actualmente el NPC
        int currentCol = (worldX + gp.tileSize / 2) / gp.tileSize;
        int currentRow = (worldY + gp.tileSize / 2) / gp.tileSize;
        int currentTile = gp.tileM.mapTileNum[currentCol][currentRow];

        actionLockCounter++;

        // 1. CONDICIÓN DE GIRO: Solo si han pasado 2 segundos (120 frames) 
        // Y el tile actual es una esquina (0-3), una T (4, 6-8) o un cruce (9).
        if (actionLockCounter >= 120) {
            
            // Verificamos si el tile actual es de "Decisión" (ID 0 al 9)
            if (currentTile >= 0 && currentTile <= 9) {
                
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) direction = "up";
                else if (i > 25 && i <= 50) direction = "down";
                else if (i > 50 && i <= 75) direction = "left";
                else if (i > 75 && i <= 100) direction = "right";

                actionLockCounter = 0; // Reiniciamos el contador tras el giro
            } 
            // Si el contador llegó a 120 pero estamos en una vía normal (10-15),
            // no reiniciamos el contador a 0, para que en cuanto toque una esquina gire.
        }
    }

    @Override
        public void update() {
        // 1. OBTENER INFORMACIÓN DEL TILE ACTUAL
        // Calculamos el centro del NPC para saber exactamente sobre qué tile está parado
        int centerWorldX = worldX + gp.tileSize / 2;
        int centerWorldY = worldY + gp.tileSize / 2;
        int currentCol = centerWorldX / gp.tileSize;
        int currentRow = centerWorldY / gp.tileSize;
        
        // Evitar errores de índice fuera de los límites del mapa
        if (currentCol >= 0 && currentCol < gp.maxWorldCol && currentRow >= 0 && currentRow < gp.maxWorldRow) {
            int currentTile = gp.tileM.mapTileNum[currentCol][currentRow];

            // 2. LÓGICA DE GIRO (SOLO EN INTERSECCIONES 0-9)
            actionLockCounter++;
            
            // Si el contador llega a 2 segundos (120 frames) Y estamos en una intersección/esquina
            if (actionLockCounter >= 120 && currentTile >= 0 && currentTile <= 9) {
                
                Random random = new Random();
                int i = random.nextInt(100) + 1; // Número entre 1 y 100

                if (i <= 25) direction = "up";
                else if (i > 25 && i <= 50) direction = "down";
                else if (i > 50 && i <= 75) direction = "left";
                else if (i > 75 && i <= 100) direction = "right";

                actionLockCounter = 0; // Solo reiniciamos el contador si hubo posibilidad de giro
            }
        }

        // 3. COMPROBACIÓN DE COLISIONES
        collisionOn = false;
        gp.cChecker.checkTile(this);         // Muros o tiles con colisión (solid)
        gp.cChecker.checkObject(this, false); // Objetos en el mapa
        gp.cChecker.checkPlayer(this);       // Si choca con el jugador

        // 4. VERIFICACIÓN DE "SALIDA DE CARRETERA"
        // Comprobamos qué tile tiene enfrente antes de avanzar
        int nextTile = getTileAhead(); 
        if (nextTile < 0 || nextTile > 15) {
            // Si lo que hay enfrente NO es una vía (0-15), activamos colisión para que no avance
            collisionOn = true;
            // Forzamos al contador a estar listo para que intente girar en el siguiente frame
            actionLockCounter = 120; 
        }

        // 5. MOVIMIENTO FINAL
        if (collisionOn == false) {
            switch (direction) {
                case "up":    worldY -= speed; break;
                case "down":  worldY += speed; break;
                case "left":  worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
    }
    
    //Método auxiliar para detectar el tile que tiene el NPC justo enfrente
    public int getTileAhead() {
        int nextWorldX = worldX;
        int nextWorldY = worldY;

        switch (direction) {
            case "up":    nextWorldY -= speed; break;
            case "down":  nextWorldY += speed; break;
            case "left":  nextWorldX -= speed; break;
            case "right": nextWorldX += speed; break;
        }

        int col = (nextWorldX + gp.tileSize / 2) / gp.tileSize;
        int row = (nextWorldY + gp.tileSize / 2) / gp.tileSize;

        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            return gp.tileM.mapTileNum[col][row];
        }
        return -1;
    }
}