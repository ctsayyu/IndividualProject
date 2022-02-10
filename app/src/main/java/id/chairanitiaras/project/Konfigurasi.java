
package id.chairanitiaras.project;

public class Konfigurasi
{
    // url dimana web API berada
    public static final String URL_GET_ALL_INSTRUKTUR = "http://192.168.100.132/inixtraining/instruktur/tr_datas_instruktur.php";
    public static final String URL_GET_DETAIL_INSTRUKTUR = "http://192.168.100.132/inixtraining/instruktur/tr_detail_instruktur.php?id_ins=";
    public static final String URL_ADD_INSTRUKTUR = "http://192.168.100.132/inixtraining/instruktur/tr_add_instruktur.php";
    public static final String URL_UPDATE_INSTRUKTUR  = "http://192.168.100.132/inixtraining/instruktur/tr_update_instruktur.php";
    public static final String URL_DELETE_INSTRUKTUR  = "http://192.168.100.132/inixtraining/instruktur/tr_delete_instruktur.php?id_ins=";

    // key and value JSON yang muncul di browser
    public static final String KEY_INS_ID = "id_ins";
    public static final String KEY_INS_NAMA = "nama_ins";
    public static final String KEY_INS_EMAIL = "email_ins";
    public static final String KEY_INS_HP = "hp_ins";


    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID_INS = "id_ins";
    public static final String TAG_JSON_NAMA_INS = "nama_ins";
    public static final String TAG_JSON_EMAIL_INS = "email_ins";
    public static final String TAG_JSON_HP_INS = "hp_ins";


    // variabel ID pegawai
    public static final String INS_ID = "id_ins";

    // url dimana web API berada
    public static final String URL_GET_ALL_PESERTA = "http://192.168.100.132/inixtraining/peserta/tr_datas_peserta.php";
    public static final String URL_GET_DETAIL_PESERTA = "http://192.168.100.132/inixtraining/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_PESERTA = "http://192.168.100.132/inixtraining/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_PESERTA = "http://192.168.100.132/inixtraining/peserta/tr_update_peserta.php";
    public static final String URL_DELETE_PESERTA = "http://192.168.100.132/inixtraining/peserta/tr_delete_peserta.php?id_pst=";

    // key and value JSON yang muncul di browser
    public static final String KEY_PST_ID = "id_pst";
    public static final String KEY_PST_NAMA = "nama_pst";
    public static final String KEY_PST_EMAIL = "email_pst";
    public static final String KEY_PST_HP = "hp_pst";
    public static final String KEY_PST_INSTANSI = "instansi_pst";

    // flag JSON
    public static final String TAG_JSON_ID_PST = "id_pst";
    public static final String TAG_JSON_NAMA_PST = "nama_pst";
    public static final String TAG_JSON_EMAIL_PST = "email_pst";
    public static final String TAG_JSON_HP_PST = "hp_pst";
    public static final String TAG_JSON_INSTANSI_PST = "instansi_pst";

    // variabel ID pegawai
    public static final String PST_ID = "id_pst";

    // url dimana web API berada
    public static final String URL_GET_ALL_MATERI = "http://192.168.100.132/inixtraining/materi/tr_datas_materi.php";
    public static final String URL_GET_DETAIL_MATERI = "http://192.168.100.132/inixtraining/materi/tr_detail_materi.php?id_mat=";
    public static final String URL_ADD_MATERI = "http://192.168.100.132/inixtraining/materi/tr_add_materi.php";
    public static final String URL_UPDATE_MATERI = "http://192.168.100.132/inixtraining/materi/tr_update_materi.php";
    public static final String URL_DELETE_MATERI = "http://192.168.100.132/inixtraining/materi/tr_delete_materi.php?id_mat=";

    // key and value JSON yang muncul di browser
    public static final String KEY_MAT_ID = "id_mat";
    public static final String KEY_MAT_NAMA = "nama_mat";

    // flag JSON
    public static final String TAG_JSON_ID_MAT = "id_mat";
    public static final String TAG_JSON_NAMA_MAT = "nama_mat";

    // variabel ID pegawai
    public static final String MAT_ID = "id_mat";

    // url dimana web API berada
    public static final String URL_GET_ALL_KELAS = "http://192.168.100.132/inixtraining/kelas/tr_datas_kelas.php";
    public static final String URL_GET_DETAIL_KELAS = "http://192.168.100.132/inixtraining/kelas/tr_detail_kelas.php?id_kls=";
    public static final String URL_ADD_KELAS = "http://192.168.100.132/inixtraining/kelas/tr_add_kelas.php";
    public static final String URL_UPDATE_KELAS = "http://192.168.100.132/inixtraining/kelas/tr_update_kelas.php";
    public static final String URL_DELETE_KELAS = "http://192.168.100.132/inixtraining/kelas/tr_delete_kelas.php?id_kls=";

    // key and value JSON yang muncul di browser
    public static final String KEY_KLS_ID = "id_kls";
    public static final String KEY_KLS_TGL_MULAI = "tgl_mulai_kls";
    public static final String KEY_KLS_TGL_SELESAI = "tgl_akhir_kls";
    public static final String KEY_KLS_INS = "id_ins";
    public static final String KEY_KLS_MAT = "id_mat";


    // flag JSON
    public static final String TAG_JSON_KLS_ID = "id_kls";
    public static final String TAG_JSON_KLS_TGL_MULAI = "tgl_mulai_kls";
    public static final String TAG_JSON_KLS_TGL_SELESAI = "tgl_akhir_kls";
    public static final String TAG_JSON_KLS_INS = "id_ins";
    public static final String TAG_JSON_KLS_MAT = "id_mat";

    // variabel ID pegawai
    public static final String KLS_ID = "id_kls";

    // url dimana web API berada
    public static final String URL_GET_ALL_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_datas_detail_kelas.php";
    public static final String URL_GET_DETAIL_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_detail_detail_kelas.php?id_kls=";
    public static final String URL_GET_DETAIL_DETAIL_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_detail_detail_detail_kelas.php?id_detail_kls=";
    public static final String URL_ADD_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_add_detail_kelas.php";
    public static final String URL_UPDATE_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_update_detail_kelas.php";
    public static final String URL_DELETE_DETAIL_KELAS = "http://192.168.100.132/inixtraining/detail_kelas/tr_delete_detail_kelas.php?id_detail_kls=";

    // key and value JSON yang muncul di browser
    public static final String KEY_DT_KLS_ID_KLS = "id_kls";
    public static final String KEY_DT_KLS_JUM = "jum_pst";



    // flag JSON
    public static final String TAG_JSON_DT_KLS_ID_KLS = "id_kls";
    public static final String TAG_JSON_DT_KLS_JUM_PST = "jum_pst";
    public static final String TAG_JSON_DT_KLS_ID_DETAIL_KLS = "id_detail_kls";
    public static final String TAG_JSON_DT_KLS_NAMA_PST = "nama_pst";

    // variabel ID pegawai
    public static final String DT_KLS_ID = "id_kls";;


}