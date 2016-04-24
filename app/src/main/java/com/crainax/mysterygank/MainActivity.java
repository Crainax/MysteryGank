package com.crainax.mysterygank;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity implements HomeView, View.OnClickListener {

    private IHomePresenter presenter;
    private int currentPage = 1;
    private ProgressBar progressBar;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        presenter = new HomePresenter(this);
    }

    private void findView() {
        progressBar = (ProgressBar) findViewById(R.id.pb);
        textView = (TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.bt_test);
        if (button != null) {
            button.setOnClickListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        presenter.getGanksData(currentPage++);
    }
}
