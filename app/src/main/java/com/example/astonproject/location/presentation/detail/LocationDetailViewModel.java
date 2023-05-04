package com.example.astonproject.location.presentation.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonproject.character.domain.model.CharacterResult;
import com.example.astonproject.episode.domain.model.EpisodeResult;
import com.example.astonproject.episode.domain.reposiitory.EpisodeRepository;
import com.example.astonproject.location.domain.model.LocationResult;
import com.example.astonproject.location.domain.repository.LocationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationDetailViewModel extends ViewModel {

    LocationRepository repository;

    @Inject
    public LocationDetailViewModel(LocationRepository repository) {
        this.repository = repository;
    }

    MutableLiveData<LocationResult> locationDetail = new MutableLiveData<>();

    MutableLiveData<List<CharacterResult>> listCharacters = new MutableLiveData<>();

    void load(int id) {
        repository.getDetailLocation(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<LocationResult>() {
                    @Override
                    public void onSuccess(LocationResult locationResult) {
                        locationDetail.setValue(locationResult);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    void loadListCharacter(String id){
        repository.getListCharacter(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new DisposableSingleObserver<List<CharacterResult>>() {
                    @Override
                    public void onSuccess(List<CharacterResult> characterResults) {
                        listCharacters.setValue(characterResults);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }
        );
    }
}
