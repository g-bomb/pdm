package pt.ipbeja.poluent_beach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Homepage
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class MainActivity extends AppCompatActivity {

    private static final int LANGUAGE_CODE = 123;

    private ImageButton buttonSettings;
    private Button reportButton;
    private Button showButton;
    private  Button showDaoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignVariables();

        //Send to Settings Activity
        buttonSettings.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), LANGUAGE_CODE));

    /*    buttonSettings.setOnClickListener(v -> {
            Intent teste = new Intent(MainActivity.this, SettingsActivity.class);
            (MainActivity.this).finish();
            teste.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(teste);
        });*/

        //Send to Report Activity
        reportButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ReportBeach.class)));
        //Send to ShowBeach Activity, which contains the RecycleView from Firebase Data
        showButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowBeach.class)));
        //Send to ShowBeachDao Activity, which contains the RecycleView from the Room
        showDaoButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowBeachRoom.class)));
    }

    /**
     * Assign all required variables to the respective layout elements
     */

    private void assignVariables() {
        buttonSettings = findViewById(R.id.settingsButton);
        reportButton = findViewById(R.id.reportButton);
        showButton = findViewById(R.id.showButton);
        showDaoButton = findViewById(R.id.showDaoButton);
    }
}