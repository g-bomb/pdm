package pt.ipbeja.poluent_beach.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
@Entity
public class Report {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String description;
    private String fileLink;
    private String gps;
    private String currentData;

    public Report (){
    }

    public Report(String name, String description, String gps, String fileLink, String currentData ){
        this.name = name;
        this.description = description;
        this.gps = gps;
        this.fileLink = fileLink;
        this.currentData = currentData;
    }
    public Report(long id, String name, String description, String gps, String fileLink, String currentData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gps = gps;
        this.fileLink = fileLink;
        this.currentData = currentData;
    }

    public long getId() {
        return id;
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

    public String getCurrentData() { return currentData; }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
}

