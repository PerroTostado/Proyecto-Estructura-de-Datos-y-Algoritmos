package gta_java;

import Entity.Entity;
import Entity.Player;
import Grafo.DiGrafo;
import Object.SuperObject;
import Tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // AJUSTES DE PANTALLA
    final int originalTileSize = 16;
    final int scale = 4;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // ESTADOS DEL JUEGO
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int winState = 3;
    public final int loseState = 4;

    // AJUSTES DEL MUNDO
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // CÁMARA
    public int cameraX;
    public int cameraY;

    // FPS
    int FPS = 60;

    Sound se = new Sound();
    Sound music = new Sound();
    public DiGrafo diGrafo = new DiGrafo(maxWorldRow, maxWorldCol);

    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;

    public Player player;
    public TileManager tileM;
    public CollisionChecker cChecker;
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[10];
    public UI ui = new UI(this);
    public Entity npc[] = new Entity[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.cChecker = new CollisionChecker(this);
        this.player = new Player(this, keyH);
        this.tileM = new TileManager(this);

        this.diGrafo = new DiGrafo(maxWorldRow, maxWorldCol);
        this.diGrafo.generar(tileM);

        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (gameState == playState) {
            player.update();

            // Actualizar posición de cámara centrada en el jugador
            cameraX = player.worldX - player.screenX;
            cameraY = player.worldY - player.screenY;

            // Límites: izquierda y arriba
            if (cameraX < 0) cameraX = 0;
            if (cameraY < 0) cameraY = 0;

            // Límites: derecha y abajo
            if (cameraX > worldWidth  - screenWidth)  cameraX = worldWidth  - screenWidth;
            if (cameraY > worldHeight - screenHeight) cameraY = worldHeight - screenHeight;

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }

        if (gameState == pauseState) {
            // El juego está congelado, no se actualiza nada
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // PLAYER
        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}