package com.bgu.project.finalprojectir;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bgu.project.finalprojectir.classes.Device;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.classes.LocationTask;
import com.bgu.project.finalprojectir.classes.Task;
import com.bgu.project.finalprojectir.classes.TaskItemAdapter;
import com.bgu.project.finalprojectir.classes.TimeTask;
import com.bgu.project.finalprojectir.fragments.DeviceFragment;
import com.bgu.project.finalprojectir.tasks.AbstractRestTask;
import com.bgu.project.finalprojectir.tasks.RestActionTask;

import org.springframework.http.ResponseEntity;

public class EditTasksActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public static final boolean useREST = DeviceFragment.useREST; // for debug

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tasks);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    frag = LocationTasksFragment.newInstance(0);
                    break;
                case 1:
                    frag = TimeTasksFragment.newInstance(1);
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_location).toUpperCase(l);
                case 1:
                    return getString(R.string.title_time).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A Location Tasks Fragment.
     */
    public static class LocationTasksFragment extends Fragment {
        static TaskItemAdapter adapter;
        static List<Task> locationTaskData;
        public static final String TAG = "TimeTasksFragment";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static LocationTasksFragment newInstance(int sectionNumber) {
            return new LocationTasksFragment();
        }

        public LocationTasksFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_list_of_location_tasks, container, false);

            locationTaskData = new ArrayList<>();
            if (!useREST) {
                locationTaskData.add(new LocationTask(R.drawable.boaz_icon, "127.0.0.1", DeviceType.TV, "Sony", true, "Home", "mute"));
                locationTaskData.add(new LocationTask(R.drawable.yoav_icon, "127.0.0.2", DeviceType.AC, "Tadiran", false, "Work", "Power"));
            } else {
                GetLocationTasks getLocTasks = new GetLocationTasks();
                getLocTasks.execute((Void) null);
            }

            adapter = new TaskItemAdapter(getActivity(),
                    R.layout.list_item_row, locationTaskData);

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.locationTasksListView);
            listView.setAdapter(adapter);
            //need this for the long press on the list
            registerForContextMenu(listView);

            return rootView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_tasks_context, menu);
        }

        @Override
        //What happens when you press long on a list item
        public boolean onContextItemSelected(MenuItem item) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            switch (item.getItemId()) {
                case R.id.remove_task:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Warning")
                            .setMessage("Are you sure you want to remove task?")
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            Task remove = locationTaskData.remove(info.position);
                                            removeLocationTask(remove);
                                            adapter.notifyDataSetChanged();

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            Toast.makeText(getActivity(),
                                                    "Coward..",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        private void addLocationTask(Task task) {
            new ChangeLocationTask(task,"add").execute();
        }

        private void removeLocationTask(Task remove) {
            new ChangeLocationTask(remove,"remove").execute();
        }

        private class GetLocationTasks extends AbstractRestTask<Void, Void, ResponseEntity<InfoFromArduino[]>> {

            public GetLocationTasks() {
                super(TAG, "get locationTasks");
            }

            @Override
            protected ResponseEntity<InfoFromArduino[]> doInBackground(Void... params) {
                try {
                    URI uri = Utils.getURIForGetLocationTasks("user", "pass");
                    return restTemplate.getForEntity(uri, InfoFromArduino[].class);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(ResponseEntity<InfoFromArduino[]> infoFromArduino) {
                super.onPostExecute(infoFromArduino);
                for (InfoFromArduino fromArduino : infoFromArduino.getBody()) {
                    DeviceType deviceType = DeviceType.fromString(fromArduino.getType());
                    String brand = fromArduino.getBrand();
                    //TODO Boaz: add info getters for this constructor. missing: IP, Leaving/Entering, place description, Action
                    locationTaskData.add(new LocationTask(R.drawable.asi_icon, "127.0.0.3", deviceType, brand, false/*leaving*/, "Work", "VolUp"));
                }
                adapter.notifyDataSetChanged();
            }
        }

        private class ChangeLocationTask extends RestActionTask {
            private Task task;

            public ChangeLocationTask(Task task, String action) {
                super(TAG,"change device",Utils.getURIForAddOrRemoveLocationTask("127.0.0.8",action,task.getDeviceType(),task.getBrand()));
                this.task = task;
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    String responseEntity = null;
                    if(useREST) {
                        responseEntity = super.doInBackground();
                    }
                    return responseEntity;
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String infoFromArduino) {
                super.onPostExecute(infoFromArduino);
                adapter.notifyDataSetChanged();
                if(useREST) {
                    Toast.makeText(getActivity(), "Response "+infoFromArduino, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Removed task " + task.getDeviceType() +"-"+ task.getBrand() +" Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    /**
     * A Location Tasks Fragment.
     */
    public static class TimeTasksFragment extends Fragment {
        static TaskItemAdapter adapter;
        static List<Task> timeTaskData;
        public static final String TAG = "TimeTasksFragment";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TimeTasksFragment newInstance(int sectionNumber) {
            return new TimeTasksFragment();
        }

        public TimeTasksFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_list_of_time_tasks, container, false);

            timeTaskData = new ArrayList<>();
            if (!useREST) {
                timeTaskData.add(new TimeTask(R.drawable.boaz_icon, "127.0.0.1", DeviceType.TV, "LG", new Date(), new Time(), "mute"));
                timeTaskData.add(new TimeTask(R.drawable.yoav_icon, "127.0.0.2", DeviceType.AC, "Tadiran", new Date(), new Time(), "Power"));
                timeTaskData.add(new TimeTask(R.drawable.asi_icon, "127.0.0.3", DeviceType.TV, "Sony", new Date(), new Time(), "VolUp"));
            } else {
                GetTimeTasks getTimeTasks = new GetTimeTasks();
                getTimeTasks.execute((Void) null);
            }

            adapter = new TaskItemAdapter(getActivity(),
                    R.layout.list_item_row, timeTaskData);

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.timeTasksListView);
            listView.setAdapter(adapter);
            //need this for the long press on the list
            registerForContextMenu(listView);

            return rootView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_tasks_context, menu);
        }

        @Override
        //What happens when you press long on a list item
        public boolean onContextItemSelected(MenuItem item) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            switch (item.getItemId()) {
                case R.id.remove_task:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Warning")
                            .setMessage("Are you sure you want to remove task?")
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            Task remove = timeTaskData.remove(info.position);
                                            removeTimeTask(remove);
                                            adapter.notifyDataSetChanged();

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            Toast.makeText(getActivity(),
                                                    "Coward..",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        private void addTimeTask(Task task) {
            new ChangeTimeTask(task,"add").execute();
        }

        private void removeTimeTask(Task remove) {
            new ChangeTimeTask(remove,"remove").execute();
        }

        private class GetTimeTasks extends AbstractRestTask<Void, Void, ResponseEntity<InfoFromArduino[]>> {

            public GetTimeTasks() {
                super(TAG, "get timeTasks");
            }

            @Override
            protected ResponseEntity<InfoFromArduino[]> doInBackground(Void... params) {
                try {
                    URI uri = Utils.getURIForGetTimeTasks("user", "pass");
                    return restTemplate.getForEntity(uri, InfoFromArduino[].class);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(ResponseEntity<InfoFromArduino[]> infoFromArduino) {
                super.onPostExecute(infoFromArduino);
                for (InfoFromArduino fromArduino : infoFromArduino.getBody()) {
                    DeviceType deviceType = DeviceType.fromString(fromArduino.getType());
                    String brand = fromArduino.getBrand();
                    //TODO Boaz: add info getters for this constructor. missing: IP, Time, Action
                    timeTaskData.add(new TimeTask(R.drawable.asi_icon, "127.0.0.3", deviceType, brand, new Date(), new Time(), "VolUp"));
                }
                adapter.notifyDataSetChanged();
            }
        }

        private class ChangeTimeTask extends RestActionTask {
            private Task task;

            public ChangeTimeTask(Task task, String action) {
                super(TAG,"change device",Utils.getURIForAddOrRemoveTimeTask("127.0.0.8",action,task.getDeviceType(),task.getBrand()));
                this.task = task;
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    String responseEntity = null;
                    if(useREST) {
                        responseEntity = super.doInBackground();
                    }
                    return responseEntity;
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String infoFromArduino) {
                super.onPostExecute(infoFromArduino);
                adapter.notifyDataSetChanged();
                if(useREST) {
                    Toast.makeText(getActivity(), "Response "+infoFromArduino, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Removed task " + task.getDeviceType() +"-"+ task.getBrand() +" Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

}

