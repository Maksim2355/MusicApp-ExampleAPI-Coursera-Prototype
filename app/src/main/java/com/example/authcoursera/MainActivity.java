package com.example.authcoursera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.authcoursera.ui.AuthorizationFragment;
import com.example.authcoursera.ui.albumList.AlbumsListFragment;

public class MainActivity extends AppCompatActivity implements FragmentManagement {

    private final int ID_CONTAINER_FRAGMENT = R.id.container_fragment;
    private Fragment mainFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null || mainFragment == null){
            Fragment authFragment = new AuthorizationFragment();
            mainFragment = authFragment;
            addFragment(authFragment);
        }else {
            addFragment(mainFragment);
        }
    }

    @Override
    public void addFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction().add(ID_CONTAINER_FRAGMENT, fr).commit();
        mainFragment = fr;

    }

    @Override
    public void deleteFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction().remove(fr).commit();
        mainFragment = fr;
    }

    @Override
    public void replaceFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction().replace(ID_CONTAINER_FRAGMENT, fr).commit();
        mainFragment = fr;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

}
