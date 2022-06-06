package com.example.mobiledevelopment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class dialog_activity extends AppCompatDialogFragment {
    private EditText display_name;
    private EditText url;
    private LinkDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(v).setTitle("Add Link")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = display_name.getText().toString();
                        String link = url.getText().toString();
                        listener.applyTexts(name, link);
                    }
                });

        display_name = v.findViewById(R.id.name);
        url = v.findViewById(R.id.link);
        //return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (LinkDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implement LinkDialogListener");
        }
    }

    public interface LinkDialogListener {
        void applyTexts(String name, String link);
    }

}