package com.prox.shortlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditLinkActivity extends AppCompatActivity {

    EditText edtLink, edtShortLink, edtPassword;
    Spinner spnPrivacy;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_link);

        edtLink = findViewById(R.id.edt_link);
        edtShortLink = findViewById(R.id.edt_short_link);
        edtPassword = findViewById(R.id.edt_password);
        spnPrivacy = findViewById(R.id.spn_privacy);
        btnSave = findViewById(R.id.btn_save);

        Intent i = getIntent();
        Link link = (Link) i.getSerializableExtra("link");
        if(link != null){
            edtLink.setText(link.getLink());
            edtShortLink.setText(link.getShortLink());
                    edtPassword.setText(link.getPassword());
            if (link.getPrivacy().equals("public")){
                spnPrivacy.setSelection(0);
            }else{
                spnPrivacy.setSelection(1);
            }
        }
    }
}