package com.unbaja.dewijaya.pemesanan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.model.LoginEvent;
import com.unbaja.dewijaya.pemesanan.view.dialog.PesanDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sigit on 01/08/2018.
 */

public class LoginFragment extends Fragment {


    private EditText mTextEmail;
    private EditText mTextKatasandi;
    private TextView mTextDaftar;
    private Button mButtonMasuk;

    public static Fragment newInstance() {
        Fragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);

        mTextDaftar.setOnClickListener(v -> {
            LoginEvent event = LoginEvent.createEventDaftarOutlet();
            EventBus.getDefault().post(event);
        });

        mButtonMasuk.setOnClickListener(v ->  {
            if (isTextKosong()) {
                DialogFragment dialog = PesanDialog.newInstance("Login Gagal", "Email atau Katasandi Harus Diisi");
                dialog.show(getChildFragmentManager(), PesanDialog.DIALOG_TAG);
            }else {
                String email = mTextEmail.getText().toString();
                String katasandi = mTextKatasandi.getText().toString();
                LoginEvent event = LoginEvent.createEventLogin();
                event.setEmail(email);
                event.setKatasandi(katasandi);
                event.setLogin(true);
                EventBus.getDefault().post(event);
            }
        });

        return view;
    }

    private boolean isTextKosong() {
        return mTextEmail.getText().toString().isEmpty() || mTextKatasandi.getText().toString().isEmpty();
    }

    private void initViews(View view) {
        mTextDaftar = view.findViewById(R.id.fragment_login_text_daftar);
        mTextEmail = view.findViewById(R.id.fragment_login_editText_email);
        mTextKatasandi = view.findViewById(R.id.fragment_login_editText_katasandi);
        mButtonMasuk = view.findViewById(R.id.fragment_login_button_masuk);
    }
}
