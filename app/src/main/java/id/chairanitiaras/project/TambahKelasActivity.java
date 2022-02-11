package id.chairanitiaras.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TambahKelasActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_tgl_mulai_kls, edit_tambah_tgl_akhir_kls, edit_tambah_id_ins_kls, edit_tambah_id_mat_kls;
    private Button btn_tambah_kelas, btn_lihat_kelas;
    Spinner spinner_ins, spinner_mat;
    private int spinner_value, spinner_value2;
    private String JSON_STRING, JSON_STRING2, id_ins, n1, n2, j1, j2;
    DatePickerDialog.OnDateSetListener setListener_mulai;
    DatePickerDialog.OnDateSetListener setListener_akhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);;

        edit_tambah_tgl_mulai_kls = findViewById(R.id.edit_tambah_tgl_mulai_kls);
        edit_tambah_tgl_akhir_kls= findViewById(R.id.edit_tambah_tgl_akhir_kls);
        spinner_ins = findViewById(R.id.spinner_ins);
        spinner_mat = findViewById(R.id.spinner_mat);
        btn_tambah_kelas = findViewById(R.id.btn_tambah_kelas);
        btn_lihat_kelas = findViewById(R.id.btn_lihat_kelas);

        btn_lihat_kelas.setOnClickListener(this);
        btn_tambah_kelas.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edit_tambah_tgl_mulai_kls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TambahKelasActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener_mulai,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        edit_tambah_tgl_akhir_kls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TambahKelasActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener_akhir,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener_mulai = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"-"+month+"-"+dayOfMonth;
                edit_tambah_tgl_mulai_kls.setText(date);
            }
        };

        setListener_akhir = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"-"+month+"-"+dayOfMonth;
                edit_tambah_tgl_akhir_kls.setText(date);
            }
        };

        getJSON();
        getJSON2();

    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahKelasActivity.this, "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_INSTRUKTUR);
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
                        String id = object.getString(Konfigurasi.TAG_JSON_ID_INS);
                        String name = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);

                        listID.add(id);
                        listNama.add(name);

                        Log.d("DataArr: ", String.valueOf(id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahKelasActivity.this, android.R.layout.simple_spinner_dropdown_item, listNama);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_ins.setAdapter(adapter);
                //slct_spin = p_comp.getSelectedItem().toString();

                spinner_ins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_value = Integer.parseInt(listID.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Log.d("spin", String.valueOf(listID));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void getJSON2() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahKelasActivity.this, "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_MATERI);
                Log.d("GetData", result);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                JSON_STRING2 = s;
                Log.d("Data_JSON", JSON_STRING2);

                JSONObject jsonObject = null;
                ArrayList<String> listID = new ArrayList<>();
                ArrayList<String> listNama = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING2);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(Konfigurasi.TAG_JSON_ID_MAT);
                        String name = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);

                        listID.add(id);
                        listNama.add(name);
                        Log.d("DataArr: ", String.valueOf(id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahKelasActivity.this, android.R.layout.simple_spinner_dropdown_item, listNama);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_mat.setAdapter(adapter);
                spinner_mat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_value2 = Integer.parseInt(listID.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_mat.setAdapter(adapter);
                //slct_spin = p_comp.getSelectedItem().toString();
                Log.d("spin", String.valueOf(listNama));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_lihat_kelas) {
            startActivity(new Intent(TambahKelasActivity.this, MainActivity.class));
        } else if (view == btn_tambah_kelas) {
            simpanDataKelas();
        }
    }

    private void simpanDataKelas() {
        // fields apa saja yang akan disimpan
        final String tgl_mulai_kls = edit_tambah_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tambah_tgl_akhir_kls.getText().toString().trim();
        //final String id_ins_kls = spinner_ins.getSelectedItem().toString().trim();
        //final String id_mat_kls = spinner_mat.getSelectedItem().toString().trim();
        String n1 = String.valueOf(spinner_value);
        String n2 = String.valueOf(spinner_value2);

        class SimpanDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this, "Menyimpan Data", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_KLS_TGL_MULAI, tgl_mulai_kls);
                params.put(Konfigurasi.KEY_KLS_TGL_SELESAI, tgl_akhir_kls);
                params.put(Konfigurasi.KEY_KLS_INS, n1);
                params.put(Konfigurasi.KEY_KLS_MAT, n2);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_KELAS, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahKelasActivity.this, "pesan " + message,
                        Toast.LENGTH_LONG).show();
                clearText();
                Intent myIntent = new Intent(TambahKelasActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }

    private void clearText() {
        edit_tambah_tgl_mulai_kls.setText("");
        edit_tambah_tgl_akhir_kls.setText("");
        edit_tambah_tgl_mulai_kls.requestFocus();
    }
}