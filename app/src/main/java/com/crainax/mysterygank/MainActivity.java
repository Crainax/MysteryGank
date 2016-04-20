
package com.crainax.mysterygank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeView {

    private static final String TAG = "Crainax.MainActivity";
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
    }

    @Override
    public void showProgress() {
        Log.i(TAG, "showProgress: ");
    }

    @Override
    public void hideProgress() {
        Log.i(TAG, "hideProgress: ");
    }

    @Override
    public void showGanksData(List<MeizhiEntity> datas) {
        for (MeizhiEntity data : datas) {
            Log.i(TAG, "showGanksData: " + datas.toString());
        }
    }

    public void click(View view){
        presenter.refreshGanks();
    }
}
