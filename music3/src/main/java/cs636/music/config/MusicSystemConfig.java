package cs636.music.config;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Betty O'Neil
 *
 *         No need to configure the service objects: Spring does that for us
 *         Just a few utilities left.
 * 
 */

public class MusicSystemConfig {
	public static final String SOUND_BASE_URL = "http://www.cs.umb.edu/cs636/music1-setup/sound/";

	// Compose an exception report
	// and return the string for callers to use
	public static String exceptionReport(Exception e) {
		String message = e.toString(); // exception name + message
		if (e.getCause() != null) {
			message += "\n  cause: " + e.getCause().toString();
			if (e.getCause().getCause() != null)
				message += "\n    cause's cause: "
						+ e.getCause().getCause().toString();
		}
		return message + exceptionStackTraceString(e);
	}
	
	private static String exceptionStackTraceString(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
