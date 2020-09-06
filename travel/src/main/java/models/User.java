package models;

public class User {
    int id;
    private String username;
    private String phone;
    private String mail;
    private String headPath;

    public User(int id) {
        this.id = id;
    }

    public User(String username, String phone, String mail, String headPath) {
        this.username = username;
        this.phone = phone;
        this.mail = mail;
        this.headPath = headPath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }


}
