package fr.univpau.boaviztapp.graniemarie.form;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.univpau.boaviztapp.graniemarie.FormActivity;
import fr.univpau.boaviztapp.graniemarie.R;
import fr.univpau.boaviztapp.graniemarie.ServerImpactGraph;

public class ComponentManager {

    public FormActivity formActivity;
    public final RequestManager requestManager;
    private final GetFieldData getFieldData;

    Map<String, String> countriesMap = null;

    public ComponentManager(FormActivity activity) {
        formActivity = activity;
        requestManager = new RequestManager(formActivity);
        getFieldData = new GetFieldData(formActivity);
    }

    private void setDropdownsListeners() {
        setShowDropDownOnFocusAndClick(R.id.usage_method_input);
        setShowDropDownOnFocusAndClick(R.id.cpu_architecture_input);
        setShowDropDownOnFocusAndClick(R.id.ssd_manufacturer_input);
        setShowDropDownOnFocusAndClick(R.id.ram_manufacturer_input);
        setShowDropDownOnFocusAndClick(R.id.usage_location_input);
    }

    private void setClearOnClickListeners(){
        setClearInputOnClick(R.id.cpu_quantity_input, R.string.cpu_quantity_placeholder);
        setClearInputOnClick(R.id.cpu_core_units_input, R.string.cpu_core_units_placeholder);
        setClearInputOnClick(R.id.cpu_tdp_input, R.string.cpu_tdp_placeholder);

        setClearInputOnClick(R.id.ssd_capacity_input, R.string.ssd_capacity_placeholder);
        setClearInputOnClick(R.id.ssd_quantity_input, R.string.ssd_quantity_placeholder);

        setClearInputOnClick(R.id.hdd_capacity_input, R.string.hdd_capacity_placeholder);
        setClearInputOnClick(R.id.hdd_quantity_input, R.string.hdd_quantity_placeholder);

        setClearInputOnClick(R.id.ram_quantity_input, R.string.ram_quantity_placeholder);
        setClearInputOnClick(R.id.ram_capacity_input, R.string.ram_capacity_placeholder);

        setClearInputOnClick(R.id.usage_lifespan_input, R.string.usage_lifespan_placeholder);
    }

    private void setClearInputOnClick(int inputId, int defaultValue){
        TextInputEditText inputView = formActivity.findViewById(inputId);
        inputView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && Objects.requireNonNull(inputView.getText()).length() > 0)
                    inputView.getText().clear();
                else if (Objects.requireNonNull(inputView.getText()).length() == 0){
                    inputView.setText(defaultValue);
                }
            }
        });
    }

    public void prepareUI(){
        setUsageContents();
        setBottomNavigationBar();
        setNavigationIconFocus();
        setClearOnClickListeners();
        setDropdownsListeners();
        setLogoOnClickListener();
    }

    private void setLogoOnClickListener() {
        ShapeableImageView logo = formActivity.findViewById(R.id.boavizta_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //formularyActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(formularyActivity.getString(R.string.url_boavizta))));
                //formularyActivity.startActivity();
            }
        });
    }

    public void populate() {

        requestManager.sendGetArrayRequestAndPopulate(formActivity.getString(R.string.url_cpu_architectures), R.id.cpu_architecture_input);
        requestManager.sendGetArrayRequestAndPopulate(formActivity.getString(R.string.url_ssd_manufacturers), R.id.ssd_manufacturer_input);
        requestManager.sendGetArrayRequestAndPopulate(formActivity.getString(R.string.url_hdd_manufacturers), R.id.ram_manufacturer_input);
        requestManager.sendGetObjectRequestAndPopulate(formActivity.getString(R.string.url_countries), R.id.usage_location_input);

        populateAutocompleteDropdownValues(R.id.usage_method_input, Arrays.asList(formActivity.getResources().getStringArray(R.array.method_options)));
    }


    void populateAutocompleteDropdownValues(int id, List<String> values) {
        MaterialAutoCompleteTextView autoCompleteTextView = formActivity.findViewById(id);
        String[] array = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            array[i] = values.get(i);
        }
        Arrays.sort(array);
        autoCompleteTextView.setSimpleItems(array);
        autoCompleteTextView.setText(array[0]);
    }

    private void setUsageContents() {
        setUsageMethodContents();
        updateMethodDetailContainer(String.valueOf(((MaterialAutoCompleteTextView) formActivity.findViewById(R.id.usage_method_input)).getText()));
    }

    private void setUsageMethodContents() {
        MaterialAutoCompleteTextView methodInput = formActivity.findViewById(R.id.usage_method_input);
        methodInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateMethodDetailContainer((String) adapterView.getItemAtPosition((int) l));
            }
        });
    }

    private void setShowDropDownOnFocusAndClick(int autoCompleteTextView) {
        MaterialAutoCompleteTextView textView = formActivity.findViewById(autoCompleteTextView);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    textView.showDropDown();
                    if (textView.getText().length() > 0)
                        textView.getText().clear();

                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.showDropDown();
            }
        });


    }

    private void updateMethodDetailContainer(String newMethodString) {
        TextInputLayout methodDetailsInputLayout = formActivity.findViewById(R.id.usage_method_details_layout);
        TextInputEditText methodDetailsInputEditText = formActivity.findViewById(R.id.usage_method_details_input);
        if (newMethodString.equals("Load")) {
            methodDetailsInputEditText.setText(R.string.usage_server_load_placeholder);
            methodDetailsInputLayout.setHint(formActivity.getString(R.string.usage_server_load_label));
            methodDetailsInputLayout.setSuffixText(formActivity.getString(R.string.usage_server_load_helper));
            return;
        }
        methodDetailsInputEditText.setText(R.string.usage_average_consumption_placeholder);
        //methodDetailsInputLayout.setHint(formActivity.getString(R.string.usage_average_consumption_label));
        methodDetailsInputLayout.setSuffixText(formActivity.getString(R.string.usage_average_consumption_helper));
    }

    private void setBottomNavigationBar() {
        BottomNavigationItemView assessServerImpactMenuBtn = formActivity.findViewById(R.id.impact_visualisation_menu_button);

        assessServerImpactMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> launchImpactAssessmentActivity()).start();
            }
        });
    }

    private void setNavigationIconFocus() {
        BottomNavigationView assessServerImpactMenu = formActivity.findViewById(R.id.bottom_navigation);
        assessServerImpactMenu.setSelectedItemId(R.id.server_configuration_menu_button);
    }

    private void launchImpactAssessmentActivity() {
        Intent formularyIntent = new Intent(formActivity.getApplicationContext(), ServerImpactGraph.class);
        Log.d("SERVER CONFIG", getFieldData.collectServerConfiguration().toString());
        formularyIntent.putExtra("serverConfiguration", getFieldData.collectServerConfiguration());
        formActivity.startActivity(formularyIntent);
    }




    public void startProgressIndicator() {
        LinearProgressIndicator progressIndicator = formActivity.findViewById(R.id.progress_indicator);
        progressIndicator.setVisibility(View.VISIBLE);

    }

    public void stopProgressIndicator() {
        LinearProgressIndicator progressIndicator = formActivity.findViewById(R.id.progress_indicator);
        progressIndicator.setVisibility(View.INVISIBLE);
    }




    void showNetworkErrorToast(int resString) {
        Snackbar.make(formActivity.findViewById(R.id.root), formActivity.getString(resString), Snackbar.LENGTH_LONG)
                .setAction(R.string.toast_action_retry, view -> requestManager.populateIfInternetAvailable())
                .setAnchorView(R.id.bottom_navigation)
                .show();
    }
}