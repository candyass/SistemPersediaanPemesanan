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

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.QueryProduk;
import com.unbaja.dewijaya.pemesanan.viewmodel.DetailPemesananActivityViewModel;

import java.text.NumberFormat;
import java.util.List;

public class DetailPemesananActivity extends AppCompatActivity {


    private static final String EXTRA_FAKTUR = "com.unbaja.dewijaya.pemesanan.extra.faktur";
    private static final String EXTRA_NAMA = "com.unbaja.dewijaya.pemesanan.extra.nama";
    private static final String EXTRA_TANGGAL = "com.unbaja.dewijaya.pemesanan.extra.tanggal";
    private static final String EXTRA_TOTAL = "com.unbaja.dewijaya.pemesanan.extra.total";
    private static NumberFormat sNumberFormat = NumberFormat.getCurrencyInstance();

    private TextView mTextNama;
    private TextView mTextTanggal;
    private TextView mTextTotal;
    private RecyclerView mRecyclerView;
    private long idFaktur;
    private DetailPemesananActivityViewModel mViewModel;


    public static Intent newIntent(Context context,long idFaktur, String nama, String tanggal, String total) {
        Intent intent = new Intent(context, DetailPemesananActivity.class);
        intent.putExtra(EXTRA_FAKTUR, idFaktur);
        intent.putExtra(EXTRA_NAMA, nama);
        intent.putExtra(EXTRA_TANGGAL, tanggal);
        intent.putExtra(EXTRA_TOTAL, total);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);

        mTextNama = findViewById(R.id.detail_pemesanan_text_nama);
        mTextTanggal = findViewById(R.id.detail_pemesanan_text_tanggal);
        mTextTotal = findViewById(R.id.detail_pemesanan_text_total);
        mRecyclerView = findViewById(R.id.detail_pemesanan_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        idFaktur = getIntent().getLongExtra(EXTRA_FAKTUR, -1);
        String nama = getIntent().getStringExtra(EXTRA_NAMA);
        String tanggal = getIntent().getStringExtra(EXTRA_TANGGAL);
        String total = getIntent().getStringExtra(EXTRA_TOTAL);

        mTextNama.setText(nama);
        mTextTanggal.setText(tanggal);
        mTextTotal.setText(total);
        mViewModel = ViewModelProviders.of(this).get(DetailPemesananActivityViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getAllProduk(idFaktur).observe(this, list -> {
            if(list != null) {
                if(list.size() > 0) {
                    mRecyclerView.setAdapter(new PemesananProdukAdapter(list));
                }
            }
        });

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
