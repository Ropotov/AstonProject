package com.example.astonproject.location.presentation.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astonproject.app.App;
import com.example.astonproject.app.di.AppComponent;
import com.example.astonproject.app.di.ViewModelFactory;
import com.example.astonproject.character.domain.model.CharacterResult;
import com.example.astonproject.databinding.FragmentLocationDetailBinding;
import com.example.astonproject.location.presentation.detail.adapter.LocationDetailAdapter;
import com.example.astonproject.location.domain.model.LocationResult;

import java.util.List;

import javax.inject.Inject;

public class LocationDetailFragment extends Fragment {

    FragmentLocationDetailBinding binding;
    RecyclerView recyclerView;
    LocationDetailViewModel viewModel;
    LocationDetailAdapter adapter;
    AppComponent component;
    String characters = "";
    private int id;

    @Inject
    ViewModelFactory viewModelFactory;

    public static final String TAG = "Location Detail";

    public static LocationDetailFragment newInstance(int id) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App application = (App) getActivity().getApplication();
        component = application.getComponent();
        component.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(LocationDetailViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvInit();
        viewModel.load(id);
        viewModel.locationDetail.observe(getViewLifecycleOwner(), new Observer<LocationResult>() {
            @Override
            public void onChanged(LocationResult locationResult) {
                content(locationResult);
                toListStringNumber(locationResult.getResidents());
                viewModel.loadListCharacter(characters);
                viewModel.listCharacters.observe(getViewLifecycleOwner(), new Observer<List<CharacterResult>>() {
                    @Override
                    public void onChanged(List<CharacterResult> characterResults) {
                        adapter.submitList(characterResults);
                    }
                });

            }
        });
    }

    private void rvInit(){
        recyclerView = binding.rvResidents;
        adapter = new LocationDetailAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                binding.rvResidents.getContext(),
                DividerItemDecoration.VERTICAL
        ));
    }

    @SuppressLint("SetTextI18n")
    private void content(LocationResult locationResult) {
        binding.tvLocationCreated.setText("Created: " + locationResult.getCreated());
        binding.tvLocationDimension.setText("Dimension: " + locationResult.getDimension());
        binding.tvLocationName.setText(locationResult.getName());
        binding.tvLocationType.setText("Type: " + locationResult.getType());
        binding.tvCharactersTitle.setText("Residents: ");
    }

    private void toListStringNumber(@NonNull List<String> list) {
        for (String i : list) {
            String newString = i.substring(i.lastIndexOf('/') + 1);
            characters += newString + ",";
        }
    }
}
