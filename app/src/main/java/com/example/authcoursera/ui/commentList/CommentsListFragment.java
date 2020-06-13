package com.example.authcoursera.ui.commentList;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.R;
import com.example.authcoursera.model.MusicDao;
import com.example.authcoursera.system.App;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CommentsListFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM = "album_id";

    private int mIdAlbumParam;

    private RecyclerView mCommentListRecyclerView;
    private Button mOpenCommentFormBtn;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    

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
        mOpenCommentFormBtn.setOnClickListener(this);

        mCommentListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onClick(View v) {
        //Открытия формы для POST запроса и перехода на dialog fragment
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(this::getComments);
    }

    @SuppressLint("CheckResult")
    private void getComments(){
        ApiUtils.getApiService().getCommentsByAlbum(mIdAlbumParam)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(comments -> {
                    getMusicDao().addComments(comments);
                })
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTION.contains(throwable.getClass())){
                        return getMusicDao().getCommentsByAlbum(mIdAlbumParam);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(comments -> {

                }, throwable -> {

                });

    }

    private MusicDao getMusicDao(){
        return App.getInstance().getDatabase().getMusicDao();
    }

}