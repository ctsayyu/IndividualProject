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

public class DetailPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_pst, edit_nama_pst, edit_email_pst, edit_hp_pst, edit_instansi_pst;
    String id_pst;
    Button btn_update_peserta, btn_delete_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_peserta);

        edit_id_pst = findViewById(R.id.edit_id_pst);
        edit_nama_pst = findViewById(R.id.edit_nama_pst);
        edit_email_pst = findViewById(R.id.edit_email_pst);
        edit_hp_pst = findViewById(R.id.edit_hp_pst);
        edit_instansi_pst = findViewById(R.id.edit_ins_pst);
        btn_delete_peserta = findViewById(R.id.btn_delete_peserta);
        btn_update_peserta = findViewById(R.id.btn_update_peserta);

        // menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id_pst = receiveIntent.getStringExtra(Konfigurasi.PST_ID);
        edit_id_pst.setText(id_pst);

        // mengambil data JSON
        getJSON();

        // event handling
        btn_update_peserta.setOnClickListener(this);
        btn_delete_peserta.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_PESERTA, id_pst);
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

            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
            String email = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
            String hp = object.getString(Konfigurasi.TAG_JSON_HP_PST);
            String instansi = object.getString(Konfigurasi.TAG_JSON_INSTANSI_PST);

            edit_nama_pst.setText(nama);
            edit_email_pst.setText(email);
            edit_hp_pst.setText(hp);
            edit_instansi_pst.setText(instansi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_peserta) {
            updateDataPeserta();
        } else if (view == btn_delete_peserta) {
            confirmDeleteDataPeserta();
        }
    }

    private void confirmDeleteDataPeserta() {
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
                deleteDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataPeserta() {
        // data apa saja yang akan diubah
        final String nama = edit_nama_pst.getText().toString().trim();
        final String email = edit_email_pst.getText().toString().trim();
        final String hp = edit_hp_pst.getText().toString().trim();
        final String instansi = edit_instansi_pst.getText().toString().trim();

        class DeleteDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Menghapus Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResponse(Konfigurasi.URL_DELETE_PESERTA, id_pst);
                return s;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailPesertaActivity.this,
                        "pesan: " + message, Toast.LENGTH_LONG).show();
                // redirect ke LihatDataActivity
                startActivity(new Intent(DetailPesertaActivity.this,MainActivity.class));
            }
        }
        DeleteDataPeserta deleteDataPeserta = new DeleteDataPeserta();
        deleteDataPeserta.execute();
    }



    private void updateDataPeserta() {
        // data apa saja yang akan diubah
        final String nama = edit_nama_pst.getText().toString().trim();
        final String email = edit_email_pst.getText().toString().trim();
        final String hp = edit_hp_pst.getText().toString().trim();
        final String instansi = edit_instansi_pst.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Mengubah Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_ID, id_pst);
                params.put(Konfigurasi.KEY_PST_NAMA, nama);
                params.put(Konfigurasi.KEY_PST_EMAIL, email);
                params.put(Konfigurasi.KEY_PST_HP, hp);
                params.put(Konfigurasi.KEY_PST_INSTANSI, instansi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PESERTA, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailPesertaActivity.this,
                        "pesan: " + message, Toast.LENGTH_LONG).show();
                // redirect ke LihatDataActivity
                startActivity(new Intent(DetailPesertaActivity.this, MainActivity.class));
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }
}