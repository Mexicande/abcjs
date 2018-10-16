package cn.com.simpleapp.ui.frgement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.simpleapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {


    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_center, container, false);
    }

}
