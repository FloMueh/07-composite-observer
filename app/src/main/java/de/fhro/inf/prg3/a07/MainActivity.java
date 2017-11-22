package de.fhro.inf.prg3.a07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.fhro.inf.prg3.a07.api.OpenMensaAPI;
import de.fhro.inf.prg3.a07.model.Meal;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.listMeals);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.meal_entry);

        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);

        super.onCreate(savedInstanceState);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMensaAPI openMensaAPI = GetOpenMensaAPI();
                Call<List<Meal>> mealCall = openMensaAPI.GetMeals("2017-11-22");

                mealCall.enqueue(new Callback<List<Meal>>() {
                    @Override
                    public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {

                        List<String> mealNames = new ArrayList<>();
                        for(Meal meal : response.body()) {
                            mealNames.add(meal.getName());
                        }
                        lv.setAdapter(adapter);
                        adapter.clear();
                        adapter.addAll(mealNames);
                    }

                    @Override
                    public void onFailure(Call<List<Meal>> call, Throwable t) {
                        return;
                    }
                });
            }
        });




        // add your code here
    }

    private OpenMensaAPI GetOpenMensaAPI() {
        // use this to intercept all requests and output them to the logging facilities
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://openmensa.org/")
                .client(client)
                .build();

        return retrofit.create(OpenMensaAPI.class);
    }
}
