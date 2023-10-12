package fr.univpau.boaviztapp.graniemarie.form;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.univpau.boaviztapp.graniemarie.FormActivity;
import fr.univpau.boaviztapp.graniemarie.R;

public class RequestManager {

    FormActivity formActivity;
    RequestQueue queue;

    public RequestManager(FormActivity formularyActivity) {
        this.formActivity = formularyActivity;
        queue = Volley.newRequestQueue(formularyActivity);
    }

    boolean isConnected() {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager) formActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void populateIfInternetAvailable() {
        if (!isConnected()) {
            formActivity.componentManager.showNetworkErrorToast(R.string.internet_connection_not_available);
            return;
        }

        new Thread(() -> {
            Looper.prepare();
            try {
                InetAddress address = InetAddress.getByName("www.google.com");
                if (!address.equals(""))
                    formActivity.componentManager.populate();
            } catch (UnknownHostException e) {
                formActivity.componentManager.showNetworkErrorToast(R.string.internet_connection_not_available);
            }
        }).start();
    }

    public void sendGetArrayRequestAndPopulate(String url, int materialAutoCompleteId) {
        formActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                formActivity.componentManager.startProgressIndicator();
            }
        });

        queue.add(
                new JsonArrayRequest
                        (Request.Method.GET,
                                url,
                                null,
                                response -> {
                                    ArrayList<String> values = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            values.add(response.getString(i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    formActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            formActivity.componentManager.populateAutocompleteDropdownValues(materialAutoCompleteId, values);
                                            formActivity.componentManager.stopProgressIndicator();
                                        }
                                    });
                                },
                                error -> {
                                    formActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            formActivity.componentManager.showNetworkErrorToast(R.string.network_error_message);
                                            formActivity.componentManager.stopProgressIndicator();
                                        }
                                    });
                                })
        );
    }

    public void sendGetObjectRequestAndPopulate(String url, int materialAutocompleteId) {
        formActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                formActivity.componentManager.startProgressIndicator();
            }
        });
        queue.add(
                new JsonObjectRequest
                        (Request.Method.GET,
                                url,
                                null,
                                response -> {
                                    Map<String, String> values = new HashMap<>();
                                    response.keys().forEachRemaining
                                            (s ->
                                                    {
                                                        try {
                                                            values.put(s, response.getString(s));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                            );
                                    if (formActivity.componentManager.countriesMap == null)
                                        formActivity.componentManager.countriesMap = values;
                                    formActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            formActivity.componentManager.populateAutocompleteDropdownValues(materialAutocompleteId, new ArrayList<>(values.keySet()));
                                            formActivity.componentManager.stopProgressIndicator();
                                        }
                                    });
                                },
                                error -> {
                                    formActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            formActivity.componentManager.showNetworkErrorToast(R.string.network_error_message);
                                            formActivity.componentManager.stopProgressIndicator();
                                        }
                                    });
                                })
        );
    }
}