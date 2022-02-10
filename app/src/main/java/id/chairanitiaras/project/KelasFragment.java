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

public class KelasFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String JSON_STRING;
    ListView list_view_kelas;
    FloatingActionButton btn_tambah_kelas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_kelas, container, false);
        list_view_kelas = view.findViewById(R.id.list_view_kelas);
        btn_tambah_kelas = view.findViewById(R.id.btn_tambah_kelas);

        list_view_kelas.setOnItemClickListener(this);

        btn_tambah_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TambahKelasActivity.class));
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
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_KELAS);
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
                String id_kls = object.getString(Konfigurasi.TAG_JSON_KLS_ID);
                String tgl_mulai_kls = object.getString(Konfigurasi.TAG_JSON_KLS_TGL_MULAI);
                String tgl_akhir_kls = object.getString(Konfigurasi.TAG_JSON_KLS_TGL_SELESAI);

                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(Konfigurasi.TAG_JSON_KLS_ID, id_kls);
                kelas.put(Konfigurasi.TAG_JSON_KLS_TGL_MULAI, tgl_mulai_kls);
                kelas.put(Konfigurasi.TAG_JSON_KLS_TGL_SELESAI, tgl_akhir_kls);

                // ubah format json menjadi array list
                list.add(kelas);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        ListAdapter listAdapter =new SimpleAdapter(getActivity(),list, R.layout.activity_list_item_kelas,
                new String[]{Konfigurasi.TAG_JSON_KLS_ID, Konfigurasi.TAG_JSON_KLS_TGL_MULAI, Konfigurasi.TAG_JSON_KLS_TGL_SELESAI},
                new int[]{R.id.txt_id_kelas, R.id.txt_tgl_mulai, R.id.txt_tgl_selesai});

        list_view_kelas.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent myIntent = new Intent(getActivity(), DetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String klsId = map.get(Konfigurasi.TAG_JSON_KLS_ID).toString();
        myIntent.putExtra(Konfigurasi.KLS_ID, klsId);
        startActivity(myIntent);
    }

}