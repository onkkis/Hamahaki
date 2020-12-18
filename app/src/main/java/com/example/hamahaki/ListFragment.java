package com.example.hamahaki;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private SearchViewModel model;

    private ArrayList<ParseItem> parseItems;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);

        model = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        model.getCheckers().observe(getViewLifecycleOwner(), checkers -> {
            TabLayout tabLayout = v.findViewById(R.id.tablayout);
            //Gigantti selected, veikonkone NOT
            if(checkers[1]&&checkers[2] != true){
                tabLayout.removeTabAt(1);
                model.getParsegigantti().observe(getViewLifecycleOwner(), parseGigantti -> {
                    onRecycle(parseGigantti,v,inflater);
                    this.parseItems = parseGigantti;
                });
                //Veikonkone selected, gigantti not
            }else if(checkers[2]&&checkers[1] != true) {
                tabLayout.removeTabAt(0);
                model.getParseVeikonkone().observe(getViewLifecycleOwner(), parseVeikonkone -> {
                    onRecycle(parseVeikonkone,v,inflater);
                    this.parseItems = parseVeikonkone;
                });
                //Both selected
            } else {
                model.getParsegigantti().observe(getViewLifecycleOwner(), parseGigantti -> {
                    onRecycle(parseGigantti,v,inflater);
                    this.parseItems = parseGigantti;
                });
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tabLayout.getSelectedTabPosition() == 0) {
                            model.getParsegigantti().observe(getViewLifecycleOwner(), parseGigantti -> {
                                parseItems = parseGigantti; // for onclick intent
                                listAdapter.resetValues(parseGigantti);
                            });
                        } else if (tabLayout.getSelectedTabPosition() == 1) {
                            model.getParseVeikonkone().observe(getViewLifecycleOwner(), parseVeikonkone -> {
                                parseItems = parseVeikonkone; // for onclick intent
                                listAdapter.resetValues(parseVeikonkone);
                            });
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
            }
        });

        return v;
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavHostFragment.findNavController(ListFragment.this)
                        .navigate(R.id.action_ListFragment_to_HomeFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void onRecycle(ArrayList<ParseItem> pi,View v,LayoutInflater inflater){
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ListAdapter(pi,inflater.getContext());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Onclick intent for browsing product url
                    Uri uri = Uri.parse(parseItems.get(position).getProductUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}