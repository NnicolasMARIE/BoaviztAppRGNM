package fr.univpau.boaviztapp.graniemarie;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import fr.univpau.boaviztapp.graniemarie.form.ComponentManager;

public class FormActivity extends AppCompatActivity {;

    public ComponentManager componentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        componentManager = new ComponentManager(this);

        //setContentView(R.layout.formulary);
        setContentView(R.layout.formulaire);

        EditText et = (EditText) findViewById(R.id.cpu_quantity_input);
        EditText et1 = (EditText) findViewById(R.id.cpu_tdp_input);
        EditText et2 = (EditText) findViewById(R.id.cpu_core_units_input);
        EditText et3 = (EditText) findViewById(R.id.ram_quantity_input);
        EditText et4 = (EditText) findViewById(R.id.ram_capacity_input);
        EditText et5 = (EditText) findViewById(R.id.ssd_quantity_input);
        EditText et6 = (EditText) findViewById(R.id.ssd_capacity_input);
        EditText et7 = (EditText) findViewById(R.id.hdd_quantity_input);
        EditText et8 = (EditText) findViewById(R.id.hdd_capacity_input);
        EditText et9 = (EditText) findViewById(R.id.usage_lifespan_input);
        EditText et10 = (EditText) findViewById(R.id.usage_method_details_input);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et1.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et3.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et4.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et5.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et6.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et7.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et8.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et9.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});
        et10.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200000")});

        focusRootWindow();

        componentManager.prepareUI();
    }

    public void focusRootWindow() {
        ((LinearLayout) findViewById(R.id.root)).requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        componentManager.requestManager.populateIfInternetAvailable();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}