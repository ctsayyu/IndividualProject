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

public class TambahInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_tambah_nama, edit_tambah_email, edit_tambah_hp;
    private Button btn_tambah_instruktur, btn_lihat_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_instruktur);

        edit_tambah_nama = findViewById(R.id.edit_tambah_nama);
        edit_tambah_email = findViewById(R.id.edit_tambah_email);
        edit_tambah_hp = findViewById(R.id.edit_tambah_hp);
        btn_tambah_instruktur = findViewById(R.id.btn_tambah_instruktur);
        btn_lihat_instruktur = findViewById(R.id.btn_lihat_instruktur);

        btn_lihat_instruktur.setOnClickListener(this);
        btn_tambah_instruktur.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == btn_lihat_instruktur) {
            startActivity(new Intent(TambahInstrukturActivity.this, MainActivity.class));
        } else if (view == btn_tambah_instruktur) {
            simpanDataInstruktur();
        }
    }

    private void simpanDataInstruktur() {
        // fields apa saja yang akan disimpan
        final String nama = edit_tambah_nama.getText().toString().trim();
        final String email = edit_tambah_email.getText().toString().trim();
        final String hp = edit_tambah_hp.getText().toString().trim();

        class SimpanDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahInstrukturActivity.this, "Menyimpan Data", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_NAMA, nama);
                params.put(Konfigurasi.KEY_INS_EMAIL, email);
                params.put(Konfigurasi.KEY_INS_HP, hp);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_INSTRUKTUR, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahInstrukturActivity.this, "pesan " + message,
                        Toast.LENGTH_SHORT).show();
                clearText();
                Intent myIntent = new Intent(TambahInstrukturActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "instruktur");
                startActivity(myIntent);
            }
        }
        SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
        simpanDataInstruktur.execute();
    }

    private void clearText() {
        edit_tambah_nama.setText("");
        edit_tambah_email.setText("");
        edit_tambah_hp.setText("");
        edit_tambah_nama.requestFocus();
    }
}