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

public class DetailKelasFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String JSON_STRING;
    ListView list_view_detail_kls;
    FloatingActionButton btn_tambah_detail_kls;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_detail_kelas, container, false);
        list_view_detail_kls = view.findViewById(R.id.list_view_detail_kelas);
        btn_tambah_detail_kls = view.findViewById(R.id.btn_tambah_detail_kelas);

        list_view_detail_kls.setOnItemClickListener(this);

        btn_tambah_detail_kls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TambahDetailKelasActivity.class));
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
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_DETAIL_KELAS);
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
                String detail_kls_id_kls = object.getString(Konfigurasi.TAG_JSON_DT_KLS_ID_KLS);
                String detail_kls_jml_pst = object.getString(Konfigurasi.TAG_JSON_DT_KLS_JUM_PST);

                HashMap<String, String> detail_kelas = new HashMap<>();
                detail_kelas.put(Konfigurasi.TAG_JSON_DT_KLS_ID_KLS, detail_kls_id_kls);
                detail_kelas.put(Konfigurasi.TAG_JSON_DT_KLS_JUM_PST, detail_kls_jml_pst);
                // ubah format json menjadi array list
                list.add(detail_kelas);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        ListAdapter listAdapter =new SimpleAdapter(getActivity(),list, R.layout.activity_list_item_detail_kelas,
                new String[]{Konfigurasi.TAG_JSON_DT_KLS_ID_KLS, Konfigurasi.TAG_JSON_DT_KLS_JUM_PST},
                new int[]{R.id.txt_detail_kls_id_kls, R.id.txt_detail_kls_jml_pst});

        list_view_detail_kls.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent myIntent = new Intent(getActivity(), DetailDetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String id_kls = map.get("id_kls").toString();
        myIntent.putExtra("id_kls", id_kls);
        startActivity(myIntent);
    }

}