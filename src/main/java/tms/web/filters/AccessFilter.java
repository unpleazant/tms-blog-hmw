package tms.web.filters;

import tms.entiy.Role;
import tms.entiy.User;
import tms.service.PostService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = {"UpdatePost", "DeletePost", "DeleteComment", "UpdateComment"})
public class AccessFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        PostService postService = new PostService();
        int postId = Integer.parseInt(req.getParameter("postId"));
        int authorId = postService.getPostById(postId).getUserId();
        User requestUser = (User) req.getSession().getAttribute("user");
        if (authorId == requestUser.getId() || requestUser.getRole().equals(Role.ADMIN)) chain.doFilter(req, res);
        else res.sendError(406, "Access denied");
    }
}