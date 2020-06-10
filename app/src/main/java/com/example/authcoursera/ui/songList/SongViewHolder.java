package com.example.authcoursera.ui.songList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
import com.example.authcoursera.model.Song;

public class SongViewHolder extends RecyclerView.ViewHolder {

    private TextView mNameSongTextView;
    private TextView mDurationSongTextView;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameSongTextView = itemView.findViewById(R.id.name_song_textView);
        mDurationSongTextView = itemView.findViewById(R.id.duration_song_textView);
    }

    public void bind(Song song){
        mNameSongTextView.setText(song.getName());
        mDurationSongTextView.setText(song.getDuration());
    }

}
