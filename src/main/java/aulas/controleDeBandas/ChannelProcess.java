package aulas.controleDeBandas;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import util.Util;

import java.io.IOException;

public class ChannelProcess {

    public static void main(final String[] args) throws IOException {
        Util util = new Util();

        MBFImage image = ImageUtilities.readMBF(util.getClassloaderFile("viuva.jpg"));

        System.out.println("Colour Space : "+image.colourSpace);

        System.out.println("Bandas : "+image.numBands());

        MBFImage clone = image.clone();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                clone.getBand(1).pixels[y][x] = 0;
                //clone.getBand(2).pixels[y][x] = 0;
            }
        }
        DisplayUtilities.display(clone);

    }

}
