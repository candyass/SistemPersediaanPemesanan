package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;
import com.unbaja.dewijaya.pemesanan.util.MyLogger;

import java.util.concurrent.ExecutionException;

/**
 * Created by sigit on 22/08/2018.
 */

public class LoginActivityViewModel extends AndroidViewModel {


    private PersediaanRepository mRepository;
    private Bitmap mBitmap;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public Bitmap getImageBitmap() {
        return mBitmap;
    }

    public long getLoginOutletId(String email, String katasandi)  {
        MyLogger.logPesan("getLoginOutlet");
        long result = -1;
        try {
            result =  mRepository.getLoginOutletId(email, katasandi);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public long insertOutlet(Outlet outlet)  {
        outlet.setFoto(mBitmap);
        MyLogger.logPesan("insertOutlet");
        long result = -1;
        try {
            result =  mRepository.insertOutlet(outlet);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
