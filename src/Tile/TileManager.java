package Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import gta_java.GamePanel;
import Grafo.Direction;

public class TileManager {

    GamePanel gp;
    public Tile[] tile; // Aumentamos el tamaño del array para más tipos de baldosas
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[125]; // Puedes aumentar este número si tienes más tipos de baldosas
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        cargarMapaAleatorio();
    }

    public void cargarMapaAleatorio() {

        String mapas[] = { // Asegúrate de crear esta carpeta y archivo en 'src/main/resources'

            "/res/maps/map.txt",
            "/res/maps/map_1.txt",
            "/res/maps/map_2.txt"
        };

        int random
                = (int) (Math.random() * mapas.length);

        loadMap(mapas[random]);
    }

    public void getTileImage() {
        //esquinas
        setupRoad(0, "esq_inf_izq", false, Direction.UP, Direction.RIGHT);
        setupRoad(1, "esq_inf_der", false, Direction.UP, Direction.LEFT);
        setupRoad(2, "esq_sup_izq", false, Direction.DOWN, Direction.RIGHT);
        setupRoad(3, "esq_sup_der", false, Direction.DOWN, Direction.LEFT);
        //intersecciones T
        setupRoad(4, "inter_inf", false, Direction.LEFT, Direction.RIGHT, Direction.UP);
        setupRoad(6, "inter_sup", false, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
        setupRoad(7, "inter_izq", false, Direction.UP, Direction.DOWN, Direction.RIGHT);
        setupRoad(8, "inter_der", false, Direction.UP, Direction.DOWN, Direction.LEFT);
        //centro
        setupRoad(9, "inter_4_vias", false, Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN);
        //cebra
        setupRoad(12, "cebra_izq", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(13, "cebra_der", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(14, "cebra_arr", false, Direction.UP, Direction.DOWN);
        setupRoad(15, "cebra_aba", false, Direction.UP, Direction.DOWN);
        //vía
        setupRoad(10, "via_horizontal", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(11, "via_vertical", false, Direction.UP, Direction.DOWN);
        //finales
        setupRoad(5, "final_abajo", false, Direction.UP, Direction.DOWN);
        setupRoad(59, "final_arriba", false, Direction.UP, Direction.DOWN);
        setupRoad(60, "final_izq", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(61, "final_der", false, Direction.LEFT, Direction.RIGHT);
        //finales sentidos
        setupRoad(62, "final_abajo", false, Direction.UP);
        setupRoad(63, "final_abajo", false, Direction.DOWN);
        setupRoad(64, "final_arriba", false, Direction.DOWN);
        setupRoad(65, "final_arriba", false, Direction.UP);
        setupRoad(66, "final_izq", false, Direction.RIGHT);
        setupRoad(67, "final_izq", false, Direction.LEFT);
        setupRoad(68, "final_der", false, Direction.RIGHT);
        setupRoad(69, "final_der", false, Direction.LEFT);
        //vía sentidos
        setupRoad(21, "via_horizontal", false, Direction.RIGHT);
        setupRoad(22, "via_horizontal", false, Direction.LEFT);
        setupRoad(23, "via_vertical", false, Direction.UP);
        setupRoad(24, "via_vertical", false, Direction.DOWN);
        //centro sentidos
        setupRoad(25, "inter_4_vias", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(26, "inter_4_vias", false, Direction.UP, Direction.DOWN);
        setupRoad(27, "inter_4_vias", false, Direction.LEFT, Direction.UP);
        setupRoad(28, "inter_4_vias", false, Direction.RIGHT, Direction.UP);
        setupRoad(29, "inter_4_vias", false, Direction.LEFT, Direction.DOWN);
        setupRoad(30, "inter_4_vias", false, Direction.RIGHT, Direction.DOWN);
        setupRoad(31, "inter_4_vias", false, Direction.UP, Direction.LEFT, Direction.RIGHT);
        setupRoad(32, "inter_4_vias", false, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        setupRoad(33, "inter_4_vias", false, Direction.UP, Direction.DOWN, Direction.LEFT);
        setupRoad(34, "inter_4_vias", false, Direction.UP, Direction.DOWN, Direction.RIGHT);
        //T sentidos
        setupRoad(35, "inter_sup", false, Direction.LEFT, Direction.DOWN);
        setupRoad(36, "inter_sup", false, Direction.RIGHT, Direction.DOWN);
        setupRoad(37, "inter_sup", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(38, "inter_sup", false, Direction.DOWN);
        setupRoad(39, "inter_inf", false, Direction.LEFT, Direction.UP);
        setupRoad(40, "inter_inf", false, Direction.RIGHT, Direction.UP);
        setupRoad(41, "inter_inf", false, Direction.LEFT, Direction.RIGHT);
        setupRoad(42, "inter_inf", false, Direction.UP);
        setupRoad(43, "inter_izq", false, Direction.UP, Direction.RIGHT);
        setupRoad(44, "inter_izq", false, Direction.DOWN, Direction.RIGHT);
        setupRoad(45, "inter_izq", false, Direction.UP, Direction.DOWN);
        setupRoad(46, "inter_izq", false, Direction.RIGHT);
        setupRoad(47, "inter_der", false, Direction.UP, Direction.LEFT);
        setupRoad(48, "inter_der", false, Direction.DOWN, Direction.LEFT);
        setupRoad(49, "inter_der", false, Direction.UP, Direction.DOWN);
        setupRoad(50, "inter_der", false, Direction.LEFT);
        //esquinas sentidos
        setupRoad(51, "esq_inf_izq", false, Direction.UP);
        setupRoad(52, "esq_inf_izq", false, Direction.RIGHT);
        setupRoad(53, "esq_inf_der", false, Direction.UP);
        setupRoad(54, "esq_inf_der", false, Direction.LEFT);
        setupRoad(55, "esq_sup_izq", false, Direction.DOWN);
        setupRoad(56, "esq_sup_izq", false, Direction.RIGHT);
        setupRoad(57, "esq_sup_der", false, Direction.DOWN);
        setupRoad(58, "esq_sup_der", false, Direction.LEFT);
        //suelos
        setup(70, "suelo", true);
        setup(71, "acera", true);
        setup(72, "bosque", true);
        //edificio 1
        setup(16, "ed", true);
        setup(17, "edificio_horizontal_1.1", true);
        setup(18, "edificio_horizontal_1.2", true);
        setup(19, "edificio_horizontal_1.3", true);
        setup(20, "edificio_horizontal_1.4", true);

        setup(73, "edificio_vertical_1.1", true);
        setup(74, "edificio_vertical_1.2", true);
        setup(75, "edificio_vertical_1.3", true);

        setup(76, "edificio_esq_sup_izq_1.1", true);
        setup(77, "edificio_esq_sup_izq_1.2", true);
        setup(78, "edificio_esq_sup_izq_1.3", true);
        setup(79, "edificio_esq_sup_izq_1.4", true);
        setup(80, "edificio_esq_sup_izq_1.5", true);

        setup(81, "edificio_esq_sup_der_1.1", true);
        setup(82, "edificio_esq_sup_der_1.2", true);
        setup(83, "edificio_esq_sup_der_1.3", true);
        setup(84, "edificio_esq_sup_der_1.4", true);
        setup(85, "edificio_esq_sup_der_1.5", true);

        setup(86, "edificio_esq_inf_der_1.1", true);
        setup(87, "edificio_esq_inf_der_1.2", true);
        setup(88, "edificio_esq_inf_der_1.3", true);
        setup(89, "edificio_esq_inf_der_1.4", true);
        setup(90, "edificio_esq_inf_der_1.5", true);

        setup(91, "edificio_esq_inf_izq_1.1", true);
        setup(92, "edificio_esq_inf_izq_1.2", true);
        setup(93, "edificio_esq_inf_izq_1.3", true);
        setup(94, "edificio_esq_inf_izq_1.4", true);
        setup(95, "edificio_esq_inf_izq_1.5", true);

        //edificio 2
        setup(96, "edificio_horizontal_2.1", true);
        setup(97, "edificio_horizontal_2.2", true);
        setup(98, "edificio_horizontal_2.3", true);
        setup(99, "edificio_horizontal_2.4", true);

        setup(100, "edificio_vertical_2.1", true);
        setup(101, "edificio_vertical_2.2", true);
        setup(102, "edificio_vertical_2.3", true);

        setup(103, "edificio_esq_sup_izq_2.1", true);
        setup(104, "edificio_esq_sup_izq_2.2", true);
        setup(105, "edificio_esq_sup_izq_2.3", true);
        setup(106, "edificio_esq_sup_izq_2.4", true);
        setup(107, "edificio_esq_sup_izq_2.5", true);

        setup(108, "edificio_esq_sup_der_2.1", true);
        setup(109, "edificio_esq_sup_der_2.2", true);
        setup(110, "edificio_esq_sup_der_2.3", true);
        setup(111, "edificio_esq_sup_der_2.4", true);
        setup(112, "edificio_esq_sup_der_2.5", true);

        setup(113, "edificio_esq_inf_der_2.1", true);
        setup(114, "edificio_esq_inf_der_2.2", true);
        setup(115, "edificio_esq_inf_der_2.3", true);
        setup(116, "edificio_esq_inf_der_2.4", true);
        setup(117, "edificio_esq_inf_der_2.5", true);

        setup(118, "edificio_esq_inf_izq_2.1", true);
        setup(119, "edificio_esq_inf_izq_2.2", true);
        setup(120, "edificio_esq_inf_izq_2.3", true);
        setup(121, "edificio_esq_inf_izq_2.4", true);
        setup(122, "edificio_esq_inf_izq_2.5", true);
        
        //casa
        
        setup(123, "casa_1", true);
        setup(124, "casa_2", true);
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

    public void setupRoad(int index, String imageName, boolean collision, Direction... dirs) {
        try {
            RoadTile rt = new RoadTile();
            rt.image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            rt.collision = collision;
            for (Direction d : dirs) {
                rt.direcciones.add(d);
            }
            tile[index] = rt;
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
                if (line == null) {
                    break;
                }

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

    /* 
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

            // Coordenadas relativas a la pantalla
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Ajuste de cámara en bordes
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }

            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // Dibujar el tile
            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
