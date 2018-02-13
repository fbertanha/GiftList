package bertanha.com.br.giftlist.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Lista;
import bertanha.com.br.giftlist.util.Utils;

/**
 * Created by berta on 2/10/2018.
 */

public class ListaViewHolder extends RecyclerView.ViewHolder {

    private final String TAG = getClass().getName();

    public final TextView nome;
    public final TextView total;
    public final ImageView imagem;
    //final ImageButton excluir;

    public ListaViewHolder(View itemView) {
        super(itemView);

        this.nome = itemView.findViewById(R.id.item_lista_nome);
        this.total = itemView.findViewById(R.id.item_lista_total);
        this.imagem = itemView.findViewById(R.id.item_lista_imagem);
        //this.excluir = itemView.findViewById(R.id.item_lista_excluir);

    }

    public void bindLista(Lista lista) {
        Double valorFormatado = 0.0;

        try {
            valorFormatado = Double.valueOf(lista.getTotal().toString());
        } catch (Exception e) {
            Log.e(TAG, "onClick: " + e.getMessage());
        }

        nome.setText(lista.getNome());
        total.setText(Utils.formataMoeda(valorFormatado));
        Picasso.with(itemView.getContext()).load(lista.getImagem()).into(imagem);
    }
}
