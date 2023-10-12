package fr.univpau.boaviztapp.graniemarie.serverimpactgraph;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import fr.univpau.boaviztapp.graniemarie.R;

public class CustomMarkerView extends MarkerView {

    private TextView display;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        display = (TextView) findViewById(R.id.display);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        BarEntry test = (BarEntry) e;
        display.setText("" + test.getYVals()[highlight.getStackIndex()]); // set the entry-value as the display text
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() ), -(getHeight() )  );
    }
}