package vn.edu.uit.jrfsit.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Connect {
    HttpClient httpclient;
    HttpPost httppost;
    HttpResponse response;
    HttpParams httpParameters;
    HttpEntity entity;

    int timeoutConnection = 3000;
    int timeoutSocket = 5000;

    public Connect(String link) {
        httpParameters = new BasicHttpParams();
        httppost = new HttpPost(link);
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        httpclient = new DefaultHttpClient(httpParameters);
    }

    /**
     * @param nameValuePairs
     * @return boolean
     */
    public boolean DUI(ArrayList<NameValuePair> nameValuePairs) throws IOException, JSONException {
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        response = httpclient.execute(httppost);
        boolean result;
        if (response.getStatusLine().getStatusCode() == 200) {
            entity = response.getEntity();
            InputStream instream = entity.getContent();
            JSONObject jsonResponse = new JSONObject(convertStreamTostring(instream));
            String temp = jsonResponse.getString("success");
            if (temp.equals("1")) {
                result = true;
            }else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    public JSONArray getArrayObject(ArrayList<NameValuePair> valuePairs, String get) throws IOException, JSONException {
            ArrayList<NameValuePair> nameValuePairs = valuePairs;
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);
            JSONArray jsonArray;
            if (response.getStatusLine().getStatusCode() == 200) {
                entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    JSONObject jsonResponse = new JSONObject(convertStreamTostring(inputStream));
                    if (jsonResponse.getString("success").equals("1")) {
                        JSONArray array = jsonResponse.getJSONArray(get);
                        jsonArray = array;
                    }else {
                        jsonArray =null;
                    }
                }else {
                    jsonArray =null;
                }
            }else {
                jsonArray =null;
            }
        return jsonArray;
    }


    public JSONObject getObject(ArrayList<NameValuePair> valuePairs) throws JSONException, IOException {
        ArrayList<NameValuePair> nameValuePairs = valuePairs;
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        response = httpclient.execute(httppost);
        JSONObject jsonResponse;
        if (response.getStatusLine().getStatusCode() == 200) {
            entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                jsonResponse = new JSONObject(convertStreamTostring(instream));
            }else {
                jsonResponse =null;
            }
        }else {
            jsonResponse =null;
        }
        return jsonResponse;
    }
    private String convertStreamTostring(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
