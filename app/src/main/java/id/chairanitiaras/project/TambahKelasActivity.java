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

public class TambahKelasActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_tgl_mulai_kls, edit_tambah_tgl_akhir_kls, edit_tambah_id_ins_kls, edit_tambah_id_mat_kls;
    private Button btn_tambah_kelas, btn_lihat_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);

        edit_tambah_tgl_mulai_kls = findViewById(R.id.edit_tambah_tgl_mulai_kls);
        edit_tambah_tgl_akhir_kls= findViewById(R.id.edit_tambah_tgl_akhir_kls);
        edit_tambah_id_ins_kls = findViewById(R.id.edit_tambah_id_ins_kls);
        edit_tambah_id_mat_kls = findViewById(R.id.edit_tambah_id_mat_kls);
        btn_tambah_kelas = findViewById(R.id.btn_tambah_kelas);
        btn_lihat_kelas = findViewById(R.id.btn_lihat_kelas);

        btn_lihat_kelas.setOnClickListener(this);
        btn_tambah_kelas.setOnClickListener(this);
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
        final String id_ins_kls = edit_tambah_id_ins_kls.getText().toString().trim();
        final String id_mat_kls = edit_tambah_id_mat_kls.getText().toString().trim();

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
                params.put(Konfigurasi.KEY_KLS_INS, id_ins_kls);
                params.put(Konfigurasi.KEY_KLS_MAT, id_mat_kls);
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
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }

    private void clearText() {
        edit_tambah_tgl_mulai_kls.setText("");
        edit_tambah_tgl_akhir_kls.setText("");
        edit_tambah_id_ins_kls.setText("");
        edit_tambah_id_mat_kls.setText("");
        edit_tambah_tgl_mulai_kls.requestFocus();
    }
}