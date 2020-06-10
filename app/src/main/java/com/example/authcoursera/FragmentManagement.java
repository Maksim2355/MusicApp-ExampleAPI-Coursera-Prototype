package com.example.authcoursera;

import androidx.fragment.app.Fragment;

public interface FragmentManagement {

    void addFragment(Fragment fr);

    void deleteFragment(Fragment fr);

    void replaceFragment(Fragment fr);
}
