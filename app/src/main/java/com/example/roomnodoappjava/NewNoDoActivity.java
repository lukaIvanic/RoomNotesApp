package com.example.roomnodoappjava;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewNoDoActivity extends AppCompatActivity {

    static final String NEW_NODO_REPLY = "com.nodoapp.android.new";
    static final String NODO_DESC = "com.nodoapp.android.desc";
    static final String EDIT_NODO_REPLY_ID = "com.nodoapp.android.edit.int";
    static final String EDIT_NODO_REPLY_TEXT = "com.nodoapp.android.edit.text";
    private EditText et_title, et_desc;
    private Button button;
    private Boolean isNewNodo = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_no_do);

        et_title = findViewById(R.id.edit_text_title);
        et_desc = findViewById(R.id.edit_text_desc);
        button = findViewById(R.id.butt_save);
        et_title.requestFocus();

        getCurrText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(et_title.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    if (isNewNodo)
                        //If the nodo is new there is no need to pass the id
                        replyIntent.putExtra(NEW_NODO_REPLY, et_title.getText().toString());
                    else {
                        replyIntent.putExtra(EDIT_NODO_REPLY_ID, getIntent().getIntExtra("nodoId", 0));
                        replyIntent.putExtra(EDIT_NODO_REPLY_TEXT, et_title.getText().toString());
                    }
                    //In any case the description is passed as well
                    replyIntent.putExtra(NODO_DESC, et_desc.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    private void getCurrText() {
        String nodoTitle = getIntent().getStringExtra("nodoTitle");
        String nodoDesc = getIntent().getStringExtra("nodoDesc");
        et_title.setText(nodoTitle);
        et_desc.setText(nodoDesc);
        isNewNodo = TextUtils.isEmpty(nodoTitle) && TextUtils.isEmpty(nodoDesc);
        button.setText((isNewNodo)? "make note" : "save");
    }
}