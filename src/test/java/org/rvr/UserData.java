package org.rvr;

public class UserData {
    public String last_name;
    public Long id;
    public String avatar;
    public String first_name;
    public String email;

    public UserData(String last_name, Long id, String avatar, String first_name, String email) {
        this.last_name = last_name;
        this.id = id;
        this.avatar = avatar;
        this.first_name = first_name;
        this.email = email;
    }

    public UserData() {
    }
}
