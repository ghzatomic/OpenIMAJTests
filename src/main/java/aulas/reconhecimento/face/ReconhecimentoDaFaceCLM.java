package aulas.reconhecimento.face;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.CLMFaceDetector;

import java.net.URL;
import java.util.List;

public class ReconhecimentoDaFaceCLM {

    public static void main(String[] args) throws Exception {
        // first, we load two images
        final URL image1url = new URL(
                "http://s3.amazonaws.com/rapgenius/fema_-_39841_-_official_portrait_of_president-elect_barack_obama_on_jan-_13.jpg");
        final MBFImage frame = ImageUtilities.readMBF(image1url);

/*
        // A simple Haar-Cascade face detector
HaarCascadeDetector det1 = new HaarCascadeDetector();
DetectedFace face1 = det1.detectFaces(img).get(0);
new SimpleDetectedFaceRenderer()
	.drawDetectedFace(mbf,10,face1);
// Get the facial keypoints
FKEFaceDetector det2 = new FKEFaceDetector();
KEDetectedFace face2 = det2.detectFaces(img).get(0);
new KEDetectedFaceRenderer()
	.drawDetectedFace(mbf,10,face2);
// With the CLM Face Model
CLMFaceDetector det3 = new CLMFaceDetector();
CLMDetectedFace face3 = det3.detectFaces(img).get(0);
new CLMDetectedFaceRenderer()
	.drawDetectedFace(mbf,10,face3);

        FKEFaceDetector det2 = new FKEFaceDetector();
        KEDetectedFace face2 = det2.detectFaces(image1).get(0);
        new KEDetectedFaceRenderer()
                .drawDetectedFace(image1,10,face2);*/

        CLMFaceDetector fd = new CLMFaceDetector();
        List<CLMDetectedFace> faces = fd.detectFaces( Transforms.calculateIntensity( frame ) );

        for( CLMDetectedFace face : faces ) {
            frame.drawShape(face.getBounds(), RGBColour.RED);

            System.out.println(face.getRoll());
        }

        DisplayUtilities.display(frame);

    }
}
