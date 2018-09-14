package com.unbaja.dewijaya.pemesanan.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.LoginEvent;
import com.unbaja.dewijaya.pemesanan.view.dialog.PesanDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sigit on 01/08/2018.
 */

public class DaftarLokasiFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_LOKASI = 101;

    private FloatingActionButton mFabLokasi;
    private TextView mTextLokasi;
    private EditText mTextPatokan;
    private Button mButtonDaftar;

    private SupportMapFragment mMapFragment;
    private Place mPlace;
    private GoogleMap mMap;
    private boolean isLocationSelected = false;


    public static Fragment newInstance() {
        Fragment fragment = new DaftarLokasiFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar_lokasi, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_LOKASI) {
                mPlace = PlacePicker.getPlace(getContext(), data);
                isLocationSelected = true;
                if (mPlace != null) {
                    mTextLokasi.setText(mPlace.getAddress());
                    updateLokasi(mPlace.getLatLng());
                }
            }
        }
    }

    private void initViews(View view) {
        mFabLokasi = view.findViewById(R.id.daftar_lokasi_fab_lokasi);
        mTextLokasi = view.findViewById(R.id.daftar_lokasi_text_lokasi);
        mTextPatokan = view.findViewById(R.id.daftar_lokasi_editText_patokan);
        mButtonDaftar = view.findViewById(R.id.daftar_lokasi_button_daftar);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.daftar_lokasi_map);
        mMapFragment.getMapAsync(this);


        mFabLokasi.setOnClickListener(v -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(getActivity()), REQUEST_LOKASI);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

        mButtonDaftar.setOnClickListener(v ->  {
            if(isLocationSelected) {
                String namaTempat = mTextLokasi.getText().toString();
                String patokan = mTextPatokan.getText().toString();
                double latitude = -1;
                double longtitude = -1;
                if(mPlace != null) {
                    latitude = mPlace.getLatLng().latitude;
                    longtitude = mPlace.getLatLng().longitude;
                }
                LoginEvent event = LoginEvent.createEventLogin();
                event.setNamaTempat(namaTempat);
                event.setPatokan(patokan);
                event.setLatitude(latitude);
                event.setLongitude(longtitude);
                EventBus.getDefault().post(event);
            }else {
                DialogFragment dialog = PesanDialog.newInstance("Daftar Gagal","Lokasi Belum Dipilih");
                dialog.show(getChildFragmentManager(), PesanDialog.DIALOG_TAG);
            }

        });
    }

    private void updateLokasi(LatLng latLng) {
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate center=
                CameraUpdateFactory.newLatLng( new LatLng(40.76793169992044,
                        -73.98180484771729));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
