package com.example.beacon_dot;




import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class LoginActivity extends Activity {
 
	public final static String EXTRA_NAME = "com.example.beacon_dot.NAME";
	public final static String EXTRA_ID = "com.example.beacon_dot.ID";
	
	protected ProgressDialog progressDialog;
	protected Button startButton;
    // activity_login.xml - EditText - id : imputName
    protected EditText usernameField;
    
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                  
        startButton = (Button) findViewById(R.id.button1);
        usernameField = (EditText) findViewById(R.id.inputName);
        
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                username = usernameField.getText().toString();
                
                if(username.isEmpty()) {
                	//create toast message for empty username
                   toastMassage();
                } else {
                	//send user input name to stamp activity              	
                	addUser(view);
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
    
    public void afterHttp(String id) {
		Intent intent = new Intent(this, StampNewActivity.class);
		intent.putExtra(EXTRA_NAME, username);
		intent.putExtra(EXTRA_ID, id);
		startActivity(intent);
		LoginActivity.this.finish();		
	}
    
    public void addUser(View view) {   	
    	//RequestTask r = new RequestTask(view.getContext());
    	RequestTask r = new RequestTask(this);
        String res = "Nothing";
		r.execute("addUser", username);

		
  		//sendName(username, res);
    }
    

} 
