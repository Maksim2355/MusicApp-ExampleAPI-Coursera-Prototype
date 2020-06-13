package com.example.authcoursera.ui.commentList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
import com.example.authcoursera.model.Comment;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextCommentTV;
    private TextView mDateCommentTv;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextCommentTV = itemView.findViewById(R.id.textComment_textView);
        mDateCommentTv = itemView.findViewById(R.id.commentDate_textView);
    }

    public void bind(Comment comment){
        mTextCommentTV.setText(comment.getTextComment());
    }
}
