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
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class registerActivity extends AppCompatActivity {

    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtNombre)
    EditText txtNombre;
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //Init service
        Retrofit retrofitCliente = retrofitClient.getInstance();
        iretrofitService = retrofitCliente.create(retrofitService.class);
    }

    @OnClick(R.id.btnRegister)
    public void onViewClicked() {
        if(txtEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtNombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese usuario", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese password", Toast.LENGTH_SHORT).show();
            return;
        }
        register(txtEmail.getText().toString(), txtPassword.getText().toString(), txtNombre.getText().toString());

        Intent intent = new Intent(registerActivity.this, MainActivity.class);
        //send userName to chat
        //intent.putExtra(userName, txtUser.getText().toString());
        startActivity(intent);
    }

    private void register(String email, String password, String name){

        compositedisposable.add(iretrofitService.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(registerActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));

    }
}
