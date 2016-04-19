package net.quizifyapp.quizifyapp;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
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
//        context.getResources().getXml(R.xml.api); // TODO: Add settings file
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

    public void register(String email, String username, String password, final APIAuthenticationResponseListener<String> listener) {

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
                        Log.d(TAG + ": ", "Register Response : " + response.toString());
                        try {
                            authKey = response.getString("token");
                            listener.getResult(null);
                        } catch (JSONException e) {
                            listener.getResult("Token not returned");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG + ": ", "Register error response code: " + error.networkResponse.statusCode);
                        listener.getResult(error.getMessage());
                    }
                });

        requestQueue.add(request);
    }

    public void login(String username, String password, final APIAuthenticationResponseListener<String> listener) {
        String url = prefixURL + "/auth/token/";
//        String url = prefixURL + "/debug/";

        Map<String, Object> jsonParams = new HashMap<>();
        Log.d("ANDREAS", username);
        Log.d("ANDREAS", password);
        Log.d("ANDREAS", clientID);
        Log.d("ANDREAS", clientSecret);
        Log.d("ANDREAS", grantType);
        jsonParams.put("username", username);
        jsonParams.put("password", password);
        jsonParams.put("client_id", clientID);
        jsonParams.put("client_secret", clientSecret);
        jsonParams.put("grant_type", grantType);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + ": ", "Login Response : " + response.toString());
                        try {
                            Log.d("ANDREAS", authKey);
                            authKey = response.getString("token");
                            Log.d("ANDREAS", authKey);
                            Log.d("ANDREAS", "==================================");
                            listener.getResult(null);
                        } catch (JSONException e) {
                            listener.getResult("Token not returned");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String lol = new String(error.networkResponse.data);

                        Log.d(TAG + ": ", "Login error response code: " + error.networkResponse.statusCode);
                        Log.d(TAG + ": ", "Login error response code: " + error.networkResponse);
                        Log.d(TAG + ": ", "Login error response code: " + lol);
                        Log.d(TAG + ": ", "Login error response msg: " + error.getMessage());
                        Log.d(TAG + ": ", "Login error response msg: " + error.getLocalizedMessage());
                        Log.d(TAG + ": ", "Login error response msg: " + error.toString());
                        listener.getResult(error.getMessage());
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            // this is the relevant method
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", "mortnod");
                params.put("password", "Andreas12");
                params.put("client_id", clientID);
                params.put("client_secret", clientSecret);
                params.put("grant_type", grantType);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("ANDREAS", "FUCK");
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void getGames(final APIObjectResponseListener<String> listener) {
        String url = prefixURL + "/games/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ANDREAS", response.toString());
                        listener.getResult(null, null); // TODO: To hashmap
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG + ": ", "Error Response code: " + error.getMessage());
                        listener.getResult(error.getMessage(), null);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =  new HashMap<>();
//                if(params==null)params = new HashMap<>();
                params.put("Authorization", "Bearer " + authKey);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void rejectInvite() {

    }

    public void acceptInvite() {

    }

    public void newGame() {

    }

    public void saveRound() {

    }

    public void getRandCateogries() {

    }
}
