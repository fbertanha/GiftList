package bertanha.com.br.giftlist.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.activity.ItensActivity;
import bertanha.com.br.giftlist.model.Lista;
import bertanha.com.br.giftlist.ui.dialog.ItemDialog;
import bertanha.com.br.giftlist.ui.dialog.ListaDialog;
import bertanha.com.br.giftlist.ui.holder.ListaViewHolder;

/**
 * Created by berta on 2/10/2018.
 */

public class ListaAdapter extends FirebaseRecyclerAdapter<Lista, ListaViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListaAdapter(@NonNull FirebaseRecyclerOptions<Lista> options) {
        super(options);
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ListaViewHolder(inflater.inflate(R.layout.item_lista, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ListaViewHolder holder, int position, @NonNull final Lista model) {
        final DatabaseReference listaRef = getRef(position);
        final String listaKey = listaRef.getKey();
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItensActivity.class);
                intent.putExtra(ItensActivity.EXTRA_LISTA_KEY, listaKey);
                v.getContext().startActivity(intent);
            }
        });

        //Edit item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Nova Lista
                ListaDialog listaDialog = new ListaDialog(v.getContext(), listaRef, model);
                listaDialog.show();
                return true;
            }
        });

        holder.bindLista(model);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

    }

}
