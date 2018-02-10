package bertanha.com.br.giftlist.util;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by berta on 2/5/2018.
 */

public class Utils {
    /**
     * Pega ref. com FirebaseDatabase
     */
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if(mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
