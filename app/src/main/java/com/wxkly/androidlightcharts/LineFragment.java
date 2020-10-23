package com.wxkly.androidlightcharts;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wxkly.androidcharts.LineView;

import java.util.ArrayList;

/**
 * Created by wxkly on 10/23/20

 */
public class LineFragment extends Fragment {
    int randomint = 8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line, container, false);
        final LineView lineView = (LineView) rootView.findViewById(R.id.line_view);
//        final LineView2 lineViewFloat = (LineView2) rootView.findViewById(R.id.line_view_float);

        initLineView(lineView);
//        initLineView(lineViewFloat);
        Button lineButton = (Button) rootView.findViewById(R.id.line_button);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(lineView);
            }
        });

        randomSet(lineView);
        return rootView;
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
        lineView.setDrawDotLine(true);
//        lineView.setShowPopup(LineView2.SHOW_POPUPS_NONE);
    }

    private void randomSet(LineView lineView) {
        ArrayList<Integer> dataList = new ArrayList<>();
        float random = (float) (Math.random() * 90 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList.add((int) (Math.random() * random));
        }

        ArrayList<Integer> dataList2 = new ArrayList<>();
        random = (int) (Math.random() * 90 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList2.add((int) (Math.random() * random));
        }

        ArrayList<Integer> dataList3 = new ArrayList<>();
        random = (int) (Math.random() * 90 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList3.add((int) (Math.random() * random));
        }

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);
        dataLists.add(dataList2);
        dataLists.add(dataList3);

        lineView.setDataList(dataLists);

//        ArrayList<Float> dataListF = new ArrayList<>();
//        float randomF = (float) (Math.random() * 90 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<Float> dataListF2 = new ArrayList<>();
//        randomF = (int) (Math.random() * 90 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF2.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<Float> dataListF3 = new ArrayList<>();
//        randomF = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF3.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
//        dataListFs.add(dataListF);
//        dataListFs.add(dataListF2);
//        dataListFs.add(dataListF3);
//
//        lineViewFloat.setFloatDataList(dataListFs);
    }
}