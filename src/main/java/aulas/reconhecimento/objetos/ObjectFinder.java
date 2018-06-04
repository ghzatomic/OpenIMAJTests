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
import org.openimaj.math.geometry.transforms.HomographyModel;
import org.openimaj.math.model.fit.RANSAC;

import java.net.URL;

public class ObjectFinder {


    public static void main(String[] args) throws Exception {
        // first, we load two images
        MBFImage query = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/query.jpg"));
        MBFImage target = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/target.jpg"));

        DoGSIFTEngine engine = new DoGSIFTEngine();
        LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
        LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());

        LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<Keypoint>(80);
        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);

        MBFImage basicMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
        DisplayUtilities.display(basicMatches);
        
        /*final MBFImage image1 = ImageUtilities.readMBF(image1url);
        DoGSIFTEngine eng = new DoGSIFTEngine();
        LocalFeatureList sourceFeats = eng.findFeatures(source);
        LocalFeatureList targetFeats = eng.findFeatures(target);
        // Prepare the matcher
        final HomographyModel model = new HomographyModel(5f);
        final RANSAC ransac =
                new RANSAC(model, 1500,
                        new RANSAC.BestFitStoppingCondition(), true);
        ConsistentLocalFeatureMatcher2d matcher =
                new ConsistentLocalFeatureMatcher2d
                        (new FastBasicKeypointMatcher(8));
        // Match the features
        matcher.setFittingModel(ransac);
        matcher.setModelFeatures(sourceFeats);
        matcher.findMatches(targetFeats);*/
    }
}
