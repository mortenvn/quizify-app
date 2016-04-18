package net.quizifyapp.quizifyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button registerButton;
    EditText usernameField;
    EditText emailField;
    EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.register_button);
        usernameField = (EditText) findViewById(R.id.username);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

    }

    public void setLogin(View v) {
        String usernameText = usernameField.getText().toString();
        String emailText = emailField.getText().toString();
        String passwordText = passwordField.getText().toString();

        NetworkManager.getInstance().register(emailText, usernameText, passwordText, new APIListener<String>() {
            @Override
            public void getResult(String result) {
                if (!result.isEmpty()) {
                    Log.d("Fuck yeah TOKEN", result);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    // TODO: Handle errors
                }
            }
        });

    }
}
