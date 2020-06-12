package com.example.authcoursera.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.ControlActionBar;
import com.example.authcoursera.FragmentManagement;
import com.example.authcoursera.InternetAccessControl;
import com.example.authcoursera.R;
import com.example.authcoursera.model.User;
import com.example.authcoursera.model.UserDao;
import com.example.authcoursera.system.App;
import com.example.authcoursera.ui.albumList.AlbumsListFragment;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorizationFragment extends Fragment implements View.OnClickListener {

    private EditText mInputLoginEditText;
    private EditText mInputPasswordEditText;
    private Button mAuthorizationBtn;
    private Button mRegistrationBtn;

    private FragmentManagement fragmentManagement;
    private InternetAccessControl internetAccessControl;

    public AuthorizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ControlActionBar controlActionBar = (ControlActionBar)getActivity();
        controlActionBar.deleteActionBar();
        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInputLoginEditText = view.findViewById(R.id.name_auth_editText);
        mInputPasswordEditText = view.findViewById(R.id.password_auth_editText);
        mAuthorizationBtn = view.findViewById(R.id.authorization_btn);
        mRegistrationBtn = view.findViewById(R.id.go_registration_btn);
        //Интерфейс для управление фрагментами из активности
        fragmentManagement = (FragmentManagement) getActivity();
        internetAccessControl = (InternetAccessControl)getActivity();

        mAuthorizationBtn.setOnClickListener(this);
        mRegistrationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.authorization_btn: {
                authorizationUser();
                break;
            }case R.id.go_registration_btn: {
                goRegistrationUser();
                break;
            }

        }
    }

    private void goRegistrationUser() {
        Fragment fragmentDestination = new RegistrationFragment();
        fragmentManagement.replaceFragment(fragmentDestination);
    }

    @SuppressLint("CheckResult")
    private void authorizationUser() {
        String email = mInputLoginEditText.getText().toString();
        String password = mInputPasswordEditText.getText().toString();
        if (internetAccessControl.getAccessInfo())
        ApiUtils.getNewApiService(email, password)
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user ->{
                        User userForDb = new User(user.getEmail(), user.getName(), password);
                        showToastMessage("Привет " + user.getName());
                        App.getInstance().getDatabase().getUserDao().loginInAccount(userForDb);
                        fragmentManagement.replaceFragment(new AlbumsListFragment());
                        },
                        throwable -> {
                            showToastMessage(throwable.getMessage());
                        });
        else showToastMessage("Отсутствует подключение к интернету");
    }


    private void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
