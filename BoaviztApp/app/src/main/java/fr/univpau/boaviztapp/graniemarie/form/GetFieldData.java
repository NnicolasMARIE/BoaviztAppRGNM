package fr.univpau.boaviztapp.graniemarie.form;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import fr.univpau.boaviztapp.graniemarie.FormActivity;
import fr.univpau.boaviztapp.graniemarie.R;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.ServerCfg;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.CFG;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Cpu;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Disk;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Ram;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Model;
import fr.univpau.boaviztapp.graniemarie.form.servcfg.Use;

public class GetFieldData {
    FormActivity formActivity;

    public GetFieldData(FormActivity formActivity) {
        this.formActivity = formActivity;
    }

    ServerCfg collectServerConfiguration() {
        Cpu cpu = new Cpu(
                getNumberFromAutocompleteTextInput(R.id.cpu_quantity_input, R.string.cpu_quantity_placeholder),
                getNumberFromAutocompleteTextInput(R.id.cpu_tdp_input, R.string.cpu_tdp_placeholder),
                getNumberFromAutocompleteTextInput(R.id.cpu_core_units_input, R.string.cpu_core_units_placeholder),
                getTextFromAutocompleteTextInput(R.id.cpu_architecture_input, R.string.cpu_architecture_placeholder)
        );

        ArrayList<Ram> ramArray = new ArrayList<>();
        ramArray.add(new Ram(
                        getNumberFromAutocompleteTextInput(R.id.ram_quantity_input, R.string.ram_quantity_placeholder),
                        getNumberFromAutocompleteTextInput(R.id.ram_capacity_input, R.string.ram_capacity_placeholder),
                        getTextFromAutocompleteTextInput(R.id.ram_manufacturer_input, R.string.ram_manufacturer_placeholder)
                )
        );

        ArrayList<Disk> disks = new ArrayList<>();
        disks.add(new Disk(
                        getNumberFromAutocompleteTextInput(R.id.ssd_quantity_input, R.string.ssd_quantity_placeholder),
                        "ssd",
                        getNumberFromAutocompleteTextInput(R.id.ssd_capacity_input, R.string.ssd_capacity_placeholder),
                        getTextFromAutocompleteTextInput(R.id.ssd_manufacturer_input, R.string.ssd_manufacturer_label)
                )
        );

        disks.add(new Disk(
                        getNumberFromAutocompleteTextInput(R.id.hdd_quantity_input, R.string.hdd_quantity_placeholder),
                        "hdd",
                        getNumberFromAutocompleteTextInput(R.id.hdd_capacity_input, R.string.hdd_capacity_placeholder)
                )
        );

        Use useConfiguration = new Use(
                getNumberFromAutocompleteTextInput(R.id.usage_lifespan_input, R.string.usage_lifespan_placeholder),
                getTextFromAutocompleteTextInput(R.id.usage_location_input, R.string.usage_location_placeholder),
                getNumberFromAutocompleteTextInput(R.id.usage_method_details_input, R.string.usage_average_consumption_placeholder),
                getTextFromAutocompleteTextInput(R.id.usage_method_input, R.string.usage_method_placeholder)
        );

        CFG componentConfiguration = new CFG(cpu, ramArray, disks);

        Model model = new Model("rack");

        return new ServerCfg(model, componentConfiguration, useConfiguration);

    }

    String getTextFromAutocompleteTextInput(int input_id, int placeholder_value) {
        MaterialAutoCompleteTextView input = formActivity.findViewById(input_id);
        if (input.getText().toString().equals(""))
            return formActivity.getString(placeholder_value);
        return input.getText().toString();
    }

    int getNumberFromAutocompleteTextInput(int input_id, int placeholder_value) {
        TextInputEditText input = formActivity.findViewById(input_id);
        if (Objects.requireNonNull(input.getText()).toString().equals(""))
            return Integer.parseInt(formActivity.getString(placeholder_value));
        return Integer.parseInt(input.getText().toString());
    }
}