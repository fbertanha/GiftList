package bertanha.com.br.giftlist.task;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import com.google.firebase.database.DatabaseReference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import bertanha.com.br.giftlist.model.Item;

/**
 * Created by berta on 2/25/2018.
 */

public class UpdateItemImage extends AsyncTask<Void, Void, Void>{
    private final DatabaseReference itemRef;
    private final Item item;
    private String TAG = getClass().getName();


    public UpdateItemImage(DatabaseReference itemRef, Item item) {
        this.itemRef = itemRef;
        this.item = item;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (!Patterns.WEB_URL.matcher(item.getLink()).matches()) {
            return null;
        }
        try {
            // Connect to website

            Document document = Jsoup.connect(item.getLink()).get();
            // Get the html document title
            Elements description = document.select("meta[name=description]");
            // Locate the content attribute
            String ogImage = null;
            Elements metaOgImage = document.select("meta[property=og:image]");
            if (metaOgImage != null) {
                itemRef.child("imagem").setValue(metaOgImage.first().attr("content"));
                Log.d(TAG, "doInBackground: " + metaOgImage.first().attr("content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }
}
