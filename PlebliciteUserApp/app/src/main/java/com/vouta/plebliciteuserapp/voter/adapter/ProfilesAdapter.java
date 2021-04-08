package com.vouta.plebliciteuserapp.voter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.activities.profileViewActivity;
import com.vouta.plebliciteuserapp.voter.model.candidateModel;

import java.util.ArrayList;
import java.util.List;

public class  ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ViewHolder>{


        Context context;
        View view;
        List<candidateModel> lists = new ArrayList<>();

        public ProfilesAdapter(Context context, List<candidateModel> lists) {
            this.context = context;
            this.lists = lists;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            view = LayoutInflater.from(context)
                    .inflate(R.layout.profile_adapter,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.Name.setText(lists.get(position).getNameProfile());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, profileViewActivity.class);
                    intent.putExtra(profileViewActivity.PROFILE, lists.get(position));
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            private TextView Name;

            public ViewHolder (View itemView){
                super(itemView);

                Name = itemView.findViewById(R.id.firstname);

            }

        }

    }

