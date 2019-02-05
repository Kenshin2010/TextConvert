package com.manroid.convert.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.manroid.convert.R;
import com.manroid.convert.utils.StyleTool;
import com.manroid.convert.view.StyleAdapter;

import java.util.ArrayList;

/**
 * Created by Manroid
 */

public class StyleListFragment extends Fragment implements TextWatcher {
    private static final String TAG = ConverterFragment.class.getSimpleName();
    public static String KEY = "STYLE_LiST";
    private View mRootView;
    private Context mContext;
    private EditText mInput;
    private RecyclerView mListResult;
    private StyleAdapter mAdapter;

    public static StyleListFragment newInstance() {
        StyleListFragment fragment = new StyleListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.content_style_list, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInput = (EditText) mRootView.findViewById(R.id.edit_input);
        mListResult = (RecyclerView) mRootView.findViewById(R.id.list_out);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mListResult.setLayoutManager(linearLayoutManager);
        mListResult.setHasFixedSize(true);

        mAdapter = new StyleAdapter(getActivity());
        mListResult.setAdapter(mAdapter);

        mInput.addTextChangedListener(this);
    }

    private void convert() {
        String inp = mInput.getText().toString();
        Log.d(TAG, "convert: " + inp);
        ArrayList<String> translate = StyleTool.convert(inp);
        mAdapter.setData(translate);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        convert();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putString(KEY + 1, mInput.getText().toString()).apply();
    }

    public void restore() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mInput.setText(sharedPreferences.getString(KEY + 1, ""));

    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }
}
