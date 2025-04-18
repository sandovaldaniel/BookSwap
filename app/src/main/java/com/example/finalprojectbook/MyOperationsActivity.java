package com.example.finalprojectbook;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class MyOperationsActivity extends AppCompatActivity {

    ListView listOps;
    ArrayAdapter<String> adapter;
    List<String> opList = new ArrayList<>();
    String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_operations);

        currentUserEmail = getIntent().getStringExtra("user_email");

        listOps = findViewById(R.id.listOps);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opList);
        listOps.setAdapter(adapter);

        DatabaseReference opRef = FirebaseDatabase.getInstance().getReference("operations");

        opRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                opList.clear();
                for (DataSnapshot opSnap : snapshot.getChildren()) {
                    String type = opSnap.child("type").getValue(String.class);
                    String date = opSnap.child("date").getValue(String.class);
                    String from = opSnap.child("fromUserEmail").getValue(String.class);
                    String to = opSnap.child("toUserEmail").getValue(String.class);

                    if (currentUserEmail.equals(from) || currentUserEmail.equals(to)) {
                        opList.add(type + " on " + date + "\nFrom: " + from + "\nTo: " + to);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
