package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class DeleteDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MejoresJugadores mejoresJugadores = (MejoresJugadores) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(mejoresJugadores);
        builder
                .setTitle(R.string.txtDialogoDeleteTitulo)
                .setMessage(R.string.txtDialogoDeletePregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoFinalAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mejoresJugadores.getBaseContext(), getString(R.string.deletePuntuacionesText), Toast.LENGTH_LONG).show();
                                mejoresJugadores.borrarPuntuaciones();
                                mejoresJugadores.finish();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoFinalNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );

		return builder.create();
	}
}
