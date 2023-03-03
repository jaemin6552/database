package Board;

import java.util.HashMap;

public class Board {
    String cospi_id;
    String user_id;
    int write_num;
    String title;
    String content;

    public Board(String cospi_id, String user_id, int write_num, String title, String content) {
        this.cospi_id = cospi_id;
        this.user_id = user_id;
        this.write_num = write_num;
        this.title = title;
        this.content = content;
    }

    public String getCospi_id() {
        return cospi_id;
    }

    public void setCospi_id(String cospi_id) {
        this.cospi_id = cospi_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
