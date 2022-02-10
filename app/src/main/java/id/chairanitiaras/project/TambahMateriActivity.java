package id.chairanitiaras.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class TambahMateriActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_nama_mat;
    private Button btn_tambah_materi, btn_lihat_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_materi);

        edit_tambah_nama_mat = findViewById(R.id.edit_tambah_nama_mat);
        btn_tambah_materi = findViewById(R.id.btn_tambah_materi);
        btn_lihat_materi = findViewById(R.id.btn_lihat_materi);

        btn_lihat_materi.setOnClickListener(this);
        btn_tambah_materi.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == btn_lihat_materi) {
            startActivity(new Intent(TambahMateriActivity.this, MainActivity.class));
        } else if (view == btn_tambah_materi) {
            simpanDataMateri();
        }
    }

    private void simpanDataMateri() {
        // fields apa saja yang akan disimpan
        final String nama = edit_tambah_nama_mat.getText().toString().trim();

        class SimpanDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahMateriActivity.this, "Menyimpan Data", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_MAT_NAMA, nama);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_MATERI, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahMateriActivity.this, "pesan " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(TambahMateriActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "materi");
                startActivity(myIntent);
            }
        }
        SimpanDataMateri simpanDataMateri = new SimpanDataMateri();
        simpanDataMateri.execute();
    }

    private void clearText() {
        edit_tambah_nama_mat.setText("");
        edit_tambah_nama_mat.requestFocus();
    }
}