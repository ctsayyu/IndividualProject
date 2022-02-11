package id.chairanitiaras.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TambahDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_id_kls, edit_tambah_id_pst;
    private Button btn_tambah_detail_kls, btn_lihat_detail_kls;
    Spinner spinner_pst, spinner_kls;
    int spinner_value, spinner_value2;
    String JSON_STRING, JSON_STRING2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail_kelas);

        edit_tambah_id_kls = findViewById(R.id.edit_tambah_id_kls);
        btn_tambah_detail_kls = findViewById(R.id.btn_tambah_detail_kelas);
        btn_lihat_detail_kls = findViewById(R.id.btn_lihat_detail_kelas);
        spinner_pst = findViewById(R.id.spinner_pst);

        btn_lihat_detail_kls.setOnClickListener(this);
        btn_tambah_detail_kls.setOnClickListener(this);

        getJSON();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahDetailKelasActivity.this, "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PESERTA);
                Log.d("GetData", result);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<String> listID = new ArrayList<>();
                ArrayList<String> listNama = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(Konfigurasi.TAG_JSON_ID_PST);
                        String name = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);

                        listID.add(id);
                        listNama.add(name);
                        Log.d("DataArr: ", String.valueOf(id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahDetailKelasActivity.this, android.R.layout.simple_spinner_dropdown_item, listNama);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_pst.setAdapter(adapter);
                spinner_pst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_value = Integer.parseInt(listID.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_pst.setAdapter(adapter);
                //slct_spin = p_comp.getSelectedItem().toString();
                Log.d("spin", String.valueOf(listNama));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    @Override
    public void onClick(View view) {
        if (view == btn_lihat_detail_kls) {
            startActivity(new Intent(TambahDetailKelasActivity.this, MainActivity.class));
        } else if (view == btn_tambah_detail_kls) {
            simpanDataDetailKelas();
        }
    }

    private void simpanDataDetailKelas() {
        // fields apa saja yang akan disimpan
        final String id_kls = edit_tambah_id_kls.getText().toString().trim();
        //final String id_pst = edit_tambah_id_pst.getText().toString().trim();
        String n1 = String.valueOf(spinner_value);

        class SimpanDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDetailKelasActivity.this, "Menyimpan Data", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_DT_KLS_ID_KLS, id_kls);
                params.put(Konfigurasi.KEY_PST_ID, n1);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_DETAIL_KELAS, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahDetailKelasActivity.this, "pesan " + message,
                        Toast.LENGTH_LONG).show();
                clearText();
                Intent myIntent = new Intent(TambahDetailKelasActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail kelas");
                startActivity(myIntent);
            }
        }
        SimpanDataDetailKelas simpanDataDetailKelas = new SimpanDataDetailKelas();
        simpanDataDetailKelas.execute();
    }

    private void clearText() {
        edit_tambah_id_kls.setText("");
        edit_tambah_id_kls.requestFocus();
    }
}