package com.example.hamahaki;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class LoadingFragment extends Fragment {
    private SearchViewModel model;
    private searchGigantti sg;
    private searchVeikonkone sv;

    private boolean canGo = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loading_fragment, container, false);

        model = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //EMPTY FOR DISABLING BACK BUTTON ON THIS VIEW
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model.getSearchWord().observe(getViewLifecycleOwner(), searchWord -> {
            model.getCheckers().observe(getViewLifecycleOwner(), checkers -> {
                if(checkers[1]&&checkers[2] != true) {
                    canGo = true;
                    getGigantti(searchWord);
                }else if(checkers[2]&&checkers[1] != true) {
                    canGo = true;
                    getVeikonkone(searchWord);
                }else {
                    getGigantti(searchWord);
                    getVeikonkone(searchWord);
                }
            });
        });
    }

    private void getGigantti(String searchWord){
        new Thread(() -> {
            ArrayList<ParseItem> pi = new ArrayList<>();
            sg = new searchGigantti(searchWord);
            sg.start();
            try {
                sg.join();
                pi = sg.getParseItems();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //post Exec
            ArrayList<ParseItem> finalPi = pi;
            getActivity().runOnUiThread(() -> {
                //Set items to view model
                model.setParseGigantti(finalPi);

                if(canGo == true){
                    NavHostFragment.findNavController(LoadingFragment.this)
                            .navigate(R.id.action_LoadingFragment_to_ListFragment);
                }else {
                    canGo = true;
                }
            });
        }).start();
    }

    private void getVeikonkone(String searchWord) {
        new Thread(() -> {
            ArrayList<ParseItem> pi = new ArrayList<>();
            sv = new searchVeikonkone(searchWord);
            sv.start();
            try {
                sv.join();
                pi = sv.getParseItems();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //post Exec
            ArrayList<ParseItem> finalPi = pi;
            getActivity().runOnUiThread(() -> {
                //Set items to view model
                model.setParseVeikonkone(finalPi);

                if(canGo == true){
                    NavHostFragment.findNavController(LoadingFragment.this)
                            .navigate(R.id.action_LoadingFragment_to_ListFragment);
                }else {
                    canGo = true;
                }


            });
        }).start();
    }

}