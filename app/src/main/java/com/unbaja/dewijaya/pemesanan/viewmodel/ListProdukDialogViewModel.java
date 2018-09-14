package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.database.entity.Produk;

import java.util.List;

/**
 * Created by sigit on 20/08/2018.
 */

public class ListProdukDialogViewModel extends AndroidViewModel {


    private PersediaanRepository mRepository;

    public ListProdukDialogViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }

    public LiveData<List<Produk>> getAllProduk() {
        return mRepository.getAllProduk();
    }
}
