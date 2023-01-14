package com.example.recipebook.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.recipebook.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {
    private OkHttpClient client;
    private CountDownLatch latch;
    private AtomicReference<Response> responseRef;

    String baseURL = "https://api.spoonacular.com/";
    private static final String TAG = "HttpRequest";

    public String getRecipe(Context context, String query) throws InterruptedException, IOException {
        client = new OkHttpClient();
        latch = new CountDownLatch(1);
        responseRef = new AtomicReference<>();

        String requestURL = baseURL + "recipes/complexSearch?query=" + query;
        Request request = new Request.Builder().url(requestURL).addHeader("x-api-key", "b80a18baa3e6421cb788bf679ed660b9").build();
        Log.i(TAG, "getRecipe: request " + request.header("x-api-key"));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Handle failure
                        Log.e(TAG, "onFailure: Something went wrong");
                        latch.countDown();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseRef.set(response);
                        latch.countDown();
                    }
                });
            }
        });
        thread.start();
        latch.await();
        String response = Objects.requireNonNull(responseRef.get().body()).string();
        Log.i(TAG, "getRecipe: response: " + response);
        return response;
    }

    public Recipe getRecipeInformation(Context context, Long id) throws InterruptedException, IOException, JSONException {
        client = new OkHttpClient();
        latch = new CountDownLatch(1);
        responseRef = new AtomicReference<>();

        String requestURL = baseURL + "recipes/" + id.toString() + "/information?includeNutrition=false";
        Request request = new Request.Builder().url(requestURL).addHeader("x-api-key", "b80a18baa3e6421cb788bf679ed660b9").build();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Handle failure
                        Log.e(TAG, "onFailure: Something went wrong");
                        latch.countDown();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseRef.set(response);
                        latch.countDown();
                    }
                });
            }
        });
        thread.start();
        latch.await();
        String response = Objects.requireNonNull(responseRef.get().body()).string();
        JSONObject jsonObject = new JSONObject(response);

        String title = jsonObject.getString("title");
        String image = jsonObject.getString("image");
        String readyInMinutes = jsonObject.getString("readyInMinutes");
        String sourceURL = jsonObject.getString("sourceUrl");
        List<String> ingredients = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("extendedIngredients");
        for (int i = 0; i < jsonArray.length(); i++) {
            ingredients.add(jsonArray.getJSONObject(i).getString("original"));
        }
        List<String> instructions = new ArrayList<>();
        jsonArray = jsonObject.getJSONArray("analyzedInstructions");
        JSONArray stepsArray;
        for (int i = 0; i < jsonArray.length(); i++) {
            stepsArray = jsonArray.getJSONObject(i).getJSONArray("steps");
            for (int j = 0; j < stepsArray.length(); j++) {
                instructions.add(j+1 + ". " + stepsArray.getJSONObject(j).getString("step"));
            }
        }
        Recipe recipe = new Recipe(id, title, image, readyInMinutes, ingredients, instructions, sourceURL);
        Log.i(TAG, "getRecipe: response: " + response);
        return recipe;
    }


    public String getRecipesByURL(String URL) throws InterruptedException, IOException {
        client = new OkHttpClient();
        latch = new CountDownLatch(1);
        responseRef = new AtomicReference<>();

        Request request = new Request.Builder().url(URL).addHeader("x-api-key", "b80a18baa3e6421cb788bf679ed660b9").build();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Handle failure
                        Log.e(TAG, "onFailure: Something went wrong");
                        latch.countDown();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseRef.set(response);
                        latch.countDown();
                    }
                });
            }
        });
        thread.start();
        latch.await();
        String response = Objects.requireNonNull(responseRef.get().body()).string();
        Log.i(TAG, "getRecipe: response: " + response);
        return response;
    }
}
