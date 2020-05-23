package com.towhid.firebaserecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    final int ITEM_LODE_COUNT = 21;
    int total_item = 0, last_visible_item;
    RecyclerViewAdapter adapter;
    boolean isLoading = false, isMaxData = false;

    String last_node = "", last_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rec);


        getLastKeyFromFirebase();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        getUser();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                total_item=layoutManager.getItemCount();
                last_visible_item=layoutManager.findLastVisibleItemPosition();

                if (!isLoading&&total_item<=(last_visible_item+ITEM_LODE_COUNT)){
                    getUser();
                    isLoading=true;
                }
            }
        });





    }

    private void getUser() {
        if (!isMaxData) {
            Query query;
            if (TextUtils.isEmpty(last_node)) {
                query = FirebaseDatabase.getInstance().getReference()
                        .child("payload")
                        .orderByKey()
                        .limitToFirst(ITEM_LODE_COUNT);
            } else
                query = FirebaseDatabase.getInstance().getReference()
                        .child("payload")
                        .orderByKey()
                        .startAt(last_node)
                        .limitToFirst(ITEM_LODE_COUNT);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        ArrayList<User> newUsers = new ArrayList<>();
                        for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                            newUsers.add(userSnapShot.getValue(User.class));
                        }
                        last_node = newUsers.get(newUsers.size() - 1).getId();
                        if (!last_node.equals(last_key))
                            newUsers.remove(newUsers.size() - 1);
                        else
                            last_node = "end";

                        adapter.addAll(newUsers);
                        isLoading = false;
                    } else {
                        isLoading = false;
                        isMaxData = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    isLoading = false;
                }
            });

        }


    }

    private void getLastKeyFromFirebase() {
        Query getLastKey = FirebaseDatabase.getInstance().getReference()
                .child("payload")
                .orderByKey()
                .limitToLast(1);

        getLastKey.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot lastkey : dataSnapshot.getChildren())
                    last_key = lastkey.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Can not get Last key", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
