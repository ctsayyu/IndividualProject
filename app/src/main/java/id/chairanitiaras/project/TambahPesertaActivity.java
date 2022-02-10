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

public class TambahPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_nama_pst, edit_tambah_email_pst, edit_tambah_hp_pst, edit_tambah_instansi_pst;
    private Button btn_tambah_peserta, btn_lihat_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

        edit_tambah_nama_pst = findViewById(R.id.edit_tambah_nama_pst);
        edit_tambah_email_pst = findViewById(R.id.edit_tambah_email_pst);
        edit_tambah_hp_pst = findViewById(R.id.edit_tambah_hp_pst);
        edit_tambah_instansi_pst = findViewById(R.id.edit_tambah_instansi_pst);
        btn_tambah_peserta = findViewById(R.id.btn_tambah_peserta);
        btn_lihat_peserta = findViewById(R.id.btn_lihat_peserta);

        btn_lihat_peserta.setOnClickListener(this);
        btn_tambah_peserta.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == btn_lihat_peserta) {
            startActivity(new Intent(TambahPesertaActivity.this, MainActivity.class));
        } else if (view == btn_tambah_peserta) {
            simpanDataPeserta();
        }
    }

    private void simpanDataPeserta() {
        // fields apa saja yang akan disimpan
        final String nama = edit_tambah_nama_pst.getText().toString().trim();
        final String email = edit_tambah_email_pst.getText().toString().trim();
        final String hp = edit_tambah_hp_pst.getText().toString().trim();
        final String instansi = edit_tambah_instansi_pst.getText().toString().trim();

        class SimpanDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahPesertaActivity.this, "Menyimpan Data", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_NAMA, nama);
                params.put(Konfigurasi.KEY_PST_EMAIL, email);
                params.put(Konfigurasi.KEY_PST_HP, hp);
                params.put(Konfigurasi.KEY_PST_INSTANSI, instansi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_PESERTA, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahPesertaActivity.this, "pesan " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }

    private void clearText() {
        edit_tambah_nama_pst.setText("");
        edit_tambah_email_pst.setText("");
        edit_tambah_hp_pst.setText("");
        edit_tambah_instansi_pst.setText("");
        edit_tambah_nama_pst.requestFocus();
    }
}