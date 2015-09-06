package mx.saudade.popularmoviesapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.fragments.DetailFragment;
import mx.saudade.popularmoviesapp.fragments.MovieFragment;
import mx.saudade.popularmoviesapp.models.Movie;


public class MovieActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = MovieActivity.class.getSimpleName();

    private static final String DETAIL_FRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DetailFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startActivity() {
        Intent i = new Intent();
        i.setClass(this, SettingsActivity.class);
        this.startActivity(i);
    }

    private void startDetailActivity(Movie movie) {
        Intent i = new Intent();
        i.setClass(this, DetailActivity.class);
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, movie);
        startActivity(i);
    }

    private void startDetailFragment(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(Intent.EXTRA_SHORTCUT_NAME, movie);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) parent.getAdapter().getItem(position);
        ((MovieAdapter) parent.getAdapter()).setSelectedIndex(position);
        if(mTwoPane) {
           startDetailFragment(movie);
        } else {
            startDetailActivity(movie);
        }
    }


}
