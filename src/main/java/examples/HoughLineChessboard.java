package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.*;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.analysis.algorithm.HoughLines;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.math.geometry.line.Line2d;
import org.openimaj.math.geometry.line.Line2d.IntersectionResult;
import org.openimaj.math.geometry.line.Line2d.IntersectionType;
import org.openimaj.math.geometry.point.Point2d;

/**
 * Example showing how to find the corners of a chessboard pattern using a hough
 * line detector.
 *
 * @author Sina Samangooei (ss@ecs.soton.ac.uk)
 */
public class HoughLineChessboard {

    public static void main(String[] args) throws IOException {

        final FImage chessboard = ImageUtilities.readF((new Util()).getClassloaderFile("xadrez2.jpg"));

        final HoughLines hlines = new HoughLines(1.f);
        chessboard.process(new CannyEdgeDetector()).analyseWith(hlines);

        final List<Line2d> lines = hlines.getBestLines(50);
        final List<Point2d> intersections = new ArrayList<Point2d>();
        for (final Line2d inner : lines) {
            for (final Line2d outer : lines) {
                if (inner == outer)
                    continue;

                final IntersectionResult intersect = inner.getIntersection(outer);
                if (intersect.type == IntersectionType.INTERSECTING) {
                    intersections.add(intersect.intersectionPoint);
                }
            }
        }

        // draw result
        final MBFImage chessboardC = chessboard.toRGB();
        chessboardC.drawLines(lines, 1, RGBColour.RED);
        chessboardC.drawPoints(intersections, RGBColour.GREEN, 3);

        DisplayUtilities.display(chessboardC);
    }
}