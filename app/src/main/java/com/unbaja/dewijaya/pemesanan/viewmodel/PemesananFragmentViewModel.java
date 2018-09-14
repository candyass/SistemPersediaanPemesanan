package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;

import java.util.List;

/**
 * Created by sigit on 22/08/2018.
 */

public class PemesananFragmentViewModel extends AndroidViewModel {

    private PersediaanRepository mRepository;

    public PemesananFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    public LiveData<String> getNamaOutlet(long idOutlet) {
        return mRepository.getNamaOutlet(idOutlet);
    }

    public LiveData<List<QueryPemesanan>> getListPemesanan(long idOutlet) {
        return mRepository.getListPemesanan(idOutlet);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }
}
