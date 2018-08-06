package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mMainName_tv, mOrigin_tv, mAlsoKnown_tv, mDescription_tv, mIngredients_tv;

    Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mMainName_tv = findViewById(R.id.main_name_tv);
        mAlsoKnown_tv = findViewById(R.id.also_known_tv);
        mDescription_tv = findViewById(R.id.description_tv);
        mIngredients_tv = findViewById(R.id.ingredients_tv);
        mOrigin_tv = findViewById(R.id.origin_tv);

        mMainName_tv.setText(mSandwich.getMainName());
        mAlsoKnown_tv.setText(mSandwich.getAlsoKnownAs().toString().replaceAll("[\\[\\]]", "").concat("\n"));
        mDescription_tv.setText(mSandwich.getDescription().concat("\n"));
        mIngredients_tv.setText(mSandwich.getIngredients().toString().replaceAll("[\\[\\]]", "").concat("\n"));
        mOrigin_tv.setText(mSandwich.getPlaceOfOrigin());
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}
