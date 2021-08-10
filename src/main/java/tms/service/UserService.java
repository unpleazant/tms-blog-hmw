package tms.service;

import tms.entiy.User;
import tms.dao.DbUserDAO;

public class UserService {

    private final DbUserDAO dbUserDAO = new DbUserDAO();

    public boolean regNewUser(User user) {
        if (dbUserDAO.exist(user)) {
            return false;
        } else {
            dbUserDAO.save(user);
            return true;
        }
    }

    public User getByUsername(String userName) {
        if (dbUserDAO.getByUsername(userName).isPresent()) return dbUserDAO.getByUsername(userName).get();
        else return null;
    }

    public boolean updateUsernameById(int id, String newUsername) {
        if (dbUserDAO.existByID(id)) {
            dbUserDAO.updateUsernameById(id, newUsername);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePasswordById(int id, String password) {
        if (dbUserDAO.existByID(id)) {
            dbUserDAO.updatePasswordById(id, password);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateRoleById(int id, String role) {
        if (dbUserDAO.existByID(id)) {
            dbUserDAO.updateRoleById(id, role);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUserById(int id) {
        if (dbUserDAO.existByID(id)) {
            dbUserDAO.deleteById(id);
            return true;
        } else {
            return false;
        }

    }
}
