package com.unbaja.dewijaya.pemesanan.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.LoginEvent;
import com.unbaja.dewijaya.pemesanan.util.MyLogger;
import com.unbaja.dewijaya.pemesanan.view.activity.LoginActivity;
import com.unbaja.dewijaya.pemesanan.view.dialog.PesanDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sigit on 01/08/2018.
 */

public class DaftarOutletFragment extends Fragment {

    private static final int REQUEST_FOTO = 100;

    private CircleImageView mFoto;
    private EditText mTextNamaPemilik;
    private EditText mTextNamaOutlet;
    private EditText mTextEmail;
    private EditText mTextKatasandi;
    private EditText mTextNoTelepon;
    private Button mButtonLanjutkan;

    private boolean isPhotoSelected;



    public static Fragment newInstance() {
        Fragment fragment = new DaftarOutletFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLogger.logPesan("DaftarOutletFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_daftar_outlet, container, false);
        initViews(view);

        mButtonLanjutkan.setOnClickListener(v -> {
            if(!isPhotoSelected) {
                tampilkanDialog("Daftar Gagal","Photo Belum Dipilih");
                return;
            }
            if (isTextKosong()) {
                tampilkanDialog("Daftar Gagal","Field Harus Diisi");
            }else {
                String namaPemilik = mTextNamaPemilik.getText().toString();
                String namaOutlet = mTextNamaOutlet.getText().toString();
                String email = mTextEmail.getText().toString();
                String katasandi = mTextKatasandi.getText().toString();
                String noTelepon = mTextNoTelepon.getText().toString();

                LoginEvent event = LoginEvent.createEventDaftarLokasi();
                event.setNamaPemilik(namaPemilik);
                event.setNamaOutlet(namaOutlet);
                event.setEmail(email);
                event.setKatasandi(katasandi);
                event.setNoTelepon(noTelepon);
                EventBus.getDefault().post(event);
            }
        });

        mFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            Intent choser = Intent.createChooser(intent, "Upload File Dari");
            startActivityForResult(choser, REQUEST_FOTO);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_FOTO) {
                Uri photoUri = data.getData();
                if(photoUri != null) {
                    LoginActivity activity = (LoginActivity) getActivity();
                    activity.setImageBitmap(photoUri);
                    activity.getmPicaso().load(photoUri).into(mFoto);
                    isPhotoSelected = true;
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.logPesan("DaftarOutletFragment onResume");
        LoginActivity loginActivity = (LoginActivity) getActivity();
        Bitmap bitmap = loginActivity.getImageBitmap();
        if(bitmap != null) {
            mFoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.logPesan("DaftarOutletFragment OnPause");
    }

    private void initViews(View view) {
        mFoto = view.findViewById(R.id.daftar_outlet_foto);
        mTextNamaPemilik = view.findViewById(R.id.daftar_outlet_text_nama_pemilik);
        mTextNamaOutlet = view.findViewById(R.id.daftar_outlet_text_nama_outlet);
        mTextEmail = view.findViewById(R.id.daftar_outlet_text_email);
        mTextNoTelepon = view.findViewById(R.id.daftar_outlet_text_no_telepon);
        mTextKatasandi = view.findViewById(R.id.daftar_outlet_text_katasandi);
        mButtonLanjutkan = view.findViewById(R.id.daftar_outlet_button_lanjutkan);
    }

    private boolean isTextKosong() {
        return mTextNamaPemilik.getText().toString().isEmpty() || mTextNamaOutlet.getText().toString().isEmpty() ||
                mTextEmail.getText().toString().isEmpty() || mTextNoTelepon.getText().toString().isEmpty() ||
                mTextKatasandi.getText().toString().isEmpty();
    }

    private void tampilkanDialog(String judul, String pesan) {
        DialogFragment dialog = PesanDialog.newInstance(judul, pesan);
        dialog.show(getChildFragmentManager(), PesanDialog.DIALOG_TAG);
    }


}
