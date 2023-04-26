package com.example.astonproject.character.presentation;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.astonproject.R;
import com.example.astonproject.app.App;
import com.example.astonproject.app.di.AppComponent;
import com.example.astonproject.app.di.ViewModelFactory;
import com.example.astonproject.character.domain.model.CharacterDetail;
import com.example.astonproject.databinding.FragmentCharacterDetailBinding;

import javax.inject.Inject;

public class CharacterDetailFragment extends Fragment {

    FragmentCharacterDetailBinding binding;
    RecyclerView recyclerView;
    CharacterDetailViewModel viewModel;
    @Inject
    ViewModelFactory viewModelFactory;

    AppComponent component;


    private int id;

    public static CharacterDetailFragment newInstance(int id) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
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

    public static final String TAG = "Character Detail";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(CharacterDetailViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.load(id);
        viewModel.characterDetail.observe(getViewLifecycleOwner(), new Observer<CharacterDetail>() {
            @Override
            public void onChanged(CharacterDetail characterDetail) {
                content(characterDetail);
            }
        });

    }

    private void rvInit() {
        recyclerView = binding.rvEpisodes;

    }

    private void content (CharacterDetail characterDetail) {
        binding.tvCharacterName.setText(characterDetail.getName());
        binding.tvCharacterCreated.setText(characterDetail.getCreated());
        binding.tvCharacterGender.setText(characterDetail.getGender());
        binding.tvCharacterLocation.setText(characterDetail.getLocation().getName());
        binding.tvCharacterSpecies.setText(characterDetail.getSpecies());
        binding.tvCharacterStatus.setText(characterDetail.getStatus());
        binding.tvCharacterOrigin.setText(characterDetail.getOrigin().getName());
        Glide.with(binding.ivPhoto).load(characterDetail.getImage()).centerCrop().into(binding.ivPhoto);
        binding.tvCharacterEpisodesTitle.setText(R.string.episodes_title);
    }

}
