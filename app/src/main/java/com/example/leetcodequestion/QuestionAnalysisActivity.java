package com.example.leetcodequestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionAnalysisActivity extends AppCompatActivity {
    private Button back;

    private TextView analysisTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        analysisTv = (TextView) findViewById(R.id.analysis_textView);
        Intent intent = getIntent();
        if (null != intent) {
            analysisTv.setText(intent.getStringExtra("analysis"));
        }
    }
}
