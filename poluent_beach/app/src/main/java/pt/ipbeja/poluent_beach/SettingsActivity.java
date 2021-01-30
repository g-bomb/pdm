package pt.ipbeja.poluent_beach;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

/**
 * Choose Language of the APP
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class SettingsActivity extends AppCompatActivity {

    private static final int LANGUAGE_CHANGE = 123;

    private ImageButton buttonPT;
    private ImageButton buttonEN;
    private Button buttonBack;
    private Locale myLocale;

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
        buttonPT.setOnClickListener(v -> {
            myLocale = new Locale("pt");
            Resources resources = getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = myLocale;
            resources.updateConfiguration(configuration, metrics);
            setResult(LANGUAGE_CHANGE);
            recreate();

        });

        buttonEN.setOnClickListener(v -> {
            myLocale = new Locale("en");
            Resources resources = getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = myLocale;
            resources.updateConfiguration(configuration, metrics);
            setResult(LANGUAGE_CHANGE);
            recreate();

        });

        //Click listener to finish the activity, and start new MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        });
    }
}