package aulas.reconhecimento;

import org.openimaj.feature.FloatFV;
import org.openimaj.feature.FloatFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypointExtractor;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.feature.FacePatchFeature;
import org.openimaj.image.processing.face.feature.comparison.FaceFVComparator;
import org.openimaj.image.processing.face.similarity.FaceSimilarityEngine;
import org.openimaj.image.processing.face.util.KEDetectedFaceRenderer;
import org.openimaj.math.geometry.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ReconhecimentoDaFace {

    public static void main(String[] args) throws Exception {
        // first, we load two images
        final URL image1url = new URL(
                "http://s3.amazonaws.com/rapgenius/fema_-_39841_-_official_portrait_of_president-elect_barack_obama_on_jan-_13.jpg");
        final MBFImage image1 = ImageUtilities.readMBF(image1url);

        FaceDetector<DetectedFace, FImage> fd = new HaarCascadeDetector(40);

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

        FKEFaceDetector fke = new FKEFaceDetector();
        List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(image1));
        for (DetectedFace face : faces) {
            image1.drawShape(face.getBounds(), RGBColour.RED);
        }

        DisplayUtilities.display(image1);

    }
}
