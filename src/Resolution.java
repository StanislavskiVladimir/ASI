import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resolution {
    public void start(String[] args) {
        if(args.length <2){
            System.out.println("Неверные аргументы");
            return;
        }
        BufferedImage img;
        try {
            img = ImageIO.read(new File(args[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ширина = " + img.getWidth());
        System.out.println("Высота = " + img.getHeight());
    }
}
