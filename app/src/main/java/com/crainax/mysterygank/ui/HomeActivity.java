package com.crainax.mysterygank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crainax.mysterygank.R;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.presenter.HomePresenter;
import com.crainax.mysterygank.ui.base.BaseActivity;
import com.crainax.mysterygank.view.HomeView;

import java.util.List;

public class HomeActivity extends BaseActivity<HomeView, HomePresenter>
        implements HomeView, View.OnClickListener {

    private HomePresenter presenter;
    private int currentPage = 1;
    private ProgressBar progressBar;
    private TextView textView;
    private Button button;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        presenter = getPresenter();
    }

    private void findView() {
        progressBar = (ProgressBar) findViewById(R.id.pb);
        textView = (TextView) findViewById(R.id.et);
        button = (Button) findViewById(R.id.bt_test);
        if (button != null) {
            button.setOnClickListener(this);
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        initSpinner();
    }

    private void initSpinner() {
        final String[] datas = {"haha", "xixi", "gugu;", "meme"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(datas[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showGanksData(List<MeizhiEntity> datas) {
        textView.setText(datas.toString());
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(HomeActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        presenter.getGanksData(currentPage++);
    }
}
