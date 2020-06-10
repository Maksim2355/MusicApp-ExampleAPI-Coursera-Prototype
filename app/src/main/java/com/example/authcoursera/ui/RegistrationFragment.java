package com.example.authcoursera.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.FragmentManagement;
import com.example.authcoursera.R;
import com.example.authcoursera.model.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText mEmailInputEditText;
    private EditText mNameInputEditText;
    private EditText mPasswordInputEditText;
    private EditText mPasswordInputAgainEditText;
    private Button mRegistrationUserBtn;
    private Button mBackAuthBtn;

    private FragmentManagement fragmentManagement;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmailInputEditText = view.findViewById(R.id.email_editText);
        mNameInputEditText = view.findViewById(R.id.name_editText);
        mPasswordInputEditText = view.findViewById(R.id.password_editText);
        mPasswordInputAgainEditText = view.findViewById(R.id.password_again_editText);
        mRegistrationUserBtn = view.findViewById(R.id.registration_btn);
        mBackAuthBtn = view.findViewById(R.id.back_auth_btn);
        fragmentManagement = (FragmentManagement)getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRegistrationUserBtn.setOnClickListener(v -> registrationUser());
        mBackAuthBtn.setOnClickListener(v ->
                fragmentManagement.replaceFragment(new AuthorizationFragment()));
    }


    @SuppressLint("CheckResult")
    private void registrationUser(){
        String email = mEmailInputEditText.getText().toString();
        String name = mNameInputEditText.getText().toString();
        //Я же не пидорас,чтобы в пуле строк хранить пароли
        String password = new String(mPasswordInputEditText.getText().toString());

        if(isInputValid(email, name, password)) {
            User user = new User(email, name, password);

            ApiUtils.getNewApiService(email, password)
                    .registration(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        showToastMessage("Успешная регистрация");
                        fragmentManagement.replaceFragment(new AuthorizationFragment());
                    }, throwable ->
                    {
                        showToastMessage(throwable.getMessage());
                    });

        } else showToastMessage("Некорректные данные для регистрации");
    }

    private boolean isInputValid(String email, String name, String password) {
        return  isEmailValid(email)
                && !TextUtils.isEmpty(name)
                && isPasswordsValid(password);
    }

    private boolean isPasswordsValid(String password) {
        String passwordAgain = mPasswordInputAgainEditText.getText().toString();
        return  password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
