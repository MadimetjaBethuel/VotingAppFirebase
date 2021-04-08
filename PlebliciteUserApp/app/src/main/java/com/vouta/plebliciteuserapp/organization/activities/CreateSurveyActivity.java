package com.vouta.plebliciteuserapp.organization.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.vouta.plebliciteuserapp.R;
import com.vouta.plebliciteuserapp.organization.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CreateSurveyActivity extends AppCompatActivity{


    private EditText question;
    private EditText name;
    private EditText aText;
    private EditText bText;
    private EditText cText;
    private EditText dText;
    private RadioButton aRadio;
    private RadioButton bRadio;
    private RadioButton cRadio;
    private RadioButton dRadio;

    private int currentquestion;
    private int previousquestion;

    private TextView questionNum;


    private ArrayList<Question> questions;
    private JSONArray jsonArray;
    private String selectedOption ="";

    private Button saveBtn;

    private AlertDialog alertDialog;
    private View dialogView;
    private String filename ="file";
    private FirebaseAuth auth;
    private DatabaseReference Surveys;


    CardView fab,f2,fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonArray = new JSONArray();
        setContentView(R.layout.activity_create_survey);
        question = findViewById(R.id.questionView);
        name = findViewById(R.id.Surveyname);
        aText = findViewById(R.id.aText);
        bText = findViewById(R.id.bText);
        cText = findViewById(R.id.cText);
        dText = findViewById(R.id.dText);
        questionNum = findViewById(R.id.questionNumber);
        aRadio = findViewById(R.id.aRadio);
        bRadio =  findViewById(R.id.bRadio);
        cRadio =  findViewById(R.id.cRadio);
        dRadio =  findViewById(R.id.dRadio);

        auth = FirebaseAuth.getInstance();
        Surveys = FirebaseDatabase.getInstance().getReference().child("Surveys");

        selectedOption = "";
        currentquestion = 1;
        setListerner();

        questions = new ArrayList<>();
        alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.survey_dialog, null);

        fab = findViewById(R.id.nextfab);
        fl = findViewById(R.id.fab2);
        f2 = findViewById(R.id.pre_card);

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousquestion > 1) {
                    previousquestion--;
                    setAllData(previousquestion);
                }
                if (previousquestion == 1) {
                    f2.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(),
                            String.valueOf(previousquestion),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousquestion != currentquestion) {
                    previousquestion++;
                    if (previousquestion != currentquestion)
                        setAllData(previousquestion);
                    else {
                        clearAllData();
                        questionNum.setText(String.valueOf(currentquestion));
                    }
                    if (previousquestion > 1)
                        f2.setVisibility(View.VISIBLE);
                }
                boolean cont = getEnteredQuestionValue();
                if (cont) {
                    previousquestion++;
                    currentquestion++;
                    Toast.makeText(getApplicationContext(),
                            "Question",
                            Toast.LENGTH_SHORT).show();
                    clearAllData();
                    f2.setVisibility(View.VISIBLE);
                }

            }

        });
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jsonArray.length() != 0){
                    final JSONObject tempObject = new JSONObject();

                    LayoutInflater li = LayoutInflater.from(CreateSurveyActivity.this);
                    View promptsView = li.inflate(R.layout.survey_dialog,null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            CreateSurveyActivity.this);


                    //set dialog to alertdialog
                    alertDialogBuilder.setView(promptsView);
                    final EditText userInput = promptsView
                            .findViewById(R.id.editTextDialogUserInput);


                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final  String str = userInput.getText().toString().trim();
                                            String temp2 = str;

                                            try{
                                                tempObject.put("Questions",jsonArray);

                                                final String Name = userInput.getText().toString();
                                                tempObject.put("Name",temp2);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            final String jsonStr = tempObject.toString();

                                                Map<String, Object> results = new Gson().fromJson(jsonStr,Map.class);

                                                filename = auth.getCurrentUser().getUid();
                                                String input = userInput.getText().toString().trim();
                                                if (!TextUtils.isEmpty(filename)){
                                                    Surveys.child(filename).setValue(results);
                                                }
                                            }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {
                    Toast.makeText(CreateSurveyActivity.this,
                            "Incomplete Question format",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Exit Without saving");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void updateData(int position){
        Question question1 = new Question();
        question1 = questions.get(position-1);
    }

    private boolean getEnteredQuestionValue() {
        boolean cont = false;
        if (TextUtils.isEmpty(question.getText().toString().trim())) {
            question.setError("Please fill in a question");
        }
        else if (TextUtils.isEmpty(aText.getText().toString().trim())) {
            aText.setError("Please fill in option A");
        }
        else if (TextUtils.isEmpty(bText.getText().toString().trim())) {
            bText.setError("Please fill in option B");
        }
        else if (TextUtils.isEmpty(cText.getText().toString().trim())) {
            cText.setError("Please fill in option C");
        }
        else if (TextUtils.isEmpty(dText.getText().toString().trim())) {
            dText.setError("Please fill in option D");
        }
        else if (selectedOption.equals("")) {
            Toast.makeText(this, "Please select the correct answer", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name.getText().toString().trim())){
                question.setError("fill in Name Of Survey");
                if (!(TextUtils.isEmpty((CharSequence) name))){
                    name.setVisibility(View.INVISIBLE);

                }

        }
        else {
            Question quest = new Question();
            quest.setId(currentquestion);
            quest.setQuestion(question.getText().toString());
            quest.setOpt_A(aText.getText().toString());
            quest.setOpt_B(bText.getText().toString());
            quest.setOpt_C(cText.getText().toString());
            quest.setOpt_D(dText.getText().toString());
            quest.setAnswer(selectedOption);
            questions.add(quest);
            cont = true;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("answer",selectedOption);
                jsonObject.put("opt_A",aText.getText().toString().trim());
                jsonObject.put("opt_B",bText.getText().toString().trim());
                jsonObject.put("opt_C",cText.getText().toString().trim());
                jsonObject.put("opt_D",dText.getText().toString().trim());
                jsonObject.put("question",question.getText().toString().trim());

            }catch (JSONException e){
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        return cont;

    }


    private void clearAllData() {
        aRadio.setChecked(false);
        bRadio.setChecked(false);
        cRadio.setChecked(false);
        dRadio.setChecked(false);
        aText.setText(null);
        bText.setText(null);
        cText.setText(null);
        dText.setText(null);
        question.setText(null);
        selectedOption = "";
    }

    private void setAllData(int position) {
        clearAllData();
        Question question1 = new Question();
        question1 = questions.get(position-1);
        questionNum.setText(String.valueOf(question1.getId()));
        aText.setText(question1.getOpt_A());
        bText.setText(question1.getOpt_B());
        cText.setText(question1.getOpt_C());
        dText.setText(question1.getOpt_D());
        switch (question1.getAnswer()){
            case "A":
                aRadio.setChecked(true);
                break;
            case "B":
                bRadio.setChecked(true);
            case "C":
                cRadio.setChecked(true);
                break;
            case "D":
                dRadio.setChecked(true);
                break;
        }
    }


    private void setListerner() {
        aRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "A";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        bRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "B";
                aRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        cRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "C";
                bRadio.setChecked(false);
                aRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        dRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "D";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                aRadio.setChecked(false);
            }
        });

    }
}