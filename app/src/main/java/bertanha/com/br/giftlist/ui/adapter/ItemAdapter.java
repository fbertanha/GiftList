package bertanha.com.br.giftlist.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.activity.ItensActivity;
import bertanha.com.br.giftlist.model.Item;
import bertanha.com.br.giftlist.ui.dialog.ItemDialog;
import bertanha.com.br.giftlist.ui.holder.ItemViewHolder;

/**
 * Created by berta on 2/10/2018.
 */

public class ItemAdapter extends FirebaseRecyclerAdapter<Item, ItemViewHolder>{

    private String TAG = getClass().getName();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.item, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position, @NonNull final Item model) {
        final DatabaseReference itemRef = getRef(position);

        holder.ativo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemRef.child("ativo").setValue(isChecked);
                Log.i(TAG, "onClick: " + itemRef);
            }
        });

        //Open webpage
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri url = Uri.parse(model.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    v.getContext().startActivity(intent);

                }catch (Exception e) {
                    Toast.makeText(v.getContext(), "Link inválido." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Edit item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Novo Item
                //DatabaseReference newItemRef = mItensRef.push();
                ItemDialog itemDialog = new ItemDialog(v.getContext(), itemRef, model);
                itemDialog.show();
                return true;
            }
        });

        holder.bindItem(model, null);

    }


}
