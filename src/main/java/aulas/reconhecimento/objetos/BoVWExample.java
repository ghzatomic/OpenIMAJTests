package aulas.reconhecimento.objetos;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openimaj.data.DataSource;
import org.openimaj.feature.SparseIntFV;
import org.openimaj.feature.SparseIntFVComparison;
import org.openimaj.feature.local.data.LocalFeatureListDataSource;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.feature.local.aggregate.BagOfVisualWords;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.ml.clustering.ByteCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.ByteKMeans;

public class BoVWExample {
	public static void main(String[] args) throws IOException {
		final URL[] imageUrls = {
				new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist1.jpg"),
				new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist2.jpg"),
				new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist3.jpg")
		};

		// Create an engine to extract some local features; in this case, we'll
		// get SIFT features located at the extrema in the
		// difference-of-Gaussian pyramid.
		final DoGSIFTEngine engine = new DoGSIFTEngine();

		// Now we load some load some images and extract their features. As
		// we're going to reuse these, we'll store them in a map of the image
		// identifier to list of local features.
		final Map<URL, LocalFeatureList<Keypoint>> imageKeypoints = new HashMap<URL, LocalFeatureList<Keypoint>>();
		for (final URL url : imageUrls) {
			// load image
			final FImage image = ImageUtilities.readF(url);

			// extract the features and store them in the map against the image
			// url
			imageKeypoints.put(url, engine.findFeatures(image));
		}

		// Next we need to cluster the features to build the set of visual
		// terms. We'll setup the clustering algorithm to create 200 visual
		// terms using approximate k-means.
		final ByteKMeans kmeans = ByteKMeans.createKDTreeEnsemble(200);

		// We need to get the data in the correct format for the clustering.
		// This can be done manually by copying the raw feature data into an
		// array, or by creating a DataSource as shown:
		final DataSource<byte[]> datasource = new LocalFeatureListDataSource<Keypoint, byte[]>(imageKeypoints);

		// Then we use the DataSource as input to the clusterer and get the
		// resultant centroids
		final ByteCentroidsResult result = kmeans.cluster(datasource);

		// In this example we want to create a standard BoVW model which uses
		// hard-assignment; this means that each local feature is mapped to a
		// single visual word. We can just use the default hard assigner to
		// achieve this.
		final HardAssigner<byte[], ?, ?> assigner = result.defaultHardAssigner();

		// We create a new BagOfVisualWords instance using our assigner, and
		// then use this to extract a vector representing the number of
		// occurrences of each visual word in our input images.
		final BagOfVisualWords<byte[]> bovw = new BagOfVisualWords<byte[]>(assigner);

		// We'll store the resultant occurrence vectors in a map
		final Map<URL, SparseIntFV> occurrences = new HashMap<URL, SparseIntFV>();
		for (final Entry<URL, LocalFeatureList<Keypoint>> entry : imageKeypoints.entrySet()) {
			occurrences.put(entry.getKey(), bovw.aggregate(entry.getValue()));
		}

		// That's basically it; from this point onwards you could use the
		// vectors to train a classifier, or measure the distance between them
		// to assess the similarity of the input images. To finish up, we'll
		// compute and print the distance matrix of our input images:
		for (final Entry<URL, SparseIntFV> entry1 : occurrences.entrySet()) {
			for (final Entry<URL, SparseIntFV> entry2 : occurrences.entrySet()) {
				// this computes the Euclidean distance. Note that we're not
				// normalising the vectors here, but in reality you probably
				// would want to.
				final double distance = SparseIntFVComparison.EUCLIDEAN.compare(entry1.getValue(), entry2.getValue());

				System.out.format("%2.3f\t", distance);
			}
			System.out.println();
		}
	}
}