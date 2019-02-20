package com.lm.scanner;


import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import static android.support.v7.app.AlertDialog.*;


public class Fragment3 extends Fragment {

    private TextView textView;
    private int selectedLocationId;
    private String newLocationText;
    private ArrayAdapter<SpinnerItem> spinnerAdapter;
    private Spinner spinner;

    public Fragment3() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_fragment3, container, false);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(1, Common.OPTIONS_MENU_ITEM_ID_1, 1, "Options 1");
        menu.add(1, Common.OPTIONS_MENU_ITEM_ID_2, 2, "Options 2");
        menu.add(1, Common.EXIT_APPLICATION, 2, "Exit");
        inflater.inflate(R.menu.fragment3_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case Common.OPTIONS_MENU_ITEM_ID_1:
                Common.showMessage("Option Selected", "You selected Fragment 3 option 1",
                        getContext(), Common.MESSAGE_TYPE_INFORMATION);
                break;

            case Common.OPTIONS_MENU_ITEM_ID_2:
                Common.showMessage("Option Selected", "You selected Fragment 3 option 2",
                        getContext(), Common.MESSAGE_TYPE_INFORMATION);
                break;


            case Common.EXIT_APPLICATION:
                getActivity().finish();
                System.exit(0);
        }

        return true;
    }


    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        GetLocations();

        /** User selected an item from the Current Locations list */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                SpinnerItem selectedItem = (SpinnerItem)adapterView.getItemAtPosition(pos);
                TextView currentLocationText  = getView().findViewById(R.id.txtCurrentLocation);
                currentLocationText.setText(selectedItem.value);
                selectedLocationId = selectedItem.valueId;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        /****************************
         * Update Location Button
         ****************************/
        Button btnUpdateLocation = getView().findViewById(R.id.btnUpdateLocation);
        btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv  = getView().findViewById(R.id.txtCurrentLocation);
                newLocationText  = tv.getText().toString().trim();

                if(newLocationText.length() > 0) {

                    DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
                    boolean status = dbhelper.UpdateLocation(selectedLocationId,newLocationText);
                    if(status == true) {
                        Common.showMessage("Update Status","Location updated",getActivity(),Common.MESSAGE_TYPE_SUCCESS );
                        GetLocations();
                        tv.setText("");
                    }
                    else {
                        Common.showMessage("Update Status","Error updating Location",getActivity(),Common.MESSAGE_TYPE_ERROR );
                    }
                }
                else {
                    Common.showMessage("Input Error","Please enter a value before submitting.",getActivity(),Common.MESSAGE_TYPE_ERROR );
                }
            }
        });

        /****************************
         * Add New Location button
         ****************************/
        Button btnNewLocation = getView().findViewById(R.id.btnNewLocation);
        btnNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv  = getView().findViewById(R.id.txtNewLocation);
                newLocationText  = tv.getText().toString().trim();

                if(newLocationText.length() > 0)
                {
                    DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
                    boolean status = dbhelper.AddNewLocation(newLocationText);

                    if(status == true) {
                        Common.showMessage("Add New Location Status","Location added",getActivity(),Common.MESSAGE_TYPE_SUCCESS );
                        GetLocations();
                        tv.setText("");
                    }
                    else {
                        Common.showMessage("Add New Location Status","Error adding Location",getActivity(),Common.MESSAGE_TYPE_ERROR );
                    }
                }
                else {
                    Common.showMessage("Input Error","Please enter a value before submitting.",getActivity(),Common.MESSAGE_TYPE_ERROR );
                }
            }
        });


        /*****************************************
         * Confirm delete action before Deleting
         ******************************************/
        Button btnDeleteLocation = getView().findViewById(R.id.btnDeleteLocation);
        btnDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv  = getView().findViewById(R.id.txtCurrentLocation);
                String LocationText  = tv.getText().toString();
                ConfirmDelete(LocationText);
            }
        });
    }

    /***************************
     * Call Delete action
     ***************************/
    private void DeleteLocation() {

        DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
        boolean status = dbhelper.DeleteLocation(selectedLocationId);

        if(status == true) {
            Common.showMessage("Delete Location Status","Location deleted",getActivity(),Common.MESSAGE_TYPE_SUCCESS );
            GetLocations();
        }
        else {
            Common.showMessage("Delete Location Status","Error deleting Location",getActivity(),Common.MESSAGE_TYPE_ERROR );
        }
    }

    /*****************************************
     * Get Locations for display
     ******************************************/
    private void GetLocations() {

        spinner = (Spinner)getActivity().findViewById(R.id.dlCurrentLocations);

        //Get locations from database
        DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
        java.util.List<SpinnerItem> locations = dbhelper.GetLocations();

         spinnerAdapter = new ArrayAdapter<SpinnerItem>(getActivity(),
                android.R.layout.simple_spinner_item, locations);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
    }

    //Get confirmation from User before deleting location
    private void ConfirmDelete(String locationText) {

        Builder alertDialog = new Builder(getActivity());
        alertDialog.setTitle("Delete Confirmation");
        alertDialog.setMessage("Delete Location => " + locationText + "?");
        alertDialog.setIcon(R.drawable.info_icon);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                    DeleteLocation();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create().show();
    }








}
