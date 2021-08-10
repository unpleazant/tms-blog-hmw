package tms.web.servlets.user;

import tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateUserRole", urlPatterns = "/update-user/role")
public class UpdateUserRole extends HttpServlet {

    private final UserService us = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        int id = Integer.parseInt(req.getParameter("id"));

        if (us.updateRoleById(id, role)) resp.getWriter().print("Role has been updated");
        else resp.getWriter().print("Error: Something went wrong =(");

    }
}