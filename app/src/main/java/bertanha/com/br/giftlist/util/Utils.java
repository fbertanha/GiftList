package bertanha.com.br.giftlist.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by berta on 2/5/2018.
 */

public class Utils {
    /**
     * Pega ref. com FirebaseDatabase
     */
    private static FirebaseDatabase mDatabase;
    private static FirebaseMessaging mMessaging;
    private static FirebaseAuth mAuth;

    public static FirebaseDatabase getDatabase() {
        if(mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static FirebaseMessaging getMessaging() {
        if (mMessaging == null) {
            mMessaging = FirebaseMessaging.getInstance();
        }
        return mMessaging;
    }

    public static FirebaseAuth getAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static DatabaseReference getQuery(String path) {
        return getDatabase().getReference().child(path);
    }

    public static String formataMoeda(Double valor) {
        NumberFormat formatoBrasileiro = DecimalFormat.getCurrencyInstance(new Locale("pt", "br"));
        return formatoBrasileiro.format(valor).replace("R$", "R$ ");
    }
}
