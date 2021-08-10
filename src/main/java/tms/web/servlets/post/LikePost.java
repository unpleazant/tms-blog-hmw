package tms.web.servlets.post;

import tms.entiy.Like;
import tms.entiy.User;
import tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LikePost", urlPatterns = "/post/like")
public class LikePost extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        User user = (User) req.getSession().getAttribute("user");
        if (postService.like(new Like(postId, user.getId()))) resp.getWriter().println("Post \"" + postId + "\" has been liked");
    }
}