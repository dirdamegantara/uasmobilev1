package com.uasmobilev1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<TicketModel> ticketList;

    public TicketAdapter(List<TicketModel> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_card_layout, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketModel ticket = ticketList.get(position);

        holder.textViewTicketId.setText("#" + ticket.getTicketId());
        holder.textViewDate.setText(ticket.getDate());
        holder.textViewTime.setText(ticket.getTime());
        holder.textViewRoute.setText(ticket.getRoute());
        holder.textViewBusInfo.setText(ticket.getBusInfo());
        holder.textViewPrice.setText(ticket.getPrice());
        holder.textViewSeatInfo.setText(ticket.getSeatInfo());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTicketId;
        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewRoute;
        public TextView textViewBusInfo;
        public TextView textViewPrice;
        public TextView textViewSeatInfo;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTicketId = itemView.findViewById(R.id.ticketid);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewRoute = itemView.findViewById(R.id.textViewRoute);
            textViewBusInfo = itemView.findViewById(R.id.textViewBusInfo);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewSeatInfo = itemView.findViewById(R.id.textViewSeatInfo);
        }
    }
}