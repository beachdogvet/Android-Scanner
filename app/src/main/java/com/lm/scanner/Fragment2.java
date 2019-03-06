package com.lm.scanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private TextView textView;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment2, container, false);
//        textView = view.findViewById(R.id.txt_display);
//        textView.setText(getArguments().getString("messageToFragment"));
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.add(1, Common.OPTIONS_MENU_ITEM_ID_1, 1, "Options 1");
        menu.add(1, Common.OPTIONS_MENU_ITEM_ID_2, 2, "Options 2");
        menu.add(1, Common.EXIT_APPLICATION, 2, "Exit");
        inflater.inflate(R.menu.fragment2_menu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()) {

            case Common.OPTIONS_MENU_ITEM_ID_1:
                Common.showMessage("Option Selected", "You selected Fragment 2 option 1",
                        getContext(), Common.MESSAGE_TYPE_INFORMATION);
                break;

            case Common.OPTIONS_MENU_ITEM_ID_2:
                Common.showMessage("Option Selected", "You selected Fragment 2 option 2",
                        getContext(), Common.MESSAGE_TYPE_INFORMATION);
                break;

            case Common.EXIT_APPLICATION:
                getActivity().finish();
                System.exit(0);
        }

        return true;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Spinner spinner = (Spinner)getActivity().findViewById(R.id.slLocations);

        //Get locations from database
        DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
        java.util.List<SpinnerItem> locations = dbhelper.GetLocations();

        ArrayAdapter<SpinnerItem> spinnerAdapter = new ArrayAdapter<SpinnerItem>(getActivity(),
                android.R.layout.simple_spinner_item, locations);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);







    }
}
