package tms.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {

    private int id;
    private String text; //(1 - 70)
    private int userId;
    private int postId;
    private boolean isApproved;

}