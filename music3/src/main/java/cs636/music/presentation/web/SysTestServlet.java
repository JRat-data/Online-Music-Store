package cs636.music.presentation.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cs636.music.config.MusicSystemConfig;
import cs636.music.presentation.SystemTest;
import cs636.music.service.UserService;
import cs636.music.service.AdminService;
// Quick way to run code as web app without JSPs
@WebServlet(
		  name = "SysTestServlet",
		  description = "Servlet to run SystemTest",
		  urlPatterns = {"/servlet/SystemTestServlet"}
		)
public class SysTestServlet extends HttpServlet {
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;

	private static final long serialVersionUID = 3971217231726348088L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result;
		try {
			SystemTest test = new SystemTest(adminService, userService);
			test.runSystemTest();
			result = "Success";
		} catch (Exception e) {
			result = "Error in run: " + MusicSystemConfig.exceptionReport(e);
			System.out.println(result); 
			throw new ServletException(result, e);  // cause HTTP 500
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("in doGet of SysTestServlet, after SystemTest execution");
		out.println("<!DOCTYPE HTML>");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet running SystemTest</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(" <h2> SystemTest Result </h2>");
		out.println("<p> "+ result + "</p>");
		out.println("<p> for more info, see console log </p>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.close();
	}

}
