package gta_java;

import java.util.ArrayList;
import java.util.Random;

import Entity.NPC_Police;
import Object.OBJ_Goal;
import Object.OBJ_Ray;
import Tile.RoadTile;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // Lista para guardar posiciones válidas de carretera
        ArrayList<int[]> carreterasDisponibles = new ArrayList<>();

        // Buscar carreteras en el mapa
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {

                int tileNum = gp.tileM.mapTileNum[col][row];

                // Verificar si el tile es carretera
                if (gp.tileM.tile[tileNum] instanceof RoadTile) {
                    carreterasDisponibles.add(new int[]{col, row});
                }
            }
        }

        // Generar objetos aleatorios
        if (!carreterasDisponibles.isEmpty()) {

            Random random = new Random();
            int randomIndex = random.nextInt(carreterasDisponibles.size());
            int[] pos = carreterasDisponibles.get(randomIndex);
            gp.obj[4] = new OBJ_Goal(gp);
                    gp.obj[4].worldX = pos[0] * gp.tileSize;
                    gp.obj[4].worldY = pos[1] * gp.tileSize;
            // Crear 3 objetos
            for (int i = 0; i < 3; i++) {

                if (i < gp.obj.length) {

                    randomIndex = random.nextInt(carreterasDisponibles.size());
                    pos = carreterasDisponibles.get(randomIndex);

                    gp.obj[i] = new OBJ_Ray(gp);
                    gp.obj[i].worldX = pos[0] * gp.tileSize;
                    gp.obj[i].worldY = pos[1] * gp.tileSize;
                }
            }
        }
    }

    public void setNPC() {

        // Lista para guardar todas las coordenadas de carreteras encontradas
        ArrayList<int[]> carreterasDisponibles = new ArrayList<>();

        // Escaneamos el mapa buscando RoadTiles
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {

                int tileNum = gp.tileM.mapTileNum[col][row];

                // Verificamos si ese tile es una carretera
                if (gp.tileM.tile[tileNum] instanceof RoadTile) {
                    carreterasDisponibles.add(new int[]{col, row});
                }
            }
        }

        // Si encontramos carreteras, posicionamos a los policías al azar
        if (!carreterasDisponibles.isEmpty()) {

            Random random = new Random();

            // Crear policías en lugares aleatorios de la carretera
            for (int i = 0; i < 1; i++) {

                if (i < gp.npc.length) {

                    int randomIndex = random.nextInt(carreterasDisponibles.size());
                    int[] pos = carreterasDisponibles.get(randomIndex);

                    gp.npc[i] = new NPC_Police(gp);
                    gp.npc[i].worldX = pos[0] * gp.tileSize;
                    gp.npc[i].worldY = pos[1] * gp.tileSize;
                }
            }
        }
    }
}