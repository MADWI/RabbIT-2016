package mad.zut.edu.pl.rabbit_2016.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mad.zut.edu.pl.rabbit_2016.R;
import mad.zut.edu.pl.rabbit_2016.fragments.AuthorsFragment;
import mad.zut.edu.pl.rabbit_2016.fragments.CompaniesFragment;
import mad.zut.edu.pl.rabbit_2016.fragments.EventsFragment;
import mad.zut.edu.pl.rabbit_2016.fragments.GuestFragment;
import mad.zut.edu.pl.rabbit_2016.fragments.PlaceholderFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAB_PRESENTATIONS = "pre";
    public static final String TAB_STANDS = "sta";
    public static final String TAB_SPECIAL_GUEST = "sgu";
    public static final String TAB_ABOUT_US = "abo";
    private DrawerLayout drawer;

    /**
     * Fragments selectable from drawer
     *
     * Arguments are:
     *   id - R.id.[...] matching name in 'menu/activity_main_drawer.xml'
     *   name - unique name used internally for remembering which fragment was recently open
     *   fragmentClass - class for fragment
     *   fragmentArguments - arguments for fragment (optional)
     *
     * Note: Drawer menu items that don't open fragment (e.g. ones opening activity)
     *       should not go here but to 'if (id == R.id.[...]'
     *       in {@link #onNavigationItemSelected(MenuItem)} method
     *       and should specify android:checkable="false" in xml
     */
    private static final DrawerFragmentItem[] DRAWER_FRAGMENTS = new DrawerFragmentItem[]{
            new DrawerFragmentItem(R.id.timetable, TAB_PRESENTATIONS, EventsFragment.class, PlaceholderFragment.makeArguments("Prezentacje")),
            new DrawerFragmentItem(R.id.stands, TAB_STANDS, CompaniesFragment.class, PlaceholderFragment.makeArguments("Firmy")),
            new DrawerFragmentItem(R.id.special_guest, TAB_SPECIAL_GUEST, GuestFragment.class, PlaceholderFragment.makeArguments("Gość specjalny")),
            new DrawerFragmentItem(R.id.about_us, TAB_ABOUT_US, AuthorsFragment.class, PlaceholderFragment.makeArguments("Autorzy"))
    };

    /**
     * Get Intent that can be used to open MainActivity and select the specified tab
     *
     * @param tab One of the TAB_* constants (e.g. {@link #TAB_PRESENTATIONS})
     */
    public static Intent getIntentToOpenWithTab(Context context, String tab) {
        return new Intent(
                ACTION_PREFIX_OPEN_FRAGMENT + tab,
                null,
                context,
                MainActivity.class
        );
    }

    /**
     * Action prefix for opening this activity and selecting fragment from drawer
     *
     * Note: As these actions are not registered in AndroidManifest.xml, they must be used with
     *       explicit Intent.
     *
     * @see #getIntentToOpenWithTab(Context, String)
     * @see #getDrawerItemFromIntent(Intent)
     */
    private static final String ACTION_PREFIX_OPEN_FRAGMENT = "pl.edu.zut.mad.rabbit2016.OPEN_FRAGMENT.";

    private static final String PREF_LAST_DRAWER_FRAGMENT = "last_selected_drawer_fragment";
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_toggle, R.string.navigation_drawer_toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Choose and open fragment
        if (savedInstanceState == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            // Choose fragment based on Intent
            DrawerFragmentItem item = getDrawerItemFromIntent(getIntent());

            // If above didn't picked anything, choose fragment based on settings
            if (item == null) {
                item = findDrawerItemFragmentWithName(prefs.getString("default_tab", null));
            }

            // If above didn't picked anything, choose fragment last selected fragment
            if (item == null) {
                item = findDrawerItemFragmentWithName(prefs.getString(PREF_LAST_DRAWER_FRAGMENT, null));
            }

            // If nothing above chosen anything, use default fragment
            if (item == null) {
                item = DRAWER_FRAGMENTS[0];
            }

            // Actually open the fragment
            openFragment(item);
            mNavigationView.setCheckedItem(item.id);

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Open fragment based on Intent
        DrawerFragmentItem item = getDrawerItemFromIntent(intent);
        if (item != null) {
            openFragment(item);
            mNavigationView.setCheckedItem(item.id);
        }
    }

    private static DrawerFragmentItem getDrawerItemFromIntent(Intent intent) {
        String action = intent.getAction();
        if (action != null && action.startsWith(ACTION_PREFIX_OPEN_FRAGMENT)) {
            return findDrawerItemFragmentWithName(action.substring(ACTION_PREFIX_OPEN_FRAGMENT.length()));
        }
        return null;
    }

    private void openFragment(DrawerFragmentItem item) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, item.createFragmentInstance())
                .commit();
    }

    private void rememberSelectedItem(DrawerFragmentItem item) {
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit()
                .putString(PREF_LAST_DRAWER_FRAGMENT, item.name)
                .apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Only actions that don't open fragment go here
        // See note at DRAWER_FRAGMENTS above
        //if (id == R.id.settings) {
        //    startActivity(new Intent(this, SettingsActivity.class));
        //} else {
            DrawerFragmentItem drawerFragmentItem = findDrawerItemFragmentWithId(id);
            if (drawerFragmentItem != null) {
                openFragment(drawerFragmentItem);
                rememberSelectedItem(drawerFragmentItem);
            }
        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static DrawerFragmentItem findDrawerItemFragmentWithId(int id) {
        for (DrawerFragmentItem item : DRAWER_FRAGMENTS) {
            if (item.id == id) {
                return item;
            }
        }

        return null;
    }

    private static DrawerFragmentItem findDrawerItemFragmentWithName(String name) {
        for (DrawerFragmentItem item : DRAWER_FRAGMENTS) {
            if (item.name.equals(name)) {
                return item;
            }
        }

        // If we didn't found fragment that was recently selected return null
        return null;
    }

    /**
     * See {@link #DRAWER_FRAGMENTS}
     */
    private static class DrawerFragmentItem {
        final int id;
        final String name;
        final Class<? extends Fragment> fragmentClass;
        final Bundle fragmentArguments;

        DrawerFragmentItem(int id, String name, Class<? extends Fragment> fragmentClass, Bundle fragmentArguments) {
            this.id = id;
            this.name = name;
            this.fragmentClass = fragmentClass;
            this.fragmentArguments = fragmentArguments;
        }

        DrawerFragmentItem(int id, String name, Class<? extends Fragment> fragmentClass) {
            this(id, name, fragmentClass, null);
        }

        Fragment createFragmentInstance() {
            try {
                Fragment fragment = fragmentClass.newInstance();
                fragment.setArguments(fragmentArguments);
                return fragment;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
