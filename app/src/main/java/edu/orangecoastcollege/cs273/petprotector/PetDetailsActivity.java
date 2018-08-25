package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    /**
     * Activity for viewing an individual Pet's details
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);
        TextView petNameDetailsTextView = (TextView) findViewById(R.id.nameDetailsTextView);
        TextView petDetailsTextView = (TextView) findViewById(R.id.detailsTextView);
        TextView petNumberTextView = (TextView) findViewById(R.id.numberDetailsTextView);

        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("Name");
        String details = detailsIntent.getStringExtra("Details");
        String number = detailsIntent.getStringExtra("Number");
        String imageUri = detailsIntent.getStringExtra("Image");

        petDetailsImageView.setImageURI(Uri.parse(imageUri));
        petNameDetailsTextView.setText(name);
        petDetailsTextView.setText(details);
        petNumberTextView.setText(number);
    }
}
