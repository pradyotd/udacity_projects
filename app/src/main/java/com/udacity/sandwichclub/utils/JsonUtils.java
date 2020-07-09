package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichObj = new JSONObject(json);
            JSONObject name  = sandwichObj.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray arr = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnown = new ArrayList<String>();
            for (int i=0;i<arr.length();i++){
                alsoKnown.add(arr.getString(i));
            }
            JSONArray ingredientsArr = sandwichObj.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for (int i =0;i<ingredientsArr.length();i++){
                ingredients.add(ingredientsArr.getString(i));
            }
            sandwich = new Sandwich(mainName, alsoKnown, sandwichObj.getString("placeOfOrigin"),
                                    sandwichObj.getString("description"), sandwichObj.getString("image"), ingredients);

        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return sandwich;
    }
}
