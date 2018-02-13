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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.ui.adapter.ItemAdapter;
import bertanha.com.br.giftlist.model.Item;
import bertanha.com.br.giftlist.ui.dialog.ItemDialog;
import bertanha.com.br.giftlist.ui.touch.ItemTouch;
import bertanha.com.br.giftlist.ui.touch.ListaTouch;

import static bertanha.com.br.giftlist.util.Utils.getDatabase;

public class ItensActivity extends AppCompatActivity {

    private String TAG = getClass().getName();
    public static final String EXTRA_LISTA_KEY = "lista_key";
    private String mListaKey;

    DatabaseReference mDatabaseRef;
    DatabaseReference mItensRef;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;

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
                //Novo Item
                DatabaseReference newItemRef = mItensRef.push();
                ItemDialog itemDialog = new ItemDialog(ItensActivity.this, newItemRef);
                itemDialog.show();
            }
        });

        mListaKey = getIntent().getStringExtra(EXTRA_LISTA_KEY);
        if (mListaKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_LISTA_KEY");
        }

        mDatabaseRef = getDatabase().getReference();
        mItensRef = mDatabaseRef.child("itens").child(mListaKey);
        mRecyclerView = (RecyclerView) findViewById(R.id.content_itens_lista);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query itensQuery = mItensRef.limitToFirst(50);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(itensQuery, Item.class)
                .build();
        mAdapter = new ItemAdapter(options);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback =  new ItemTouch(0, ItemTouchHelper.LEFT, mAdapter, this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        if (mAdapter != null) {
            mAdapter.startListening();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

}
