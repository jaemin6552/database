package Board;

public class Comments {
    String user_id;
    int write_num;
    String comments_letter;
    int comments_num;

    public Comments(String user_id, int write_num, String comments_letter, int comments_num) {
        this.user_id = user_id;
        this.write_num = write_num;
        this.comments_letter = comments_letter;
        this.comments_num = comments_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getWrite_num() {
        return write_num;
    }

    public void setWrite_num(int write_num) {
        this.write_num = write_num;
    }

    public String getComments_letter() {
        return comments_letter;
    }

    public void setComments_letter(String comments_letter) {
        this.comments_letter = comments_letter;
    }

    public int getComments_num() {
        return comments_num;
    }

    public void setComments_num(int comments_num) {
        this.comments_num = comments_num;
    }
}
