package com.example.authcoursera.ui;

import java.util.List;

public interface AdapterDataManagement<T> {

    void addData(T data);

    void setData(List<T> dataList);

    void updateData(T data, int position);

}
