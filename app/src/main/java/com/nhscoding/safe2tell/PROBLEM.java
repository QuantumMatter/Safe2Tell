package com.nhscoding.safe2tell;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nhscoding.safe2tell.API.PostObject;
import com.nhscoding.safe2tell.API.PostParser;
import com.nhscoding.safe2tell.API.ProblemObject;
import com.nhscoding.safe2tell.API.ProblemParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PROBLEM.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PROBLEM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PROBLEM extends android.support.v4.app.Fragment {
    View rootview;

    private OnFragmentInteractionListener mListener;

    RecyclerView mRecyclerView;
    CardAdapter adapter;

    PostParser postParser;

    String name = "";
    String key1 = "arrayID";

    int ID = -1;
    ProblemParser problemParser;
    List Problems = new ArrayList();
    List Posts = new ArrayList();

    ViewPager viewPager;
    PagerAdapter pagerAdapter;


    public PROBLEM(int i) {
        ID = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_problem, container, false);

        //mRecyclerView = (RecyclerView) rootview.findViewById(R.id.problemRecycler);

        postParser = new PostParser();
        postParser.execute();
        InputStream postIn;

        problemParser = new ProblemParser();
        problemParser.execute();
        InputStream problemIn;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            postIn = postParser.get(5000, TimeUnit.MILLISECONDS);
            Posts = postParser.readJSONStream(postIn);

            problemIn = problemParser.get(5000, TimeUnit.MILLISECONDS);
            Problems = problemParser.readJSONStream(problemIn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomCard[] dataset = null;

        String[] array = getResources().getStringArray(R.array.problems);
        for (int i = 0; i < array.length; i++) {
            if (i == ID) {
                name = array[i];
                break;
            }
        }

        for (int i = 0; i < Problems.size(); i++) {
            ProblemObject entry = (ProblemObject) Problems.get(i);
            if (name.equals(entry.Name)) {
                ID = entry.ID;
                break;
            }
        }

        int count = 0;

        for (int i = 0; i < Posts.size(); i++) {
            PostObject entry = (PostObject) Posts.get(i);
            if (entry._ProblemID == ID) {
                count++;
            }
        }

        dataset = new CustomCard[count];

        int data = -1;
            for (int i = 0; i < Posts.size(); i++) {
                PostObject entry = (PostObject) Posts.get(i);
                if (entry._ProblemID == ID) {
                    CustomCard card = new CustomCard(getActivity());
                    card.mText = entry._Text;
                    card.mTitleText = entry._Title;
                    data++;
                    dataset[data] = card;
                }
            }

        //adapter = new CardAdapter(dataset, getActivity());
        //mRecyclerView.setAdapter(adapter);

        viewPager = (ViewPager) rootview.findViewById(R.id.problemPager);
        pagerAdapter = new PagerAdapter(getFragmentManager(), ID);
        viewPager.setAdapter(pagerAdapter);

        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //mRecyclerView.setLayoutManager(layoutManager);
        final FloatingActionButton subTip = (FloatingActionButton) view.findViewById(R.id.subTip);
        subTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),SUBMIT_TIP.class);
                startActivityForResult(intent,0);
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int i = Integer.parseInt(getArguments().getString(key1));

            String[] array = getResources().getStringArray(R.array.problems);
            name = array[i];
            Log.i("Name", name);
        }
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

    public static PROBLEM newInstance(int i) {
        PROBLEM fragment = new PROBLEM(-1);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        int _ID;

        public PagerAdapter(FragmentManager fm, int ID) {
            super(fm);
            _ID = ID;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            android.support.v4.app.Fragment frag = null;
            switch (i) {
                case 0:
                    frag = new LEARN();
                    break;

                case 1:
                    frag = new QUIZ();
                    break;

                default:
                    frag = new LEARN();
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
