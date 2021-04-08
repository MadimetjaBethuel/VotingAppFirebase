package com.vouta.plebliciteuserapp.voter.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.voter.model.candidateModel;
import com.vouta.plebliciteuserapp.voter.model.survey;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    Context context;
    List<survey> lists = new ArrayList<>();
    View view;

    public SurveyAdapter(Context context, List<survey> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context)
                .inflate(R.layout.survey_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.NameSurvey.setText(lists.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView NameSurvey;
        private CardView surveycard;

        public ViewHolder(View itemView){
            super(itemView);

            NameSurvey = itemView.findViewById(R.id.item_textView);
            surveycard = itemView.findViewById(R.id.surveycard);
        }
    }
    }

