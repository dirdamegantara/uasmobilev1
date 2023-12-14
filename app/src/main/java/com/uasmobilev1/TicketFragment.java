package com.uasmobilev1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TicketFragment extends Fragment {

    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;

    private TextView textViewTicketId;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewRoute;
    private TextView textViewBusInfo;
    private TextView textViewPrice;
    private TextView textViewSeatInfo;

    private int ticketId;
    private String date;
    private String time;
    private String route;
    private String busInfo;
    private String price;
    private String seatInfo;

    CardView tiket;
    List<TicketModel> ticketList = new ArrayList<>();
    // Other variables and methods if needed

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        ticketAdapter = new TicketAdapter(ticketList);
        recyclerView.setAdapter(ticketAdapter);
        // Retrieve ticket data from arguments

        //tiket = view.findViewById(R.id.tiket1);
//        textViewTicketId = view.findViewById(R.id.ticketId);
//        textViewDate = view.findViewById(R.id.textViewDate);
//        textViewTime = view.findViewById(R.id.textViewTime);
//        textViewRoute = view.findViewById(R.id.textViewRoute);
//        textViewBusInfo = view.findViewById(R.id.textViewBusInfo);
//        textViewPrice = view.findViewById(R.id.textViewPrice);
//        textViewSeatInfo = view.findViewById(R.id.textViewSeatInfo);

        Bundle args = getArguments();
        if (args != null) {

            ticketId = args.getInt("ticketId", 0);
            date = args.getString("date", "");
            time = args.getString("time", "");
            route = args.getString("route", "");
            busInfo = args.getString("busInfo", "");
            price = args.getString("price", "");
            seatInfo = args.getString("seatInfo", "");

            // Set the text of TextViews in TicketFragment
            //tiket.setVisibility(View.VISIBLE);
            textViewTicketId.setText("#"+ticketId);
            textViewDate.setText(date);
            textViewTime.setText(time);
            textViewRoute.setText(route);
            textViewBusInfo.setText(busInfo);
            textViewPrice.setText(price);
            textViewSeatInfo.setText(seatInfo);

        }
        retrieveTicketData();

        // Other initialization code for TicketFragment
        // ...

        return view;
    }

    private void retrieveTicketData() {

        // Get current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Access Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a reference to the user's tickets collection
        CollectionReference ticketsCollectionRef = db.collection("users").document(userId).collection("tickets");

        // Query the tickets collection
        ticketsCollectionRef.get().addOnCompleteListener(task -> {
            if (isAdded()) {
                if (task.isSuccessful()) {
                    if (!task.getResult().getDocuments().isEmpty()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            // Retrieve ticket data from the document
                            Object ticketIdObj = document.get("ticketId");
                            String ticketId = String.valueOf(ticketIdObj);
                            String date = document.getString("date");
                            String time = document.getString("time");
                            String route = document.getString("route");
                            String busInfo = document.getString("busInfo");
                            String price = document.getString("price");
                            String seatInfo = document.getString("seatInfo");

                            // Log the retrieved ticket data
                            Log.d("TicketFragment", "Ticket Data: " + ticketId + ", " + date + ", " + time + ", " + route + ", " + busInfo + ", " + price + ", " + seatInfo);

                            // Create a View for each ticket (not CardView)
                            View ticketView = createTicketCard(ticketId, date, time, route, busInfo, price, seatInfo);
                            ticketList.add(new TicketModel(ticketId, date, time, price, busInfo, route, seatInfo));
                            ticketAdapter.notifyDataSetChanged();

                            // Add the View to the container

                        }
                    } else {
                        // Handle the case where there are no documents in the result set
                    }
                } else {
                    // Handle failures
                }
            }
        });
    }

    private View createTicketCard(String ticketId, String date, String time, String route, String busInfo, String price, String seatInfo) {
        // Inflate the card layout
        View cardViewContent = LayoutInflater.from(requireContext()).inflate(R.layout.ticket_card_layout, null);

        // Find and set TextViews in the card layout
        TextView textViewTicketId = cardViewContent.findViewById(R.id.ticketid);
        TextView textViewDate = cardViewContent.findViewById(R.id.textViewDate);
        TextView textViewTime = cardViewContent.findViewById(R.id.textViewTime);
        TextView textViewRoute = cardViewContent.findViewById(R.id.textViewRoute);
        TextView textViewBusInfo = cardViewContent.findViewById(R.id.textViewBusInfo);
        TextView textViewPrice = cardViewContent.findViewById(R.id.textViewPrice);
        TextView textViewSeatInfo = cardViewContent.findViewById(R.id.textViewSeatInfo);

        textViewTicketId.setText("#" + ticketId);
        textViewDate.setText(date);
        textViewTime.setText(time);
        textViewRoute.setText(route);
        textViewBusInfo.setText(busInfo);
        textViewPrice.setText(price);
        textViewSeatInfo.setText(seatInfo);

        return cardViewContent;
    }
}