package tms.web.servlets.post;

import tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApprovePost", urlPatterns = "/post/approve")
public class ApprovePost extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        if (postService.approve(postId)) resp.getWriter().print("Post " + postId + " has been approve");
        else resp.getWriter().print("Error: Something went wrong =(");
    }
}