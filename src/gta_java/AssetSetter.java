package gta_java;

import Entity.NPC_Police;
import Object.OBJ_Ray;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Ray(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 8 * gp.tileSize;

        // Se pueden añadir más objetos 
    }

    public void setNPC() {
    gp.npc[0] = new NPC_Police(gp);
    gp.npc[0].worldX = gp.tileSize * 21;
    gp.npc[0].worldY = gp.tileSize * 8;
}
}