package id.chairanitiaras.project;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment implements View.OnClickListener{

    Spinner spinner;
    //    EditText home_edit_name, home_edit_email;
    Button button_guest_daftar;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RadioGroup radioGender;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        button_guest_daftar = view.findViewById(R.id.button_guest_daftar);

        EditText layout_nama = view.findViewById(R.id.edit_guest_name);
        EditText layout_email= view.findViewById(R.id.edit_guest_email);
        EditText layout_phone = view.findViewById(R.id.edit_guest_phone);
        RadioButton perempuan = (RadioButton) view.findViewById(R.id.radiobutton_perempuan);
        RadioButton laki = (RadioButton) view.findViewById(R.id.radiobutton_laki);


        radioGroup = view.findViewById(R.id.radioGroup);
        button_guest_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = null;
                Log.d("nama", String.valueOf(layout_nama.getText()));
                String nama = String.valueOf(layout_nama.getText());
                String email = String.valueOf(layout_email.getText());
                String phone = String.valueOf(layout_phone.getText());

                if(perempuan.isChecked()){
                    res = "Perempuan";
                }
                else if(laki.isChecked()){
                    res = "Laki-Laki";
                }

                Log.d("selected_text",res);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Insert Data");
                builder.setMessage("Are you sure want to insert this data? " +
                        "\n Nama : " + nama +
                        "\n Email: " + email +
                        "\n Phone: " + phone +
                        "\n Jenis Kelamin : " + res);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel",null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == button_guest_daftar){
//            Log.d("email", String.valueOf(layout_nama));
        }
    }
}