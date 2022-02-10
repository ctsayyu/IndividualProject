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

public class DetailMateriActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_mat, edit_nama_mat;
    String id_mat;
    Button btn_update_materi, btn_delete_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        edit_id_mat = findViewById(R.id.edit_id_mat);
        edit_nama_mat= findViewById(R.id.edit_nama_mat);
        btn_delete_materi = findViewById(R.id.btn_delete_materi);
        btn_update_materi = findViewById(R.id.btn_update_materi);

        // menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id_mat = receiveIntent.getStringExtra(Konfigurasi.MAT_ID);
        edit_id_mat.setText(id_mat);

        // mengambil data JSON
        getJSON();

        // event handling
        btn_update_materi.setOnClickListener(this);
        btn_delete_materi.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_MATERI, id_mat);
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

            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);

            edit_nama_mat.setText(nama);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_materi) {
            updateDataMateri();
        } else if (view == btn_delete_materi) {
            confirmDeleteDataMateri();
        }
    }

    private void confirmDeleteDataMateri() {
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
                deleteDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataMateri() {
        // data apa saja yang akan diubah
        final String nama = edit_nama_mat.getText().toString().trim();


        class DeleteDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Menghapus Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResponse(Konfigurasi.URL_DELETE_MATERI, id_mat);
                return s;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailMateriActivity.this,
                        "pesan: " + message, Toast.LENGTH_LONG).show();
                // redirect ke LihatDataActivity
                startActivity(new Intent(DetailMateriActivity.this,MainActivity.class));
            }
        }
        DeleteDataMateri deleteDataMateri = new DeleteDataMateri();
        deleteDataMateri.execute();
    }



    private void updateDataMateri() {
        // data apa saja yang akan diubah
        final String nama = edit_nama_mat.getText().toString().trim();

        class UpdateDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Mengubah Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_MAT_ID, id_mat);
                params.put(Konfigurasi.KEY_MAT_NAMA, nama);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_MATERI, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailMateriActivity.this,
                        "pesan: " + message, Toast.LENGTH_LONG).show();
                // redirect ke LihatDataActivity
                startActivity(new Intent(DetailMateriActivity.this, MainActivity.class));
            }
        }
        UpdateDataMateri updateDataMateri= new UpdateDataMateri();
        updateDataMateri.execute();
    }
}