package com.example.authcoursera.ui.albumList;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.ControlActionBar;
import com.example.authcoursera.FragmentManagement;
import com.example.authcoursera.R;
import com.example.authcoursera.model.Album;
import com.example.authcoursera.model.MusicDao;
import com.example.authcoursera.system.App;
import com.example.authcoursera.ui.AdapterDataManagement;
import com.example.authcoursera.ui.AuthorizationFragment;


import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AlbumsListFragment extends Fragment {

    private RecyclerView mAlbumListRecyclerView;
    private AdapterDataManagement<Album> albumAdapterManagement;
    private Toolbar toolbar;
    private FragmentManagement fragmentManagement;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlbumListRecyclerView = view.findViewById(R.id.albumList_recyclerView);
        mAlbumListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AlbumAdapter albumAdapter = new AlbumAdapter();
        albumAdapterManagement = albumAdapter;
        mAlbumListRecyclerView.setAdapter(albumAdapter);

        toolbar = view.findViewById(R.id.toolbar);
        ControlActionBar controlActionBar = (ControlActionBar)getActivity();
        controlActionBar.setActionBar(toolbar);


        fragmentManagement = (FragmentManagement) getActivity();
        loadDataAlbumsForAdapter();
    }

    @SuppressLint("CheckResult")
    private void loadDataAlbumsForAdapter() {
        ApiUtils.getApiService().getAlbumsList()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(albums -> {
                    getMusicDao().addAlbums(albums);
                } )
                .onErrorReturn(throwable -> {
                    System.out.println("Пизда " + getMusicDao().getAlbums());
                    if (ApiUtils.NETWORK_EXCEPTION.contains(throwable.getClass())){
                        return getMusicDao().getAlbums();
                    }else return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albums -> {
                    albumAdapterManagement.setData(albums);
                }, throwable -> {
                    Toast.makeText(getActivity(), "Ошибка при загрузке " +
                            throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


    private MusicDao getMusicDao(){
        return App.getInstance().getDatabase().getMusicDao();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ControlActionBar controlActionBar = (ControlActionBar)getActivity();
        controlActionBar.deleteActionBar();
    }
}
