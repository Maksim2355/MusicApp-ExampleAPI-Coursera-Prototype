package com.example.authcoursera.ui.albumList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
import com.example.authcoursera.model.Album;
import com.example.authcoursera.ui.AdapterDataManagement;


import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> implements AdapterDataManagement<Album> {

    private List<Album> albumList = new ArrayList<>();



    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public void addData(Album data) {
        albumList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<Album> dataList) {
        albumList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public void updateData(Album data, int position) {
        albumList.set(position, data);
        notifyDataSetChanged();
    }


}
