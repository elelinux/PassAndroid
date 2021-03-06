package org.ligi.passandroid.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;

import org.ligi.passandroid.Tracker;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class ImportAsyncTask extends AsyncTask<Void, Void, InputStream> {

    private Uri intent_uri;
    protected Activity ticketImportActivity;

    public ImportAsyncTask(Activity ticketImportActivity, Uri intent_uri) {
        this.ticketImportActivity = ticketImportActivity;
        this.intent_uri = intent_uri;
    }

    @Override
    protected InputStream doInBackground(Void... params) {

        if (intent_uri.toString().startsWith("content://")) {
            try {
                return ticketImportActivity.getContentResolver().openInputStream(intent_uri);
            } catch (FileNotFoundException e) {
                Tracker.get().trackException("ticketImportActivity in ImportAsyncTask", e, false);
            }
        } else
            try {
                return new BufferedInputStream(new URL("" + intent_uri).openStream(), 4096);
            } catch (MalformedURLException e) {
                Tracker.get().trackException("MalformedURLException in ImportAsyncTask", e, false);
            } catch (IOException e) {
                Tracker.get().trackException("IOException in ImportAsyncTask", e, false);
            }

        // TODO bring back Tracker.get().trackTiming("load_time", System.currentTimeMillis() - start_time, "import", "" + intent_uri);
        return null;
    }


}
