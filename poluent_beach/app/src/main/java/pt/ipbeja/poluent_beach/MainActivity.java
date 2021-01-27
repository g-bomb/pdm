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
    private  Button showDaoButton;
    private ShowBeach showBeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSettings = findViewById(R.id.settingsButton);
        reportButton = findViewById(R.id.reportButton);
        showButton = findViewById(R.id.showButton);
        showDaoButton = findViewById(R.id.showDaoButton);


        //Click listeneres for all the buttons, the settings button starts activity for result to inform if there was a language change.
        buttonSettings.setOnClickListener(v ->
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), LANGUAGE_CODE));


//        buttonSettings.setOnClickListener(v -> {
//            Intent ddd = new Intent(MainActivity.this, SettingsActivity.class);
//            (MainActivity.this).finish();
//            ddd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(ddd);
//        });

        reportButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ReportBeach.class)));

        showButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowBeach.class)));

        showDaoButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShowBeachDao.class)));
    }
}