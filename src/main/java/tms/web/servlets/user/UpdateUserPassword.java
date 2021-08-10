package tms.web.servlets.user;

import tms.entiy.Role;
import tms.entiy.User;
import tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateUserPassword", urlPatterns = "/update-user/password")
public class UpdateUserPassword extends HttpServlet {

    private final UserService us = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password;
        int id;

        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals(Role.ADMIN)) {
            password = req.getParameter("value");
            id = Integer.parseInt(req.getParameter("id"));
        } else { // USER able to change password value for yourself only
            password = req.getParameter("value");
            id = user.getId();
        }

        if (us.updatePasswordById(id, password)) resp.getWriter().print("Password has been updated");
        else resp.getWriter().print("Error: Something went wrong =(");


    }
}