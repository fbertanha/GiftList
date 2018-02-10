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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.adapter.ListasAdapter;
import bertanha.com.br.giftlist.model.Lista;

import static bertanha.com.br.giftlist.util.Utils.getDatabase;

public class ListasActivity extends AppCompatActivity {

    private String TAG = getClass().getName();
    DatabaseReference mDatabaseRef;
    private RecyclerView mRecyclerView;
    private ListasAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListasActivity.this);
                // Get the layout inflater
                final LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_lista, null))
                        // Add action buttons
                        .setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference listasRef = mDatabaseRef.child("listas");
                                String key = listasRef.push().getKey();
                                EditText nome = (EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_lista_nome);
                                Lista novaLista = new Lista(nome.getText().toString());

                                listasRef.child(key).setValue(novaLista);
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

        mDatabaseRef = getDatabase().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.content_main_lista);
        // Show most recent items at the top
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        // Write a message to the database
//        database = FirebaseDatabase.getInstance();
//        lista = findViewById(R.id.content_main_lista);
//        // Show most recent items at the top
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        lista.setLayoutManager(layoutManager);
//
//        DatabaseReference myRef = database.getReference().child("listas");
//        final ListasAdapter listaItensAdapter = new ListasAdapter(this, myRef);
//        lista.setAdapter(listaItensAdapter);
//
//        // Make sure new events are visible
//        listaItensAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeChanged(int positionStart, int itemCount) {
//                lista.smoothScrollToPosition(listaItensAdapter.getItemCount());
//            }
//        });
//
//        //myRef.setValue("Hello, Worlsd!");
//        //String key = myRef.child("listas").push().getKey();
//
//        myRef.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Lista value = dataSnapshot.getValue(Lista.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Lista value = dataSnapshot.getValue(Lista.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", databaseError.toException());
//            }
//        });
//
//        //myRef.child("listas/" + key).setValue(new Lista("Lista ROlissoa", new Date(), null, 129.9));
//        myRef.child("1").setValue(new Lista("Lista ROlissoa", new Date(), null, 129.9));
//        myRef.child("2").setValue(new Lista("Lista ROlissoa2", new Date(), null, 329.9));
//        myRef.child("3").setValue(new Lista("Lista ROlissoa2", new Date(), null, 429.9));
//        //Log.e(TAG, "onCreate: " + myRef.child("nome"));

        test();
    }

    private void test() {
        DatabaseReference listas = mDatabaseRef.child("listas");
//        listas.keepSynced(true);
//
//        listas.child("1").setValue(new Lista("Lista ROlissoa", new Date(), null, 129.9));
//        listas.child("2").setValue(new Lista("Lista ROlissoa2", new Date(), null, 329.9));
//        listas.child("3").setValue(new Lista("Lista ROlissoa2", new Date(), null, 429.9));

        //listas.equalTo("this").
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference mRef = mDatabaseRef.child("listas");
        mAdapter = new ListasAdapter(this, mRef);
        mRecyclerView.setAdapter(mAdapter);

        // Make sure new events are visible
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
