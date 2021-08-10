package tms.web.servlets.user;

import tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUser", urlPatterns = "/delete-user")
public class DeleteUser extends HttpServlet {

    private final UserService us = new UserService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        if (us.deleteUserById(id)) resp.getWriter().print("User has been deleted");
        else resp.getWriter().print("User not found");
    }

}