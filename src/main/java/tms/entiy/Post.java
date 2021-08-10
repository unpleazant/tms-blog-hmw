package tms.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Post {

    private int id;
    private String title; // (6 - 170)
    private String text; // (6 - 240)
    private int userId;
    private List<Comment> comments;
    private List<Like> likes;
    private boolean isApproved;

    public Post(int id, String title, String text, int userId, boolean isApproved) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.isApproved = isApproved;
    }

    @Override
    public String toString() {
        return "{" +"\n"+
                "   id: " + id + ",\n"+
                "   title: '" + title + "\',\n"+
                "   text: " + text + ",\n" +
                "   userId: " + userId + ",\n"+
//                ", comments=" + comments.toString() +
                "   likes: " + likes.size() + "\n"+
                '}';
    }
}
