package com.example.android.harshitadhingranewsapp;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import static com.example.android.harshitadhingranewsapp.MainActivity.LOG_TAG;

public abstract class Utilities  {
    private Utilities() {}
    public static final List<HDNews> news = new ArrayList<>();
    public static List<HDNews> fetchNewsData(String requestUrl) {
        Log.i(LOG_TAG,"fetchData");
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<HDNews> news=extractFeatureFromJson(jsonResponse);
        return news;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = " ";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static List<HDNews> extractFeatureFromJson(String BookJSON) {
        String webTitle=" ";
        String webUrl=" ";
        String webPublicationDate=" ";
        String section=" ";
        if (TextUtils.isEmpty(BookJSON)) {
            return null;
        }
        try {
            JSONObject root=new JSONObject(BookJSON);
            JSONObject response=root.getJSONObject("response");
            JSONArray results=response.getJSONArray("results");
            for(int i=0;i<results.length();i++)
            {
                JSONObject feat = results.getJSONObject(i);
                 webTitle = feat.getString("webTitle");
                 webUrl=feat.getString("webUrl");
                webPublicationDate=feat.getString("webPublicationDate");
                section=feat.getString("sectionName");
                news.add(new HDNews(webTitle,webPublicationDate,section,webUrl));
            }
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return news;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
