package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;

/**
 * Created by sigit on 27/08/2018.
 */

public class ProfilFragmentViewModel extends AndroidViewModel {

    private PersediaanRepository mRepository;

    public ProfilFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    public LiveData<Outlet> getOutlet(long idOutlet) {
        return mRepository.getOutlet(idOutlet);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }
}

