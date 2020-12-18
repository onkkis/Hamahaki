package com.example.hamahaki;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.lang.reflect.Array;

public class HomeFragment extends Fragment {

    private SearchViewModel model;
    private boolean[] checkers = {false,false,false};

    private EditText searchWord;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.home_fragment, container, false);
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        searchWord = v.findViewById(R.id.searchWord);

        //To be done.
        CheckBox verkkokauppa = v.findViewById(R.id.checkbox_verkkokauppa);
        verkkokauppa.setText("TÄMÄ EI KÄYTÖSSÄ!");

        model = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.checkbox_verkkokauppa).setOnClickListener(view1 -> {
            if(checkers[0]) {
                checkers[0] = false;
            }else {
                checkers[0] = true;
            }
        });
        view.findViewById(R.id.checkbox_gigantti).setOnClickListener(view1 -> {
            if(checkers[1]) {
                checkers[1] = false;
            }else {
                checkers[1] = true;
            }
        });

        view.findViewById(R.id.checkbox_veikonkone).setOnClickListener(view1 -> {
            if(checkers[2]) {
                checkers[2] = false;
            }else {
                checkers[2] = true;
            }
        });

        view.findViewById(R.id.button_first).setOnClickListener(view1 -> {
            String s = searchWord.getText().toString();;

            if(s.isEmpty()) {
                Toast.makeText(getActivity(), "Anna syöte hakukenttään.",
                        Toast.LENGTH_LONG).show();
            }else if(checkers[1]==false&&checkers[2]==false) {
                Toast.makeText(getActivity(), "Valitse mistä haetaan.",
                        Toast.LENGTH_LONG).show();
            }
            else {
                //Put data into viewmodel
                model.setSearchWord(s);
                model.setCheckers(checkers);

                 NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_LoadingFragment);

            }

        });
    }
}