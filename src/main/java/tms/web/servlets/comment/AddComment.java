package tms.web.servlets.comment;

import tms.entiy.Comment;
import tms.entiy.User;
import tms.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddComment", urlPatterns = "/comment/add")
public class AddComment extends HttpServlet {

    private final CommentService commentService = new CommentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        int postId = Integer.parseInt(req.getParameter("postId"));

        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();
        if (commentService.add(new Comment(0, text, postId, userId, false))) {
            resp.getWriter().println("Comment has been saved");
        } else {
            resp.getWriter().println("Something went wrong");
        }
    }

}
