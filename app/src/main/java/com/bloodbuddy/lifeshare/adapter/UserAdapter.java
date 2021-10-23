package com.bloodbuddy.lifeshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bloodbuddy.lifeshare.Model.RaisedRequests;
import com.bloodbuddy.lifeshare.R;
import com.bloodbuddy.lifeshare.Model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<RaisedRequests> raisedRequestsList;

    public UserAdapter(Context context, List<RaisedRequests> raisedRequestsList) {
        this.context = context;
        this.raisedRequestsList = raisedRequestsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_display_adapter, parent , false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RaisedRequests raisedRequests = raisedRequestsList.get(position);

        holder.display_phone_number.setText(raisedRequests.getPhone_number());
        holder.display_blood_group.setText(raisedRequests.getBlood_group());
        holder.display_name.setText(raisedRequests.getName());
        holder.display_email.setText(raisedRequests.getEmail());
        holder.display_amount.setText(raisedRequests.getAmount());
        holder.display_hospital_area.setText(raisedRequests.getHospital_area());
        holder.display_hospital_city_pin.setText(raisedRequests.getHospital_city() + "  " +  raisedRequests.getHospital_pin());
        holder.display_hospital_name.setText(raisedRequests.getHospital_name());
        holder.display_patients_condition.setText(raisedRequests.getPatient_condition());
    }

    @Override
    public int getItemCount() {
        return raisedRequestsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView display_blood_group, display_phone_number, display_name, display_email;
        public TextView display_amount, display_hospital_name, display_hospital_area, display_hospital_city_pin, display_patients_condition ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            display_blood_group = itemView.findViewById(R.id.display_blood_group);
            display_name = itemView.findViewById(R.id.display_name);
            display_email = itemView.findViewById(R.id.display_email);
            display_phone_number = itemView.findViewById(R.id.display_phone_number);
            display_amount = itemView.findViewById(R.id.display_amount);
            display_hospital_name = itemView.findViewById(R.id.display_hospital_name);
            display_hospital_area = itemView.findViewById(R.id.display_hospital_area);
            display_hospital_city_pin = itemView.findViewById(R.id.display_hospital_city_pin);
            display_patients_condition = itemView.findViewById(R.id.display_patients_condition);


        }
    }
}
