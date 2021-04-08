package com.vouta.plebiscite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vouta.plebiscite.R;
import com.vouta.plebiscite.getterandsetters.Model;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class SendRegNum extends AppCompatActivity {

    public  static final String RegNum = "VERIFICATION";

    private TextView verification;
    private EditText Subject;
    CircularProgressButton circularProgressButton;

    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reg_num);

        verification = findViewById(R.id.verification);
        Subject = findViewById(R.id.Subjectid);



        circularProgressButton = findViewById(R.id.btn_sendEmail);


         model = getIntent().getParcelableExtra(RegNum);

        verification.setText(model.getRegNum());

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circularProgressButton.startAnimation();

                sendEmail();

                circularProgressButton.revertAnimation();

            }
        });





    }
    private void sendEmail(){
        String subject = Subject.getText().toString();
        String to = model.getEmail();


        Intent intent = new Intent(Intent.ACTION_SEND);





        intent.putExtra(Intent.EXTRA_EMAIL,new String[] {to});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "Dear candidate \n \n "
                + "please find the attached registration number \n \n" + model.getRegNum());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"send Email"));



    }
}