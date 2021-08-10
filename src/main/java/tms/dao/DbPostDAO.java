package tms.dao;

import tms.entiy.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbPostDAO extends DbHelper {

    public DbPostDAO() {
        openConnection();
    }

    public boolean save(Post post) {
        try {
            PreparedStatement postStatement = connection.prepareStatement("insert into posts values (default, ?, ?, ?, ?) returning id");
            postStatement.setString(1, post.getTitle());
            postStatement.setString(2, post.getText());
            postStatement.setInt(3, post.getUserId());
            postStatement.setBoolean(4, post.isApproved());
            ResultSet postRes = postStatement.executeQuery();
            if (postRes.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteById(int id) {
        try {
            PreparedStatement postStatement = connection.prepareStatement("delete from posts where id = ?");
            postStatement.setInt(1, id);
            postStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean existById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Optional<Post> getById(int postId) {
        try {
            PreparedStatement postStatement = connection.prepareStatement("select * from posts where id = ?");
            postStatement.setInt(1, postId);
            ResultSet resultSet = postStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String text = resultSet.getString("description");
                int userId = resultSet.getInt("user_id");
                boolean isApproved = resultSet.getBoolean("is_approved");
                return Optional.of(new Post(postId, title, text, userId, isApproved));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(int postId, String title, String text) {
        try {
            PreparedStatement post = connection.prepareStatement("" +
                    "update posts " +
                    "set title = ?, description = ?, is_approved = false " +
                    "where id = ?");
            post.setString(1, title);
            post.setString(2, text);
            post.setInt(3, postId);
            post.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void approve(int postId) {
        try {
            PreparedStatement post = connection.prepareStatement("" +
                    "update posts " +
                    "set is_approved = true " +
                    "where id = ?");
            post.setInt(1, postId);
            post.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<List<Post>> getApproved(Boolean isApproved) {
        List<Post> approvedPostList = new ArrayList<>();
        try {
            PreparedStatement approved = connection.prepareStatement("select * from posts where is_approved = ?");
            approved.setBoolean(1, isApproved);
            ResultSet resultSet = approved.executeQuery();
            while (resultSet.next()) {
                approvedPostList.add(new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("user_id"),
                        resultSet.getBoolean("is_approved")
                ));
            }
            if (!approvedPostList.isEmpty()) return Optional.of(approvedPostList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
