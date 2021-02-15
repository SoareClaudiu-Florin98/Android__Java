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
import android.widget.ListView;
import android.widget.Toast;

import com.example.proiect.FormularMijlocFix;
import com.example.proiect.FormularObiectInventar;
import com.example.proiect.JSONParser.ObiecteDeInventarJsonParser;
import com.example.proiect.R;
import com.example.proiect.adapters.ObiectInventarAdapter;
import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.database.model.MijlocFix;
import com.example.proiect.database.model.ObiectInventar;
import com.example.proiect.database.service.MijlocFixService;
import com.example.proiect.database.service.ObiectInventarService;
import com.example.proiect.firebase.FirebaseService;
import com.example.proiect.network.HttpManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static android.app.Activity.RESULT_OK;

public class ObiectInventarFragment extends Fragment {
    private static final int ADAUGA_OB_INVENTAR_REQUEST_CODE = 201;
    private static final int UPDATE_OBIECT_INVENTAR_REQUEST_CODE = 202;
    private ListView lvObiecteDeInventar;
    private List<ObiectInventar> obiecteInventar= new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner() ;
    public static final String OBIECTE_INVENTAR_URL =  "https://jsonkeeper.com/b/1BQZ" ;
    private FloatingActionButton fab ;
    private ObiectInventarService obiectInventarService;
    public ObiectInventarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_obiect_inventar, container, false);
        lvObiecteDeInventar = view.findViewById(R.id.lv_obiectInventar);
        fab= view.findViewById(R.id.floatingActionButton) ;
        lvObiecteDeInventar.setOnItemClickListener(updateEventListener());
        lvObiecteDeInventar.setOnItemLongClickListener(deleteEventListener());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), FormularObiectInventar.class);
                startActivityForResult(intent, ADAUGA_OB_INVENTAR_REQUEST_CODE);
            }
        });
        addObiecteInventar();
        getObiecteDeInventarFromNetwork();

        obiectInventarService = new ObiectInventarService(getContext().getApplicationContext());
        obiectInventarService.getAll(getAllFromDbCallback());

        return view ;

    }
    private Callback<List<ObiectInventar>> getAllFromDbCallback() {
        return new Callback<List<ObiectInventar>>() {
            @Override
            public void runResultOnUiThread(List<ObiectInventar> result) {
                if (result != null) {
                    obiecteInventar.clear();
                    obiecteInventar.addAll(result);
                    notifyInternalAdapter();
                }
            }
        };
    }
    private Callback<ObiectInventar> insertIntoDbCallback() {
        return new Callback<ObiectInventar>() {
            @Override
            public void runResultOnUiThread(ObiectInventar result) {
                if (result != null) {
                    obiecteInventar.add(result);
                    notifyInternalAdapter();
                }
            }
        };
    }

    private Callback<ObiectInventar> updateToDbCallback() {
        return new Callback<ObiectInventar>() {
            @Override
            public void runResultOnUiThread(ObiectInventar result) {
                if (result != null) {
                    for (ObiectInventar obiectInventar : obiecteInventar) {
                        if (obiectInventar.getId() == result.getId()) {
                            obiectInventar.setDenumire(result.getDenumire());
                            obiectInventar.setValoare(result.getValoare());
                            obiectInventar.setDataAdaugare(result.getDataAdaugare());
                            obiectInventar.setCantitate(result.getCantitate());
                            obiectInventar.setCategorie(result.getCategorie());
                            break;
                        }
                    }
                    notifyInternalAdapter();
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            ObiectInventar obiectInventar = (ObiectInventar) data.getSerializableExtra(FormularObiectInventar.NEW_OBIECT_INVENTAR_KEY);

            if (requestCode == ADAUGA_OB_INVENTAR_REQUEST_CODE) {
                obiectInventarService.insert(insertIntoDbCallback(),obiectInventar);
            } else if (requestCode == UPDATE_OBIECT_INVENTAR_REQUEST_CODE) {
                obiectInventarService.update(updateToDbCallback(), obiectInventar);
            }
        }

    }
    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext().getApplicationContext(), FormularObiectInventar.class);
                intent.putExtra(FormularObiectInventar.NEW_OBIECT_INVENTAR_KEY, obiecteInventar.get(position));
                startActivityForResult(intent, UPDATE_OBIECT_INVENTAR_REQUEST_CODE);
            }
        };
    }
    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    obiecteInventar.remove(position);
                    notifyInternalAdapter();
                }
            }
        };
    }
    private AdapterView.OnItemLongClickListener deleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                obiectInventarService.delete(deleteToDbCallback(position), obiecteInventar.get(position));
                return true;
            }
        };
    }
    public static ObiectInventarFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ObiectInventarFragment fragment = new ObiectInventarFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void addObiecteInventar(){
        ObiectInventarAdapter adapter = new ObiectInventarAdapter(getContext().getApplicationContext(),R.layout.lv_row_obiecte_inventar, obiecteInventar , getLayoutInflater()) ;
        lvObiecteDeInventar.setAdapter(adapter);
    }
    public void notifyInternalAdapter(){
        ArrayAdapter adapter = (ArrayAdapter) lvObiecteDeInventar.getAdapter();
        adapter.notifyDataSetChanged();
    }
    private void getObiecteDeInventarFromNetwork(){
        Callable<String> asyncOp = new HttpManager(OBIECTE_INVENTAR_URL) ;
        Callback<String> mainThreadOp = new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                obiecteInventar.addAll(ObiecteDeInventarJsonParser.fromJSONObiectDeInventar(result)) ;
                notifyInternalAdapter();
            }

        } ;
        asyncTaskRunner.executeAsync(asyncOp,mainThreadOp);
    }
}