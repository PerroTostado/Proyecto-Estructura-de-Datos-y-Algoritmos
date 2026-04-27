package gta_java;
import javax.swing.JFrame;

public class GTA_JAVA {

    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("GTA II (JAVA)");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // Hace que la ventana se ajuste al tamaño preferido del GamePanel

        window.setLocationRelativeTo(null); // La ventana aparece en el centro
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}