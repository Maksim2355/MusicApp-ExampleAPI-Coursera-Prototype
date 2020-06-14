package com.example.authcoursera.ui.commentList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authcoursera.R;
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
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(commentList.get(position));
    }


    @Override
    public int getItemCount() { return commentList.size(); }

    @Override
    public void addData(Comment data) {
        commentList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<Comment> dataList, ClickViewHolder<Comment> clickViewHolder) {
        commentList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void updateData(Comment data, int position) {
        commentList.set(position, data);
        notifyDataSetChanged();
    }
}
