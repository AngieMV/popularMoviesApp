package mx.saudade.popularmoviesapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.AppAdapter;
import mx.saudade.popularmoviesapp.interfaces.IContentListView;
import mx.saudade.popularmoviesapp.models.Results;

/**
 * Created by angie on 9/1/15.
 */
public class ContentListView<T> extends LinearLayout implements IContentListView {

    private static final String TAG = ContentListView.class.getSimpleName();

    private static final String KEY_LIST_POSITION = TAG + "_POSITION_";

    private static final String KEY_SELECTED_INDEX = TAG + "_SELECTED_ITEM_";

    private static final String KEY_STATE = TAG + "_KEY_STATE_";

    private AbsListView view;

    private TextView notificationView;

    private AppAdapter adapter;

    private int layoutId;

    public ContentListView(Context context) {
        super(context);
        init();
    }

    public ContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout(attrs);
        init();
    }

    public ContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout(attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, this, true);

        this.view = (AbsListView) findViewById(R.id.listView);
        this.notificationView = (TextView) findViewById(R.id.loading);
        this.notificationView.setVisibility(View.VISIBLE);
    }

    private void setLayout(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.mx_saudade_popularmoviesapp_views_ContentListView, 0, 0);
        layoutId = a.getResourceId(R.styleable.mx_saudade_popularmoviesapp_views_ContentListView_content_layout, 0);
        a.recycle();
    }

    @Override
    public void setAdapter(AppAdapter adapter) {
        this.adapter = adapter;
        this.view.setAdapter(this.adapter);
    }

    @Override
    public void setResults(List results) {
        adapter.setResults(results);
        view.setAdapter(adapter);
        notificationView.setVisibility(View.GONE);
    }

    public List getResults() {
        if (adapter == null || adapter.getResults() == null) {
            return new ArrayList();
        }
        return adapter.getResults().getResults();
    }

    @Override
    public void error() {
        adapter.setResults(new ArrayList<T>());
        view.setAdapter(adapter);
        notificationView.setText(getContext().getString(R.string.error_no_result));
        notificationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void evaluateResults(List results) {
        if (results == null || results.size() == 0) {
            error();
        } else {
            setResults(results);
        }
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.view.setOnItemClickListener(listener);
    }

    public void restoreState(Bundle bundle, AppAdapter adapter) {
        Log.v(TAG, "xxx onViewStateRestored");
        if(this.adapter == null)
            setAdapter(adapter);

        if (bundle == null) {
            return;
        }

        List results = ((Results) bundle.getSerializable(KEY_STATE + adapter.IDENTIFIER)).getResults();
        setResults(results);
        view.setSelection(bundle.getInt(KEY_LIST_POSITION + adapter.IDENTIFIER));
        adapter.setSelectedIndex(bundle.getInt(KEY_SELECTED_INDEX + adapter.IDENTIFIER));
    }

    public void saveState(Bundle bundle) {
        Log.v(TAG, "xxx onSaveInstanceState");
        if (bundle == null) {
            return;
        }
        bundle.putSerializable(KEY_STATE + adapter.IDENTIFIER, this.adapter.getResults());
        bundle.putInt(KEY_LIST_POSITION + adapter.IDENTIFIER, view.getFirstVisiblePosition());
        bundle.putInt(KEY_SELECTED_INDEX + adapter.IDENTIFIER, adapter.getSelectedIndex());
    }

    public void cleanPosition() {
        adapter.setSelectedIndex(AppAdapter.INVALID_INDEX);
    }

}
