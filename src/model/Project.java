package model;

public class Project {
    private final String name;

     private  final  String logo;
    public Project(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }
}
