package pt.ipbeja.poluent_beach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final int LANGUAGE_CODE = 123;

    private ImageButton buttonSettings;
    private Button reportButton;
    private Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSettings = findViewById(R.id.settingsButton);
        reportButton = findViewById(R.id.reportButton);
        showButton = findViewById(R.id.showButton);

        //Click listeneres for all the buttons, the settings button starts activity for result to inform if there was a language change.
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), LANGUAGE_CODE);

            }
        });


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportBeach.class));
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowBeach.class));
            }
        });
    }

    /**
     * Method responsible for checking if there was a language change when coming from the settings activity
     *
     * @param requestCode code used to identify the operation that took place
     * @param resultCode  indicator of weather the operation was successful or not
     * @param data        intent received from the previous activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LANGUAGE_CODE && resultCode == RESULT_OK) {
            //Intent refresh = new Intent(this, MainActivity.class);
            //finish();
            //startActivity(getIntent());
            recreate();
        }

    }
}