package cs636.music.presentation;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// by GET to /servlet/SystemTestServlet
import org.springframework.boot.web.servlet.ServletComponentScan;
// This specifies where to look for @WebServlet servlets like SysTestServlet
@ServletComponentScan(basePackages = "cs636.music")
// This specifies where to look for @Components, ets.
@SpringBootApplication(scanBasePackages = { "cs636.music" })
// SBApplication is short for SpringBootApplication (web or not)
public class SBApplication {
	public static void main(String[] args) {
		System.out.println("Starting SBApplication, #args = " + args.length);
		SpringApplication app = new SpringApplication(SBApplication.class);
		String appCase = null; // web or SystemTest or ...
		if (args.length == 0) {
			appCase = "web";  // default to web case
		} else
			appCase = args[0];
		if (!appCase.equals("web")) {
			System.out.println("have arg " + appCase + " , assuming client execution");
			app.setBannerMode(Banner.Mode.OFF);
			app.setWebApplicationType(WebApplicationType.NONE); // don't start tomcat
		}
		app.run(args); // execute CommandRun.run
	}
}
