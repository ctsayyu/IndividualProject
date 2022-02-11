package id.chairanitiaras.project;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchStudentDataFragment extends Fragment {
    private EditText edit_search_id_pst;
    private Button btn_search_pst_id;
    private View view;
    private ListView list_item_search_pst;
    private String JSON_STRING;
    private ProgressDialog loading;


    public SearchStudentDataFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_student_data, container, false);

        edit_search_id_pst = view.findViewById(R.id.edit_search_id_pst);

        list_item_search_pst = view.findViewById(R.id.list_view_search_kls);


//        listView.setVisibility(View.GONE);

        btn_search_pst_id = view.findViewById(R.id.btn_search_pst);
        btn_search_pst_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = edit_search_id_pst.getText().toString().trim();

                getData(val);
            }
        });

        return view;
    }


    private void getData(String val) {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Ambil Data ", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_PST,val);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);

                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                displaySearchResult(JSON_STRING);
//                displaySearchResult();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displaySearchResult(String json) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Log.d("json",json);

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id_pst = object.getString("p.id_pst");
                String nama_pst = object.getString("p.nama_pst");
                String id_detail_kls = object.getString("dk.id_detail_kls");
                String id_kls = object.getString("dk.id_kls");
                String nama_mat = object.getString("m.nama_mat");

                HashMap<String, String> res = new HashMap<>();
                res.put("p.id_pst", id_pst);
                res.put("p.nama_pst", nama_pst);
                res.put("dk.id_detail_kls", id_detail_kls);
                res.put("dk.id_kls", id_kls);
                res.put("m.nama_mat", nama_mat);


                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.list_item_search_student,
                new String[]{"p.id_pst", "p.nama_pst", "dk.id_detail_kls", "dk.id_kls", "m.nama_mat"},
                new int[]{R.id.search_id_pst, R.id.search_nama_pst, R.id.search_email_pst, R.id.search_phone_pst, R.id.search_instansi_pst}

        );
        list_item_search_pst.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);

    }


    private void search_data(String val) {
        Toast.makeText(getContext(), val, Toast.LENGTH_SHORT).show();
    }
}