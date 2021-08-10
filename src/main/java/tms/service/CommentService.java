package tms.service;

import tms.dao.DbCommentDAO;
import tms.entiy.Comment;

public class CommentService {

    private final DbCommentDAO dbCommentDAO = new DbCommentDAO();

    public boolean add(Comment comment) {
        return dbCommentDAO.save(comment);
    }

    public void approve(int commentId) {
        dbCommentDAO.approve(commentId);
    }

    public Comment getCommentById(int commentId) {
        return (Comment) dbCommentDAO.getById(commentId).orElse(null);
    }

    public void update(int commentId, String text) {
        dbCommentDAO.update(commentId, text);
    }

    public void delete(int commentId) {
        dbCommentDAO.deleteById(commentId);
    }
}
