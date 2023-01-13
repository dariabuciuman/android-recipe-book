package com.example.recipebook.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
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

    public void getRecipeWithQuery(String URL, List<String> params) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("x-api-key", "b80a18baa3e6421cb788bf679ed660b9").url(URL).build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Log.i(TAG, "getRecipeWithQuery: " + responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
