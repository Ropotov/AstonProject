package com.example.astonproject.character.presentation.detail;

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

import com.bumptech.glide.Glide;
import com.example.astonproject.R;
import com.example.astonproject.app.App;
import com.example.astonproject.app.di.AppComponent;
import com.example.astonproject.app.di.ViewModelFactory;
import com.example.astonproject.character.domain.model.CharacterDetail;
import com.example.astonproject.character.presentation.detail.adapter.DetailAdapter;
import com.example.astonproject.databinding.FragmentCharacterDetailBinding;
import com.example.astonproject.episode.domain.model.EpisodeResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class CharacterDetailFragment extends Fragment {

    FragmentCharacterDetailBinding binding;
    RecyclerView recyclerView;
    CharacterDetailViewModel viewModel;
    DetailAdapter adapter;
    AppComponent component;
    String episodes = "";
    private int id;

    @Inject
    ViewModelFactory viewModelFactory;

    public static final String TAG = "Character Detail";
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
        rvInit();
        viewModel.load(id);
        viewModel.characterDetail.observe(getViewLifecycleOwner(), new Observer<CharacterDetail>() {
            @Override
            public void onChanged(CharacterDetail characterDetail) {
                content(characterDetail);
                toListStringNumber(characterDetail.getEpisode());
                viewModel.loadListEpisodes(episodes);
                viewModel.listEpisodeDetail.observe(getViewLifecycleOwner(), new Observer<List<EpisodeResult>>() {
                    @Override
                    public void onChanged(List<EpisodeResult> episodeResults) {
                        adapter.submitList(episodeResults);
                    }
                });
            }
        });
    }

    private void rvInit() {
        recyclerView = binding.rvEpisodes;
        adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                binding.rvEpisodes.getContext(),
                DividerItemDecoration.VERTICAL
        ));
    }

    @SuppressLint("SetTextI18n")
    private void content(CharacterDetail characterDetail) {
        binding.tvCharacterName.setText(characterDetail.getName());
        binding.tvCharacterCreated.setText(("Created: ").concat(characterDetail.getCreated()));
        binding.tvCharacterGender.setText(("Gender: ").concat(characterDetail.getGender()));
        binding.tvCharacterLocation.setText(("Location: ").concat(characterDetail.getLocation().getName()));
        binding.tvCharacterSpecies.setText(("Species: ").concat(characterDetail.getSpecies()));
        binding.tvCharacterStatus.setText(("Status: ").concat(characterDetail.getStatus()));
        binding.tvCharacterOrigin.setText(("Origin: ").concat(characterDetail.getOrigin().getName()));
        Glide.with(binding.ivPhoto).load(characterDetail.getImage()).centerCrop().into(binding.ivPhoto);
        binding.tvCharacterEpisodesTitle.setText(R.string.episodes_title);
    }

    private void toListStringNumber(@NonNull List<String> list) {
        for (String i : list) {
            String newString = i.substring(i.lastIndexOf('/') + 1);
            episodes += newString + ",";
        }
    }
}
