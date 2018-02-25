package bertanha.com.br.giftlist.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Item;

/**
 * Created by berta on 2/25/2018.
 */

public class ItemImageDialog extends AlertDialog {
    private static final int GALLERY = 0;
    private final String TAG = getClass().getName();
    private Context context;
    private DatabaseReference itemRef;

    private ImageView imagem;
    private Item item;

    public ItemImageDialog(@NonNull Context context, DatabaseReference itemRef) {
        super(context);
        this.context = context;
        this.itemRef = itemRef;
        setup();
    }

    public ItemImageDialog(@NonNull Context context, DatabaseReference itemRef, Item item) {
        super(context);
        this.context = context;
        this.itemRef = itemRef;
        this.item = item;
        setup();
    }

    private void setup() {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_item_image, null);

        this.setView(view);
        //this.setTitle(item.getDescricao());

        this.imagem = (ImageView) view.findViewById(R.id.dialog_item_image_imagem);

        Glide.with(context)
                .load(item.getImagem())
                //.placeholder(context.getResources().getDrawable(R.drawable.ic_image_24dp))
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_24dp))
                .into(imagem);

        //Change image
        this.imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        //Check if is new Item
//        if (item == null) {
//            item = new Item();
//        } else {
//
//        }

//        // Add action buttons
//        this.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                String linkFormatado;
//                Double valorFormatado;
//
//                linkFormatado = link.getText().toString();
//                if (!linkFormatado.contains("http://") && !linkFormatado.contains("https://") && !linkFormatado.isEmpty()) {
//                    linkFormatado = "https://" + linkFormatado;
//                }
//                try {
//                    valorFormatado = Double.valueOf(valor.getText().toString());
//                } catch (Exception e) {
//                    valorFormatado = 0.0;
//                    Log.e(TAG, "onClick: " + e.getMessage());
//                }
//
//                item.setDescricao(descricao.getText().toString());
//                item.setLink(linkFormatado);
//                item.setValor(valorFormatado);
//                item.setAtivo(false);
//
//                itemRef.setValue(item);
//
//                //update list total
////                itemRef.getRoot().child("listas").child(itemRef.getParent().getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        itemRef.getRoot().child("listas").child(itemRef.getParent().getKey()).child("total").setValue(dataSnapshot.getValue(Lista.class).getTotal() + item.getValor());
////                        Log.i(TAG, "onDataChange: " + dataSnapshot.getValue(Lista.class).getTotal());
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError databaseError) {
////
////                    }
////                });
//
//            }
//        });
//        this.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                //LoginDialogFragment.this.getDialog().cancel();
//            }
//        });
    }


}
