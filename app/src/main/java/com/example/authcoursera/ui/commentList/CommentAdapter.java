package com.example.authcoursera.ui.commentList;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.model.Comment;
import com.example.authcoursera.ui.AdapterDataManagement;
import com.example.authcoursera.ui.ClickViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder>
        implements AdapterDataManagement<Comment> {

    private List<Comment> commentList = new ArrayList<>();


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void addData(Comment data) {

    }

    @Override
    public void setData(List<Comment> dataList, ClickViewHolder<Comment> clickViewHolder) {

    }

    @Override
    public void updateData(Comment data, int position) {

    }
}
