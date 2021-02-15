package com.example.proiect.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.proiect.FormularMijlocFix;
import com.example.proiect.R;
import com.example.proiect.adapters.MijlocFixAdapter;
import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.model.MijlocFix;
import com.example.proiect.database.service.MijlocFixService;
import com.example.proiect.firebase.FirebaseService;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class  MijlocFixFragment extends Fragment {
    private static final int ADAUGA_MIJLOC_FIX_REQUEST_CODE = 201;
    private static final int UPDATE_MIJLOC_FIX_REQUEST_CODE = 202;
    private ListView lvMijlocFix;
    private Button btnAdauga;
    private List<MijlocFix> mijloaceFixe= new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner() ;
    FirebaseService firebaseService ;
    private MijlocFixService mijlocFixService;
    private int selectedIndex = -1 ;


    public MijlocFixFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mijloc_fix, container, false);
        lvMijlocFix = view.findViewById(R.id.lv_mijlocFix);
        btnAdauga = view.findViewById(R.id.btn_mijlocFix);

        lvMijlocFix.setOnItemClickListener(updateEventListener());
        lvMijlocFix.setOnItemLongClickListener(deleteEventListener());


        addMijloaceFixeAdapter();
        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), FormularMijlocFix.class);
                startActivityForResult(intent, ADAUGA_MIJLOC_FIX_REQUEST_CODE);

            }
        });
        mijlocFixService = new MijlocFixService(getContext().getApplicationContext());


        mijlocFixService.getAll(getAllFromDbCallback());
        firebaseService= FirebaseService.getInstance() ;

        return view;
    }
    private Callback<List<MijlocFix>> dataChangeEventListener() {
        return new Callback<List<MijlocFix>>() {
            @Override
            public void runResultOnUiThread(List<MijlocFix> result) {
                if (result != null) {
                    mijloaceFixe.clear();
                    for(MijlocFix mijlocFix:result){
                        mijlocFixService.insert(insertIntoDbCallback(),mijlocFix);
                    }
                    mijloaceFixe.addAll(result);
                    notifyInternalAdapter();

                }
            }
        };
    }

    private Callback<List<MijlocFix>> getAllFromDbCallback() {
        return new Callback<List<MijlocFix>>() {
            @Override
            public void runResultOnUiThread(List<MijlocFix> result) {
                if (result != null) {
                    mijloaceFixe.clear();
                    mijloaceFixe.addAll(result);
                    notifyInternalAdapter();
                    firebaseService.attachDataChangeEventListner(dataChangeEventListener());

                }
            }
        };
    }

    private Callback<MijlocFix> insertIntoDbCallback() {
        return new Callback<MijlocFix>() {
            @Override
            public void runResultOnUiThread(MijlocFix result) {
                if (result != null) {
                    mijloaceFixe.add(result);
                    firebaseService.upsert(result);
                    notifyInternalAdapter();
                }
            }
        };
    }

    private Callback<MijlocFix> updateToDbCallback() {
        return new Callback<MijlocFix>() {
            @Override
            public void runResultOnUiThread(MijlocFix result) {
                if (result != null) {
                    for (MijlocFix mijlocFix : mijloaceFixe) {
                        if (mijlocFix.getId() == result.getId()) {
                            mijlocFix.setDenumire(result.getDenumire());
                            mijlocFix.setValoare(result.getValoare());
                            mijlocFix.setDataAdaugare(result.getDataAdaugare());
                            firebaseService.upsert(mijlocFix);
                            break;
                        }
                    }
                    notifyInternalAdapter();
                }
            }
        };
    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    if(mijloaceFixe.get(position) != null){
                        firebaseService.delete(mijloaceFixe.get(position));
                        mijloaceFixe.remove(position);
                    }

                    notifyInternalAdapter();
                }
            }
        };
    }

    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext().getApplicationContext(), FormularMijlocFix.class);
                intent.putExtra(FormularMijlocFix.NEW_MijlocFix_KEY, mijloaceFixe.get(position));
                startActivityForResult(intent, UPDATE_MIJLOC_FIX_REQUEST_CODE);
            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mijlocFixService.delete(deleteToDbCallback(position), mijloaceFixe.get(position));
                return true;
            }
        };
    }

    public static MijlocFixFragment newInstance() {
        Bundle args = new Bundle();
        MijlocFixFragment fragment = new MijlocFixFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            MijlocFix mijlocFix = (MijlocFix) data.getSerializableExtra(FormularMijlocFix.NEW_MijlocFix_KEY);
            String id = selectedIndex!=-1?mijloaceFixe.get(selectedIndex).getIdFirebase():null ;
            mijlocFix.setIdFirebase(id);
            if (requestCode == ADAUGA_MIJLOC_FIX_REQUEST_CODE) {
                mijlocFixService.insert(insertIntoDbCallback(),mijlocFix);
            } else if (requestCode == UPDATE_MIJLOC_FIX_REQUEST_CODE) {
                mijlocFixService.update(updateToDbCallback(), mijlocFix);
            }
        }
    }

    private void addMijloaceFixeAdapter(){
        MijlocFixAdapter adapter = new MijlocFixAdapter(getContext().getApplicationContext(),R.layout.lv_row_mijloace_fixe, mijloaceFixe , getLayoutInflater()) ;
        lvMijlocFix.setAdapter(adapter);
    }
    public void notifyInternalAdapter(){
        ArrayAdapter adapter = (ArrayAdapter) lvMijlocFix.getAdapter();
        adapter.notifyDataSetChanged();
    }
}