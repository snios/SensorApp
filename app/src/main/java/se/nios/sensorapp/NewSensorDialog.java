package se.nios.sensorapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import se.nios.sensorapp.dbhelper.SensorDBHelper;

/**
 * Created by Nicklas on 2017-04-05.
 */

public class NewSensorDialog extends DialogFragment implements  TextView.OnClickListener {
    private EditText mIdEditText;
    private EditText mNameEditText;
    private EditText mGroupEditText;
    private Button mButtonAdd;
    //Empty constructor
    public NewSensorDialog(){
    }
    //oncreateview



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsensor_dialog, container);
        mIdEditText = (EditText) view.findViewById(R.id.editTextId);
        mIdEditText.requestFocus();
        mNameEditText = (EditText) view.findViewById(R.id.editTextName);
        mGroupEditText = (EditText) view.findViewById(R.id.editTextGroup);
        mButtonAdd = (Button) view.findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    //Interface for edit texts..
    public interface newSensorEditTextListener{
        void onNewSensorDialogFinish(String url, String name, String group);
    }

    @Override
    public void onClick(View v) {
        newSensorEditTextListener activity = (newSensorEditTextListener) getActivity();
        activity.onNewSensorDialogFinish(mIdEditText.getText().toString(),mNameEditText.getText().toString(),mGroupEditText.getText().toString());
        this.dismiss();
    }
}
