package com.unbaja.dewijaya.pemesanan.view.dialog;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.database.entity.Produk;
import com.unbaja.dewijaya.pemesanan.model.ProdukEvent;
import com.unbaja.dewijaya.pemesanan.util.MyLogger;
import com.unbaja.dewijaya.pemesanan.viewmodel.ListProdukDialogViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by sigit on 17/08/2018.
 */

public class ListProdukDialog extends DialogFragment {

    public static final String S_DIALOG_TAG = "com.unbaja.dewijaya.dialog.tag.listproduk";



    private RecyclerView mRecyclerView;
    private Button mBatalButton;
    private ListProdukDialogViewModel mViewModel;






    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_list_produk, null);
        mRecyclerView = view.findViewById(R.id.list_dialog_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel = ViewModelProviders.of(this).get(ListProdukDialogViewModel.class);
        mViewModel.getAllProduk().observe(this, produks -> {
            if(produks != null) {
                if(produks.size() > 0) {
                    MyLogger.logPesan("Setup Adapter");
                    mRecyclerView.setAdapter(new MyAdapter(produks));
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("Daftar Produk");
        builder.setMessage("Pilih Produk Yang Ingin Dipesan");
        builder.setPositiveButton("Batal", (dialog, which) -> {

        });
        return builder.create();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mGambar;
        private TextView mTextDeskripsi;
        private TextView mTextNamaProduk;
        private TextView mTextHarga;
        private Produk mProduk;

        public MyHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.list_produk_gambar);
            mTextNamaProduk = itemView.findViewById(R.id.list_produk_text_nama);
            mTextDeskripsi = itemView.findViewById(R.id.list_produk_text_deskripsi);
            mTextHarga = itemView.findViewById(R.id.list_produk_text_harga);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Produk produk) {
            mProduk = produk;
            mGambar.setImageResource(mProduk.getGambar());
            mTextDeskripsi.setText(mProduk.getSpesifikasi());
            mTextNamaProduk.setText(mProduk.getNamaProduk());
            mTextHarga.setText(String.valueOf(mProduk.getHarga()));
        }

        @Override
        public void onClick(View v) {
            ProdukEvent event = new ProdukEvent(mProduk.getIdProduk(), mProduk.getNamaProduk(), mProduk.getHarga());
            EventBus.getDefault().post(event);
            dismiss();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        private List<Produk> mList;

        public MyAdapter(List<Produk> list) {
            mList = list;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.list_produk, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.bindItem(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
