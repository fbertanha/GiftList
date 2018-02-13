package bertanha.com.br.giftlist.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Lista;
import bertanha.com.br.giftlist.util.Utils;

/**
 * Created by berta on 2/12/2018.
 */

public class ListaDialog extends AlertDialog.Builder {

    private Context context;
    private EditText nome;
    private DatabaseReference listaRef;
    private Lista lista;

    public ListaDialog(@NonNull Context context, DatabaseReference listaRef) {
        super(context);
        this.context = context;
        this.listaRef = listaRef;
        setup();
    }

    public ListaDialog(@NonNull Context context, DatabaseReference listaRef, Lista lista) {
        super(context);
        this.context = context;
        this.listaRef = listaRef;
        this.lista = lista;
        setup();
    }

    private void setup() {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_lista, null);

        this.setView(view);
        this.setTitle(R.string.lista);

        this.nome = (EditText) view.findViewById(R.id.dialog_lista_nome);

        //Check if is new Item
        if (lista == null) {
            lista = new Lista();
        } else {
            this.nome.setText(lista.getNome());
        }

        // Add action buttons
        this.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //String key = listaRef.push().getKey();
                lista.setNome(nome.getText().toString());
                lista.setImagem(Utils.getAuth().getCurrentUser().getPhotoUrl().toString());
                listaRef.setValue(lista);
            }
        });
        this.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //LoginDialogFragment.this.getDialog().cancel();
            }
        });
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }
}
