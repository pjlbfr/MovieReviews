package com.moviereviews.critic;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.moviereviews.R;

import java.util.ArrayList;

public class CriticActivity extends AppCompatActivity{

    public static final String TAG = CriticActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic);

        Intent intent = getIntent();
        // список, содержащий все нужные для вывода на экран данные
        ArrayList<String> critic = intent.getStringArrayListExtra(TAG);
        // установка заголовка приложения именем критика
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            AppCompatTextView textView = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.text_title);
            textView.setText(critic.get(0));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
            getSupportActionBar().setElevation(0);
        }

        CriticFragment criticFragment = CriticFragment.newInstance(critic);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.frame_layout, criticFragment)
                                   .commit();
        new CriticPresenter(this, criticFragment);
    }
}
