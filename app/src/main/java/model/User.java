package model;

public class User {
    public String userId;
    public String username;
    public String email;
    public String role;
    public String createdAt;
    public Profile profile;

    public User() {}

    public User(String userId, String username, String email, String role, String createdAt, Profile profile) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.profile = profile;
    }

    public static class Profile {
        public String bio;
        public String profilePic;

        public Profile() {}

        public Profile(String bio, String profilePic) {
            this.bio = bio;
            this.profilePic = profilePic;
        }
    }
}
