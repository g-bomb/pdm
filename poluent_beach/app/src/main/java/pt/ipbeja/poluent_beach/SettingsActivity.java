package pt.ipbeja.poluent_beach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private static final int LANGUAGE_CHANGE = 123;

    private ImageButton buttonPT;
    private ImageButton buttonEN;
    private Button buttonBack;
    private Locale myLocale;
    private int counter;
    private int counter1;

    /**
     * Oncreate method tha runs when the activity is launched
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonPT = findViewById(R.id.PortugueseButton);
        buttonEN = findViewById(R.id.EnglishButton);
        buttonBack = findViewById(R.id.BackButton);

        //Click listener for both the language buttons, that changes the configuration locale to the corresponding language
        buttonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocale = new Locale("pt");
                Resources resources = getResources();
                DisplayMetrics metrics = resources.getDisplayMetrics();
                Configuration configuration = resources.getConfiguration();
                configuration.locale = myLocale;
                resources.updateConfiguration(configuration, metrics);
                setResult(LANGUAGE_CHANGE);
                recreate();

            }
        });

        buttonEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocale = new Locale("en");
                Resources resources = getResources();
                DisplayMetrics metrics = resources.getDisplayMetrics();
                Configuration configuration = resources.getConfiguration();
                configuration.locale = myLocale;
                resources.updateConfiguration(configuration, metrics);
                setResult(LANGUAGE_CHANGE);
                recreate();

            }
        });

        //Click listener to finish the activity
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    finish();
                startActivity(intent);
                }
        });
    }
}