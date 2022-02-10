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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class InstrukturFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String JSON_STRING;
    ListView list_view_ins;
    FloatingActionButton btn_tambah_instruktur;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_instruktur, container, false);
        list_view_ins = view.findViewById(R.id.list_view_ins);
        btn_tambah_instruktur = view.findViewById(R.id.btn_tambah_instruktur);

        list_view_ins.setOnItemClickListener(this);

        btn_tambah_instruktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TambahInstrukturActivity.class));
            }
        });

        getJSON();
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
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_INSTRUKTUR);
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
                String id_ins = object.getString(Konfigurasi.TAG_JSON_ID_INS);
                String name_ins = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);

                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put(Konfigurasi.TAG_JSON_ID_INS, id_ins);
                instruktur.put(Konfigurasi.TAG_JSON_NAMA_INS, name_ins);
                // ubah format json menjadi array list
                list.add(instruktur);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        ListAdapter listAdapter =new SimpleAdapter(getActivity(),list, R.layout.activity_list_item_ins,
                new String[]{Konfigurasi.TAG_JSON_ID_INS, Konfigurasi.TAG_JSON_NAMA_INS},
                new int[]{R.id.txt_id_ins, R.id.txt_nama_ins});

        list_view_ins.setAdapter(listAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent myIntent = new Intent(getActivity(), DetailInstrukturActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String insId = map.get(Konfigurasi.TAG_JSON_ID_INS).toString();
        myIntent.putExtra(Konfigurasi.INS_ID, insId);
        startActivity(myIntent);
    }
}