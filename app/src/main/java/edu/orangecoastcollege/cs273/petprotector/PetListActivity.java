package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView petImageView;
    private ListView petListView;
    private EditText petNameEditText;
    private EditText petDetailsEditText;
    private EditText petNumberEditText;

    private DBHelper db;
    private ArrayList<Pet> mPetsList = new ArrayList<>();
    private PetListAdapter petListAdapter;

    // Constants for permissions
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;

    /**
     * Initiates the main activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        this.deleteDatabase(DBHelper.DATABASE_NAME);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        imageUri = getUriFromResource(this, R.drawable.none);
        petImageView.setImageURI(imageUri);
        petListView = (ListView) findViewById(R.id.petListView);
        petNameEditText = (EditText) findViewById(R.id.nameEditText);
        petDetailsEditText = (EditText) findViewById(R.id.detailsEditText);
        petNumberEditText = (EditText) findViewById(R.id.numberEditText);

        db = new DBHelper(this);
        mPetsList = db.getAllPets();

        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, mPetsList);
        petListView.setAdapter(petListAdapter);
    }

    /**
     * Gets a Uri from a resource id
     * @param context current context
     * @param resId the resource id
     * @return a Uri of the resource
     */
    public static Uri getUriFromResource(Context context, int resId){
        Resources res = context.getResources();
        // Build a string in the URI form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resId) + "/"
                + res.getResourceTypeName(resId) + "/"
                + res.getResourceEntryName(resId);

        // Parse the uri to construct a URI
        return Uri.parse(uri);
    }

    /**
     * Adds a pet to the database and list
     * @param v button that called onClick
     */
    public void addPetButtonClick(View v) {
        if (TextUtils.isEmpty(petNameEditText.getText().toString()) ||
                TextUtils.isEmpty(petDetailsEditText.getText().toString()) ||
                TextUtils.isEmpty(petNumberEditText.getText().toString())) {

            Toast.makeText(this, "Please enter any missing fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            Pet petToAdd = new Pet(
                    petNameEditText.getText().toString(),
                    petDetailsEditText.getText().toString(),
                    petNumberEditText.getText().toString(),
                    imageUri
            );
            db.addPet(petToAdd);
            mPetsList.add(petToAdd);
            petListAdapter.notifyDataSetChanged();

            petNameEditText.setText("");
            petDetailsEditText.setText("");
            petNumberEditText.setText("");
            petImageView.setImageResource(R.drawable.none);
        }
    }

    /**
     * Launches the details activity based on what list item was clicked
     * @param v the list item
     */
    public void viewPetDetails(View v){
        Intent toDetailsIntent = new Intent(this, PetDetailsActivity.class);

        Pet selectedPet = (Pet) v.getTag();
        toDetailsIntent.putExtra("Name", selectedPet.getName());
        toDetailsIntent.putExtra("Details", selectedPet.getDetails());
        toDetailsIntent.putExtra("Number", selectedPet.getNumber());
        toDetailsIntent.putExtra("Image", selectedPet.getImageUri().toString());

        startActivity(toDetailsIntent);
    }

    /**
     * Selects a pet image and prompts for permissions to do so from user's gallery
     * @param v image view that called this method
     */
    public void selectPetImage(View v) {
        List<String> permsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int hasReadStoragePerm =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePerm == DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int hasWriteStoragePerm =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some permissions have not been granted
        if (permsList.size() > 0){
            // convert permsList into array
            String[] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);

            // Ask user for them:
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Make sure we have all perms, then start up the image gallery
        if (hasCameraPerm == GRANTED
                && hasReadStoragePerm == GRANTED
                && hasWriteStoragePerm == GRANTED){
            // Open image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the activity *for a result*, a pic
            startActivityForResult(galleryIntent, 1);
        }
    }

    /**
     * Gets the image Uri from whatever the user selected in the gallery or took with camera
     * @param requestCode
     * @param resultCode
     * @param data the image data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            // data = data from gallery intent, URI of some image
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }
}
