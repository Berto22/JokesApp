package com.example.android.jokesonme.free;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokesonme.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * create an instance of this fragment.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("free", "free fragment");

        MobileAds.initialize(getContext(), "ca-app-pub-1822618669019557/2990058229");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Create ad request
        AdView adView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("3A1D3943584BE41A8DFCD807D62E70DD")
                .build();
        adView.loadAd(adRequest);

        return root;

    }

}
