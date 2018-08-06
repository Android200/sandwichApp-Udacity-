package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json){

        Sandwich sSandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");

            sSandwich.setMainName(mainName);
            sSandwich.setAlsoKnownAs(jsonArrayToList(alsoKnownAs));
            sSandwich.setPlaceOfOrigin(placeOfOrigin);
            sSandwich.setDescription(description);
            sSandwich.setImage(image);
            sSandwich.setIngredients(jsonArrayToList(ingredients));

        } catch (JSONException e) {
            Log.d(TAG, "error in parsing json \n"+e);
            return null;
        }

        return sSandwich;
    }


    /*****
     *
     * @param jsonArray JSONArray to be converted to ArrayList of strings
     * @return list
     */
    private static ArrayList<String> jsonArrayToList(JSONArray jsonArray){
        ArrayList<String> list = new ArrayList<>();
        int length = jsonArray.length();
        if(length <= 0){
            return list;
        }
        for(int i =0; i<jsonArray.length(); i++){
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                Log.d(TAG, "error in getting jsonArray values \n"+e);
            }
        }

        return list;
    }
}


/***************************************
 ************JSON STRUCTURE*************
 *                                     *
 *                                     *
 * name: Json Object                   *
 * ---- mainName: String               *
 * ---- alsoKnownAs : array of Strings *
 *                                     *
 *                                     *
 * placeOfOrigin: String               *
 *                                     *
 *                                     *
 * description: String                 *
 *                                     *
 *                                     *
 * image: String                       *
 *                                     *
 *                                     *
 * ingredients: array of Strings       *
 *                                     *
 *                                     *
 ***************************************
 * ************************************/
