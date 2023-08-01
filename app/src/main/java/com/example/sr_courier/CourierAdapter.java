package com.example.sr_courier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CourierAdapter extends ArrayAdapter<Courier> {
    private ArrayList<Courier> couriers;
    private Context context;

    public CourierAdapter(Activity context, ArrayList<Courier> couriers) {
        super(context, 0, couriers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_courier_item, parent, false);
        }
        Courier currentCourier = getItem(position);
        TextView txtSerialNumber, txtDate, txtConsignmentNumber_list, txtCourierProvider, txtSenderName_list,
                txtSenderMobile_list, txtReceiverName_list, txtReceiverMobile_list, txtDestination_list;


        txtSerialNumber = listItemView.findViewById(R.id.txtSerialNumber);
        txtDate = listItemView.findViewById(R.id.txtDate);
        txtConsignmentNumber_list = listItemView.findViewById(R.id.txtConsignmentNumber_list);
        txtCourierProvider = listItemView.findViewById(R.id.txtCourierProvider);
        txtSenderName_list = listItemView.findViewById(R.id.txtSenderName_list);
        txtSenderMobile_list = listItemView.findViewById(R.id.txtSenderMobile_list);
        txtReceiverName_list = listItemView.findViewById(R.id.txtReceiverName_list);
        txtReceiverMobile_list = listItemView.findViewById(R.id.txtReceiverMobile_list);
        txtDestination_list = listItemView.findViewById(R.id.txtDestination_list);

        txtSerialNumber.setText(String.valueOf(currentCourier.getUID()));
        txtDate.setText(currentCourier.getDate());
        txtConsignmentNumber_list.setText(currentCourier.getConsignmentNumber());
        txtCourierProvider.setText(currentCourier.getCourierProvider());
        txtSenderName_list.setText(currentCourier.getSenderName());
        txtSenderMobile_list.setText(currentCourier.getSenderMobile());
        txtReceiverName_list.setText(currentCourier.getReceiverName());
        txtReceiverMobile_list.setText(currentCourier.getReceiverMobile());
        txtDestination_list.setText(currentCourier.getDestination());

        return listItemView;

    }
}