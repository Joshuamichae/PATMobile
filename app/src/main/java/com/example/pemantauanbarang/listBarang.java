package com.example.pemantauanbarang;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class listBarang extends AppCompatActivity {

    private ListView listBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
        listBarang = findViewById(R.id.listBarang);

        new FetchKostDataTask().execute();
    }

    private class FetchKostDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                URL url = new URL("http://192.168.137.1:8000/listbarang");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    result = response.toString();
                } else {
                    result = "Error: " + responseCode;
                }
                connection.disconnect();
            } catch (Exception e) {
                result = "Error: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> barangList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject listBarangObject = jsonArray.getJSONObject(i);
                    String idBarang = listBarangObject.getString("idbarang");
                    String pembuatan = listBarangObject.getString("pembuatan");
                    String harga = listBarangObject.getString("harga");
                    String nama = listBarangObject.getString("nama");

                    String DetailBarang = "ID Barang: " + idBarang + "\n" + "Pembuatan: " + pembuatan + "\n"
                            + "Harga Barang: " + harga + "\n"
                            + "Nama Barang: " + nama;

                    barangList.add(DetailBarang);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(listBarang.this,
                        android.R.layout.simple_list_item_1, barangList);
                listBarang.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(listBarang.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}