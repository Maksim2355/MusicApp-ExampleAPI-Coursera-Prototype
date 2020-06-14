package com.example.authcoursera.ui.commentList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
import com.example.authcoursera.model.Comment;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView mAuthorNameTV;
    private TextView mTextCommentTV;
    private TextView mDateCommentTv;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        mAuthorNameTV = itemView.findViewById(R.id.authorName_textView);
        mTextCommentTV = itemView.findViewById(R.id.textComment_textView);
        mDateCommentTv = itemView.findViewById(R.id.commentDate_textView);
    }

    public void bind(Comment comment){
        mAuthorNameTV.setText(comment.getAuthor());
        mTextCommentTV.setText(comment.getTextComment());
        mDateCommentTv.setText(comment.getTimestamp());
    }
    
}
