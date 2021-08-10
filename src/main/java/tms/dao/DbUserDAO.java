package tms.dao;

import tms.entiy.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbUserDAO extends DbHelper {

    public DbUserDAO() {
        openConnection();
    }

    public void save(User user) {
        try {
            PreparedStatement userStatement = connection.prepareStatement("insert into users values (default, ?, ?, ?, ?)");
            userStatement.setString(1, user.getName());
            userStatement.setString(2, user.getUsername());
            userStatement.setString(3, user.getPassword());
            userStatement.setString(4, user.getRole());
            userStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateUsernameById(int id, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set username = ? where id = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePasswordById(int id, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ? where id = ?");
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRoleById(int id, String role) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set role = ? where id = ?");
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<User> getByUsername(String searchUsername) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select u.id, u.name, u.username, u.password, u.role " +
                            "from users u " +
                            "where username = ?");

            preparedStatement.setString(1, searchUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                return Optional.of(new User(userId, name, username, password, role));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<User>> getAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                users.add(new User(id, name, username, password, role));
            }
            if (!users.isEmpty()) return Optional.of(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean exist(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean existByID(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteById(int id) {
        try {
            setAutoCommit(false);

            PreparedStatement users = connection.prepareStatement("delete from users where id = ?");
            users.setInt(1, id);
            users.execute();

            PreparedStatement posts = connection.prepareStatement("delete from posts where user_id = ?");
            posts.setInt(1, id);
            posts.execute();

            PreparedStatement comments = connection.prepareStatement("delete from comments where user_id = ?");
            comments.setInt(1, id);
            comments.execute();

            connection.commit();
        } catch (SQLException throwables) {
            commitRollback();
            throwables.printStackTrace();
        } finally {
            setAutoCommit(true);
        }
    }
}