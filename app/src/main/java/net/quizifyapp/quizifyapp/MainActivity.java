package net.quizifyapp.quizifyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        setContentView(R.layout.activity_main);


        // Example request
        NetworkManager.getInstance().register("morten@qandroid.net", "qan", "123qwe123", new APIListener<String>() {
            @Override
            public void getResult(String result) {
                if (!result.isEmpty()) {
                    Log.d("FUCYEAH", result);
                    // TODO: Handle errors
                    //do what you need with the result...
                }
            }
        });
    }
}
