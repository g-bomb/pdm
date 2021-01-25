package pt.ipbeja.poluent_beach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import pt.ipbeja.poluent_beach.data.Report;

public class BeachHistory extends AppCompatActivity {

    private TextView name;
    private TextView descriptiontitle;
    private TextView description;
    private TextView gpsTitle;
    private TextView gpsText;
    private ImageView image;
    private ImageButton mapButton;

    private Button buttonBack;
    private Button buttonDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_history);

        assignVariables();
        Intent i = getIntent();
        String nameString = i.getStringExtra("name");
        name.setText(nameString);
        String descriptionString = i.getStringExtra("description");
        description.setText(descriptionString);
        String gpsString = i.getStringExtra("gps");
        gpsText.setText(gpsString);
        String linkString = i.getStringExtra("photo");
        Glide.with(getApplicationContext()).load(linkString).into(image);



        //Button to see the location on the map, the address string is passed on the intent.
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeachHistory.this, MainActivity.class));
            }
        });

        //Click listener to finish the activity
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Click listener to "claim" the pet, this will remove the current report from the database
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ddd = getIntent().getStringExtra("id");
                FirebaseFirestore.getInstance().collection("reports").document(ddd).delete();
                finish();
            }
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