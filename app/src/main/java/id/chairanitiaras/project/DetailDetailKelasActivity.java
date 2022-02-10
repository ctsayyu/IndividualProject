package id.chairanitiaras.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailDetailKelasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String id_kls;
    private ListView list_view;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_detail_kelas);

        //penanganan list view
        list_view = findViewById(R.id.list_view_detail_detail_kelas);
        list_view.setOnItemClickListener(this);

        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra("id_kls");

        //method untuk ambil data JSON
        getJSON();
    }


    private void getJSON() {
        //bantuan dari class AsynTask
        class GetJSON extends AsyncTask<Void, Void, String> { //inner class
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDetailKelasActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_DETAIL_KELAS, id_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON", JSON_STRING);

                //menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON", JSON_STRING);

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String get_id_kls = object.getString("id_kls");
                String get_id_detail_kls = object.getString("id_detail_kls");
                String get_nama_pst = object.getString("nama_pst");

                HashMap<String, String> detaildetailkelas = new HashMap<>();
                detaildetailkelas.put("id_kls", get_id_kls);
                detaildetailkelas.put("id_detail_kls", get_id_detail_kls);
                detaildetailkelas.put("nama_pst", get_nama_pst);

                //ubah format json menjadi array list
                list.add(detaildetailkelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.list_item_detail_kelas,
                new String[]{"id_kls", "id_detail_kls", "nama_pst"},
                new int[]{R.id.txt_id_kls, R.id.txt_mulai_kls, R.id.txt_akhir_kls}
        );
        list_view.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ketika salah satu list dipilih
        // detail: ID, Name, Desg, Salary
        Intent myIntent = new Intent(DetailDetailKelasActivity.this, DetailDetailDetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String dkId = map.get("id_detail_kls").toString();
        myIntent.putExtra("id_detail_kls", dkId);
        startActivity(myIntent);
    }
}