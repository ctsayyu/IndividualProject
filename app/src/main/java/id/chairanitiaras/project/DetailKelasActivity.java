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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_kls, edit_tgl_mulai_kls, edit_tgl_akhir_kls, edit_id_ins_kls, edit_id_mat_kls;
    String id_kls;
    Button btn_update_kelas, btn_delete_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_tgl_mulai_kls = findViewById(R.id.edit_tgl_mulai_kls);
        edit_tgl_akhir_kls = findViewById(R.id.edit_tgl_akhir_kls);
        edit_id_ins_kls = findViewById(R.id.edit_id_ins_kls);
        edit_id_mat_kls = findViewById(R.id.edit_id_mat_kls);
        btn_delete_kelas = findViewById(R.id.btn_delete_kelas);
        btn_update_kelas = findViewById(R.id.btn_update_kelas);

        // menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra(Konfigurasi.KLS_ID);
        edit_id_kls.setText(id_kls);

        // mengambil data JSON
        getJSON();

        // event handling
        btn_update_kelas.setOnClickListener(this);
        btn_delete_kelas.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_KELAS, id_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String tgl_mulai_kls = object.getString(Konfigurasi.TAG_JSON_KLS_TGL_MULAI);
            String tgl_akhir_kls = object.getString(Konfigurasi.TAG_JSON_KLS_TGL_SELESAI);
            String id_ins_kls = object.getString(Konfigurasi.TAG_JSON_KLS_INS);
            String id_mat_kls = object.getString(Konfigurasi.TAG_JSON_KLS_MAT);

            edit_tgl_mulai_kls.setText(tgl_mulai_kls);
            edit_tgl_akhir_kls.setText(tgl_akhir_kls);
            edit_id_ins_kls.setText(id_ins_kls);
            edit_id_mat_kls.setText(id_mat_kls);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_kelas) {
            updateDataKelas();
        } else if (view == btn_delete_kelas) {
            confirmDeleteDataKelas();
        }
    }

    private void confirmDeleteDataKelas() {
        // Confirmation Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yakin menghapus data ini?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataKelas() {
        // data apa saja yang akan diubah
        final String tgl_mulai_kls = edit_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tgl_akhir_kls.getText().toString().trim();
        final String id_ins_kls = edit_id_ins_kls.getText().toString().trim();
        final String id_mat_kls = edit_id_mat_kls.getText().toString().trim();

        class DeleteDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Menghapus Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResponse(Konfigurasi.URL_DELETE_KELAS, id_kls);
                return s;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_LONG).show();
                // redirect ke LihatDataActivity
                Intent myIntent = new Intent(DetailKelasActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);;
            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }



    private void updateDataKelas() {
        // data apa saja yang akan diubah
        final String tgl_mulai_kls = edit_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tgl_akhir_kls.getText().toString().trim();
        final String id_ins_kls = edit_id_ins_kls.getText().toString().trim();
        final String id_mat_kls = edit_id_mat_kls.getText().toString().trim();

        class UpdateDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Mengubah Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_KLS_ID, id_kls);
                params.put(Konfigurasi.KEY_KLS_TGL_MULAI, tgl_mulai_kls);
                params.put(Konfigurasi.KEY_KLS_TGL_SELESAI, tgl_akhir_kls);
                params.put(Konfigurasi.KEY_KLS_INS, id_ins_kls);
                params.put(Konfigurasi.KEY_KLS_MAT, id_mat_kls);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_KELAS, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                Intent myIntent2 = new Intent(DetailKelasActivity.this, MainActivity.class);
                myIntent2.putExtra("keyName", "kelas");
                startActivity(myIntent2);
            }
        }
        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();
    }
}