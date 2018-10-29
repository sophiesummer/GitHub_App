package com.hiddleston.gitapplication;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Challenge;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private String clientId = "220a7b036754fa4be54d";
    private String clientSecret = "304618e1fb4efd28a6d3cbc669b670c8c4cdddb3";

    private String token = "7ca5a4d8f7829680446089d0ba4d114ee40200a6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.editTextLogin);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.login_button);

/*
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .authenticator(new Authenticator() {
                            @Override
                            public Request authenticate(Route route, Response response) throws IOException {
                                if (response.request().header("Authorization") != null) {
                                    return null; // Give up, we've already attempted to authenticate.
                                }

                                System.out.println("Authenticating for response: " + response);
                                System.out.println("Challenges: " + response.challenges());
                                String credential = Credentials.basic("sophiesummer", "Sophie831");
                                return response.request().newBuilder()
                                        .header("Authorization", credential)
                                        .build();
                            }
                        }).build();


                String url = "https://api.github.com/users";
                Request request = new Request.Builder().url(url).
                        build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    return response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                try {
                    System.out.print(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute();

*/

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                com.squareup.okhttp.OkHttpClient client = new com.squareup.okhttp.OkHttpClient();

                String url = "https://api.github.com/?access_token=" + token;
                com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).
                        header("User-Agent", "sophiesummer").
                        build();
                com.squareup.okhttp.Response response = null;
                try {
                    response = client.newCall(request).execute();
                    return response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                try {
                    Log.d("token", o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public static String getRequest() {
        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet();

            URI uri = new URI("https://api.github.com/user");
            httpGet.setURI(uri);
            httpGet.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials("sophiesummer", "Sophie831"),
                    HTTP.UTF_8, false));

            HttpResponse httpResponse = httpClient.execute(httpGet);
            Log.d("response", "rrrrrrrrrrrr");
            Log.d("response code", httpResponse.getStatusLine()
                    .getStatusCode() + "");

            Log.d("response", httpResponse.getStatusLine()
                    .getStatusCode() + "");
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }
        return stringBuffer.toString();
    }



}
