package tms.dao;

import tms.entiy.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static tms.dao.DbHelper.*;

public class DbCommentDAO {

    public DbCommentDAO() {
        openConnection();
    }

    public boolean save(Comment comment) {
        try {
            PreparedStatement commentStatement = connection.prepareStatement("insert into posts values (default, ?, ?, ?, ?) returning id");
            commentStatement.setString(1, comment.getText());
            commentStatement.setInt(2, comment.getUserId());
            commentStatement.setInt(3, comment.getPostId());
            commentStatement.setBoolean(4, comment.isApproved());
            ResultSet commRes = commentStatement.executeQuery();
            if (commRes.next()) {
                closeConnection();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void approve(int commentd) {
        try {
            PreparedStatement post = connection.prepareStatement("" +
                    "update comments " +
                    "set is_approved = true " +
                    "where id = ?");
            post.setInt(1, commentd);
            post.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Object> getById(int commentId) {
        try {
            PreparedStatement commentStatement = connection.prepareStatement("select * from comments where id = ?");
            commentStatement.setInt(1, commentId);
            ResultSet resultSet = commentStatement.executeQuery();
            if (resultSet.next()) {
                String text = resultSet.getString("text");
                int userId = resultSet.getInt("user_id");
                int postId = resultSet.getInt("post_id");
                boolean isApproved = resultSet.getBoolean("is_approved");
                return Optional.of(new Comment(commentId, text, postId, userId, isApproved));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(int commentId, String text) {
        try {
            PreparedStatement comment = connection.prepareStatement("" +
                    "update comments " +
                    "set text = ?, is_approved = false " +
                    "where id = ?");
            comment.setString(1, text);
            comment.setInt(2, commentId);
            comment.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int commentId) {
        try {
            PreparedStatement comment = connection.prepareStatement("delete from comments where id = ?");
            comment.setInt(1, commentId);
            comment.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
