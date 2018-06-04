package aulas.reconhecimento.objetos;

import org.openimaj.OpenIMAJ;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.io.IOUtils;

import java.io.IOException;

public class BasicSIFTExtractionExample {
	public static void main(String[] args) throws IOException {
		// read an image
		final FImage image = ImageUtilities.readF(OpenIMAJ.getLogoAsStream());

		// create the extractor - this can be reused (and is thread-safe)
		final DoGSIFTEngine engine = new DoGSIFTEngine();

		// find interest points in the image and extract SIFT descriptors
		final LocalFeatureList<Keypoint> keypoints = engine.findFeatures(image);

		// print the interest points to stdout
		IOUtils.writeASCII(System.out, keypoints);
	}
}