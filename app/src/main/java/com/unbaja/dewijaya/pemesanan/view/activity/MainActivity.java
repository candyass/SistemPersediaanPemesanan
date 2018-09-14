package com.unbaja.dewijaya.pemesanan.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.view.fragment.PemesananFragment;
import com.unbaja.dewijaya.pemesanan.view.fragment.ProfilFragment;

public class MainActivity extends AppCompatActivity {


    private static final String EXTRA_ID = "com.unbaja.dewijaya.extra.id";

    private long mIdOutlet;

    public static Intent newIntent(Context context, long idOutlet) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_ID, idOutlet);
        return intent;
    }

    private BottomNavigationView mNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profil:
                    if(mNavigationView.getSelectedItemId() != R.id.navigation_profil) {
                        changeFragment(ProfilFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_pemesanan:
                    if(mNavigationView.getSelectedItemId() != R.id.navigation_pemesanan) {
                        changeFragment(PemesananFragment.newInstance());
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, ProfilFragment.newInstance()).commit();
        mNavigationView.setSelectedItemId(R.id.navigation_profil);

        mIdOutlet = getIntent().getLongExtra(EXTRA_ID, -1);
    }


    public long getIdOutlet() {
        return mIdOutlet;
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_container, fragment).commit();
    }


}
