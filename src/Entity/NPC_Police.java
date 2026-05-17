package Entity;

import gta_java.GamePanel;
import java.util.List;
import javax.imageio.ImageIO;
import Grafo.Nodo;

public class NPC_Police extends Entity {

    public NPC_Police(GamePanel gp) {
        super(gp);
        direction = "left";
        speed = 2;
        getImage();
    }

    public void getImage() {
        try {
            up    = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_arr.png"));
            down  = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_aba.png"));
            left  = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_der.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/npc/policia_izq.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAction() {
        int policeCol = (worldX + gp.tileSize / 2) / gp.tileSize;
        int policeRow = (worldY + gp.tileSize / 2) / gp.tileSize;
        int playerCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int playerRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

        if (worldX % gp.tileSize == 0 && worldY % gp.tileSize == 0) {
            List<Nodo> ruta = gp.diGrafo.buscarCaminoAStar(policeRow, policeCol, playerRow, playerCol);
            if (ruta != null && ruta.size() > 1) {
                Nodo siguiente = ruta.get(1);
                if      (siguiente.getRow() < policeRow) direction = "up";
                else if (siguiente.getRow() > policeRow) direction = "down";
                else if (siguiente.getCol() < policeCol) direction = "left";
                else if (siguiente.getCol() > policeCol) direction = "right";
            }
        }
    }
}