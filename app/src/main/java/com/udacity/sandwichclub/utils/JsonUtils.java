package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainObj = new JSONObject(json);
            String mainName = mainObj.getJSONObject("name").getString("mainName");
            JSONArray akas = mainObj.getJSONObject("name").getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < akas.length(); i++) {
                alsoKnownAs.add(akas.getString(i));
            }
            String placeOfOrigin = mainObj.getString("placeOfOrigin");
            String description = mainObj.getString("description");
            String image = mainObj.getString("image");
            List<String> ingredients = new ArrayList<>();

            for (int i = 0; i < mainObj.getJSONArray("ingredients").length(); i++) {
                ingredients.add(mainObj.getJSONArray("ingredients").getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
