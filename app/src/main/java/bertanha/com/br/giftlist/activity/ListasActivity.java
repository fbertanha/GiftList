package bertanha.com.br.giftlist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Arrays;
import java.util.List;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.model.Lista;
import bertanha.com.br.giftlist.ui.adapter.ListaAdapter;
import bertanha.com.br.giftlist.ui.dialog.ListaDialog;
import bertanha.com.br.giftlist.ui.touch.ListaTouch;

import static bertanha.com.br.giftlist.util.Utils.getAuth;
import static bertanha.com.br.giftlist.util.Utils.getDatabase;
import static bertanha.com.br.giftlist.util.Utils.getMessaging;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ListasActivity extends AppCompatActivity {

    private String TAG = getClass().getName();
    DatabaseReference mDatabaseRef;
    private RecyclerView mRecyclerView;
    private ListaAdapter mAdapter;
    private static final int RC_SIGN_IN = 123;

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
                //New Lista
                DatabaseReference newListaRef = mDatabaseRef.child("listas").push();
                ListaDialog listaDialog = new ListaDialog(ListasActivity.this, newListaRef);
                listaDialog.show();
            }
        });

        mDatabaseRef = getDatabase().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.content_main_lista);
        // Show most recent items at the top
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        setupNotifications();

        if (getAuth().getCurrentUser() == null) {
            signIn();
        } else {
            Log.i(TAG, "onCreate: " + getAuth().getCurrentUser().getUid());
        }

    }

    private void signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build()
                                //new AuthUI.IdpConfig.TwitterBuilder().build())
                        ))
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //startActivity(SignedInActivity.createIntent(this, response));
                //finish();

                Toast.makeText(this, "Logado como " + getAuth().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onActivityResult: " + FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                return;
            } else {
                finish();
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    //showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //howSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            //showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    private void setupNotifications() {
        getMessaging().subscribeToTopic("listas");
        getMessaging().subscribeToTopic("itens");
    }


    @Override
    protected void onStart() {
        super.onStart();

        Query listasQuery = mDatabaseRef.child("listas").limitToFirst(50);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Lista>()
                .setQuery(listasQuery, Lista.class)
                .build();
        mAdapter = new ListaAdapter(options);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ListaTouch(0, ItemTouchHelper.LEFT, mAdapter, this);
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
            getAuth().signOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
