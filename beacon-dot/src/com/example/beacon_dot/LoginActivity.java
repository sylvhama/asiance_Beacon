package com.example.beacon_dot;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
 
	private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                
        final EditText usernameField = (EditText) findViewById(R.id.inputName);
        

        Button startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                username = usernameField.getText().toString();
                
                if(username.isEmpty()) {
                	//call toast message method to handle empty user name
                   toastMassage();
                } else {
                	// create intent to connect next activity
                	Intent myIntent = new Intent(view.getContext(), StampActivity.class);
                    myIntent.putExtra("username", username);
                    startActivityForResult(myIntent, 0);
                }
            }
        });
    }
    
    //This method generates toast message when user click welcome button with empty name field
    public void toastMassage () {
    	Toast msg = Toast.makeText(this, "Please enter your name again", Toast.LENGTH_LONG);
    	msg.show();
    }
} 
