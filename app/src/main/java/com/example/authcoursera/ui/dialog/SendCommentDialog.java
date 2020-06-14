package com.example.authcoursera.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.authcoursera.ApiUtils;
import com.example.authcoursera.R;
import com.example.authcoursera.model.UserCommentForSending;
import com.example.authcoursera.ui.SendComment;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SendCommentDialog extends DialogFragment implements View.OnClickListener {

    private EditText inputText;
    private static final String ARG_ALBUM_ID = "ALBUM_ID";
    private int mAlbumId;
    private static SendComment sendComment;

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_btn: {
                if (!inputText.getText().toString().isEmpty()){
                    UserCommentForSending message = new UserCommentForSending(mAlbumId,inputText.getText().toString() );
                    ApiUtils.getApiService().postComment(message)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> {
                                Toast.makeText(getActivity(), "Успешная отправка", Toast.LENGTH_LONG).show();
                                dismiss();
                                sendComment.sendComment(true);
                            },throwable -> {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            });
                }
                break;
            } case R.id.cancel_btn: {
                dismiss();
                break;
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            mAlbumId = getArguments().getInt(ARG_ALBUM_ID);
        }
        getDialog().setTitle("Введите комментарий");
        View v = inflater.inflate(R.layout.dialog_comment_send, null);
        inputText = v.findViewById(R.id.textComment_textView);
        v.findViewById(R.id.send_btn).setOnClickListener(this);
        v.findViewById(R.id.cancel_btn).setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputText.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("CheckResult")
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    UserCommentForSending message = new UserCommentForSending(mAlbumId,inputText.getText().toString() );
                    ApiUtils.getApiService().postComment(message)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> {
                                Toast.makeText(getActivity(), "Успешная отправка", Toast.LENGTH_LONG).show();
                                dismiss();
                                sendComment.sendComment(true);
                            },throwable -> {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            });
                    return true;
                }
                return false;
            }
        });
    }

    public static SendCommentDialog newInstance(int idAlbum, Fragment context) {
        sendComment = (SendComment)context;
        Bundle args = new Bundle();
        args.putInt(ARG_ALBUM_ID, idAlbum);

        SendCommentDialog fragment = new SendCommentDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
