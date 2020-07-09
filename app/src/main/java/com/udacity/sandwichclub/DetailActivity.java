package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;




import java.util.stream.Collectors;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mDescriptionTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownAsTextView;

    private TextView mDescriptionLabelTextView;
    private TextView mPlaceOfOriginLabelTextView;
    private TextView mIngredientsLabelTextView;
    private TextView mAlsoKnownAsLabelTextView;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        mDescriptionTextView = (TextView)findViewById(R.id.description_tv);
        mAlsoKnownAsTextView = (TextView)findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = (TextView)findViewById(R.id.place_of_origin_tv);

        mDescriptionLabelTextView = (TextView)findViewById(R.id.description_label_tv);
        mAlsoKnownAsLabelTextView = (TextView)findViewById(R.id.also_known_label_tv);
        mIngredientsLabelTextView = (TextView)findViewById(R.id.ingredients_label_tv);
        mPlaceOfOriginLabelTextView = (TextView)findViewById(R.id.place_of_origin_label_tv);


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty()){
            mAlsoKnownAsTextView.setVisibility(View.INVISIBLE);
            mAlsoKnownAsLabelTextView.setVisibility(View.INVISIBLE);
        } else {
            String alsoKnownAsStr = sandwich.getAlsoKnownAs().stream().collect(Collectors.joining(","));
            mAlsoKnownAsTextView.setText(alsoKnownAsStr);
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isBlank(sandwich.getDescription())){
            mDescriptionTextView.setVisibility(View.INVISIBLE);
            mDescriptionLabelTextView.setVisibility(View.INVISIBLE);
        } else {
            mDescriptionTextView.setText(sandwich.getDescription());
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionLabelTextView.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isBlank(sandwich.getPlaceOfOrigin())) {
            mPlaceOfOriginTextView.setVisibility(View.INVISIBLE);
            mPlaceOfOriginLabelTextView.setVisibility(View.INVISIBLE);
        } else {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginLabelTextView.setVisibility(View.VISIBLE);
        }
        if (sandwich.getIngredients().isEmpty()){
            mIngredientsTextView.setVisibility(View.INVISIBLE);
            mIngredientsLabelTextView.setVisibility(View.INVISIBLE);

        } else {
            String ingredientsStr = sandwich.getIngredients().stream().collect(Collectors.joining(","));
            mIngredientsTextView.setText(ingredientsStr);
            mIngredientsTextView.setVisibility(View.VISIBLE);
            mIngredientsLabelTextView.setVisibility(View.VISIBLE);

        }

    }
}
