package com.example.hamahaki;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {

    //From HomeFragment --->
    private MutableLiveData<String> searchWord = new MutableLiveData<>();
    private MutableLiveData<boolean[]> checkers = new MutableLiveData<>();

    //From LoadingFragment --->
    private MutableLiveData<ArrayList<ParseItem>> parseGigantti = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ParseItem>> parseVeikonkone = new MutableLiveData<>();

    //parseGigantti getter + setter
    public void setParseGigantti(ArrayList<ParseItem> parseGigantti){ this.parseGigantti.setValue(parseGigantti); }
    public LiveData<ArrayList<ParseItem>> getParsegigantti() { return parseGigantti; }

    //parseVeikonkone getter + setter
    public void setParseVeikonkone(ArrayList<ParseItem> parseVeikonkone){ this.parseVeikonkone.setValue(parseVeikonkone); }
    public LiveData<ArrayList<ParseItem>> getParseVeikonkone() { return parseVeikonkone; }

    //searchWord getter + setter
    public void setSearchWord(String searchWord) { this.searchWord.setValue(searchWord); }
    public LiveData<String> getSearchWord() { return searchWord; }

    //checkers getter + setter
    public void setCheckers(boolean[] checkers) {this.checkers.setValue(checkers); }
    public LiveData<boolean[]>getCheckers() { return checkers; }

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }
}
