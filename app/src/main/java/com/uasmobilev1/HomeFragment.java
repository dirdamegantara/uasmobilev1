package com.uasmobilev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find your CardViews
        CardView palman1Card = view.findViewById(R.id.palman1);
        CardView palman2Card = view.findViewById(R.id.palman2);
        CardView palman3Card = view.findViewById(R.id.palman3);
        CardView palmak1Card = view.findViewById(R.id.palmak1);
        CardView palmak2Card = view.findViewById(R.id.palmak2);
        CardView palmak3Card = view.findViewById(R.id.palmak3);

        // Set OnClickListener for each CardView
        palman1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palman1","Senin, 01/01/2024", "09:00 AM", "Rp400.000", "HARVEST", "Rute Palu - Manado");
            }
        });

        palman2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palman2","Selasa, 02/01/2024", "09:00 AM", "Rp375.000", "DAMRI", "Rute Palu - Manado");
            }
        });

        palman3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palman3","Rabu, 03/01/2024", "09:00 AM", "Rp400.000", "HARVEST", "Rute Palu - Manado");
            }
        });

        palmak1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palmak1","Senin, 01/01/2024", "07:00 AM", "Rp370.000", "DAMRI", "Rute Palu - Makassar");
            }
        });

        palmak2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palmak2","Selasa, 02/01/2024", "09:00 AM", "Rp350.000", "NEO TRANS", "Rute Palu - Makassar");
            }
        });

        palmak3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectBusFragment("palmak3", "Rabu, 03/01/2024", "09:00 AM", "Rp385.000", "HARVEST", "Rute Palu - Makassar");
            }
        });

        return view;
    }

    private void openSelectBusFragment(String routeid, String date, String time, String price, String busInfo, String route) {
        // Create an instance of the SelectBusFragment
        SelectBusFragment selectBusFragment = new SelectBusFragment();

        // Pass data as arguments to the fragment
        Bundle args = new Bundle();
        args.putString("routeid", routeid);
        args.putString("date", date);
        args.putString("time", time);
        args.putString("price", price);
        args.putString("busInfo", busInfo);
        args.putString("route", route);
        selectBusFragment.setArguments(args);

        // Get the FragmentManager and start a fragment transaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new one
        transaction.replace(R.id.container_layout, selectBusFragment);

        // Add the transaction to the back stack (optional)
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
