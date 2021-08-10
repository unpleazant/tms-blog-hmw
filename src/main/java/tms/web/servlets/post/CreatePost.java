package tms.web.servlets.post;

import tms.entiy.Post;
import tms.entiy.User;
import tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreatePost", urlPatterns = "/post/create")
public class CreatePost extends HttpServlet {

    private final PostService postService = new PostService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");

        if (!validateTitleLength(title)) resp.sendError(400, "the title max length is 170");
        if (!validateTextLength(text)) resp.sendError(400, "the text max length is 240");

        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();
        if (postService.create(new Post(0, title, text, userId, false))) {
            resp.getWriter().print("Post \"" + title + "\" has been created");
        } else {
            resp.getWriter().print("There is some error due creating the post");
        }
    }

    private boolean validateTitleLength(String title) {
        return title.length() > 6 && title.length() < 170;
    }

    private boolean validateTextLength(String text) {
        return text.length() > 6 && text.length() < 240;
    }

}