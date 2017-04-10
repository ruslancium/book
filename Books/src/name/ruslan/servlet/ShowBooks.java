package name.ruslan.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.ruslan.dao.BookDAOImpl;

@WebServlet("/showAllBooks")
public class ShowBooks extends HttpServlet{
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {		  
	     request.setAttribute("books", new BookDAOImpl().findAllBooks());
	     RequestDispatcher view = request.getRequestDispatcher("/jsp/listOfBooks1.jsp");
	        view.forward(request, response);
	 }


}
