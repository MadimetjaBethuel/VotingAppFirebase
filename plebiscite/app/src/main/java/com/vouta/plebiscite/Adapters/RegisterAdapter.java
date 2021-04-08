package com.vouta.plebiscite.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vouta.plebiscite.R;
import com.vouta.plebiscite.activities.SendRegNum;
import com.vouta.plebiscite.getterandsetters.Model;
import com.vouta.plebiscite.getterandsetters.addorg;

import java.util.ArrayList;
import java.util.List;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

    Context context;
    View view;
    List<Model> lists = new ArrayList<>();

    public RegisterAdapter(Context context, List<Model> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context)
                .inflate(R.layout.card_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.firstName.setText(lists.get(position).getFirstName());
        holder.Email.setText(lists.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendRegNum.class);
                intent.putExtra(SendRegNum.RegNum, lists.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView firstName,Email;

        public ViewHolder (View itemView){
            super(itemView);

            firstName = itemView.findViewById(R.id.firstname);
            Email = itemView.findViewById(R.id.email);

        }

    }

}
