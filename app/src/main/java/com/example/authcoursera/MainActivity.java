package com.example.authcoursera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.authcoursera.model.User;
import com.example.authcoursera.model.UserDao;
import com.example.authcoursera.system.App;
import com.example.authcoursera.ui.AuthorizationFragment;
import com.example.authcoursera.ui.albumList.AlbumsListFragment;

public class MainActivity extends AppCompatActivity implements FragmentManagement, ControlActionBar, InternetAccessControl {

    private static final int REQUEST_CODE_PERMISSION_INTERNET = 111;
    private static int COUNT_FRAGMENT_STACK = 0;
    private final int ID_CONTAINER_FRAGMENT = R.id.container_fragment;
    private Fragment mainFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment startFragment;
        User authUser = getUserDao().getAuthUser();
            if (authUser != null) {
                startFragment = new AlbumsListFragment();
                mainFragment = startFragment;
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", authUser.getName());
                addFragment(startFragment);
            } else {
                startFragment = new AuthorizationFragment();
                mainFragment = startFragment;
                addFragment(mainFragment);
            }

    }

    @Override
    public void addFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction().add(ID_CONTAINER_FRAGMENT, fr)
                .addToBackStack(null).commit();
        mainFragment = fr;
        COUNT_FRAGMENT_STACK += 1;

    }

    @Override
    public void deleteFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction().remove(fr).commit();
        mainFragment = fr;
    }

    @Override
    public void replaceFragment(Fragment fr) {
        getSupportFragmentManager().beginTransaction()
                .replace(ID_CONTAINER_FRAGMENT, fr).addToBackStack(null).commit();
        mainFragment = fr;
    }


    private UserDao getUserDao(){
        return App.getInstance().getDatabase().getUserDao();
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void deleteActionBar() {
        setSupportActionBar(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_menuItem){
            App.getInstance().getDatabase().getUserDao().loginOutAccount();
            replaceFragment(new AuthorizationFragment());
        }else if (item.getItemId() == android.R.id.home){
            onBackPressed();
            System.out.println("Нажали кнопку назад");
        }
        return true;
    }

    @Override
    public boolean getAccessInfo() {
        int permissionStatus
                = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else return false;
    }

    @Override
    public void sendPermissionRequest() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},
                REQUEST_CODE_PERMISSION_INTERNET);
    }


}
