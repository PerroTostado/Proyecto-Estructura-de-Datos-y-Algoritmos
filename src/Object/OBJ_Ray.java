package Object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Ray extends SuperObject {
    public OBJ_Ray() {
        name = "Ray";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/ray.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}