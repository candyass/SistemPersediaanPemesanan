package com.unbaja.dewijaya.pemesanan.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.QueryProduk;
import com.unbaja.dewijaya.pemesanan.viewmodel.DetailPemesananActivityViewModel;

import java.text.NumberFormat;
import java.util.List;

public class DetailPengirimanActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static NumberFormat sNumberFormat = NumberFormat.getCurrencyInstance();
    private static final String EXTRA_FAKTUR = "com.unbaja.dewijaya.pengiriman.extra.faktur";
    private static final String EXTRA_PATOKAN = "com.unbaja.dewijaya.pengiriman.extra.patokan";
    private static final String EXTRA_TANGGAL = "com.unbaja.dewijaya.pengiriman.extra.tanggal";
    private static final String EXTRA_LATITUDE = "com.unbaja.dewijaya.pengiriman.extra.latitude";
    private static final String EXTRA_LONGITUDE = "com.unbaja.dewijaya.pengiriman.extra.longitude";
    private static final String EXTRA_NAMA = "com.unbaja.dewijaya.pengiriman.extra.nama";

    private TextView mTextPatokan;
    private TextView mTextTanggal;
    private TextView mTextNama;
    private RecyclerView mRecyclerView;
    private SupportMapFragment mMap;
    private double latitude;
    private double longitude;
    private long fakturId;
    private DetailPemesananActivityViewModel mViewModel;


    public static Intent newIntent(Context context, long idFaktur, String patokan, String tanggal, String nama,
                                   double latitude, double longitude) {
        Intent intent = new Intent(context, DetailPengirimanActivity.class);
        intent.putExtra(EXTRA_FAKTUR, idFaktur);
        intent.putExtra(EXTRA_PATOKAN, patokan);
        intent.putExtra(EXTRA_TANGGAL, tanggal);
        intent.putExtra(EXTRA_NAMA, nama);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengiriman);

        mTextPatokan = findViewById(R.id.detail_pengiriman_text_patokan);
        mTextTanggal = findViewById(R.id.detail_pengiriman_text_tanggal);
        mTextNama = findViewById(R.id.detail_pengiriman_text_nama);
        mRecyclerView = findViewById(R.id.detail_pengiriman_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_pengiriman_map);

        fakturId = getIntent().getLongExtra(EXTRA_FAKTUR, -1);
        mTextNama.setText(getIntent().getStringExtra(EXTRA_NAMA));
        mTextTanggal.setText(getIntent().getStringExtra(EXTRA_TANGGAL));
        mTextPatokan.setText(getIntent().getStringExtra(EXTRA_PATOKAN));
        latitude = getIntent().getDoubleExtra(EXTRA_LATITUDE, -1);
        longitude = getIntent().getDoubleExtra(EXTRA_LONGITUDE, -1);
        mMap.getMapAsync(this);
        mViewModel = ViewModelProviders.of(this).get(DetailPemesananActivityViewModel.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getAllProduk(fakturId).observe(this, list -> {
            if(list != null) {
                if(list.size() > 0) {
                    mRecyclerView.setAdapter(new PemesananProdukAdapter(list));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(latitude != -1 | longitude != -1 ) {
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
    }



    class PemesananProdukHolder extends RecyclerView.ViewHolder {

        private TextView mTextNamaProduk;
        private TextView mTextHarga;
        private TextView mTextKuantitas;

        public PemesananProdukHolder(View itemView) {
            super(itemView);
            mTextNamaProduk = itemView.findViewById(R.id.list_transaksi_text_nama);
            mTextHarga = itemView.findViewById(R.id.list_transaksi_text_harga);
            mTextKuantitas = itemView.findViewById(R.id.list_transaksi_text_kuantitas);
        }

        public void bindItem(QueryProduk query) {
            mTextNamaProduk.setText(query.getNamaProduk());
            mTextHarga.setText(sNumberFormat.format(query.getHarga()));
            mTextKuantitas.setText(String.valueOf(query.getKuantitas()));
        }
    }


    class PemesananProdukAdapter extends RecyclerView.Adapter<PemesananProdukHolder> {

        private List<QueryProduk> list;


        public PemesananProdukAdapter(List<QueryProduk> list) {
            this.list = list;
        }

        @Override
        public PemesananProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            View view = inflater.inflate(R.layout.list_transaksi, parent, false);
            return new PemesananProdukHolder(view);
        }

        @Override
        public void onBindViewHolder(PemesananProdukHolder holder, int position) {
            holder.bindItem(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
