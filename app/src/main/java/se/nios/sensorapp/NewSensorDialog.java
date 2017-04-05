package se.nios.sensorapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nicklas on 2017-04-05.
 */

public class NewSensorDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText mUrlEditText;
    private EditText mNameEditText;
    private EditText mGroupEditText;
    //Empty constructor
    public NewSensorDialog(){
    }
    //oncreateview



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsensor_dialog, container);
        mUrlEditText = (EditText) view.findViewById(R.id.editTextUrl);
        mUrlEditText.setOnEditorActionListener(this);
        mUrlEditText.requestFocus();
        mNameEditText = (EditText) view.findViewById(R.id.editTextName);
        mNameEditText.setOnEditorActionListener(this);
        mGroupEditText = (EditText) view.findViewById(R.id.editTextGroup);
        mGroupEditText.setOnEditorActionListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    //Interface for edit texts..
    public interface newSensorEditTextListener{
        void onNewSensorDialogFinish(String url, String name, String group);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        newSensorEditTextListener activity = (newSensorEditTextListener) getActivity();
        activity.onNewSensorDialogFinish(mUrlEditText.toString(),mNameEditText.toString(),mGroupEditText.toString());
        this.dismiss();
        return false;
    }
}
