package bertanha.com.br.giftlist.task;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FetchWebsiteData extends AsyncTask<Void, Void, Void> {
    private String TAG = getClass().getName();
    private final String url;
    private String imageUrl;
    private String title;
    private String description;

    public FetchWebsiteData(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//            mProgressDialog = new ProgressDialog(MainActivity.this);
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Connect to website
            Document document = Jsoup.connect(url).get();
            // Get the html document title
            title = document.title();
            Elements description = document.select("meta[name=description]");
            // Locate the content attribute
            this.description = description.attr("content");
            String ogImage = null;
            Elements metaOgImage = document.select("meta[property=og:image]");
            if (metaOgImage != null) {
                imageUrl = metaOgImage.first().attr("content");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d(TAG, "onPostExecute: " + title + "------" + imageUrl);
        //mProgressDialog.dismiss();

    }

}