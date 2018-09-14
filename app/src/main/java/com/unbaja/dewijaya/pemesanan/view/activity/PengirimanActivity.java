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
import android.widget.ViewSwitcher;

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;
import com.unbaja.dewijaya.pemesanan.view.fragment.PemesananFragment;
import com.unbaja.dewijaya.pemesanan.viewmodel.PengirimanActivityViewModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PengirimanActivity extends AppCompatActivity {


    private DateFormat sDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    private NumberFormat sNumberFormat = NumberFormat.getCurrencyInstance();

    private ViewSwitcher mSwitcher;
    private RecyclerView mRecycerView;
    private TextView mTextLabel;
    private PengirimanActivityViewModel mViewModel;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PengirimanActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_stub);

        mSwitcher = findViewById(R.id.list_view_switcher);
        mRecycerView = findViewById(R.id.list_view_recyclerView);
        mTextLabel = findViewById(R.id.list_view_text);

        mRecycerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mTextLabel.setText("Belum Ada Pemesanan Tersedia");


        mViewModel = ViewModelProviders.of(this).get(PengirimanActivityViewModel.class);
        mViewModel.getListPemesanan().observe(this, query -> {
            if(query != null) {
                if(query.size() > 0) {
                    tampilkanList(true);
                    mRecycerView.setAdapter(new QueryPemesananAdapter(query));
                }
            }
        });
    }

    private void tampilkanList(boolean value) {
        if(value) {
            if (mSwitcher.getNextView().getId() == R.id.list_view_recyclerView) {
                mSwitcher.showNext();
            }
        }
    }



    class QueryPemesananHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView mFoto;
        private TextView mTextNamaOutlet;
        private TextView mTextTanggal;
        private TextView mTextJumlah;
        private TextView mTextTotal;
        private QueryPemesanan q;

        public QueryPemesananHolder(View itemView) {
            super(itemView);
            mFoto = itemView.findViewById(R.id.list_faktur_foto);
            mTextNamaOutlet = itemView.findViewById(R.id.list_faktur_text_nama_outlet);
            mTextTanggal = itemView.findViewById(R.id.list_faktur_text_tanggal);
            mTextJumlah = itemView.findViewById(R.id.list_faktur_text_jumlah);
            mTextTotal = itemView.findViewById(R.id.list_faktur_text_total);
            itemView.setOnClickListener(this);
        }

        public void bindItem(QueryPemesanan query) {
            q = query;
            mFoto.setImageBitmap(q.getFoto());
            mTextNamaOutlet.setText(q.getNamaOutlet());
            mTextJumlah.setText(String.valueOf(q.getTotalPemesanan()));
            mTextTanggal.setText(sDateFormat.format(q.getTanggalPemesanan()));
            mTextTotal.setText(sNumberFormat.format(q.getJumlahPembayaran()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailPengirimanActivity.newIntent(getBaseContext(),q.getIdFaktur(),q.getPatokan(),
                    mTextTanggal.getText().toString(),q.getNamaOutlet(),q.getLatitude(),q.getLongitude());
            startActivity(intent);
        }
    }

    class QueryPemesananAdapter extends RecyclerView.Adapter<QueryPemesananHolder> {

        private List<QueryPemesanan> mList;

        public QueryPemesananAdapter(List<QueryPemesanan> list) {
            mList = list;
        }

        @Override
        public QueryPemesananHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
            View v = inflater.inflate(R.layout.list_faktur, parent, false);
            return new QueryPemesananHolder(v);
        }

        @Override
        public void onBindViewHolder(QueryPemesananHolder holder, int position) {
            holder.bindItem(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
