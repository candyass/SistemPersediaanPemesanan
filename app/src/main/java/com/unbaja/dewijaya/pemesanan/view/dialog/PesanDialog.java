package com.unbaja.dewijaya.pemesanan.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.unbaja.dewijaya.pemesanan.R;

/**
 * Created by sigit on 27/08/2018.
 */

public class PesanDialog extends DialogFragment {

    public static final String DIALOG_TAG = "com.unbaja.dewijaya.pemesanan.tag.pesandialog";

    private String judul;
    private String pesan;

    public static DialogFragment newInstance(String judul, String pesan) {
        PesanDialog dialog = new PesanDialog();
        dialog.setJudul(judul);
        dialog.setPesan(pesan);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(judul).setMessage(pesan).setPositiveButton("OK", (dialog, which) -> {
            dismiss();
        });
        builder.setIcon(R.drawable.ic_dewi_jaya);
        return builder.create();
    }

    private void setPesan(String pesan) {
        this.pesan = pesan;
    }

    private void setJudul(String judul) {
        this.judul = judul;
    }
}
