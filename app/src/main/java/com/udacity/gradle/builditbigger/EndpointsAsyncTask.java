package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jokeandroidlib.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static com.example.jokeandroidlib.JokeActivity.JOKE;


class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    //place this class in the main/ source set, since it is being accessed from
    //both flavors.
    private static MyApi myApiService = null;
    private Context context;

    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();

    @Override
    protected String doInBackground(Context ... context) {
        if(myApiService == null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();

            Log.d(LOG_TAG, "do in background running ... myApiService : " + myApiService);
        }

        this.context = context[0];

        try{
            Log.d(LOG_TAG, "fetching joke from the backend");
            return myApiService.getJoke().execute().getData();
        }catch (IOException e){
            Log.d(LOG_TAG, "error trying to fetch joke from the backend");
            return "";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(LOG_TAG, "on post execute running ... ");

        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JOKE, s);
        context.startActivity(intent);

        Log.d(LOG_TAG, s);
    }
}
