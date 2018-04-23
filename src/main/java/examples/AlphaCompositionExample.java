package examples;

import java.awt.Font;
import java.io.IOException;
import java.net.URL;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.typography.general.GeneralFont;
import util.Util;

/**
 * Example showing alpha composition of text on an image.
 * 
 * @author David Dupplaw (dpd@ecs.soton.ac.uk)
 * @created 20 Jul 2012
 * @version $Author$, $Revision$, $Date$
 */
public class AlphaCompositionExample
{
    /**
	 * Main method
	 * 
	 * @param args
	 *            ignored
	 * @throws IOException
	 *             if image can't be loaded
	 */
	public static void main(final String[] args) throws IOException
	{

		// ------------------------------------------------------------------
		// FImage test
		// ------------------------------------------------------------------
		final FImage fi1 = ImageUtilities.readF((new Util()).getClassloaderFile("textoimagem.jpg"));
		final FImage fi2 = new FImage(300, 100);
		fi2.drawText("HELLO", 0, 35, new GeneralFont("Arial", Font.BOLD), 48);

		final FImage alpha = fi2.clone().multiplyInplace(0.5f);
		fi1.overlayInplace(fi2, alpha, 200, 200);

		DisplayUtilities.display(fi1, "Composite");

		// ------------------------------------------------------------------
		// MBFImage test
		// ------------------------------------------------------------------
		final MBFImage i1 = ImageUtilities.readMBF((new Util()).getClassloaderFile("textoimagem.jpg"));
		final MBFImage i2 = new MBFImage(300, 100, 3);
		i2.drawText("HELLO", 0, 35, new GeneralFont("Arial", Font.BOLD), 48);
		i2.addBand(alpha);
		i1.overlayInplace(i2, 200, 200);
		DisplayUtilities.display(i1, "Multiband Composite");

		final MBFImage i1b = ImageUtilities.readMBF((new Util()).getClassloaderFile("textoimagem.jpg"));
		i1b.drawImage(i2, 200, 200);
		DisplayUtilities.display(i1b, "Multiband Composite");
	}
}