package model;

import fr.opensagres.xdocreport.document.images.IImageProvider;

public class User {

    private final String userName;

    private final IImageProvider avatar;

    public User(String userName, IImageProvider avatar) {
        this.userName = userName;
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public IImageProvider getAvatar() {
        return avatar;
    }
}
