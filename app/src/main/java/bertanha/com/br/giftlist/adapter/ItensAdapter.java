package bertanha.com.br.giftlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Item;


/**
 * Created by berta on 11/7/2017.
 */

public class ItensAdapter extends FirebaseRecyclerAdapter<Item, ItensAdapter.ItensHolder> {

    /**
     * ItensAdapter
     */
    static class ItensHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "ItensHolder";

        final TextView descricao;
        final TextView link;
        final CheckBox ativo;
//        final ImageButton excluir;

        public ItensHolder(final View itemView) {
            super(itemView);

            this.descricao = itemView.findViewById(R.id.item_descricao);
            this.link = itemView.findViewById(R.id.item_link);
            this.link.setMovementMethod(LinkMovementMethod.getInstance());

            this.link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ((TextView) v.findViewById(R.id.item_link)).getText().toString();
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(intent);

                    }catch (Exception e) {
                        Toast.makeText(v.getContext(), "Erro ao abrir página, verifique o link digitado!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }

                }
            });

            this.ativo  = itemView.findViewById(R.id.item_ativo);

        }
    }

    private static final String TAG = "ItensAdapter";
    private Context context;

    public ItensAdapter(Context context, DatabaseReference ref) {
        super(Item.class, R.layout.item, ItensHolder.class, ref);
        context = context.getApplicationContext();
    }

    @Override
    protected void populateViewHolder(ItensHolder viewHolder, final Item model, final int position) {
        viewHolder.descricao.setText(model.getNome());
        viewHolder.link.setText(model.getLink());
        if (viewHolder.link.getText().toString().contains("http")) {
            viewHolder.link.setTextColor(viewHolder.itemView.getResources().getColor(android.R.color.holo_blue_dark));
        }
        viewHolder.ativo.setChecked(model.isAtivo());
        viewHolder.ativo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG, "onCheckedChanged: " + isChecked);
                getRef(position).child("ativo").setValue(isChecked);
            }
        });
        //viewHolder.link.setText("R$ " + model.getTotal().toString());
//        viewHolder.excluir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Log.e(TAG, "onClick: " + "CLICKED");
//                //getRef(position).child("descricao").setValue(model.getNome() + "_C");
//                getRef(position).removeValue();
//            }
//        });
//
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "onClick: CLICK" );
//                Intent intent = new Intent(v.getContext(), ItensActivity.class);
//                intent.putExtra("lista", model);
//                v.getContext().startActivity(intent);
//            }
//        });
    }




}
