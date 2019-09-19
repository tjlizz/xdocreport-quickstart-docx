package model;

import fr.opensagres.xdocreport.document.images.IImageProvider;

public class Developer {
    private final String name;
    private final String lastName;
    private final String mail;
    private final IImageProvider logo;

    public Developer(String name, String lastName, String mail, IImageProvider logo) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public IImageProvider getLogo() {
        return logo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }
}
