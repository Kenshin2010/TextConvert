package com.manroid.convert.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.manroid.convert.R;
import com.manroid.convert.utils.ASCIITool;
import com.manroid.convert.utils.BinaryTool;
import com.manroid.convert.utils.ClipboardManager;
import com.manroid.convert.utils.HexTool;
import com.manroid.convert.utils.OctalTool;
import com.manroid.convert.utils.ReverserTool;
import com.manroid.convert.utils.SubScriptText;
import com.manroid.convert.utils.SupScriptText;
import com.manroid.convert.utils.UpperLowerTool;
import com.manroid.convert.utils.UpsideDownTool;
import com.manroid.convert.view.BaseEditText;


import static com.manroid.convert.fragment.ConverterFragment.TextType.ASCII;
import static com.manroid.convert.fragment.ConverterFragment.TextType.BINARY;
import static com.manroid.convert.fragment.ConverterFragment.TextType.HEX;
import static com.manroid.convert.fragment.ConverterFragment.TextType.KEY_TEXT;
import static com.manroid.convert.fragment.ConverterFragment.TextType.LOWER;
import static com.manroid.convert.fragment.ConverterFragment.TextType.OCTAL;
import static com.manroid.convert.fragment.ConverterFragment.TextType.REVERSER;
import static com.manroid.convert.fragment.ConverterFragment.TextType.SUB_SCRIPT;
import static com.manroid.convert.fragment.ConverterFragment.TextType.SUPPER_SCRIPT;
import static com.manroid.convert.fragment.ConverterFragment.TextType.UPPER;
import static com.manroid.convert.fragment.ConverterFragment.TextType.UPSIDE_DOWNSIDE;


/**
 * TextFragment
 * Created by Manroid
 */

public class ConverterFragment extends Fragment {
    private static final String TAG = ConverterFragment.class.getSimpleName();
    private static final String KEY = ConverterFragment.class.getSimpleName();
    private View mRootView;
    private Context mContext;
    private BaseEditText mInput, mOutput;
    private Spinner mChoose;
    TextWatcher outputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mOutput.isFocused()) convert(false);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mInput.isFocused()) convert(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View imgShareIn, imgShareOut, imgCopyIn, imgCopyOut;

    public static ConverterFragment newInstance(String init) {
        ConverterFragment fragment = new ConverterFragment();
        Bundle bundle = new Bundle();
        if (init != null) {
            bundle.putString(Intent.EXTRA_TEXT, init);
        }
        fragment.setArguments(bundle);
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
        mRootView = inflater.inflate(R.layout.content_convert_text, container, false);
        return mRootView;
    }


    @Override
    public void onDestroyView() {

        mInput.removeTextChangedListener(inputWatcher);
        mOutput.removeTextChangedListener(outputWatcher);

        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInput = (BaseEditText) mRootView.findViewById(R.id.edit_input);
        mOutput = (BaseEditText) mRootView.findViewById(R.id.edit_output);
        mInput.addTextChangedListener(inputWatcher);
        mOutput.addTextChangedListener(outputWatcher);

        mChoose = (Spinner) mRootView.findViewById(R.id.spinner_choose);

        imgCopyIn = mRootView.findViewById(R.id.img_copy);
        imgShareIn = mRootView.findViewById(R.id.img_share);

        imgShareIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mInput);
            }
        });

        imgCopyIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(mContext, mInput.getText().toString());
            }
        });

        imgCopyOut = mRootView.findViewById(R.id.img_copy_out);
        imgShareOut = mRootView.findViewById(R.id.img_share_out);

        imgShareOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mOutput);
            }
        });
        imgCopyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(mContext, mOutput.getText().toString());
            }
        });

        String[] data = getResources().getStringArray(R.array.convert_style);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mChoose.setAdapter(arrayAdapter);
        mChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInput.hasFocus()) {
                    convert(true);
                } else {
                    convert(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doShareText(EditText editText) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        intent.setType("text/plain");
        getActivity().startActivity(intent);
    }

    private void convert(boolean to) {
        int key = mChoose.getSelectedItemPosition();
        String inp = mInput.getText().toString();
        String out = mOutput.getText().toString();
        switch (key) {
            case ASCII:
                if (to) {
                    mOutput.setText(ASCIITool.textToAscii(inp));
                } else {
                    mInput.setText(ASCIITool.asciiToText(out));
                }
                break;
            case OCTAL:
                if (to) {
                    mOutput.setText(OctalTool.textToOctal(inp));
                } else {
                    mInput.setText(OctalTool.octalToText(out));
                }
                break;
            case BINARY:
                if (to) {
                    mOutput.setText(BinaryTool.textToBinary(inp));
                } else {
                    mInput.setText(BinaryTool.binaryToText(out));
                }
                break;
            case HEX:
                if (to) {
                    mOutput.setText(HexTool.textToHex(inp));
                } else {
                    mInput.setText(HexTool.hexToTex(out));
                }
                break;
            case UPPER:
                if (to) {
                    mOutput.setText(UpperLowerTool.upperText(inp));
                } else {
                    mInput.setText(UpperLowerTool.lowerText(out));
                }
                break;
            case LOWER:
                if (to) {
                    mOutput.setText(UpperLowerTool.lowerText(inp));
                } else {
                    mInput.setText(UpperLowerTool.upperText(out));
                }
                break;
            case REVERSER:
                if (to) {
                    mOutput.setText(ReverserTool.reverseText(inp));
                } else {
                    mInput.setText(ReverserTool.reverseText(out));
                }
                break;
            case UPSIDE_DOWNSIDE:
                if (to) {
                    mOutput.setText(UpsideDownTool.textToUpsideDown(inp));
                } else {
                    mInput.setText(UpsideDownTool.upsideDownToText(out));
                }
                break;
            case SUPPER_SCRIPT:
                if (to) {
                    mOutput.setText(SupScriptText.textToSup(inp));
                } else {
                    mInput.setText(SupScriptText.supToText(out));
                }
                break;
            case SUB_SCRIPT:
                if (to) {
                    mOutput.setText(SubScriptText.textToSub(inp));
                } else {
                    mInput.setText(SubScriptText.subToText(out));
                }
                break;
        }
        //reset cursor
        mInput.setSelection(mInput.getText().toString().length());
        mOutput.setSelection(mOutput.getText().toString().length());
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

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putString(KEY + getArguments().getInt(KEY_TEXT), mInput.getText().toString()).apply();
    }

    public void restore() {
        String text = getArguments().getString(Intent.EXTRA_TEXT, "");
        if (!text.isEmpty()) {
            mInput.setText(text);
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mInput.setText(sharedPreferences.getString(KEY + getArguments().getInt(KEY_TEXT), ""));
        }
        convert(true);
    }

    public static final class TextType {
        static final String KEY_TEXT = "KEY_TEXT";
        static final int ASCII = 0;
        static final int BINARY = 1;
        static final int HEX = 2;
        static final int OCTAL = 3;
        static final int REVERSER = 4;
        static final int UPPER = 5;
        static final int LOWER = 6;
        static final int UPSIDE_DOWNSIDE = 7;
        static final int SUPPER_SCRIPT = 8;
        static final int SUB_SCRIPT = 9;
    }
}
