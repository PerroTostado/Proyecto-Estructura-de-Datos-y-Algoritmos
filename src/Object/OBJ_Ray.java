package Object;

import java.io.IOException;
import javax.imageio.ImageIO;
import gta_java.GamePanel;
import gta_java.UtilityTool;

public class OBJ_Ray extends SuperObject {
    public OBJ_Ray(GamePanel gp) {
        name = "Ray";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/ray.png"));
            UtilityTool uTool = new UtilityTool();
            // Escalamos el objeto al tamaño del tile
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}