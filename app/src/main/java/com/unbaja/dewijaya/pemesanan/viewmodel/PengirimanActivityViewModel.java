package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;

import java.util.List;

/**
 * Created by sigit on 02/09/2018.
 */

public class PengirimanActivityViewModel extends AndroidViewModel {

    private PersediaanRepository mRepository;

    public PengirimanActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }

    public LiveData<List<QueryPemesanan>> getListPemesanan() {
        return mRepository.getListPemesanan();
    }
}
