package id.chairanitiaras.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import id.chairanitiaras.project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        // custom toolbar
        setSupportActionBar(binding.toolbar);

        // default fragment dibuka pertama kali
        getSupportActionBar().setTitle("Home");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new HomeFragment())
                .commit();
        binding.navView.setCheckedItem(R.id.nav_home);

        // membuka drawer
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar,
                R.string.open, R.string.close);

        // drawer back button
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        // sinkronisasi drawer
        toggle.syncState();

        // salah satu menu navigasi dipilih
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_instruktur:
                        fragment = new InstrukturFragment();
                        getSupportActionBar().setTitle("List Instruktur");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_materi:
                        fragment = new MateriFragment();
                        getSupportActionBar().setTitle("List Materi");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_peserta:
                        fragment = new PesertaFragment();
                        getSupportActionBar().setTitle("List Peserta");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_kelas:
                        fragment = new KelasFragment();
                        getSupportActionBar().setTitle("List Kelas");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_detail_kelas:
                        fragment = new DetailKelasFragment();
                        getSupportActionBar().setTitle("List Detail Kelas");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                }
                return true;
            }
        });
    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}