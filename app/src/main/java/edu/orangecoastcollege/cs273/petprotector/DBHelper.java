package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/6/2017.
 * DB Helper to create/add/get from the db
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_IMAGE_NAME = "image_name";

    /**
     * Constructs a DBHelper object
     * @param context context in which the database is in
     */
    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database
     * @param sqLiteDatabase a new database to write to
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "CREATE TABLE " + DATABASE_TABLE + " ("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAILS + " TEXT, "
                + FIELD_NUMBER + " TEXT, "
                + FIELD_IMAGE_NAME + " TEXT)";
        sqLiteDatabase.execSQL(table);
    }

    /**
     * Updates the database
     * @param sqLiteDatabase current database
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Add's a Pet object to the database table.
     * @param newPet the new Pet object to be added to the database
     */
    public void addPet(Pet newPet){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, newPet.getName());
        values.put(FIELD_DETAILS, newPet.getDetails());
        values.put(FIELD_NUMBER, newPet.getNumber());
        String imageURI = newPet.getImageUri().toString();
        values.put(FIELD_IMAGE_NAME, imageURI);

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    /**
     * Gets all current Pets from the database
     * @return an ArrayList of Pet objects
     */
    public ArrayList<Pet> getAllPets(){
        ArrayList<Pet> petsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{
                        KEY_FIELD_ID,
                        FIELD_NAME,
                        FIELD_DETAILS,
                        FIELD_NUMBER,
                        FIELD_IMAGE_NAME
                }, null, null, null, null, null
        );
        if (cursor.moveToFirst()){
            do{
                Pet pet = new Pet(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Uri.parse(cursor.getString(4))
                );
                petsList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return petsList;
    }

}
