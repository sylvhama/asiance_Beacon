package asiance.anniversary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class RequestTask extends AsyncTask<String, String, String>{
	
	private ProgressDialog progressDialog;
	private LoginActivity la;
	private FactActivity fa;
	
	public RequestTask(LoginActivity a) {
		la = a;
		fa = null;
	}
	
	public RequestTask(FactActivity a) {
		la = null;
		fa = a;
	}
	
	@Override
    protected void onPreExecute() {
		// instantiate new progress dialog
		if(la != null) {
			progressDialog = new ProgressDialog(la); 
		}else if(fa != null) {
			progressDialog = new ProgressDialog(fa); 
		}	
		// spinner (wheel) style dialog
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
		// better yet - use a string resource getString(R.string.your_message)
		progressDialog.setMessage("Loading..."); 
		// display dialog
		progressDialog.show(); 
	}

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        String url = "http://10th.asiance.com/php/android.php?f=" + uri[0];
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        if (uri[0] == "addUser") {
        	nameValuePairs.add(new BasicNameValuePair("name", uri[1]));
        }else if (uri[0] == "addAnswer")  {
        	nameValuePairs.add(new BasicNameValuePair("idUser", uri[1]));
        	nameValuePairs.add(new BasicNameValuePair("idQuestion", uri[2]));
        }
        HttpPost httppost = new HttpPost(url);
        try {       	
        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        	response = httpclient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
        //return "ok";
    }
    
    

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss(); 
        if(la != null) {
			la.afterHttp(result);
		}else if(fa != null) {
			fa.afterHttp();
		}
    }
}