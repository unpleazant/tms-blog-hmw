package tms.service;

import tms.entiy.Like;
import tms.entiy.Post;
import tms.dao.DbLikeDAO;
import tms.dao.DbPostDAO;

import java.util.List;

public class PostService {

    private final DbPostDAO dbPostDAO = new DbPostDAO();
    private final DbLikeDAO likeDAO = new DbLikeDAO();

    public boolean create(Post post) {
        return dbPostDAO.save(post);
    }

    public boolean approve(int postId) {
        if (dbPostDAO.existById(postId)) {
            dbPostDAO.approve(postId);
            return true;
        } else {
            return false;
        }
    }

    public List<Post> getApprovedPost(Boolean isApproved) {
        List<Post> postList = dbPostDAO.getApproved(isApproved).orElse(null);
        postList.forEach(post -> post.setLikes(likeDAO.getLikesByPostId(post.getId()).orElse(null)));
        return postList;
    }

    public boolean update(int postId, String title, String text) {
        if (dbPostDAO.existById(postId)) {
            dbPostDAO.update(postId, title, text);
            return true;
        } else {
            return false;
        }
    }

    public boolean like(Like like) {
        if (likeDAO.save(like)) return true;
        else return false;
    }

    public Post getPostById(int postId) {
        Post post = dbPostDAO.getById(postId).get();
        post.setLikes(likeDAO.getLikesByPostId(postId).orElse(null));
        return post;
    }

    public boolean delete(int postId) {
        if (dbPostDAO.existById(postId)) {
            dbPostDAO.deleteById(postId);
            return true;
        } else {
            return false;
        }
    }

}