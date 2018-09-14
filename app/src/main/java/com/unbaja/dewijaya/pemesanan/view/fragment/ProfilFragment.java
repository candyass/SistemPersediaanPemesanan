package com.unbaja.dewijaya.pemesanan.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.view.activity.MainActivity;
import com.unbaja.dewijaya.pemesanan.viewmodel.ProfilFragmentViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 22/08/2018.
 */

public class ProfilFragment extends Fragment implements OnMapReadyCallback {


    private CircleImageView mFoto;
    private TextView mTextNamaPemilik;
    private TextView mTextNamaOutlet;
    private TextView mTextEmail;
    private TextView mTextNoTelepon;
    private TextView mTextAlamat;
    private TextView mTextPatokan;
    private SupportMapFragment mMapFragment;

    private ProfilFragmentViewModel mViewModel;
    private long mIdOutlet;
    private GoogleMap mGoogleMap;

    public static Fragment newInstance() {
        Fragment fragment = new ProfilFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        mIdOutlet = activity.getIdOutlet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        initViews(view);
        mViewModel = ViewModelProviders.of(this).get(ProfilFragmentViewModel.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mIdOutlet != -1) {
            mViewModel.getOutlet(mIdOutlet).observe(this, outlet -> {
                mFoto.setImageBitmap(outlet.getFoto());
                mTextNamaPemilik.setText(outlet.getNamaPemilik());
                mTextNamaOutlet.setText(outlet.getNamaOutlet());
                mTextEmail.setText(outlet.getEmail());
                mTextNoTelepon.setText(outlet.getNoTelepon());
                mTextAlamat.setText(outlet.getNamaTempat());
                mTextPatokan.setText(outlet.getPatokan());
                if(outlet.getLatitude() != -1) {
                    LatLng latLng = new LatLng(outlet.getLatitude(), outlet.getLongitude());
                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(latLng);
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                    mGoogleMap.addMarker(new MarkerOptions().position(latLng));
                    mGoogleMap.moveCamera(center);
                    mGoogleMap.animateCamera(zoom);
                }
            });
        }
    }

    private void initViews(View view) {
        mFoto = view.findViewById(R.id.fragment_profil_foto);
        mTextNamaPemilik = view.findViewById(R.id.fragment_profil_text_nama_pemilik);
        mTextNamaOutlet = view.findViewById(R.id.fragment_profil_text_nama_outlet);
        mTextEmail = view.findViewById(R.id.fragment_profil_text_email);
        mTextNoTelepon = view.findViewById(R.id.fragment_profil_text_no_telepon);
        mTextAlamat = view.findViewById(R.id.fragment_profil_text_alamat);
        mTextPatokan = view.findViewById(R.id.fragment_profil_text_patokan);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_profil_map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
