package com.example.authcoursera.ui.albumList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
import com.example.authcoursera.model.Album;
import com.example.authcoursera.ui.ClickViewHolder;


public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private TextView mNameAlbumTextView;
    private TextView mReleaseAlbumDateTextView;



    public AlbumViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameAlbumTextView = itemView.findViewById(R.id.albumName_textView);
        mReleaseAlbumDateTextView = itemView.findViewById(R.id.releaseDateAlbum_textView);
    }



    public void bind(Album item, ClickViewHolder<Album> click) {
        itemView.setOnClickListener(v -> {
            click.clickViewHolder(item);
        });
        mNameAlbumTextView.setText(item.getName());
        mReleaseAlbumDateTextView.setText(item.getReleaseDate());
    }
}
