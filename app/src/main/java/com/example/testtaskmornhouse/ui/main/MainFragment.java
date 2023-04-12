package com.example.testtaskmornhouse.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testtaskmornhouse.R;
import com.example.testtaskmornhouse.data.NumberFact;

import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private MainViewModel mViewModel;

    private Button mGetRandomFactBtn;
    private Button mGetFactBtn;
    private EditText mNumberEdit;
    private LinearLayout mRequestHistory;

    private boolean mIsFirstLoad = true;

    private TextView createNumberFactTextView(NumberFact nf) {
        TextView tv = new TextView(getContext());
        tv.setText(nf.toString());
        tv.setMaxLines(1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.container, FactFragment.newInstance(nf.id)).commitNow();
            }
        });
        return tv;
    }
    private Observer<List<NumberFact>> mNumberFactObs = new Observer<List<NumberFact>>() {
        @Override
        public void onChanged(List<NumberFact> numberFacts) {
            if (numberFacts != null) {
                if (mIsFirstLoad) {
                    for (NumberFact nf : numberFacts) {
                        mRequestHistory.addView(createNumberFactTextView(nf), 0);
                    }
                    mIsFirstLoad = false;
                } else {
                    NumberFact nf = numberFacts.get(numberFacts.size() - 1);
                    mRequestHistory.addView(createNumberFactTextView(nf), 0);
                }
            }
        }
    };

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mNumberEdit = view.findViewById(R.id.numberEdit);

        mGetRandomFactBtn = view.findViewById(R.id.getRndFactBtn);
        mGetRandomFactBtn.setOnClickListener(view1 -> mViewModel.randomNumberFactCheck());

        mGetFactBtn = view.findViewById(R.id.getFactBtn);
        mGetFactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number;
                try {
                    number = Integer.parseInt(mNumberEdit.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }
                mViewModel.numberFactCheck(number);
            }
        });

        mRequestHistory = view.findViewById(R.id.requestHistory);
        mViewModel.getNumberFacts().observe(getViewLifecycleOwner(), mNumberFactObs);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mIsFirstLoad = true;
        mRequestHistory.removeAllViewsInLayout();
    }
}