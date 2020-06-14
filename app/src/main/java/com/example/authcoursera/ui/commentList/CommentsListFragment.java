package com.example.authcoursera.ui.commentList;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.R;
import com.example.authcoursera.model.Comment;
import com.example.authcoursera.model.MusicDao;
import com.example.authcoursera.system.App;
import com.example.authcoursera.ui.AdapterDataManagement;
import com.example.authcoursera.ui.SendComment;
import com.example.authcoursera.ui.dialog.SendCommentDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CommentsListFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, SendComment {

    private static final String ARG_PARAM = "album_id";

    private int mIdAlbumParam;

    private AdapterDataManagement<Comment> dataManagement;

    private RecyclerView mCommentListRecyclerView;
    private Button mOpenCommentFormBtn;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Comment> revelantComment = new ArrayList<>();



    @Override
    public void sendComment(boolean status) {
        if (status){
            onRefresh();
        }
    }


    public CommentsListFragment() {
        // Required empty public constructor
    }


    public static CommentsListFragment newInstance(int mIdAlbumParam) {
        CommentsListFragment fragment = new CommentsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, mIdAlbumParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdAlbumParam = getArguments().getInt(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOpenCommentFormBtn = view.findViewById(R.id.goComments_btn);
        mCommentListRecyclerView = view.findViewById(R.id.commentsList_recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.updateComment_swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mOpenCommentFormBtn.setOnClickListener(this);
        mCommentListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentAdapter adapter = new CommentAdapter();
        dataManagement = adapter;
        mCommentListRecyclerView.setAdapter(adapter);

        onRefresh();
    }

    @Override
    public void onClick(View v) {
        //Открытия формы для POST запроса и перехода на dialog fragment
        DialogFragment fragment = SendCommentDialog.newInstance(mIdAlbumParam,this);
        fragment.show(getActivity().getSupportFragmentManager(), "DlgSend");
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(this::loadComments);
    }




    @SuppressLint("CheckResult")
    private void loadComments(){
        ApiUtils.getApiService().getCommentsByAlbum(mIdAlbumParam)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comments -> {
                    if (revelantComment.hashCode() != comments.hashCode()) getMusicDao().addComments(comments);
                })
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTION.contains(throwable.getClass())) {
                        revelantComment = getMusicDao().getCommentsByAlbum(mIdAlbumParam);
                        return revelantComment;
                    } else return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(comments -> {
                    System.out.println("Комменты такие " + comments.toString());
                    if (!((comments.size() == revelantComment.size()) && comments.containsAll(revelantComment))){
                        dataManagement.setData(comments, null);
                        showToastMessage("Данные обновлены");
                        revelantComment = comments;
                    }else {
                        showToastMessage("Нету новых комментариев");
                    }

                }, throwable -> {
                    showToastMessage(throwable.getMessage());
                });

    }

    private MusicDao getMusicDao(){
        return App.getInstance().getDatabase().getMusicDao();
    }

    private void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}