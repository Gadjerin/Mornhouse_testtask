package com.example.testtaskmornhouse.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.testtaskmornhouse.R;
import com.example.testtaskmornhouse.data.NumberFact;

import java.util.List;
import java.util.function.Predicate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FactFragment extends Fragment {

    private TextView mNumberTv;
    private TextView mFactTv;
    private Button mGetBackButton;
    private MainViewModel mViewModel;
    private int mSelectedId;

    public FactFragment(int id) {
        mSelectedId = id;
    }

    public static FactFragment newInstance(int id) {
         return new FactFragment(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fact, container, false);
        List<NumberFact> numberFacts = mViewModel.getNumberFacts().getValue();
        NumberFact nf = numberFacts.stream().filter(numberFact -> {
            if (numberFact.id == mSelectedId) {
                return true;
            } else {
                return false;
            }
        }).findFirst().get();

        mNumberTv = view.findViewById(R.id.numberTv);
        mNumberTv.setText(String.valueOf(nf.number));

        mFactTv = view.findViewById(R.id.factTv);
        mFactTv.setText(nf.fact);

        mGetBackButton = view.findViewById(R.id.backBtn);
        mGetBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.container, MainFragment.newInstance()).commitNow();
            }
        });
        return view;
    }
}