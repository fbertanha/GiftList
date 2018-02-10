package bertanha.com.br.giftlist.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.adapter.ItensAdapter;
import bertanha.com.br.giftlist.model.Item;
import bertanha.com.br.giftlist.model.Lista;

import static bertanha.com.br.giftlist.util.Utils.getDatabase;

public class ItensActivity extends AppCompatActivity {

    private String TAG = getClass().getName();
    DatabaseReference mDatabaseRef;
    private RecyclerView mRecyclerView;
    private ItensAdapter mAdapter;
    private Lista mLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItensActivity.this);
                // Get the layout inflater
                final LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_item, null))
                        // Add action buttons
                        .setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference itensRef = mDatabaseRef.child("itens");

                                String itemKey = itensRef.push().getKey();
                                EditText descricao = (EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_item_nome);
                                EditText link = (EditText) ((AlertDialog) dialog).findViewById(R.id.item_link);
                                Item novoItem = new Item(descricao.getText().toString(), link.getText().toString());
                                novoItem.setAtivo(false);

                                itensRef.child(mLista.getCodigo()).child(itemKey).setValue(novoItem);
                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //LoginDialogFragment.this.getDialog().cancel();
                            }
                        });
                builder.show();
            }
        });

        mLista = (Lista) getIntent().getExtras().get("lista");

        if (mLista != null) {
            Log.i(TAG, "onCreate: " + mLista.getCodigo());

        }

        mDatabaseRef = getDatabase().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.content_itens_lista);
        // Show most recent items at the top
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference itensRef = mDatabaseRef.child("itens/" + mLista.getCodigo());


        mAdapter = new ItensAdapter(this, itensRef);
        mRecyclerView.setAdapter(mAdapter);

        // Make sure new events are visible
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
    }

}
