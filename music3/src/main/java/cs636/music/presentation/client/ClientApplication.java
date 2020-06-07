package cs636.music.presentation.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;

@SpringBootApplication(scanBasePackages = { "cs636.music" })
public class ClientApplication {	
	public static void main(String[] args) {
		 System.out.println("starting ClientApplication...");
		//disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(ClientApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setWebApplicationType(WebApplicationType.NONE);  // don't start tomcat
        System.out.println("starting ClientRun...");
        app.run(args);
	}
}
