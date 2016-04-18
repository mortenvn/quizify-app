package net.quizifyapp.quizifyapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;

    private static final String prefixURL = "http://kane.royrvik.org:8000";
    private static final String clientID = "VoquKbj42SBvq9bpfcSsFIbnUOcsihQ2roHL0js9";
    private static final String clientSecret = "P8WnEHBUpFRDR09jFnCzZ2wHQvDF8B5JyFHagTM27gjFHaGIhinT3sPCC0DpfGgvlZlge1nNWUUSh0rX16MimCbil1yAkrSGe2RB2mc7jEKnQe2QrW7UcoUMVbqtAIvy";
    private static final String grantType = "password";

    private static String authKey = null;

    //for Volley API
    public RequestQueue requestQueue;

    private NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
//        context.getResources().getXml(R.xml.api);
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    // This is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void register(String email, String username, String password, final APIListener<String> listener) {

        String url = prefixURL + "/register/";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("username", username);
        jsonParams.put("password", password);
        jsonParams.put("client_id", clientID);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + ": ", "somePostRequest Response : " + response.toString());
                        if(null != response.toString()) {
                            listener.getResult(response.toString());
                            // TODO: Save authkey
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                });

        requestQueue.add(request);
    }

    public void login(String username, String password, final APIListener<String> listener) {
        String url = prefixURL + "/auth/token/";

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("username", username);
        jsonParams.put("password", password);
        jsonParams.put("client_id", clientID);
        jsonParams.put("client_secret", clientSecret);
        jsonParams.put("grant_type", grantType);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + ": ", "somePostRequest Response : " + response.toString());
                        if(null != response.toString()) {
                            listener.getResult(response.toString());
                            // TODO: Save authkey
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                });

        requestQueue.add(request);
    }

    public void getGames(final APIListener<String> listener) {
        String url = prefixURL + "/games/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            listener.getResult(null);
                        }
                    }
                });

        requestQueue.add(request);
    }

    public void newGame() {

    }
}
