package com.wxkly.androidlightcharts;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wxkly.androidcharts.PieHelper;
import com.wxkly.androidcharts.PieView;

import java.util.ArrayList;

/**
 * Created by wxkly on 10/23/20

 */
public class PieFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pie_s, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        final PieView pieView = (PieView) rootView.findViewById(R.id.pie_view);
        Button button = (Button) rootView.findViewById(R.id.pie_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(pieView);
            }
        });
        randomSet(pieView);
        return rootView;
    }

    private void randomSet(PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        ArrayList<Integer> intList = new ArrayList<Integer>();
        int totalNum = (int) (10 * Math.random());

        int totalInt = 0;
        for (int i = 0; i < totalNum; i++) {
            int ranInt = (int) (Math.random() * 10) + 1;
            intList.add(ranInt);
            totalInt += ranInt;
        }
        for (int i = 0; i < totalNum; i++) {
            pieHelperArrayList.add(new PieHelper(100f * intList.get(i) / totalInt));
        }

        pieView.selectedPie(PieView.NO_SELECTED_INDEX);
        pieView.showPercentLabel(true);
        pieView.setData(pieHelperArrayList);
    }
}