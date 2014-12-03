package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;


/**
 * Created by matana on 27.11.14.
 */
public class DeleteDialogFragment extends DialogFragment {

    private int position;

    private String textData;
    private Bitmap imageData;

    DeleteDialogFragmentListener listener;

    public interface DeleteDialogFragmentListener {

        public void onDialogPositiveClick(DialogFragment dialog, int position);
        public void onDialogNegativeClick(DialogFragment dialog);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {
            listener = (DeleteDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DeleteDialogFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            textData =  savedInstanceState.getString("textData");
            imageData = savedInstanceState.getParcelable("imageData");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // Log.i(getClass().getSimpleName(), "DeleteDialogFragment onCreateView called... ");

        getDialog().setTitle(R.string.delete_message);

        View view = inflater.inflate(R.layout.fragment_delete_dialog, container);

        ((ImageView)view.findViewById(R.id.image_to_delete)).setImageBitmap(imageData);
        ((TextView)view.findViewById(R.id.text_to_delete)).setText(textData);

        ((Button) view.findViewById(R.id.confirm)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Aufruf der onDialogPositiveClick Methode in MainActivity
                listener.onDialogPositiveClick(DeleteDialogFragment.this, position);
            }
        });

        ((Button) view.findViewById(R.id.cancel)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Aufruf der onDialogNegativeClick Methode in MainActivity
                listener.onDialogNegativeClick(DeleteDialogFragment.this);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.i(getClass().getSimpleName(), "onSaveInstanceState... " + textData);
        outState.putString("textData", textData);
        outState.putParcelable("imageData", imageData);
        outState.putInt("position", position);

    }

}
