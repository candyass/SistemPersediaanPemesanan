package com.unbaja.dewijaya.pemesanan.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.database.entity.Faktur;
import com.unbaja.dewijaya.pemesanan.database.entity.PemesananProduk;
import com.unbaja.dewijaya.pemesanan.model.ProdukEvent;
import com.unbaja.dewijaya.pemesanan.view.dialog.ListProdukDialog;
import com.unbaja.dewijaya.pemesanan.view.dialog.PesanDialog;
import com.unbaja.dewijaya.pemesanan.viewmodel.InputPemesananActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputPemesananActivity extends AppCompatActivity {

    private static final NumberFormat sNumberFormat = NumberFormat.getCurrencyInstance();
    private static final String EXTRA_ID = "com.unbaja.dewijaya.inputpemesananactivity.extra.id";
    private static final String EXTRA_NAMA = "com.unbaja.dewijaya.inputpemesananactivity.extra.nama";

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private Button mButtonPesan;
    private TextView mTextNamaOutlet;
    private TextView mTextTanggal;
    private TextView mTextTotal;

    private InputPemesananActivityViewModel mViewModel;
    private ProdukAdapter mAdapter;
    private long mIdOutlet;
    private String mNamaOutlet;
    private int mTotal = 0;


    public static Intent newIntent(Context context, long idOutlet, String namaOutlet) {
        Intent intent = new Intent(context, InputPemesananActivity.class);
        intent.putExtra(EXTRA_ID, idOutlet);
        intent.putExtra(EXTRA_NAMA, namaOutlet);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pemesanan);
        initViews();
        mViewModel = ViewModelProviders.of(this).get(InputPemesananActivityViewModel.class);

        mIdOutlet = getIntent().getLongExtra(EXTRA_ID, -1);
        mNamaOutlet = getIntent().getStringExtra(EXTRA_NAMA);
        mTextNamaOutlet.setText(mNamaOutlet);

        mFab.setOnClickListener(v -> {            ListProdukDialog dialog = new ListProdukDialog();
            dialog.show(getSupportFragmentManager(), ListProdukDialog.S_DIALOG_TAG);
        });

        mButtonPesan.setOnClickListener(v -> {
            if(mAdapter.isItemEmpty()) {
                tampilkanDialog("Pemesanan Gagal","Belum Ada Produk Dipilih");
                return;
            }
            if(pemesananKosong()) {
                tampilkanDialog("Pemesanan Gagal", "Produk Tidak Boleh Kosong");
                return;
            }
            Faktur faktur = new Faktur(mIdOutlet, new Date());
            List<PemesananProduk> list = new ArrayList<>();
            for(ProdukEvent e : mAdapter.list) {
                list.add(new PemesananProduk(e.getIdProduk(),e.getJumlahPemesanan()));
            }
            mViewModel.simpanPemesanan(faktur, list, mTotal);
            Toast.makeText(getBaseContext(), "Pemesanan Berhasil", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean pemesananKosong() {
        for(ProdukEvent e : mAdapter.list) {
            if (e.getJumlahPemesanan() == 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProdukEvent(ProdukEvent event) {
        if(event != null) {
            mAdapter.tambahItem(event);
        }
    }

    private void initViews() {
        mTextNamaOutlet = findViewById(R.id.input_pemesanan_text_nama_outlet);
        mTextTanggal = findViewById(R.id.input_pemesanan_text_tanggal);
        mTextTotal = findViewById(R.id.input_pemesanan_text_total);
        mRecyclerView = findViewById(R.id.input_pemesanan_recyclerView);
        mFab = findViewById(R.id.input_pemesanan_fab_tambah);
        mButtonPesan = findViewById(R.id.input_pemesanan_button_pesan);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mAdapter = new ProdukAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateTotal() {
        mTextTotal.setText(sNumberFormat.format(mTotal));
    }

    private void tambahItem(int nilai) {
        mTotal += nilai;
        updateTotal();
    }

    private void kurangItem(int nilai) {
        mTotal -= nilai;
        updateTotal();
    }

    private void tampilkanDialog(String judul, String pesan) {
        DialogFragment dialog = PesanDialog.newInstance(judul, pesan);
        dialog.show(getSupportFragmentManager(), PesanDialog.DIALOG_TAG);
    }



    class ProdukHolder extends RecyclerView.ViewHolder {

        private ImageView mButtonTambah;
        private ImageView mButtonKurang;
        private TextView mTextNama;
        private TextView mTextHarga;
        private TextView mTextJumlah;
        private int jumlahPemesanan;
        private ProdukEvent produkEvent;
        private boolean isKosong;

        public ProdukHolder(View itemView) {
            super(itemView);
            mButtonKurang = itemView.findViewById(R.id.list_pemesanan_button_kurang);
            mButtonTambah = itemView.findViewById(R.id.list_pemesanan_button_tambah);
            mTextNama = itemView.findViewById(R.id.list_pemesanan_text_nama_produk);
            mTextHarga = itemView.findViewById(R.id.list_pemesanan_text_harga);
            mTextJumlah = itemView.findViewById(R.id.list_pemesanan_text_jumlah);

            mButtonTambah.setOnClickListener(v -> {
                tambahPemesanan();
            });

            mButtonKurang.setOnClickListener(v -> {
                kurangPemesanan();
            });
        }

        public void bindItem(ProdukEvent event) {
            mTextNama.setText(event.getNamaProduk());
            mTextHarga.setText(sNumberFormat.format(event.getHarga()));
            mTextJumlah.setText(String.valueOf(event.getJumlahPemesanan()));
            jumlahPemesanan = event.getJumlahPemesanan();
            produkEvent = event;
        }

        private void tambahPemesanan() {
            jumlahPemesanan += 1;
            produkEvent.setJumlahPemesanan(jumlahPemesanan);
            updateJumlah();
            tambahItem(1 * produkEvent.getHarga());
            isKosong = false;
        }

        private void kurangPemesanan() {
            if(bisaDikurang()) {
                jumlahPemesanan -= 1;
                produkEvent.setJumlahPemesanan(jumlahPemesanan);
                updateJumlah();
                kurangItem(1 * produkEvent.getHarga());
            }else {
                isKosong = true;
            }
        }

        private boolean bisaDikurang() {
            return jumlahPemesanan > 0;
        }

        private void updateJumlah() {
            mTextJumlah.setText(String.valueOf(jumlahPemesanan));
        }
    }

    class ProdukAdapter extends RecyclerView.Adapter<ProdukHolder> {

        private List<ProdukEvent> list;


        public ProdukAdapter() {
            list = new ArrayList<>();
        }

        @Override
        public ProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            View view = inflater.inflate(R.layout.list_pemesanan, parent, false);
            return new ProdukHolder(view);
        }

        @Override
        public void onBindViewHolder(ProdukHolder holder, int position) {
            holder.bindItem(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void tambahItem(ProdukEvent event) {
            list.add(event);
            notifyItemInserted(list.indexOf(event));
        }

        public boolean isItemEmpty() {
            return mAdapter.getItemCount() <= 0;
        }

        public List<ProdukEvent> getList() {
            return list;
        }
    }
}
