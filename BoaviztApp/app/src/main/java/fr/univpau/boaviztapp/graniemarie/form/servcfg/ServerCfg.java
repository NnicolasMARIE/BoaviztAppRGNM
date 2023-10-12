package fr.univpau.boaviztapp.graniemarie.form.servcfg;

import com.google.gson.Gson;

import java.io.Serializable;

import fr.univpau.boaviztapp.graniemarie.form.servcfg.CFG;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Use;

public class ServerCfg implements Serializable {
    public Model model;
    public CFG configuration;
    public Use use;

    public ServerCfg(Model model, CFG configuration, Use use) {
        this.model = model;
        this.configuration = configuration;
        this.use = use;
    }

    public String getAsJson()  {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "model=" + model +
                ", configuration=" + configuration +
                ", usage=" + use +
                '}';
    }
}

