package cs636.music.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cs636.music.presentation.client.AdminApp;
import cs636.music.presentation.client.UserApp;
import cs636.music.service.AdminService;
import cs636.music.service.UserService;

@Component
public class CommandRun implements CommandLineRunner {
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	// uncomment the following if you uncomment the listing of beans below 
//	@Autowired
//	ApplicationContext ctx;

	// note that this runs at webapp startup as well as client-app startup, allowing
	// dynamic
	// initialization of the webapp if needed.
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting CommandRun");	
		// at this point, the beans are already set up--look at them--
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		System.out.println("Seeing beans: ");
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
		if (args.length == 0 || args[0].contentEquals("web")) {
			return; // could do webapp initialization here if needed
		}
		String clientApp = args[0];
		
		switch (clientApp) {
		case "SystemTest":
			System.out.println("Starting SystemTest from CommandRun");
			SystemTest st = new SystemTest(adminService, userService);
			st.runSystemTest();
			break;
		case "UserApp":
			System.out.println("Starting UserApp from CommandRun");
			UserApp app = new UserApp(userService);
			app.handleCatalogPage(); // top level of app
			break;
		case "AdminApp":
			System.out.println("Starting AdminApp from CommandRun");
			AdminApp adminApp = new AdminApp(adminService);
			adminApp.loginPage();
			break;
		default:
			System.out.println("Unknown client app: " + clientApp);
		}
	}
}
