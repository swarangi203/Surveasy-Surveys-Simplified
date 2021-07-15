package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Cartesian3d;
import com.anychart.charts.Gantt;
import com.anychart.charts.Pie;
import com.anychart.charts.Venn;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.Bar3d;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.text.UStringsKt;
public class GraphActivity extends AppCompatActivity {

    String qStatement,surveyName, graphname;
    FirebaseDatabase rootnode;
    DatabaseReference ref1,ref2;
    List<DataEntry> data = new ArrayList<>();
    List<String> keylist = new ArrayList();
    List<Integer> valuelist = new ArrayList<>();
    Integer values=0;
    Long nochild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        surveyName=getIntent().getStringExtra("name");
        qStatement=getIntent().getStringExtra("qsts");
        graphname=getIntent().getStringExtra("graph");
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        rootnode=FirebaseDatabase.getInstance();
        if(graphname.equals("bar")) {
            Cartesian3d pie = AnyChart.bar3d();
            pie.title(qStatement);
            pie.labels().position("outside");
            pie.legend().title().enabled(true);
            pie.legend().title()
                    .text("Data")
                    .padding(0d, 0d, 10d, 0d);
            pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER);
            pie.labels().fontColor("#111");
            pie.palette().items();
            anyChartView.setChart(pie);
            anyChartView.setBackgroundColor(Color.BLACK);
            pie.background().corners(6);
            pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                @Override
                public void onClick(Event event) {
                    pie.animation();
                }
            });
            ref2 = rootnode.getReference("graph").child(surveyName).child(qStatement);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        nochild = snap.getChildrenCount();
                        Integer childcount = nochild.intValue();
                        String key = snap.getKey();
                        Long val = snap.getValue(Long.class);
                        Integer val2 = val.intValue();
                        for (int i = 0; i <= childcount; i++) {
                            keylist.add(i, key);
                            valuelist.add(i, val2);
                            valuelist.add(val2);
                            //   Toast.makeText(GraphActivity.this, keylist.get(i) + valuelist.get(i).toString(), Toast.LENGTH_SHORT).show();
                            values++;
                            data.add(new ValueDataEntry(keylist.get(i), valuelist.get(i)));
                        }
                        // Toast.makeText(GraphActivity.this, values.toString(), Toast.LENGTH_SHORT).show();

                    }
                    pie.data(data);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (graphname.equals("pie"))
        {
            Pie pie = AnyChart.pie();
            pie.title(qStatement);
            pie.labels().position("outside");
            pie.legend().title().enabled(true);
            pie.legend().title()
                    .text("Data")
                    .padding(0d, 0d, 10d, 0d);
            pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER);
            anyChartView.setChart(pie);
            pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                @Override
                public void onClick(Event event) {
                    pie.animation();
                }
            });
            ref2 = rootnode.getReference("graph").child(surveyName).child(qStatement);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        nochild = snap.getChildrenCount();
                        Integer childcount = nochild.intValue();
                        String key = snap.getKey();
                        Long val = snap.getValue(Long.class);
                        Integer val2 = val.intValue();
                        for (int i = 0; i <= childcount; i++) {
                            keylist.add(i, key);
                            valuelist.add(i, val2);
                            valuelist.add(val2);
                            //   Toast.makeText(GraphActivity.this, keylist.get(i) + valuelist.get(i).toString(), Toast.LENGTH_SHORT).show();
                            values++;
                            data.add(new ValueDataEntry(keylist.get(i), valuelist.get(i)));
                        }
                        // Toast.makeText(GraphActivity.this, values.toString(), Toast.LENGTH_SHORT).show();

                    }
                    pie.data(data);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (graphname.equals("column"))
        {
            Cartesian3d pie = AnyChart.column3d();
            pie.title(qStatement);
            pie.labels().position("outside");
            pie.legend().title().enabled(true);
            pie.legend().title()
                    .text("Data")
                    .padding(0d, 0d, 10d, 0d);
            pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER);
            anyChartView.setChart(pie);
            pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                @Override
                public void onClick(Event event) {
                    pie.animation();
                }
            });
            ref2 = rootnode.getReference("graph").child(surveyName).child(qStatement);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        nochild = snap.getChildrenCount();
                        Integer childcount = nochild.intValue();
                        String key = snap.getKey();
                        Long val = snap.getValue(Long.class);
                        Integer val2 = val.intValue();
                        for (int i = 0; i <= childcount; i++) {
                            keylist.add(i, key);
                            valuelist.add(i, val2);
                            valuelist.add(val2);
                            //   Toast.makeText(GraphActivity.this, keylist.get(i) + valuelist.get(i).toString(), Toast.LENGTH_SHORT).show();
                            values++;
                            data.add(new ValueDataEntry(keylist.get(i), valuelist.get(i)));
                        }
                        // Toast.makeText(GraphActivity.this, values.toString(), Toast.LENGTH_SHORT).show();

                    }
                    pie.data(data);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (graphname.equals("line"))
        {
            Cartesian pie = AnyChart.verticalLine();
            pie.title(qStatement);
    //        pie.labels().position("outside");
            pie.legend().title().enabled(true);
            pie.legend().title()
                    .text("Data")
                    .padding(0d, 0d, 10d, 0d);
            pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER);
            anyChartView.setChart(pie);
            pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                @Override
                public void onClick(Event event) {
                    pie.animation();
                }
            });
            ref2 = rootnode.getReference("graph").child(surveyName).child(qStatement);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        nochild = snap.getChildrenCount();
                        Integer childcount = nochild.intValue();
                        String key = snap.getKey();
                        Long val = snap.getValue(Long.class);
                        Integer val2 = val.intValue();
                        for (int i = 0; i <= childcount; i++) {
                            keylist.add(i, key);
                            valuelist.add(i, val2);
                            valuelist.add(val2);
                            //   Toast.makeText(GraphActivity.this, keylist.get(i) + valuelist.get(i).toString(), Toast.LENGTH_SHORT).show();
                            values++;
                            data.add(new ValueDataEntry(keylist.get(i), valuelist.get(i)));
                        }
                        // Toast.makeText(GraphActivity.this, values.toString(), Toast.LENGTH_SHORT).show();

                    }
                    pie.data(data);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}



