import java.awt.GraphicsDevice;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.io.ByteArrayOutputStream;
import java.applet.Applet;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class CaptureCurrentMonitorScreenshot extends Applet{

	public String screenshot() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage exportImage = null;
		String imageString = null;
		try {		
			try {
				exportImage = AccessController.doPrivileged(
					new PrivilegedExceptionAction<BufferedImage>() {
						@Override
						public BufferedImage run() throws AWTException {
							Robot robot = new Robot();
							GraphicsDevice currentDevice = MouseInfo.getPointerInfo().getDevice();
							return robot.createScreenCapture(currentDevice.getDefaultConfiguration().getBounds());
						}
					}
				);
			} catch (PrivilegedActionException e) {
				throw (AWTException) e.getException();
			}
			ImageIO.write(exportImage, "png", baos);
			imageString = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());			
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return imageString;
	}

}