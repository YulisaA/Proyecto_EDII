package com.example.dell.proyectoedii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    public RecyclerView myRecylerView;
    public List<message> MessageList;
    public chatAdapter chatBoxAdapter;
    public EditText messagetxt;
    public Button send;
    //declare socket object
    private Socket socket;

    public String Nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagetxt = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.btnSend);
        // get the nickame of the user
        //Nickname = (String) getIntent().getExtras().getString(MainActivity.userName);
        //connect you socket client to the server

        //setting up recyler
        MessageList = new ArrayList<>();
        myRecylerView = (RecyclerView) findViewById(R.id.messagelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());


    }

}

