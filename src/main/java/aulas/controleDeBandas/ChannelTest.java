package aulas.controleDeBandas;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import util.Util;

import java.io.IOException;

public class ChannelTest {

    public static void main(final String[] args) throws IOException{
        Util util = new Util();

        MBFImage image = ImageUtilities.readMBF(util.getClassloaderFile("viuva.jpg"));

        System.out.println(image.colourSpace);

        DisplayUtilities.display(image);
        DisplayUtilities.display(image.getBand(0),"ChannelTest 0 ");
        DisplayUtilities.display(image.getBand(1),"ChannelTest 1 ");
        DisplayUtilities.display(image.getBand(2),"ChannelTest 2 ");

    }

}
