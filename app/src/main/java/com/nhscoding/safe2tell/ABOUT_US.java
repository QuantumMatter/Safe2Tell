package com.nhscoding.safe2tell;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhscoding.safe2tell.API.PostParser;

/**
 * Created by davidkopala on 2/6/15.
 */
public class ABOUT_US extends Fragment {

    View rootview;

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_about_us, container, false);
        PostParser parser = new PostParser();
        parser.execute();
        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static ABOUT_US newInstance() {
        ABOUT_US fragment = new ABOUT_US();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}