package pt.ipbeja.poluent_beach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Show resume from clicked item
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class BeachHistory extends AppCompatActivity {

    private TextView name;
    private TextView descriptiontitle;
    private TextView description;
    private TextView gpsTitle;
    private TextView gpsText;
    private TextView data;
    private ImageView image;
    private ImageButton mapButton;

    private Button buttonBack;
    private Button buttonDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_history);

        //Call variables
        assignVariables();

        //Retrieve data and Display it
        Intent i = getIntent();
        String nameString = i.getStringExtra("name");
        name.setText(nameString);
        String descriptionString = i.getStringExtra("description");
        description.setText(descriptionString);
        String gpsString = i.getStringExtra("gps");
        gpsText.setText(gpsString);
        String linkString = i.getStringExtra("photo");
        Glide.with(getApplicationContext()).load(linkString).into(image);
        //String dataString = i.getStringExtra("data");
        //data.setText(dataString);


        //Button to see the location on the map, the GPS string is passed on the intent.
        mapButton.setOnClickListener(v -> startActivity(new Intent(BeachHistory.this, MapsActivityHistory.class).putExtra("gps", gpsText.getText().toString())));

        //Click listener to finish the activity
        buttonBack.setOnClickListener(v -> finish());

        //Click listener to clear Report this will remove the current report from the database
        buttonDelete.setOnClickListener(v -> {
            String idString = getIntent().getStringExtra("id");
            FirebaseFirestore.getInstance().collection("reports").document(idString).delete();
            finish();
        });
    }


    /**
     * Assign all required variables to the respective layout elements
     */
    private void assignVariables() {
        name = findViewById(R.id.beach_name_history);
        descriptiontitle = findViewById(R.id.description_history);
        description = findViewById(R.id.description_text_history);
        gpsTitle = findViewById(R.id.gps_history);
        gpsText = findViewById(R.id.gps_text_history);
        image = findViewById(R.id.photo_report_history);
        mapButton = findViewById(R.id.show_map_history);
        buttonBack = findViewById(R.id.button_cancel_history);
        buttonDelete = findViewById(R.id.button_delete);
    }
}