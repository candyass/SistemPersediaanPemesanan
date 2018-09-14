package com.unbaja.dewijaya.pemesanan.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;
import com.unbaja.dewijaya.pemesanan.view.activity.DetailPemesananActivity;
import com.unbaja.dewijaya.pemesanan.view.activity.InputPemesananActivity;
import com.unbaja.dewijaya.pemesanan.view.activity.MainActivity;
import com.unbaja.dewijaya.pemesanan.viewmodel.PemesananFragmentViewModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 22/08/2018.
 */

public class PemesananFragment extends Fragment {

    private static final DateFormat sDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    private static final NumberFormat sNumberFormat = NumberFormat.getCurrencyInstance();

    private FloatingActionButton mFab;
    private ViewSwitcher mSwitcher;
    private RecyclerView mRecyclerView;
    private TextView mTextLabel;

    private long mIdOutlet;
    private String mNamaOutlet;
    private PemesananFragmentViewModel mViewModel;

    public static Fragment newInstance() {
        Fragment fragment = new PemesananFragment();
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
       View view = inflater.inflate(R.layout.fragment_pemesanan, container, false);
       mViewModel = ViewModelProviders.of(this).get(PemesananFragmentViewModel.class);

       mFab = view.findViewById(R.id.fragment_pemesanan_fab);
       mSwitcher = view.findViewById(R.id.list_view_switcher);
       mTextLabel = view.findViewById(R.id.list_view_text);
       mRecyclerView = view.findViewById(R.id.list_view_recyclerView);

       mTextLabel.setText("Belum Ada Pemesanan Tersedia");
       mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       mFab.setOnClickListener(v -> {
           Intent intent = InputPemesananActivity.newIntent(getContext(), mIdOutlet, mNamaOutlet);
           startActivity(intent);
       });

       return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getNamaOutlet(mIdOutlet).observe(this, s -> {
            mNamaOutlet = s;
        });

        mViewModel.getListPemesanan(mIdOutlet).observe(this, queries -> {
            if(queries != null) {
                if(queries.size() > 0) {

                    tampilkanList(true);
                    mRecyclerView.setAdapter(new QueryPemesananAdapter(queries));
                }
            }
        });
    }

    private void tampilkanList(boolean value) {
        if(value) {
            if(mSwitcher.getNextView().getId() == R.id.list_view_recyclerView) {
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
            Intent intent = DetailPemesananActivity.newIntent(getContext(), q.getIdFaktur(), mTextNamaOutlet.getText().toString(),
                    mTextTanggal.getText().toString(), mTextTotal.getText().toString());
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
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
