package com.example.dell.proyectoedii;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtUser)
    EditText txtUser;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    public static final String userName = "userName";
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        //if login is not empty go to chatbox activity and add the nickname to the intent extra

        /*if (!txtUser.getText().toString().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, chatActivity.class);
            //send userName to chat
            intent.putExtra(userName, txtUser.getText().toString());
            startActivity(intent);
        }*/
        //make GET request
        new GetDataTask().execute("http://192.168.1.5:3000/api/users");
        //make POST request
        //new PostDataTask().execute("http://192.168.1.5:3000/api/users");
    }

    class GetDataTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("loading ");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            try{
                return getData(params[0]);
            } catch(IOException e){
                return "Network error";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //set data to textView
            textView.setText(result);

            //cancel progress dialog
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }

        private String getData(String urlPath) throws IOException{
            StringBuilder result = new StringBuilder();
            //initialize and config request, connect to server
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /*milliseconds*/);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json"); //header
                urlConnection.connect();

                //Read data from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }
            return result.toString();
        }
    }

    class PostDataTask extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("inserting ");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                return postData(params[0]);
            } catch(IOException e){
                return "Network error";
            }catch(JSONException e){
                return "Invalid data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);

            if(progressDialog != null){
                progressDialog.dismiss();
            }

        }

        private String postData(String urlPath) throws IOException, JSONException{
            BufferedWriter bufferedWriter = null;
            try {

                JSONObject dataToSend = new JSONObject();

                dataToSend.put("email", txtEmail.getText().toString());
                dataToSend.put("userName", txtUser.getText().toString());
                dataToSend.put("password", txtPassword.getText().toString());

                //initialize server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /*milliseconds*/);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true); //enable output
                urlConnection.setRequestProperty("Content-Type", "application/json"); //header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();
            } finally {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            }

            return "";
        }
    }


}