package tms.web.servlets.comment;

import tms.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateComment", urlPatterns = "/comment/update")
public class UpdateComment extends HttpServlet {

    private final CommentService commentService = new CommentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));

        String text;
        if (req.getParameter("text") == null) text = commentService.getCommentById(commentId).getText();
        else text = req.getParameter("text");

        commentService.update(commentId, text);
    }
}
