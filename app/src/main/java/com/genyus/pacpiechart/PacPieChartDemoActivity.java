package com.genyus.pacpiechart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.genyus.pacpiechart.library.Pacpie;
import com.genyus.pacpiechart.library.PacpieSlice;

import java.util.ArrayList;
import java.util.List;

public class PacPieChartDemoActivity extends Activity {

    Pacpie pacpie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_pie_chart_demo);

        pacpie = (Pacpie)findViewById(R.id.pacpieChart);
        pacpie.setValues(generateFakeSlices());
    }

    public void toggle(View v){
        pacpie.toggle();
    }

    private List<PacpieSlice> generateFakeSlices(){
        List<PacpieSlice> slices = new ArrayList<>();

        PacpieSlice slice1 = new PacpieSlice();
        slice1.count(40);
        slice1.color(getResources().getColor(R.color.slice_color));
        slices.add(slice1);

        PacpieSlice slice2 = new PacpieSlice();
        slice2.count(20);
        slice2.color(getResources().getColor(R.color.slice_color_2));
        slices.add(slice2);

        PacpieSlice slice3 = new PacpieSlice();
        slice3.count(20);
        slice3.color(getResources().getColor(R.color.slice_color_3));
        slices.add(slice3);

        //here we let the color value empty (default value inside library will be used)
        PacpieSlice slice4 = new PacpieSlice();
        slice4.count(10);
        slices.add(slice4);

        PacpieSlice slice5 = new PacpieSlice();
        slice5.count(10);
        slice5.color(getResources().getColor(R.color.slice_color_4));
        slices.add(slice5);

        return slices;
    }
}
