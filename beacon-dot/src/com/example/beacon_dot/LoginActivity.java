package com.example.beacon_dot;




import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
 
	public final static String EXTRA_NAME = "com.example.beacon_dot.NAME";
	public final static String EXTRA_ID = "com.example.beacon_dot.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                
     // activity_login.xml - EditText - id : imputName
        final EditText usernameField = (EditText) findViewById(R.id.inputName);
        
        Button startButton = (Button) findViewById(R.id.button1);
        
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                
                if(username.isEmpty()) {
                	//create toast message for empty username
                   toastMassage();
                } else {
                	//send user input name to stamp activity
                	addUser(view, username);
                }
            }			
        });
    }
    
    public void toastMassage () {
    	Toast msg = Toast.makeText(this, "Please enter your informations", Toast.LENGTH_LONG);
    	msg.show();
    }
    
    /*private void sendName(View view) {
		Intent myIntent = new Intent(view.getContext(), StampActivity.class);
		myIntent.putExtra("username", username);
		startActivity(myIntent);
	}*/
    
    public void sendName(View view, String username, String id) {
		Intent intent = new Intent(this, StampNewActivity.class);
		intent.putExtra(EXTRA_NAME, username);
		intent.putExtra(EXTRA_ID, id);
		startActivity(intent);
		LoginActivity.this.finish();		
	}
    
    public void addUser(View view, String username) {
    	RequestTask r = new RequestTask();
        String res = "Nothing";
  		try {
  			res = r.execute("addUser", username).get();
  		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			res = "InterruptedException";
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			res = "ExecutionException";
			e.printStackTrace();
		}
  		sendName(view, username, res);
    }
} 
