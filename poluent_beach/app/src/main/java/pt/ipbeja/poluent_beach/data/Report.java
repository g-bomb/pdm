package pt.ipbeja.poluent_beach.data;

import com.google.firebase.firestore.Exclude;

public class Report {
    private String name;
    private String description;
    private String firestoreId;

    public Report (){

    }

    public Report(String name, String description ){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

