package examples;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.analysis.algorithm.histogram.HistogramAnalyser;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.math.statistics.distribution.Histogram;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Histograma {

    public static void main(final String[] args) throws IOException {
        /*URL[] imageURLs = new URL[]{
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist1.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist2.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist3.jpg")
        };
        List<MultidimensionalHistogram> histograms = new ArrayList<MultidimensionalHistogram>();
        HistogramModel model = new HistogramModel(4, 4, 4);
        for (URL u : imageURLs) {
            model.estimateModel(ImageUtilities.readMBF(u));
            histograms.add(model.histogram.clone());
        }
        for (int i = 0; i < histograms.size(); i++) {
            for (int j = i; j < histograms.size(); j++) {
                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);
            }
        }*/
        Util util = new Util();
        MBFImage image = ImageUtilities.readMBF(util.getClassloaderFile("viuva.jpg"));

        HistogramModel model = new HistogramModel(10,4,1);
        MBFImage space = Transforms.RGB_TO_HSV(image);
        model.estimateModel(space);
        MultidimensionalHistogram feature = model.histogram;
        Float[][] colours = buildBinCols(feature);
        MBFImage colourGrid = new MBFImage(80,image.getHeight(),3);
        int sqW = (colourGrid.getWidth()/4);
        int sqH = (colourGrid.getHeight()/10);
        for(int y = 0; y < 4; y++){
            for(int k = 0; k < 10; k++){
                Rectangle draw = new Rectangle(y * sqW,sqH*k,sqW,sqH);
                colourGrid.drawShapeFilled(draw, colours[y * 10 + k]);
            }
        }

		DisplayUtilities.displayName(colourGrid, "wang");
        image.drawImage(colourGrid, image.getWidth()-colourGrid.getWidth(), 0);
    }

    public static Float[][] buildBinCols(MultidimensionalHistogram feature) {
        Float[][] binCols = new Float[10*4*1][];
        double maxFeature = feature.max();
        if(maxFeature == 0) maxFeature = 1;
        for (int k=0; k<10; k++) {
            for (int j=0; j<4; j++) {
                float s = (float)j/4 + (0.5f/4);
                float h = (float)k/10 + (0.5f/10);

                MBFImage img = new MBFImage(1,1, ColourSpace.HSV);
                img.setPixel(0, 0, new Float[] {h,s,(float) (feature.get(k,j,0) / maxFeature)});
//				img.setPixel(0, 0, new Float[] {h,s,1f});

                img = Transforms.HSV_TO_RGB(img);

                binCols[j* 10 + k] = img.getPixel(0, 0);
            }
        }
        return binCols;
    }
}
