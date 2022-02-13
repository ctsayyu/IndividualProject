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
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchStudentDataFragment extends Fragment {
    private EditText edit_search;
    private Button button_search;
    private View view;
    private ListView listView;
    private String JSON_STRING;
    private ProgressDialog loading;
    Spinner spinner;


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

        edit_search = view.findViewById(R.id.edit_search);

        listView = view.findViewById(R.id.listView);

        spinner = (Spinner) view.findViewById(R.id.spinner);


        button_search = view.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinner_val = spinner.getSelectedItem().toString();

                char char_val = spinner_val.charAt(0);
                String spin_val = Character.toString(char_val);
                String val = edit_search.getText().toString().trim();
                Toast.makeText(getContext(), spin_val, Toast.LENGTH_SHORT).show();

                if(spin_val.equals("1")){
                    getData_1(val);
                }
                else if(spin_val.equals("2")){
                    getData_2(val);
                }
                else if(spin_val.equals("3")){
                    getData_3(val);
                }

            }
        });


        return view;
    }

    private void getData_1(String val) {
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
                displaySearchResult_1(JSON_STRING);
//                displaySearchResult();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displaySearchResult_1(String json) {
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
                res.put("id_pst", id_pst);
                res.put("nama_pst", nama_pst);
                res.put("id_detail_kls", id_detail_kls);
                res.put("id_kls", id_kls);
                res.put("nama_mat", nama_mat);


                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.list_item_search_student,
                new String[]{"id_pst",
                        "nama_pst",
                        "id_detail_kls",
                        "id_kls",
                        "nama_mat"},
                new int[]{R.id.search_id_pst,
                        R.id.search_nama_pst,
                        R.id.search_email_pst,
                        R.id.search_phone_pst,
                        R.id.search_instansi_pst}

        );
        listView.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);

    }

    private void getData_2(String val) {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Ambil Data ", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_INS,val);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);

                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                displaySearchResult_2(JSON_STRING);
//                displaySearchResult();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displaySearchResult_2(String json) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Log.d("json",json);

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String nama_ins = object.getString("i.nama_ins");
                String id_kls = object.getString("k.id_kls");
                String nama_mat = object.getString("m.nama_mat");
                String tgl_mulai_kls = object.getString("k.tgl_mulai_kls");
                String jml_pst = object.getString("jml_pst");

                HashMap<String, String> res = new HashMap<>();
                res.put("i.nama_ins", nama_ins);
                res.put("k.id_kls", id_kls);
                res.put("m.nama_mat", nama_mat);
                res.put("k.tgl_mulai_kls", tgl_mulai_kls);
                res.put("jml_pst", jml_pst);


                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.list_item_search_ins,
                new String[]{"i.nama_ins",
                        "k.id_kls",
                        "m.nama_mat",
                        "k.tgl_mulai_kls",
                        "jml_pst"},

                new int[]{R.id.search_nama_ins,
                        R.id.search_ins_id_kls,
                        R.id.search_ins_nama_mat,
                        R.id.search_ins_tgl_mulai,
                        R.id.search_jml_pst}

        );
        listView.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);

    }


    private void getData_3(String val) {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Ambil Data ", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_KLS,val);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);

                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                displaySearchResult_3(JSON_STRING);
//                displaySearchResult();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displaySearchResult_3(String json) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Log.d("json",json);

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id_kls = object.getString("k.id_kls");
                String tgl_mulai_kls = object.getString("k.tgl_mulai_kls");
                String tgl_akhir_kls = object.getString("k.tgl_akhir_kls");
                String nama_mat = object.getString("m.nama_mat");
                String count_pst = object.getString("count_id_pst");

                HashMap<String, String> res = new HashMap<>();
                res.put("id_kls", id_kls);
                res.put("tgl_mulai_kls", tgl_mulai_kls);
                res.put("tgl_akhir_kls", tgl_akhir_kls);
                res.put("nama_mat", nama_mat);
                res.put("count_pst", count_pst);


                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.list_item_search_kls,
                new String[]{
                        "id_kls",
                        "tgl_mulai_kls",
                        "tgl_akhir_kls",
                        "nama_mat",
                        "count_pst"},

                new int[]{
                        R.id.search_id_kls,
                        R.id.search_tgl_mulai,
                        R.id.search_tgl_akhir,
                        R.id.search_nama_mat,
                        R.id.search_count_pst}

        );
        listView.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);

    }
    private void clearText() {
        edit_search.setText("");
        edit_search.requestFocus();
    }
}