package com.example.astonproject.character.presentation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonproject.character.domain.model.CharacterDetail;
import com.example.astonproject.character.domain.repository.CharacterRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailViewModel extends ViewModel {

    CharacterRepository repository;

    @Inject
    public CharacterDetailViewModel(CharacterRepository repository){
        this.repository = repository;
    };

    MutableLiveData<CharacterDetail> characterDetail = new MutableLiveData<>();

    void load(int id) {
        try {
            repository.getDetailCharacter(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<CharacterDetail>() {
                        @Override
                        public void onSuccess(CharacterDetail result) {
                            characterDetail.setValue(result);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }
}

