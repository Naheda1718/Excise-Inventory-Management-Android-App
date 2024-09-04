package com.excise.excisemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

public class Excise_Section extends Fragment {

    GridView excisegrid;

    CustomAdapter excisecustomadapter;

    int icons[] = {R.drawable.rcvexcise, R.drawable.inprocessexcise,R.drawable.returnvendor,R.drawable.returnvendor, R.drawable.exciseinv,R.drawable.report,R.drawable.report};
    String name[] = {"Received Stamps", "In-process Excise","Excise Inventory Adjustment", "RTV Product Excise","Excise Inventory", "Excise Report","Inprocess Section"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_excise__section, container, false);


        excisegrid = view.findViewById(R.id.excise_gridview);


        excisecustomadapter = new CustomAdapter(name, icons, getActivity());
        excisegrid.setAdapter(excisecustomadapter);

       ;
        if (getActivity() != null) {
            getActivity().setTitle("Excise Management ");
        }

        excisegrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openrecevestamp();
                        break;
                    case 1:
                       opentrackstamp();
                        break;
                    case 2:
                    openadjustvalue();
                        break;
                    case 4:
                        openexciseinventory();
                        break;
                    case 5:
                        openreport();
                        break;

                    case 6:
                        openinprocesssection();
                        break;
                }
            }
        });
        return view;
    }

    private void openinprocesssection() {
        Intent intent = new Intent(getActivity(), Inprocess_section.class);
        startActivity(intent);
    }

    private void openadjustvalue() {
        Intent intent = new Intent(getActivity(), excise_adjustvalue.class);
        startActivity(intent);
    }

    private void openreport() {
        Intent intent = new Intent(getActivity(), excise_report.class);
        startActivity(intent);
    }

    private void openexciseinventory() {
        Intent intent = new Intent(getActivity(), excise_inventory.class);
        startActivity(intent);
    }

    private void opentrackstamp() {
        Intent intent = new Intent(getActivity(), track_stamps.class);
        startActivity(intent);

    }

    private void openrecevestamp() {
        Intent intent = new Intent(getActivity(), receive_stamps.class);
        startActivity(intent);
    }


}