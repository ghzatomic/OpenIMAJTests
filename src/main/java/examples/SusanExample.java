package examples;

import java.io.IOException;
import java.net.URL;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.edges.SUSANEdgeDetector;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

public class SusanExample {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final SUSANEdgeDetector ss = new SUSANEdgeDetector();

        // Process an image
        FImage fi = ImageUtilities.readF(new URL(
                "http://upload.wikimedia.org/wikipedia/commons/6/6e/Similar-geometric-shapes.png"));
        DisplayUtilities.display(fi.process(ss));
        DisplayUtilities.display(SUSANEdgeDetector.smoothCircularSusan(fi, 0.01, 24, 3.8));

        // Process a video with the simple SUSAN detector
        VideoCapture vc = new VideoCapture(640, 480);
        VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(vc);
        vd.addVideoListener(new VideoDisplayListener<MBFImage>() {
            public void beforeUpdate(MBFImage frame) {
                frame.processInplace(ss);
            }

            public void afterUpdate(VideoDisplay<MBFImage> display) {
            }
        });
    }
}