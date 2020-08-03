package com.eventure.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eventure.model.MyEvent;
import com.eventure.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseServiceImp implements FirebaseService {

    private String TAG = "FirebaseService";
    private FirebaseFirestore db;

    public FirebaseServiceImp() {
        db = FirebaseFirestore.getInstance();
        // FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
        // db.setFirestoreSettings(settings);
    }

    @Override
    public void write(String path, Object value) {
        db.collection(path)
                .add(value)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUser(String login) {
        return null;
}

    @Override
    public ArrayList<User> getAllUsers() {
        db.collection("users")
                .get()
                .
    }

    @Override
    public void changeUser(String login, User newUser) {

    }

    @Override
    public void addEvent(MyEvent event) {

    }

    @Override
    public MyEvent getEvent(String title) {
        return null;
    }

    @Override
    public ArrayList<MyEvent> getAllEvents() {
        return null;
    }

    @Override
    public void removeEvent(String title) {

    }


}
