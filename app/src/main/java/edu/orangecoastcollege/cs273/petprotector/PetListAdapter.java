package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/6/2017.
 */

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetList = new ArrayList<>();
    private int mResId;

    private ImageView mPetImageView;
    private TextView mNameTextView;
    private TextView mDetailsTextView;
    private LinearLayout mLinearLayout;

    /**
     * Constructs a PetListAdapter
     * @param context current context
     * @param resource layout to inflate for the list
     * @param objects the list of Pet objects to fill the list
     */
    public PetListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResId = resource;
        mPetList = objects;
    }

    /**
     * Inflates a list item with a custom layout
     * @param position index of the list item
     * @param convertView
     * @param parent
     * @return the new list item with a custom layout
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResId, null);

        final Pet selectedPet = mPetList.get(position);
        mNameTextView = (TextView) view.findViewById(R.id.itemNameTextView);
        mDetailsTextView = (TextView) view.findViewById(R.id.itemDetailsTextView);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.listItemLinearLayout);
        mPetImageView = (ImageView) view.findViewById(R.id.listItemImageView);

        mNameTextView.setText(selectedPet.getName());
        mDetailsTextView.setText(selectedPet.getDetails());
        Uri imageURI = selectedPet.getImageUri();
        mPetImageView.setImageURI(imageURI);
        mLinearLayout.setTag(selectedPet);

        return view;
    }
}
