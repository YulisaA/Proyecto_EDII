package com.example.dell.proyectoedii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.proyectoedii.Retrofit.retrofitClient;
import com.example.dell.proyectoedii.Retrofit.retrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    retrofitService iretrofitService;
    CompositeDisposable compositedisposable = new CompositeDisposable();

    @Override
    protected void onStop(){
        compositedisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Init service
        Retrofit retrofitCliente = retrofitClient.getInstance();
        iretrofitService = retrofitCliente.create(retrofitService.class);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClickedLogin() {
        /*if (!txtUser.getText().toString().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, chatActivity.class);
            //send userName to chat
            intent.putExtra(userName, txtUser.getText().toString());
            startActivity(intent);
        }*/
        //make GET request


        //make POST request
        //new PostDataTask().execute("http://192.168.1.5:3000/api/users");
        login(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.btnRegister)
    public void onViewClickedRegister() {
        Intent intent = new Intent(MainActivity.this, registerActivity.class);
        //send userName to chat
        //intent.putExtra(userName, txtUser.getText().toString());
        startActivity(intent);
    }

    private void login(String email, String password){
        if(txtEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        compositedisposable.add(iretrofitService.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }

                }));

    }

    /*class GetDataTask extends AsyncTask<String, Void, String>
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
                urlConnection.setReadTimeout(10000);
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

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("inserting ");
            progressDialog.show();
        }*/
/*
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);

            if(progressDialog != null){
                progressDialog.dismiss();
            }

        }*/


}