package id.chairanitiaras.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HelpActivity extends AppCompatActivity {

    EditText edit_nama, edit_email, edit_hp, edit_problem;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        edit_nama = findViewById(R.id.editTextTextPersonName);
        edit_email = findViewById(R.id.editTextTextEmailAddress);
        edit_hp = findViewById(R.id.editTextPhone);
        edit_problem = findViewById(R.id.editTextTextPersonName3);
        btn_send = findViewById(R.id.button);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = null;
                Log.d("nama", String.valueOf(edit_nama.getText()));
                String nama = String.valueOf(edit_nama.getText());
                String email = String.valueOf(edit_email.getText());
                String phone = String.valueOf(edit_hp.getText());
                String problem = String.valueOf(edit_problem.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
                builder.setTitle("Insert Feedback");
                builder.setMessage("Are you sure want to send this feedback? " +
                        "\n Nama : " + nama +
                        "\n Email: " + email +
                        "\n Phone: " + phone +
                        "\n Problem : " + problem);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}