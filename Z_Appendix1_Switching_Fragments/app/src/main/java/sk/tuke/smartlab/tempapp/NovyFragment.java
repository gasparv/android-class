package sk.tuke.smartlab.tempapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NovyFragment extends Fragment {
    private static NovyFragment fragment;
    public NovyFragment() {
    }
    public static NovyFragment newInstance() {
        if (fragment == null)
            fragment = new NovyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.fragment_novy, container, false);
        return rootview;
    }
}
