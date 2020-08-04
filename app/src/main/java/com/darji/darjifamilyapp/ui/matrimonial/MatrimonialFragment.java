package com.darji.darjifamilyapp.ui.matrimonial;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darji.darjifamilyapp.Adapter.MatrimonialAdapter;
import com.darji.darjifamilyapp.Adapter.MatrimonialView;
import com.darji.darjifamilyapp.Model.ApiClient;
import com.darji.darjifamilyapp.Model.ApiInterface;
import com.darji.darjifamilyapp.Model.MatrimonialData;
import com.darji.darjifamilyapp.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatrimonialFragment extends Fragment implements MatrimonialAdapter.OnMatrimonialListener {
    private RecyclerView matrimonial;
    private List<MatrimonialData> matrimonialList;
    private MatrimonialAdapter matrimonialAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_matrimonial, container, false);

        matrimonial = root.findViewById(R.id.matrimonial);
        matrimonial.setHasFixedSize(true);
        matrimonial.setLayoutManager(new LinearLayoutManager(getContext()));

        //get the spinner from the xml.
        Spinner dropdown = root.findViewById(R.id.candidatesearch_nri);
//create a list of items for the spinner.
        String[] items = new String[]{"Yes", "No"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);



        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MatrimonialData>> data = apiService.getMatrimonial();

        data.enqueue(new Callback<List<MatrimonialData>>() {
            @Override
            public void onResponse(Call<List<MatrimonialData>> call, Response<List<MatrimonialData>> response) {
                matrimonialList = response.body();
                if(matrimonialList!=null)
                    CallData();
            }

            @Override
            public void onFailure(Call<List<MatrimonialData>> call, Throwable t) {
                //Toast.makeText(getContext(),"Network Failed!",Toast.LENGTH_LONG).show();
            }
        });



        return root;
    }

    public void CallData(){
        matrimonialAdapter = new MatrimonialAdapter(getActivity(),matrimonialList, this);
        matrimonial.setAdapter(matrimonialAdapter);
    }

    @Override
    public void onMatrimonialClick(int position) {

        Gson gson = new Gson();
        String jsonObj = gson.toJson(matrimonialList.get(position));

        Intent intent = new Intent(getContext(),MatrimonialView.class);
        intent.putExtra("data",jsonObj);
        startActivity(intent);

    }
}