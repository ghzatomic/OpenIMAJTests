package aulas.reconhecimento.objetos;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

import java.io.File;
import java.net.URL;

public class CaioSIFTVideo {

    public static void main(String[] args) throws Exception {
        // first, we load two images
        final MBFImage query = ImageUtilities.readMBF(new File("/Dados/Desenvolvimento/WorkspaceTeste/TesteOpenIMAJ/src/main/resources/bolinha.jpeg"));


        final DoGSIFTEngine engine = new DoGSIFTEngine();
        final LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());



        // MOSTRAR OS PONTOS

        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(new VideoCapture(320, 240));
        display.addVideoListener(
                new VideoDisplayListener<MBFImage>() {
                    public void beforeUpdate(MBFImage frame) {
                        RobustHomographyEstimator modelFitter = new RobustHomographyEstimator(0.1, 1000, new RANSAC.BestFitStoppingCondition(),
                                HomographyRefinement.NONE);
                        ConsistentLocalFeatureMatcher2d<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                                createFastBasicMatcher());


                        matcher.setFittingModel(modelFitter);
                        matcher.setModelFeatures(queryKeypoints);
                        matcher.findMatches(engine.findFeatures(frame.flatten()));

                        MBFImage consistentMatches = MatchingUtilities.drawMatches(query, frame, matcher.getMatches(),
                                RGBColour.RED);
                        /*frame.drawShape(
                                query.getBounds().transform(modelFitter.getModel().getTransform().inverse()), 3, RGBColour.BLUE);*/

                    }

                    public void afterUpdate(VideoDisplay<MBFImage> display) {
                    }
                });


    }

    private static LocalFeatureMatcher<Keypoint> createFastBasicMatcher() {
        return new FastBasicKeypointMatcher<Keypoint>(8);
    }

}
