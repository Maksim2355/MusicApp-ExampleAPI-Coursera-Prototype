package com.example.authcoursera.ui.songList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.authcoursera.ControlActionBar;
import com.example.authcoursera.R;
import com.example.authcoursera.model.Album;
import com.example.authcoursera.model.AlbumAndSongs;
import com.example.authcoursera.model.Song;

import java.io.IOException;
import java.util.List;

public class SongsListFragment extends Fragment {
    private static final String DATA_TAG = "album";
    private RecyclerView mSongListRecycler;
    private AlbumAndSongs mAlbumAndSongs;
    private TextView mNameAlbumTextView;
    private Button mGoCommentsBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAlbumAndSongs = (AlbumAndSongs) getArguments().getSerializable(DATA_TAG);

        return inflater.inflate(R.layout.fragment_songs_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ControlActionBar controlActionBar = (ControlActionBar)getActivity();
        controlActionBar.setActionBar(toolbar);

        mSongListRecycler = view.findViewById(R.id.songs_list_recyclerView);
        mNameAlbumTextView = view.findViewById(R.id.albumAndSongsList_textView);
        mGoCommentsBtn = view.findViewById(R.id.goComments_btn);
        mGoCommentsBtn.setOnClickListener(v -> {
            goComments();
        });
        mSongListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSongListRecycler.setAdapter(new SongsAdapter(mAlbumAndSongs.getSongs()));
        mNameAlbumTextView.setText(mAlbumAndSongs.getName());
    }

    private void goComments() {

    }


    public static Fragment getInstanceWithArguments(AlbumAndSongs data){
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_TAG, data);
        Fragment instance = new SongsListFragment();
        instance.setArguments(bundle);
        return instance;
    }

}