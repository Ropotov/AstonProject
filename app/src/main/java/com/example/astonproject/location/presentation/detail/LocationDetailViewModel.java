package com.example.astonproject.location.presentation.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonproject.character.domain.model.CharacterResult;
import com.example.astonproject.location.domain.model.LocationResult;
import com.example.astonproject.location.domain.useCases.GetDetailLocationFromDbUseCase;
import com.example.astonproject.location.domain.useCases.GetListCharactersUseCase;
import com.example.astonproject.location.domain.useCases.GetLocationDetailUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationDetailViewModel extends ViewModel {

    GetLocationDetailUseCase getLocationDetailUseCase;
    GetListCharactersUseCase getListCharactersUseCase;
    GetDetailLocationFromDbUseCase getDetailLocationFromDbUseCase;

    @Inject
    public LocationDetailViewModel(GetLocationDetailUseCase getLocationDetailUseCase,
                                   GetListCharactersUseCase getListCharactersUseCase,
                                   GetDetailLocationFromDbUseCase getDetailLocationFromDbUseCase) {
        this.getLocationDetailUseCase = getLocationDetailUseCase;
        this.getListCharactersUseCase = getListCharactersUseCase;
        this.getDetailLocationFromDbUseCase = getDetailLocationFromDbUseCase;
    }

    MutableLiveData<LocationResult> locationDetail = new MutableLiveData<>();
    MutableLiveData<List<CharacterResult>> listCharacters = new MutableLiveData<>();

    void load(int id) {
        getLocationDetailUseCase.getDetailLocation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    void loadFromDb(int id) {
        getDetailLocationFromDbUseCase.getDetailLocationFromDb(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new DisposableSingleObserver<LocationResult>() {
                            @Override
                            public void onSuccess(LocationResult locationResult) {
                                locationDetail.setValue(locationResult);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }
                );
    }

    void loadListCharacter(String id) {
        getListCharactersUseCase.getListCharacters(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<CharacterResult>>() {
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
