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

public class DetailDetailDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_detail_kls, edit_dk_id_kls, edit_dk_id_pst;
    String id;
    Button btn_update_detail_kls, btn_delete_detail_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_detail_detail_kelas);

        edit_id_detail_kls = findViewById(R.id.edit_id_detail_kls);
        edit_dk_id_kls = findViewById(R.id.edit_dk_id_kls);
        edit_dk_id_pst = findViewById(R.id.edit_dk_id_pst);
        btn_delete_detail_kls = findViewById(R.id.btn_delete_detail_kelas);
        btn_update_detail_kls = findViewById(R.id.btn_update_detail_kelas);

        // menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra("id_detail_kls");

        // mengambil data JSON
        getJSON();

        // event handling
        btn_update_detail_kls.setOnClickListener(this);
        btn_delete_detail_kls.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDetailDetailKelasActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_DETAIL_DETAIL_KELAS, id);
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

            String id_detail_kls = object.getString("id_detail_kls");
            String id_kls = object.getString("id_kls");
            String id_pst = object.getString("id_pst");

            edit_id_detail_kls.setText(id_detail_kls);
            edit_dk_id_kls.setText(id_kls);
            edit_dk_id_pst.setText(id_pst);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_detail_kls) {
            updateDataDetailKelas();
        } else if (view == btn_delete_detail_kls) {
            confirmDeleteDataDetailKelas();
        }
    }

    private void confirmDeleteDataDetailKelas() {
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
                deleteDataDetailKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataDetailKelas() {
        // data apa saja yang akan diubah
        final String id_detail_kls = edit_id_detail_kls.getText().toString().trim();
        final String id_kls = edit_dk_id_kls.getText().toString().trim();
        final String id_pst = edit_dk_id_pst.getText().toString().trim();

        class DeleteDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDetailDetailKelasActivity.this,
                        "Menghapus Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResponse(Konfigurasi.URL_DELETE_DETAIL_KELAS, id);
                return s;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailDetailDetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                Intent myIntent = new Intent(DetailDetailDetailKelasActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail kelas");
                startActivity(myIntent);
            }
        }
        DeleteDataDetailKelas deleteDataDetailKelas = new DeleteDataDetailKelas();
        deleteDataDetailKelas.execute();
    }



    private void updateDataDetailKelas() {
        // data apa saja yang akan diubah
        final String id_detail_kls = edit_id_detail_kls.getText().toString().trim();
        final String id_kls = edit_dk_id_kls.getText().toString().trim();
        final String id_pst = edit_dk_id_pst.getText().toString().trim();

        class UpdateDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailDetailDetailKelasActivity.this,
                        "Mengubah Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_detail_kls", id_detail_kls);
                params.put("id_kls", id_kls);
                params.put("id_pst", id_pst);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_DETAIL_KELAS, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailDetailDetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                Intent myIntent2 = new Intent(DetailDetailDetailKelasActivity.this, MainActivity.class);
                myIntent2.putExtra("keyName", "detail kelas");
                startActivity(myIntent2);
            }
        }
        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }
}