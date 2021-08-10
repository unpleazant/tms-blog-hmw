package tms.dao;

import tms.entiy.Like;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbLikeDAO extends DbHelper {

    public DbLikeDAO() {
        openConnection();
    }

    public boolean save(Like like) {
        if (!exist(like)) {
            try {
                PreparedStatement likeStatement = connection.prepareStatement("insert into likes values (default, ?, ?) returning id");
                likeStatement.setInt(1, like.getPostId());
                likeStatement.setInt(2, like.getUserId());
                ResultSet postRes = likeStatement.executeQuery();
                if (postRes.next()) return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public boolean exist(Like like) {
        try {
            PreparedStatement likeStatement = connection.prepareStatement("" +
                    "select * from likes where post_id = ? and user_id = ?");
            likeStatement.setInt(1, like.getPostId());
            likeStatement.setInt(2, like.getUserId());
            ResultSet resultSet = likeStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Optional<List<Like>> getLikesByPostId(int postId) {
        List<Like> likes = new ArrayList<>();
        try {
            PreparedStatement likeStatement = connection.prepareStatement("select * from likes where post_id = ?");
            likeStatement.setInt(1, postId);
            ResultSet resultSet = likeStatement.executeQuery();
            while (resultSet.next()) {
                likes.add(new Like(
                        resultSet.getInt("post_id"),
                        resultSet.getInt("user_id")
                        ));
            }
            if (!likes.isEmpty()) return Optional.of(likes);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

}