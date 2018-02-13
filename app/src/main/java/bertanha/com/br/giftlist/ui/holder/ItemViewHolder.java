package bertanha.com.br.giftlist.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Item;
import bertanha.com.br.giftlist.util.Utils;

/**
 * Created by berta on 2/10/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private String TAG  = getClass().getName();
    public final TextView descricao;
    public final TextView link;
    public final TextView valor;
    public final CheckBox ativo;

    public ItemViewHolder(View itemView) {
        super(itemView);
        this.descricao = itemView.findViewById(R.id.item_descricao);
        this.link = itemView.findViewById(R.id.item_link);
        this.valor = itemView.findViewById(R.id.item_valor);
        this.ativo  = itemView.findViewById(R.id.item_ativo);

    }

    public void bindItem(Item item, View.OnClickListener ativoOnClickListener) {
        Double valorFormatado = 0.0;
        String linkFormatado;
        try {
            valorFormatado = Double.valueOf(item.getValor().toString());
        } catch (Exception e) {
            Log.e(TAG, "onClick: " + e.getMessage());
        }
        descricao.setText(item.getDescricao());

        linkFormatado = item.getLink();
        if (linkFormatado.length() > 30) {
            linkFormatado = linkFormatado.substring(0, 30) + "...";
        }
        link.setText(linkFormatado);
        valor.setText(Utils.formataMoeda(valorFormatado));
        ativo.setChecked(item.isAtivo());

        ativo.setOnClickListener(ativoOnClickListener);
    }


}
