package bertanha.com.br.giftlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.activity.ItensActivity;
import bertanha.com.br.giftlist.model.Lista;
import bertanha.com.br.giftlist.util.Utils;


/**
 * Created by berta on 11/7/2017.
 */

public class ListasAdapter extends FirebaseRecyclerAdapter<Lista, ListasAdapter.ListaItensHolder> {

    /**
     * ListasAdapter
     */
    static class ListaItensHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "ItensHolder";

        final TextView nome;
        final TextView total;
        final ImageButton excluir;

        public ListaItensHolder(final View itemView) {
            super(itemView);

            this.nome = itemView.findViewById(R.id.item_lista_nome);
            this.total = itemView.findViewById(R.id.item_lista_total);
            this.excluir = itemView.findViewById(R.id.item_lista_excluir);

        }
    }

    private static final String TAG = "ListasAdapter";
    private Context context;

    public ListasAdapter(Context context, DatabaseReference ref) {
        super(Lista.class, R.layout.item_lista, ListaItensHolder.class, ref);
        context = context.getApplicationContext();
    }

    @Override
    protected void populateViewHolder(ListaItensHolder viewHolder, final Lista model, final int position) {
        viewHolder.nome.setText(model.getNome());
        //viewHolder.link.setText("R$ " + model.getTotal().toString());
        viewHolder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e(TAG, "onClick: " + "CLICKED");
                //getRef(position).child("descricao").setValue(model.getNome() + "_C");
                getRef(position).removeValue();
                Utils.getDatabase().getReference().child("itens/"+ model.getCodigo()).removeValue();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: CLICK:");
                model.setCodigo(getRef(position).getKey());
                Intent intent = new Intent(v.getContext(), ItensActivity.class);
                intent.putExtra("lista", model);
                v.getContext().startActivity(intent);
            }
        });
    }




}
