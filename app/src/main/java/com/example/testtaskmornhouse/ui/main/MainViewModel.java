package com.example.testtaskmornhouse.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.testtaskmornhouse.data.NumberFact;
import com.example.testtaskmornhouse.data.NumberFactDB;
import com.example.testtaskmornhouse.data.NumberFactDao;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "TestTaskModel";
    private static final String BASE_URL = "http://numbersapi.com/";
    private final OkHttpClient mHttpClient = new OkHttpClient();

    private Application mApplication;
    private NumberFactDB mDb;
    private LiveData<List<NumberFact>> mNumberFacts;
    private int mSectedId = -1;

    private Callback mNumberFactCallback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            Log.e(TAG, e.getMessage());
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            String responseStr = response.body().string();
            Log.d(TAG, responseStr);
            storeNumberFact(responseStr);
        }
    };

    public MainViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mDb = Room.databaseBuilder(mApplication.getApplicationContext(),
                NumberFactDB.class, "number-fact.db").build();
        mNumberFacts = mDb.numberFactDao().loadAll();
    }

    public LiveData<List<NumberFact>> getNumberFacts() {
        return mNumberFacts;
    }

    public void numberFactCheck(int number) {
        Request request = new Request.Builder()
                .url(BASE_URL + number)
                .build();
        mHttpClient.newCall(request).enqueue(mNumberFactCallback);
    }

    public void randomNumberFactCheck() {
        Request request = new Request.Builder()
                .url(BASE_URL + "random/math")
                .build();
        mHttpClient.newCall(request).enqueue(mNumberFactCallback);
    }

    private void storeNumberFact(String numberFact) {
        int numberEndIdx = numberFact.indexOf(" is ");
        int number = Integer.parseInt(numberFact.substring(0, numberEndIdx));
        String fact = numberFact.substring(numberEndIdx + 1);

        NumberFact nf = new NumberFact(number, fact);
        mDb.numberFactDao().insert(nf);
    }
}