package com.example.jespe.initiativiet;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatistikFragment extends Fragment {
    api_call_statistics api_stat = new api_call_statistics();
    ListView list_stat;
    String inp = "http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2011";
    ListView piechartlist;
    String titel = "";
    ArrayList<PieChart> PL = new ArrayList<>();
    Float[] ydata = new Float[]{1f,2f,3f};
    String[] xdata = new String[]{"For", "Imod", "Hverken for eller imod"};
    private ArrayAdapter aap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aap = new ArrayAdapter<api_call_statistics.Wrapper>(getActivity(), R.layout.stat_piechart, R.id.textView2){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View vi = super.getView(position, convertView, parent);
                PieChart pieChart = (PieChart) vi.findViewById(R.id.piechart);
                TextView textView = (TextView) vi.findViewById(R.id.textView2);
                textView.setVisibility(View.GONE);

                api_call_statistics.Wrapper item = this.getItem(position);

                String titel = item.lovTitel;
                String sub = item.resultat;
                String nummer = item.lovNummber;

                String[] test = sub.split(",");
                Float for1 = Float.valueOf(Integer.parseInt(test[0]));
                Float imod = Float.valueOf(Integer.parseInt(test[1]));
                Float hverken = Float.valueOf(Integer.parseInt(test[2]));
                pieChart = addDataSet(pieChart,for1,imod,hverken, titel, nummer);
                PL.add(pieChart);

                return vi;
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);
        piechartlist = (ListView) v.findViewById(R.id.piechartlist);

        piechartlist.setAdapter(aap);

        //To stop app from fetching 2800 pages ofhttps://www.canva.com/design/DACoLEbbwxQ/share?role=EDITOR&token=n8hz2lZ9MI0uNUVNgeB1zg&utm_content=DACoLEbbwxQ&utm_campaign=designshare&utm_medium=link&utm_source=sharebutton api data once again
        //when fidgeting around with the tabs. PLEASE DO NOT REMOVE <3
        Log.e("stat", "inside");
        if(api_stat.getWrappers().isEmpty()) {
            api_stat.fetchData(new Runnable() {
                @Override
                public void run() {
                    StatistikFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            aap.addAll(api_stat.getWrappers());
                        }
                    });
                }
            }, inp);
        }
        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
    private PieChart addDataSet(PieChart pieChart, Float for1, Float imod, Float hverken,String titel, String nummer) {
        //https://github.com/PhilJay/MPAndroidChart/
        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(for1 != 0){
            entries.add(new PieEntry(for1,"For"));
            colors.add(Color.GREEN);
        }
        if(imod != 0){
            entries.add(new PieEntry(imod,"Imod"));
            colors.add(Color.RED);
        }
        if(hverken != 0){
            entries.add(new PieEntry(hverken,"Ved ikke"));
            colors.add(Color.GRAY);
        }
        if(for1 ==0 && imod == 0 && hverken == 0){
            colors.add(Color.CYAN);
        }
        final Float[] ydata = {for1, imod, hverken};


        PieDataSet pieDataSet = new PieDataSet(entries,titel);
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        pieChart.setDescription("");
        pieChart.setNoDataText("Data mangler, refresh side");
        pieChart.setCenterText(nummer);
        pieChart.setCenterTextSize(27);
        pieChart.setHoleColor(250);
        pieChart.setDrawSliceText(false);
        pieChart.setHoleRadius(40);
        pieChart.setTransparentCircleRadius(45);
        pieChart.setTransparentCircleAlpha(50);
        pieChart.animateX(1000);
        pieChart.animateY(1000);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int position = e.toString().indexOf("(sum): ");
                String votes = e.toString().substring(position + 7);

                for(int i = 0; i < ydata.length; i++){
                    if(ydata[i] == Float.parseFloat(votes)){
                        position = i;
                        break;
                    }
                }
                String votesAnswer = xdata[position];
                Toast.makeText(getActivity(), "" + votesAnswer + ": \n" + votes, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return pieChart;
    }
}