package tms.web.servlets.post;

import tms.entiy.Post;
import tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetUnapprovedPosts", urlPatterns = "/post/get-unapproved")
public class GetUnapprovedPosts extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Post> postList = postService.getApprovedPost(false);
        if (!postList.isEmpty()) {
            for (Post post : postList) resp.getWriter().println(post.toString());
        } else resp.sendError(403);
    }

}