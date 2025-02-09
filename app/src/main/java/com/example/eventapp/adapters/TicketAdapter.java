package com.example.eventapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.R;
import com.example.eventapp.models.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> ticketList;

    public TicketAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_card, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.ticketEventName.setText("Event: " + ticket.getEventName());
        holder.ticketDate.setText("Date: " + ticket.getEventDate());
        holder.ticketSeatType.setText("Seat Type: " + ticket.getSeatType());
        holder.ticketNumSeats.setText("Seats: " + ticket.getTicketCount());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketEventName, ticketDate, ticketSeatType, ticketNumSeats;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketEventName = itemView.findViewById(R.id.ticket_event_name);
            ticketDate = itemView.findViewById(R.id.ticket_date);
            ticketSeatType = itemView.findViewById(R.id.ticket_seat_type);
            ticketNumSeats = itemView.findViewById(R.id.ticket_num_seats);
        }
    }
}
