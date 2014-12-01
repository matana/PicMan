package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    public interface DeleteDialogFragmentListener {

        public void onDialogPositiveClick(DialogFragment dialog, int position);
        public void onDialogNegativeClick(DialogFragment dialog);

    }


    DeleteDialogFragmentListener listener;


//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        builder.setMessage(R.string.delete_message)
//                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Delete image
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
//
//        return builder.create();
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instanziierung des NoticeDialogListeners via Casting
            listener = (DeleteDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            // Die Activity implementiert nicht das Interface "DeleteDialogFragmentListener", folglich wird
            // eine ClassCastException geworfen.
            throw new ClassCastException(activity.toString()
                    + " must implement DeleteDialogFragmentListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

}
