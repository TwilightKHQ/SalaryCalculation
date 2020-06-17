package com.twilightkhq.salarycalculation.Activity.AddInformation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import java.util.List;

public class FragmentAddStyle extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentAddStyle.class.getSimpleName();

    private SalaryDao salaryDao;
    private EntityStyle oldStyle;
    private SharedPreferences mSharedPreferences;

    private Button button;
    private EditText editStyle;
    private EditText editNumber;
    private EditText editStylePrice;
    private EditText editProcessNumber;

    public FragmentAddStyle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_style, container, false);

        initData();
        initView(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.putString("action", "add");
        editor.apply();
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(getActivity());
    }

    private void initView(View view) {
        button = view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);

        editStyle = view.findViewById(R.id.edit_style);
        editNumber = view.findViewById(R.id.edit_number);
        editStylePrice = view.findViewById(R.id.edit_style_price);
        editProcessNumber = view.findViewById(R.id.edit_process_number);
        editStyle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
            }
        });
        editNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
            }
        });
        editStylePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
            }
        });
        editProcessNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
            }
        });

        intentAction();
        judgeButton();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            if (button.getText().equals(getString(R.string.add))) {
                if (findEntityStyle(editStyle.getText().toString()) != null) {
                    Toast.makeText(getActivity(), "款式重名", Toast.LENGTH_SHORT).show();
                    return;
                }
                salaryDao.insertStyle(new EntityStyle(
                        editStyle.getText().toString(),
                        Integer.parseInt(editProcessNumber.getText().toString()),
                        SomeUtils.handlePrice(editStylePrice.getText().toString()),
                        Integer.parseInt(editNumber.getText().toString())
                ));
                editStyle.setText("");
                editNumber.setText("");
                editStylePrice.setText("");
                editProcessNumber.setText("");
            } else if (button.getText().equals(getString(R.string.change))) {
                salaryDao.updateStyle(oldStyle, new EntityStyle(
                        editStyle.getText().toString(),
                        Integer.parseInt(editProcessNumber.getText().toString()),
                        SomeUtils.handlePrice(editStylePrice.getText().toString()),
                        Integer.parseInt(editNumber.getText().toString())
                ));
                getActivity().onBackPressed();
            }
        }
    }

    private boolean findStyle(String style) {
        List<EntityStyle> styleList = salaryDao.getStyleList();
        for (EntityStyle entityStyle : styleList) {
            if (entityStyle.getStyle().equals(style)) return true;
        }
        return false;
    }

    private EntityStyle findEntityStyle(String style) {
        List<EntityStyle> styleList = salaryDao.getStyleList();
        for (EntityStyle entityStyle : styleList) {
            if (entityStyle.getStyle().equals(style)) return entityStyle;
        }
        return null;
    }

    private void intentAction() {
        mSharedPreferences = getActivity().getSharedPreferences("shared",
                AddInformationActivity.MODE_PRIVATE);
        if ("change".equals(mSharedPreferences.getString("action", "add"))
                && mSharedPreferences.getInt("type", -1) == 1) {
            button.setText(R.string.change);
            oldStyle = findEntityStyle(mSharedPreferences.getString("oldStyle", ""));
            if (oldStyle != null) {
                editStyle.setText(oldStyle.getStyle());
                editNumber.setText(oldStyle.getNumber() + "");
                editProcessNumber.setText(oldStyle.getProcessNumber() + "");
                editStylePrice.setText(SomeUtils.priceToShow(oldStyle.getStylePrice() + ""));
                editStyle.setSelection(editStyle.getText().length());
            }
        }
    }

    private void judgeButton() {
        boolean styleFlag = editStyle.getText().length() >= 1 && !" ".contentEquals(editStyle.getText());
        boolean numberFlag = editNumber.getText().length() >= 1;
        boolean processNumberFlag = editNumber.getText().length() >= 1;
        boolean processPriceFlag = editNumber.getText().length() >= 1;
        button.setEnabled(styleFlag && numberFlag && processNumberFlag && processPriceFlag);
    }
}