package bertanha.com.br.giftlist.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Item;
import bertanha.com.br.giftlist.model.Lista;

/**
 * Created by berta on 2/12/2018.
 */

public class ItemDialog extends AlertDialog.Builder {
    private final String TAG = getClass().getName();
    private Context context;
    private DatabaseReference itemRef;

    private EditText descricao;
    private EditText link;
    private EditText valor;
    private Item item;

    public ItemDialog(@NonNull Context context, DatabaseReference itemRef) {
        super(context);
        this.context = context;
        this.itemRef = itemRef;
        setup();
    }

    public ItemDialog(@NonNull Context context, DatabaseReference itemRef, Item item) {
        super(context);
        this.context = context;
        this.itemRef = itemRef;
        this.item = item;
        setup();
    }

    private void setup() {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_item, null);

        this.setView(view);
        this.setTitle(R.string.item);

        this.descricao = (EditText) view.findViewById(R.id.dialog_item_nome);
        this.link = (EditText) view.findViewById(R.id.dialog_item_link);
        this.valor = (EditText) view.findViewById(R.id.dialog_item_valor);

        //Check if is new Item
        if (item == null) {
            item = new Item();
        } else {
            this.descricao.setText(item.getDescricao());
            this.link.setText(item.getLink());
            this.valor.setText(item.getValor().toString());
        }

        // Add action buttons
        this.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String linkFormatado;
                Double valorFormatado;

                linkFormatado = link.getText().toString();
                if (!linkFormatado.contains("http://") && !linkFormatado.contains("https://") && !linkFormatado.isEmpty()) {
                    linkFormatado = "https://" + linkFormatado;
                }
                try {
                    valorFormatado = Double.valueOf(valor.getText().toString());
                } catch (Exception e) {
                    valorFormatado = 0.0;
                    Log.e(TAG, "onClick: " + e.getMessage());
                }

                item.setDescricao(descricao.getText().toString());
                item.setLink(linkFormatado);
                item.setValor(valorFormatado);
                item.setAtivo(false);

                itemRef.setValue(item);

                //update list total
//                itemRef.getRoot().child("listas").child(itemRef.getParent().getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        itemRef.getRoot().child("listas").child(itemRef.getParent().getKey()).child("total").setValue(dataSnapshot.getValue(Lista.class).getTotal() + item.getValor());
//                        Log.i(TAG, "onDataChange: " + dataSnapshot.getValue(Lista.class).getTotal());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

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
