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

public class TambahDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_id_kls, edit_tambah_id_pst;
    private Button btn_tambah_detail_kls, btn_lihat_detail_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail_kelas);

        edit_tambah_id_kls = findViewById(R.id.edit_tambah_id_kls);
        edit_tambah_id_pst = findViewById(R.id.edit_tambah_id_pst);
        btn_tambah_detail_kls = findViewById(R.id.btn_tambah_detail_kelas);
        btn_lihat_detail_kls = findViewById(R.id.btn_lihat_detail_kelas);

        btn_lihat_detail_kls.setOnClickListener(this);
        btn_tambah_detail_kls.setOnClickListener(this);
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
        final String id_pst = edit_tambah_id_pst.getText().toString().trim();

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
                params.put("id_pst", id_pst);
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
        edit_tambah_id_pst.setText("");
        edit_tambah_id_kls.requestFocus();
    }
}