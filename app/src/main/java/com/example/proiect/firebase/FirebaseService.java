package com.example.proiect.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.model.MijlocFix;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    public static final String OBIECTE_INVENTAR_TABLE = "mijloace_fixe";
    private  static FirebaseService firebaseService;
    private DatabaseReference databaseRef ;
    private FirebaseService(){
        databaseRef = FirebaseDatabase.getInstance().getReference(OBIECTE_INVENTAR_TABLE) ;
    }
    public static FirebaseService getInstance(){
        if(firebaseService == null){
            synchronized (FirebaseService.class){
                if(firebaseService == null){
                    firebaseService = new FirebaseService() ;
                }
            }
        }
        return firebaseService ;
    }
    public void upsert(MijlocFix mijlocFix){
        if(mijlocFix == null){
            return ;
        }
        if(mijlocFix.getIdFirebase() == null || mijlocFix.getIdFirebase().trim().isEmpty())
        {
            String id = databaseRef.push().getKey() ;
            mijlocFix.setIdFirebase(id);
        }
        databaseRef.child(mijlocFix.getIdFirebase()).setValue(mijlocFix) ;
        }

        public void delete(MijlocFix mijlocFix)
        {
            if(mijlocFix ==null || mijlocFix.getIdFirebase() == null
            ||mijlocFix.getIdFirebase().trim().isEmpty()){
                return ;
            }
            databaseRef.child(mijlocFix.getIdFirebase()).removeValue() ;
            databaseRef.child(mijlocFix.getIdFirebase()).removeEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("FirebaseService","Remove is working") ;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i("FirebaseService","Remove is not  working") ;
                }
            });
        }
        public void attachDataChangeEventListner(Callback<List<MijlocFix>> callback){
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MijlocFix> mijloaceFixe = new ArrayList<>() ;
                for(DataSnapshot data: snapshot.getChildren()){
                    MijlocFix mijlocFix = data.getValue(MijlocFix.class) ;
                    if(mijlocFix != null){
                        mijloaceFixe.add(mijlocFix) ;
                    }
                }
                callback.runResultOnUiThread(mijloaceFixe);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseService","Data is not available") ;

            }
        });
        }
}
