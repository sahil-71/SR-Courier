package com.example.sr_courier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {
    private ArrayList<Courier> couriers;
    private Context context;

    public CourierAdapter(ArrayList<Courier> couriers, Context context) {
        this.couriers = couriers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_couriers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Courier courier = couriers.get(position);
        holder.txtSerialNumber.setText(courier.getUID());
        holder.txtDate.setText(courier.getDate());
        holder.txtConsignmentNumber_list.setText(courier.getConsignmentNumber());
        holder.txtSenderName_list.setText(courier.getSenderName());
        holder.txtSenderMobile_list.setText(courier.getSenderMobile());
        holder.txtReceiverName_list.setText(courier.getReceiverName());
        holder.txtReceiverMobile_list.setText(courier.getReceiverMobile());
        holder.txtDestination_list.setText(courier.getDestination());
    }

    @Override
    public int getItemCount() {
        return couriers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView txtSerialNumber, txtDate, txtConsignmentNumber_list, txtSenderName_list,
                txtSenderMobile_list, txtReceiverName_list, txtReceiverMobile_list, txtDestination_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            txtSerialNumber = itemView.findViewById(R.id.txtSerialNumber);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtConsignmentNumber_list = itemView.findViewById(R.id.txtConsignmentNumber_list);
            txtSenderName_list = itemView.findViewById(R.id.txtSenderName_list);
            txtSenderMobile_list = itemView.findViewById(R.id.txtSenderMobile_list);
            txtReceiverName_list = itemView.findViewById(R.id.txtReceiverName_list);
            txtReceiverMobile_list = itemView.findViewById(R.id.txtReceiverMobile_list);
            txtDestination_list = itemView.findViewById(R.id.txtDestination_list);
        }
    }
}
