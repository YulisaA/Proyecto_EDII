package com.example.dell.proyectoedii;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder> {
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.txtMessage)
    TextView txtMessage;
    private List<message> MessageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public TextView message;


        public MyViewHolder(View view) {
            super(view);

            nickname = txtUser;
            message = txtMessage;
        }
    }
// in this adaper constructor we add the list of messages as a parameter so that
// we will passe  it when making an instance of the adapter object in our activity


    public chatAdapter(List<message> MessagesList) {

        this.MessageList = MessagesList;

    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.additem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //binding the data from our ArrayList of object to the item.xml using the viewholder

        message m = MessageList.get(position);
        holder.nickname.setText(m.getUserName());

        holder.message.setText(m.getMessage());
    }


}
