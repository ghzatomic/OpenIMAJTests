package aulas.controleDeBandas;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;
import util.Util;

import java.io.IOException;

public class DesenhoNaImagem {
    public static void main(final String[] args) throws IOException {
        Util util = new Util();

        MBFImage image = ImageUtilities.readMBF(util.getClassloaderFile("picapal.jpg"));

        System.out.println(image.colourSpace);

        MBFImage clone = image.clone();
        //clone.processInplace(new CannyEdgeDetector());

        clone.drawShapeFilled(new Ellipse(280f, 180f, 10f, 5f, 0f), RGBColour.WHITE);
        clone.drawShapeFilled(new Ellipse(330f, 155f, 15f, 7f, 0f), RGBColour.WHITE);
        clone.drawShapeFilled(new Ellipse(380f, 120f, 25f, 10f, 0f), RGBColour.WHITE);
        clone.drawShapeFilled(new Ellipse(420f, 65f, 50f, 20f, 0f), RGBColour.WHITE);

        clone.drawText("Pipoca !", 380, 70, HersheyFont.TIMES_MEDIUM, 15, RGBColour.BLACK);

        DisplayUtilities.display(clone);

    }

}
