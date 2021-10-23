package com.bloodbuddy.lifeshare.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodbuddy.lifeshare.AdminLogin;
import com.bloodbuddy.lifeshare.BloodRequest;
import com.bloodbuddy.lifeshare.Model.RaisedRequests;
import com.bloodbuddy.lifeshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    private Context context;
    private List<RaisedRequests> raisedRequestsList;
    private int request_no;
    private DatabaseReference databaseRef ;

    public AdminAdapter(Context context, List<RaisedRequests> raisedRequestsList) {
        this.context = context;
        this.raisedRequestsList = raisedRequestsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_admin_adapter, parent , false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
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

        holder.accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("com.bloodbuddy.lifeshare.accepted_requests" , context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                request_no = sharedPreferences.getInt("request_no" , 0);
                request_no  = request_no + 1;
                editor.putInt("request_no" ,request_no);
                editor.apply();
                databaseRef = FirebaseDatabase.getInstance().getReference().child("accepted_requests").child(Integer.toString(request_no));
                HashMap userData = new HashMap();
                userData.put("uid" , raisedRequests.getUid());
                userData.put("name", raisedRequests.getName());
                userData.put("phone_number", raisedRequests.getPhone_number());
                userData.put("email",raisedRequests.getEmail());
                userData.put("amount", raisedRequests.getAmount());
                userData.put("blood_group", raisedRequests.getBlood_group());
                userData.put("hospital_name", raisedRequests.getHospital_name());
                userData.put("hospital_area", raisedRequests.getHospital_area());
                userData.put("hospital_city", raisedRequests.getHospital_city());
                userData.put("hospital_pin", raisedRequests.getHospital_pin());
                userData.put("patient_condition", raisedRequests.getPatient_condition());
                userData.put("request_no", Integer.toString(request_no));
                databaseRef.updateChildren(userData).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                        } else {
                            String error = task.getException().toString();
                        }
                    }
                });

                databaseRef = FirebaseDatabase.getInstance().getReference();
                Query query = databaseRef.child("raised_requests").orderByChild("request_no").equalTo(raisedRequests.getRequest_no());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        holder.reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef = FirebaseDatabase.getInstance().getReference();
                Query query = databaseRef.child("raised_requests").orderByChild("request_no").equalTo(raisedRequests.getRequest_no());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return raisedRequestsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView display_blood_group, display_phone_number, display_name, display_email;
        public TextView display_amount, display_hospital_name, display_hospital_area, display_hospital_city_pin, display_patients_condition ;
        public Button reject_button , accept_button;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accept_button = itemView.findViewById(R.id.accept_button);
            reject_button = itemView.findViewById(R.id.reject_button);
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