package id.chairanitiaras.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchStudentDataFragment extends Fragment {
    private String JSON_STRING;
    ListView list_view_search_pst;
    String id_pst;
    Button btn_search_pst;
    TextView search_id_pst, search_nama_pst, search_email_pst, search_hp_pst, search_instansi_pst;
    EditText edit_search_id_pst;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search_student_data, container, false);
        list_view_search_pst = view.findViewById(R.id.list_view_search_pst);
        btn_search_pst = view.findViewById(R.id.btn_search_pst);
        edit_search_id_pst = view.findViewById(R.id.edit_search_id_pst);
        search_id_pst = view.findViewById(R.id.search_id_pst);
        search_nama_pst = view.findViewById(R.id.search_nama_pst);
        search_email_pst = view.findViewById(R.id.search_email_pst);
        search_hp_pst = view.findViewById(R.id.search_phone_pst);
        search_instansi_pst = view.findViewById(R.id.search_instansi_pst);

        getJSON();

        btn_search_pst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;

    }

    private void getJSON()
    {
        class GetJSON extends AsyncTask<Void,Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading =ProgressDialog.show(getActivity(), "Mengambil Data",
                        "Harap menunggu...", false, false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PESERTA);
                return hasil;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("Data JSON: ", JSON_STRING);

                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
                String search_id_pst = object.getString(Konfigurasi.TAG_JSON_ID_PST);
                String search_nama_pst = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
                String search_email_pst = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
                String search_hp_pst = object.getString(Konfigurasi.TAG_JSON_HP_PST);
                String search_instansi_pst = object.getString(Konfigurasi.TAG_JSON_INSTANSI_PST);

                HashMap<String, String> searchpst = new HashMap<>();
                searchpst.put(Konfigurasi.TAG_JSON_ID_PST, search_id_pst);
                searchpst.put(Konfigurasi.TAG_JSON_NAMA_PST, search_nama_pst);
                searchpst.put(Konfigurasi.TAG_JSON_EMAIL_PST, search_email_pst);
                searchpst.put(Konfigurasi.TAG_JSON_HP_PST, search_hp_pst);
                searchpst.put(Konfigurasi.TAG_JSON_INSTANSI_PST, search_instansi_pst);

                // ubah format json menjadi array list
                list.add(searchpst);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        ListAdapter listAdapter =new SimpleAdapter(getActivity(),list, R.layout.list_item_search_student,
                new String[]{Konfigurasi.TAG_JSON_ID_PST, Konfigurasi.TAG_JSON_NAMA_PST, Konfigurasi.TAG_JSON_EMAIL_PST, Konfigurasi.TAG_JSON_HP_PST, Konfigurasi.TAG_JSON_INSTANSI_PST},
                new int[]{R.id.search_id_pst, R.id.search_nama_pst, R.id.search_email_pst, R.id.search_phone_pst, R.id.search_instansi_pst});

        list_view_search_pst.setAdapter(listAdapter);
    }
}