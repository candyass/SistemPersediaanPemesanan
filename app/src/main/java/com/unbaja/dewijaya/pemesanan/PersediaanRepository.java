package com.unbaja.dewijaya.pemesanan;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.unbaja.dewijaya.pemesanan.database.LocalDatabase;
import com.unbaja.dewijaya.pemesanan.database.entity.Faktur;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;
import com.unbaja.dewijaya.pemesanan.database.entity.PemesananProduk;
import com.unbaja.dewijaya.pemesanan.database.entity.Produk;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;
import com.unbaja.dewijaya.pemesanan.model.QueryProduk;
import com.unbaja.dewijaya.pemesanan.util.DataSetup;
import com.unbaja.dewijaya.pemesanan.util.MyLogger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by sigit on 20/08/2018.
 */

public class PersediaanRepository {

    private LocalDatabase mDatabase;
    private MyPreferences myPreferences;
    private Executor mExecutor;

    private static PersediaanRepository singleton;

    public static PersediaanRepository getInstance(Context context) {
        if(singleton == null) {
            singleton = new PersediaanRepository(context);
        }
        return singleton;
    }


    private PersediaanRepository(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        mDatabase = app.getDatabase();
        myPreferences = MyPreferences.getInstance(context);
        mExecutor = Executors.newSingleThreadExecutor();
        boolean value = myPreferences.getBoolean();
        if(!value) {
            Runnable r = () -> {
                mDatabase.getProdukDAO().insertProduk(DataSetup.getAllProduk());
                MyLogger.logPesan("Inserting All Products");
            };
            mExecutor.execute(r);
            myPreferences.saveBoolean(true);
        }
    }


    public long getLoginOutletId(String email, String katasandi) throws ExecutionException, InterruptedException {
        Callable<Long> callable = () -> {
            return mDatabase.getOutletDAO().getLoginOutletId(email, katasandi);
        };
        FutureTask<Long> task = new FutureTask<Long>(callable);
        new Thread(task).start();
        return  task.get();
    }

    public long insertOutlet(Outlet outlet) throws ExecutionException, InterruptedException {
        Callable<Long> callable = () -> {
            return mDatabase.getOutletDAO().insertOutlet(outlet);
        };
        FutureTask<Long> task = new FutureTask<Long>(callable);
        new Thread(task).start();
        return task.get();
    }

    public LiveData<Outlet> getOutlet(long idOutlet) {
        return mDatabase.getOutletDAO().getOutlet(idOutlet);
    }

    public LiveData<String> getNamaOutlet(long idOutlet) {
        return mDatabase.getOutletDAO().getNamaOutlet(idOutlet);
    }

    public void simpanPemesanan(Faktur faktur, List<PemesananProduk> listPemesanan, int total) {
        Runnable r = () -> {
            mDatabase.getFakturDAO().simpanPemesanan(faktur, listPemesanan, total);
        };
        mExecutor.execute(r);
    }

    public LiveData<List<Produk>> getAllProduk() {
        return mDatabase.getProdukDAO().getAllProduk();
    }

    public LiveData<List<QueryPemesanan>> getListPemesanan(long outletId) {
        return mDatabase.getFakturDAO().getListPemesanan(outletId);
    }

    public LiveData<List<QueryPemesanan>> getListPemesanan() {
        return mDatabase.getFakturDAO().getListPemesanan();
    }

    public LiveData<List<QueryProduk>> getListPemesananProduk(long fakturId) {
        return mDatabase.getFakturDAO().getListPemesananProduk(fakturId);
    }



}
