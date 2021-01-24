package pt.ipbeja.poluent_beach.data;

import com.google.firebase.firestore.Exclude;

public class Report {
    private String name;
    private String description;
    private String fileLink;
    private String gps;
    public Report (){

    }

    public Report(String name, String description, String gps, String fileLink ){
        this.name = name;
        this.description = description;
        this.gps = gps;
        this.fileLink = fileLink;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getGps() {
        return gps;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}

