package com.example.authcoursera.ui.albumList;

import android.annotation.SuppressLint;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.ControlActionBar;
import com.example.authcoursera.FragmentManagement;
import com.example.authcoursera.InternetAccessControl;
import com.example.authcoursera.R;
import com.example.authcoursera.model.Album;
import com.example.authcoursera.model.AlbumAndSongs;
import com.example.authcoursera.model.MusicDao;
import com.example.authcoursera.model.Song;
import com.example.authcoursera.system.App;
import com.example.authcoursera.ui.AdapterDataManagement;
import com.example.authcoursera.ui.ClickViewHolder;
import com.example.authcoursera.ui.songList.SongsListFragment;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AlbumsListFragment extends Fragment implements ClickViewHolder<Album> {

    private RecyclerView mAlbumListRecyclerView;
    private AdapterDataManagement<Album> albumAdapterManagement;
    private Toolbar toolbar;
    private FragmentManagement fragmentManagement;
    private ClickViewHolder<Album> clickViewHolder;
    private InternetAccessControl internetAccessControl;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        clickViewHolder = (ClickViewHolder<Album>)this;
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
        internetAccessControl = (InternetAccessControl)getActivity();

        if (internetAccessControl.getAccessInfo()){
            loadDataAlbumsForAdapter();
        }
        else {
            internetAccessControl.sendPermissionRequest();
        }
    }



    @SuppressLint("CheckResult")
    private void loadDataAlbumsForAdapter() {
        ApiUtils.getApiService().getAlbumsList()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(albums -> {
                    getMusicDao().addAlbums(albums);
                } )
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTION.contains(throwable.getClass())){
                        return getMusicDao().getAlbums();
                    }else return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albums -> {
                    if (albums != null){
                        albumAdapterManagement.setData(albums, clickViewHolder);
                    }else   Toast.makeText(getActivity(), "Нету данных ",
                            Toast.LENGTH_LONG).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), "Ошибка при загрузке " +
                            throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


    private MusicDao getMusicDao(){
        return App.getInstance().getDatabase().getMusicDao();
    }


    /*
       После клика, получаем альбом с сервера. Возвращается элемент AlbumAndSongs.
       List<Song> из него добавляем в нашу БД
       В Случае ошибки при неисправности интернета, достаем albumsWithSongs из нашей бд
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    public void clickViewHolder(Album data) {
        ApiUtils.getApiService().getAlbumById(data.getId())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(albumAndSongs -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N )
                        setInfoAlbum(albumAndSongs);
                    else {
                        for (Song song:albumAndSongs.getSongs()){
                            song.setIdAlbum(albumAndSongs.getId());
                        }
                    }
                    getMusicDao().addSongs(albumAndSongs.getSongs());
                })
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTION.contains(throwable.getClass())){
                        return getMusicDao().getAlbumsWithSongs(data.getId());
                    }else return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumAndSongs -> {
                    fragmentManagement.
                            replaceFragment(SongsListFragment.getInstanceWithArguments(albumAndSongs));
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setInfoAlbum(AlbumAndSongs albumAndSongs) {
        int albumId = albumAndSongs.getId();
        albumAndSongs.getSongs().forEach(song -> {song.setIdAlbum(albumId);});
    }
}
