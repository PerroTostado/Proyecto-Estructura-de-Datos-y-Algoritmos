package Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import gta_java.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile; // Aumentamos el tamaño del array para más tipos de baldosas
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[17]; // Puedes aumentar este número si tienes más tipos de baldosas
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/map.txt"); // Asegúrate de crear esta carpeta y archivo en 'src/main/resources'
    }

    public void getTileImage() {
        
            // Esquineras
            setup(0, "esq_inf_izq", false);
            setup(1, "esq_inf_der", false);
            setup(2, "esq_sup_izq", false);
            setup(3, "esq_sup_der", false);
            // Intersecciones en T y Cruce
            setup(4, "inter_inf", false);
            setup(6, "inter_sup", false);
            setup(7, "inter_izq", false);
            setup(8, "inter_der", false);
            setup(9, "inter_4_vias", false);
            // Vías normales
            setup(10, "via_horizontal", false);
            setup(11, "via_vertical", false);
            // Pasos de cebra
            setup(12, "cebra_izq", false);
            setup(13, "cebra_der", false);
            setup(14, "cebra_arr", false);
            setup(15, "cebra_aba", false);
            // Edificios / Fondo
            setup(16, "ed", false);
        
    }

    // Método auxiliar para cargar imágenes de forma limpia
    public void setup(int index, String imageName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                String numbers[] = line.trim().split(" ");

                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



/** 
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
*/
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            
            // Calcular la posición relativa a la pantalla
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Rendimiento: Solo dibujar si la baldosa es visible en pantalla
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}