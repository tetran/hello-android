package org.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    private AlertDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        View aboutButton = rootView.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.about_title)
                        .setMessage(R.string.about_text)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok_label, null)
                        .show();
            }
        });

        View newButton = rootView.findViewById(R.id.new_button);
        View continueButton = rootView.findViewById(R.id.continue_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
