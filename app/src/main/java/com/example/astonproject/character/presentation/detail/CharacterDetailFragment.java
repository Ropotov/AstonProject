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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.astonproject.R;
import com.example.astonproject.app.App;
import com.example.astonproject.app.CustomizeAppBarTitle;
import com.example.astonproject.app.Navigator;
import com.example.astonproject.app.di.AppComponent;
import com.example.astonproject.app.di.ViewModelFactory;
import com.example.astonproject.character.domain.model.CharacterDetail;
import com.example.astonproject.character.presentation.detail.adapter.DetailAdapter;
import com.example.astonproject.databinding.FragmentCharacterDetailBinding;
import com.example.astonproject.episode.presentation.detail.EpisodeDetailFragment;
import com.example.astonproject.location.presentation.detail.LocationDetailFragment;

import java.util.List;

import javax.inject.Inject;

public class CharacterDetailFragment extends Fragment implements CustomizeAppBarTitle {

    FragmentCharacterDetailBinding binding;
    RecyclerView recyclerView;
    CharacterDetailViewModel viewModel;
    DetailAdapter adapter;
    CharacterDetail detailCharacter;
    AppComponent component;
    String episodes = "";
    private int id;
    Navigator navigator;


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
        App application = (App) requireActivity().getApplication();
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
        navigator = (Navigator) getActivity();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvInit();
        loadContent();
    }


    private void addClickListeners() {
        String originUrl = detailCharacter.getOrigin().getUrl();
        String locationUrl = detailCharacter.getLocation().getUrl();
        if (!locationUrl.equals("")) {
            binding.locationLearnMore.setVisibility(View.VISIBLE);
            binding.locationLearnMore.setOnClickListener(view -> {
                String id = locationUrl.substring(locationUrl.lastIndexOf("/") + 1);
                navigator.replaceFragment(
                        LocationDetailFragment.newInstance((Integer.parseInt(id))));
            });
        }

        if (!originUrl.equals("")) {
            binding.tvOriginLearnMore.setVisibility(View.VISIBLE);
            binding.tvOriginLearnMore.setOnClickListener(view -> {
                String id = originUrl.substring(originUrl.lastIndexOf("/") + 1);
                navigator.replaceFragment(
                        LocationDetailFragment.newInstance((Integer.parseInt(id))));
            });
        }
        adapter.setOnEpisodeClickListener(result -> navigator
                .replaceFragment(EpisodeDetailFragment.newInstance(result.getId())));
    }

    private void loadContent() {
        viewModel.load(id);
        viewModel.characterDetail.observe(getViewLifecycleOwner(), characterDetail -> {
            detailCharacter = characterDetail;
            content(characterDetail);
            toListStringNumber(characterDetail.getEpisode());
            viewModel.loadListEpisodes(episodes);
            viewModel.listEpisodeDetail.observe(getViewLifecycleOwner(), episodeResults -> {
                adapter.submitList(episodeResults);
                addClickListeners();
            });
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
        binding.tvCharacterCreated.setText(("Created: ").concat(characterDetail.getCreated().substring(0, 10)));
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
            episodes = episodes.concat(newString + ",");
        }
    }

    @NonNull
    @Override
    public String customTitle() {
        return TAG;
    }
}
