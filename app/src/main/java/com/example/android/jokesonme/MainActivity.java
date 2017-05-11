package com.example.android.jokesonme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.R.attr.button;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.android.jokesonme.MainActivity.newJoke;

//import static com.google.cloud.sql.jdbc.internal.ConnectionOperationHelper.commit;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;


import com.example.MyJokes.*;

import com.example.MyJokes;
import com.example.android.displaymyjokes.DisplayMyJokesActivity;
//import com.example.android.jokesonme.free.MainActivityFragment;
//import com.example.android.jokesonme.paid.MainActivityFragment;
//import com.example.android.jokesonme.free.MainActivityFragment;
import com.example.android.jokesonme.free.MainActivityFragment;
//import com.example.android.jokesonme.paid.MainActivityFragment;
import com.example.berto.jokesonme.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import com.example.berto.jokesonme.backend.myApi.MyApi.GetJoke;

class MyJokesAsyncTask extends AsyncTask<String, Void, String> {
    private MyApi myApiService = null;
    private Context context;


    MyJokesAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        //return null;
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    //.setRootUrl("http://xxxxxxx:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            return myApiService.getJoke().execute().getMMyJokes();

        } catch (IOException e) {
            return e.getMessage();

        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("message", "joke here" + s);
        /*Intent intent = new Intent(context, DisplayMyJokesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("display", s);
        context.startActivity(intent); */
        if(s != null) {
            Intent intent = new Intent(context, DisplayMyJokesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("display", s);
            context.startActivity(intent);

        } else {
            Toast.makeText(context, "There are no jokes", Toast.LENGTH_LONG).show();

        }

    }
}

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String newJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainActivityFragment())
                    .commit();
        }

    }

    public void fetchJoke (View view) {
        Context context = this;
        new MyJokesAsyncTask(context).execute();
    }


}

