package by.htp.library.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.controller.command.Command;
import by.htp.library.dao.util.EMUtil;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommandProvider provider = new CommandProvider();
       
    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String command = request.getParameter("command");
		
		Command commandObject = provider.getCommand(command);
		commandObject.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		Command commandObject = provider.getCommand(command);
		commandObject.execute(request, response);

		/*
		EntityManager em = EMUtil.getEntityManager();
		try {
			em.getTransaction().begin();

			em.getTransaction().commit();
		} catch (RollbackException e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		*/
		//doGet(request, response);
	}
	
	public static boolean isAttributeExecute(HttpServletRequest request, String attribute) {
		boolean isAttributeExecute = false; 
		HttpSession session = request.getSession();
		Enumeration<String> eNames = session.getAttributeNames();
		while (eNames.hasMoreElements()) {
			  String attributeName = (String) eNames.nextElement();
			  if(attributeName.equals(attribute)) {
				  isAttributeExecute = true;
				  break;
			  }
			}
		return isAttributeExecute;
	}

}
