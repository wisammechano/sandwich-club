package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        int position = -1;
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView akaTv = findViewById(R.id.also_known_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descTv = findViewById(R.id.description_tv);

        if (sandwich.getAlsoKnownAs().size() == 0) {
            akaTv.setText("No other names!");
        } else {
            StringBuilder akaSb = new StringBuilder();
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                akaSb.append(sandwich.getAlsoKnownAs().get(i));
                if (i + 1 != sandwich.getAlsoKnownAs().size()) akaSb.append(", ");
            }
            akaTv.setText(akaSb.toString());
        }

        if (sandwich.getIngredients().size() == 0) {
            ingredientsTv.setText("Unknown yet!");
        } else {
            StringBuilder IngredientSb = new StringBuilder();
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                IngredientSb.append("• ");
                IngredientSb.append(sandwich.getIngredients().get(i));
                if (i + 1 != sandwich.getIngredients().size()) IngredientSb.append("\n");
            }
            ingredientsTv.setText(IngredientSb.toString());
        }

        originTv.setText(sandwich.getPlaceOfOrigin().equals("") ? "Unknown" : sandwich.getPlaceOfOrigin());
        descTv.setText(sandwich.getDescription());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
