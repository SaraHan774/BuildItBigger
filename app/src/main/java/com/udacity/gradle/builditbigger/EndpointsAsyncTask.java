package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.jokeandroidlib.JokeActivity;
import com.example.telljokelib.TellJokeClass;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static com.example.jokeandroidlib.JokeActivity.JOKE;


class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();

    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {
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

        context = pairs[0].first;
        String joke = pairs[0].second;

        try{
            Log.d(LOG_TAG, "try block running...");
            //MyEndpoint class 에 접근해서 sayHi 메소드 호출, execute 한 결과로 MyBean 객체 반환,
            //반환된 MyBean 객체의 getter 사용해서 set 된 데이터를 가져온다.
            //MyEndpoint class -> sayHi 메소드 -> MyBean 객체 setData 할 때 setter 에다가 joke 넣어주기.
            return myApiService.sayHi(joke).execute().getData();
        }catch (IOException e){
            Log.d(LOG_TAG, "catch block running ...");
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(LOG_TAG, "on post execute running ... ");
        if(s.equals(new TellJokeClass().getJoke())){
            Toast.makeText(context, "connected to the server", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "connection failed : " + s, Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JOKE, s);
        context.startActivity(intent);

        Log.d(LOG_TAG, s);
    }
}
