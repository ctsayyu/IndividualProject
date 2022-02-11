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

public class DetailInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id, edit_nama, edit_email, edit_hp;
    String id;
    Button btn_update_instruktur, btn_delete_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruktur);

        edit_id = findViewById(R.id.edit_id);
        edit_nama = findViewById(R.id.edit_nama);
        edit_email = findViewById(R.id.edit_email);
        edit_hp = findViewById(R.id.edit_hp);
        btn_delete_instruktur = findViewById(R.id.btn_delete_instruktur);
        btn_update_instruktur = findViewById(R.id.btn_update_instruktur);

        // menerima intent dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.INS_ID);
        edit_id.setText(id);

        // mengambil data JSON
        getJSON();

        // event handling
        btn_update_instruktur.setOnClickListener(this);
        btn_delete_instruktur.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_INSTRUKTUR, id);
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

            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);
            String email = object.getString(Konfigurasi.TAG_JSON_EMAIL_INS);
            String hp = object.getString(Konfigurasi.TAG_JSON_HP_INS);

            edit_nama.setText(nama);
            edit_email.setText(email);
            edit_hp.setText(hp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_instruktur) {
            confirmUpdateDataInstruktur();
        } else if (view == btn_delete_instruktur) {
            confirmDeleteDataInstruktur();
        }
    }

    private void confirmDeleteDataInstruktur() {
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
                deleteDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataInstruktur() {
        // data apa saja yang akan diubah
        final String nama = edit_nama.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        final String hp = edit_hp.getText().toString().trim();

        class DeleteDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Menghapus Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResponse(Konfigurasi.URL_DELETE_INSTRUKTUR, id);
                return s;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailInstrukturActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                Intent myIntent = new Intent(DetailInstrukturActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "instruktur");
                startActivity(myIntent);
            }
        }
        DeleteDataInstruktur deleteDataInstruktur = new DeleteDataInstruktur();
        deleteDataInstruktur.execute();
    }

    private void confirmUpdateDataInstruktur() {
        // Confirmation Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mengupdate Data");
        builder.setMessage("Apakah anda yakin input data Anda sudah benar?");
        builder.setIcon(getResources().getDrawable(R.drawable.refresh));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void updateDataInstruktur() {
        // data apa saja yang akan diubah
        final String nama = edit_nama.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        final String hp = edit_hp.getText().toString().trim();

        class UpdateDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Mengubah Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_ID, id);
                params.put(Konfigurasi.KEY_INS_NAMA, nama);
                params.put(Konfigurasi.KEY_INS_EMAIL, email);
                params.put(Konfigurasi.KEY_INS_HP, hp);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_INSTRUKTUR, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailInstrukturActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                Intent myIntent2 = new Intent(DetailInstrukturActivity.this, MainActivity.class);
                myIntent2.putExtra("keyName", "instruktur");
                startActivity(myIntent2);
            }
        }
        UpdateDataInstruktur updateDataInstruktur = new UpdateDataInstruktur();
        updateDataInstruktur.execute();
    }
}