package tms.web.servlets.post;

import tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdatePost", urlPatterns = "/post/update")
public class UpdatePost extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));

        String title;
        if (req.getParameter("title") == null) title = postService.getPostById(postId).getTitle();
        else title = req.getParameter("title");

        String text;
        if (req.getParameter("text") == null) text = postService.getPostById(postId).getText();
        else text = req.getParameter("text");

        if (postService.update(postId, title, text)) resp.getWriter().print("Post \"" + title + "\" has been updated");
        else resp.getWriter().print("Error: Something went wrong =(");
    }

}