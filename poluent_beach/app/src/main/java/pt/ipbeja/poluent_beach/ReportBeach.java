package pt.ipbeja.poluent_beach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import pt.ipbeja.poluent_beach.data.Report;

public class ReportBeach extends AppCompatActivity {

    private static final int CAMERA_PERMISSION = 51;
    private static final int GALLERY_PERMISSION = 52;

    private static final int MAP_REQUEST_CODE = 61;
    private static final int CAMERA_REQUEST_CODE = 62;
    private static final int GALLERY_REQUEST_CODE = 63;

    private TextView title_report_beach;
    private TextView textLocal;
    private TextView textPhoto;
    private TextView textName;
    private TextView textDescription;
    private EditText editTextName;
    private EditText editTextLocal;
    private Button buttonCamera;
    private Button buttonGallery;
    private Button buttonCheck;
    private Button buttonCancel;
    private ImageView selectedPhoto;
    private ImageButton mapButton;
    private TextView addressText;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_beach);

        Variables();

        //confirm button is set to disable to force users to complete the required fields before continuing
        buttonCheck.setEnabled(true);

        //Click listener for the confirm button that compresses the image selected and converts it to a byte array,
        //and a new report is created with the information currently selected to add to the database.
        //An async task is used to stored data in the database
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextName.getText().toString();
                String description = editTextLocal.getText().toString();

                // Criar um objecto da class Report
                Report report = new Report(name, description);

                FirebaseFirestore.getInstance()
                        .collection("reports")
                        .add(report)
                        .addOnSuccessListener(ReportBeach.this, documentReference -> finish());

                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(photo, 1000, 1000, false);
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();


                report.setImage(byteArray);
                report.setLocation(showAddress.getText().toString());

                new ReportActivity.AsyncTask().execute(report);*/

                finish();
            }
        });



        //Click listener to access the gallery of the phone. Permissions are requested before the access is allowed.
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ReportBeach.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportBeach.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION);
                    return;
                }

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
                startActivityForResult(i, GALLERY_REQUEST_CODE);
            }
        });

        //Click listener to access the camera of the phone. Permissions are requested before the access is allowed.
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ReportBeach.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportBeach.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    return;
                }

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        //Click listener for the location button that opens the map activity when clicked
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ReportBeach.this, MapsActivity.class), MAP_REQUEST_CODE);
            }
        });

        //Click listener to finish the activity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Method that assigns all the variables to their respective layout elements
     */
    private void Variables() {

        textName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        editTextName = findViewById(R.id.editTextName);
        editTextLocal = findViewById(R.id.editTextLocal);

        buttonCamera = findViewById(R.id.buttonCamera);

        buttonGallery = findViewById(R.id.buttonGallery);

        selectedPhoto = findViewById(R.id.selectedPhoto);

        selectedPhoto.setVisibility(View.INVISIBLE);
        mapButton = findViewById(R.id.settingsButton);
        addressText = findViewById(R.id.addressText);

        buttonCheck = findViewById(R.id.buttonCheck);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    /**
     * Method responsible for checking if there was a change made by the camera, gallery or maps activity.
     * In case of the map activity the address received and displayed to the user.
     * In case of the camera activity the photo taken is placed on the image view and presented to the user.
     * In case of the gallery activity the image selected is decoded into a Bitmap and is the presented on the image view.
     * Finally if both the map address and the image have been selected the confirm button is set to enabled.
     *
     * @param requestCode code used to identify the operation that took place
     * @param resultCode  indicator of weather the operation was successful or not
     * @param data        intent received from the previous activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            selectedPhoto.setImageBitmap(photo);
            selectedPhoto.setVisibility(View.VISIBLE);
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = Objects.requireNonNull(data.getData());
            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePath, null, null, null);
            cursor.moveToFirst();

            int colIndex = cursor.getColumnIndex(filePath[0]);
            String finalPath = cursor.getString(colIndex);
            cursor.close();

            photo = BitmapFactory.decodeFile(finalPath);
            selectedPhoto.setImageBitmap(photo);
            selectedPhoto.setVisibility(View.VISIBLE);
        } else if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            addressText.setText(data.getStringExtra("address"));
            addressText.setVisibility(View.VISIBLE);
        }
    }
}