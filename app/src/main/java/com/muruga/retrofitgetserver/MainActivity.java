package com.muruga.retrofitgetserver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.muruga.retrofitgetserver.models.Model;
import com.muruga.retrofitgetserver.retrofit.ServiceGenerator;
import com.muruga.retrofitgetserver.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);
    }


    /**
     * Here getValues without parameter from Network API
     */
    private void callGetService() {
        Utilkit.showSpinnerDialog(mContext, false);
        WebService webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebService.class);
        Call<Model> call = webServiceObj.callGetService();
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Utilkit.dismisssSpinnerDialog();
                try {
                    Model model = response.body();

                    Log.d("model", "result" + model);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Utilkit.dismisssSpinnerDialog();
                Utilkit.alertRetrofitExceptionDialog(MainActivity.this, t);
            }
        });
    }


    /**
     * Here getValues with Parameter to Network API
     */
    private void callSendService() {
        Utilkit.showSpinnerDialog(mContext, false);
        WebService webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebService.class);
        Call<Model> call = webServiceObj.callSendService("muruga", "12345");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Utilkit.dismisssSpinnerDialog();
                try {
                    Model model = response.body();

                    Log.d("model", "result" + model);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Utilkit.dismisssSpinnerDialog();
                Utilkit.alertRetrofitExceptionDialog(MainActivity.this, t);
            }
        });
    }

}
