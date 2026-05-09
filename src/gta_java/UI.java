package gta_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import Object.OBJ_Ray; 

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage heartImage; 
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    private Graphics2D g2;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        
        OBJ_Ray ray = new OBJ_Ray(gp); 
        heartImage = ray.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        if (gp.gameState == gp.playState) {
            // Dibuja la UI normal (tiempo, mensajes, etc.)
            playTime += (double) 1 / 60;
            g2.setFont(arial_40);
            g2.setColor(Color.yellow);
            g2.drawString("Tiempo: " + dFormat.format(playTime), gp.tileSize * 11, 65);
        }
        
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if (gameFinished) {
            drawFinalScreen(g2);
        }

        // Mensaje de texto (¡Velocidad!)
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                
            messageCounter++;
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        g2.setColor(Color.yellow);
        String text = "PAUSA";
        int x = getXforCenteredText(text, g2);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawFinalScreen(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        String text = "¡Misión Completada!";
        int x = getXforCenteredText(text, g2);
        int y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);

        g2.setFont(arial_80B);
        g2.setColor(Color.yellow);
        text = "¡CONSEGUIDO!";
        x = getXforCenteredText(text, g2);
        y = gp.screenHeight / 2 + (gp.tileSize * 2);
        g2.drawString(text, x, y);
        
        gp.gameThread = null;
    }

    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}