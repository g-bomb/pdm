package pt.ipbeja.poluent_beach.data;

import com.google.firebase.firestore.Exclude;

public class Report {
    private String name;
    private String description;
    private String firestoreId;
    private String fileLink;
    public Report (){

    }

    public Report(String name, String description, String fileLink ){
        this.name = name;
        this.description = description;
        this.fileLink = fileLink;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}

