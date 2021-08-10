package tms.web.servlets.user;

import tms.entiy.User;
import tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = "/login")
public class AuthServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.getByUsername(username);

        if (user == null) {
            resp.getWriter().print("User not found");
        } else if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
        } else {
            resp.getWriter().print("Wrong credentials");
        }

        if (req.getSession().getAttribute("user") != null) resp.getWriter().print("Authorization was successful");

    }

}