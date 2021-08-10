package tms.web.filters;

import tms.entiy.Role;
import tms.entiy.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = {
        "DeleteUser",
        "UpdateUserRole",
        "ApprovePost",
        "GetUnapprovedPosts",
        "ApproveComment",
        "GetAllUnapprovedComments"
})
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null && user.getRole().equals(Role.ADMIN)) chain.doFilter(req, res);
        else res.sendError(403);
    }

}