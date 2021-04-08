package com.vouta.plebiscite.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.vouta.plebiscite.R;
import com.vouta.plebiscite.activities.SendRegNum;
import com.vouta.plebiscite.getterandsetters.Model;


public class FirebaseDapter extends FirebaseRecyclerAdapter<Model,FirebaseDapter.myViewHolder> {

    Context context;
    public FirebaseDapter (FirebaseRecyclerOptions<Model> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull final Model model) {

      holder.firstName.setText(model.getFirstName());
      holder.Email.setText(model.getEmail());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent = new Intent(context, SendRegNum.class);
              intent.putExtra(SendRegNum.RegNum, (Parcelable) getRef(position));
              context.startActivity(intent);

          }
      });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);


        return viewHolder;
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView firstName,Email;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            firstName = itemView.findViewById(R.id.firstname);
            Email = itemView.findViewById(R.id.email);




        }

    }
}
