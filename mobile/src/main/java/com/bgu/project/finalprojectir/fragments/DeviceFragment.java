package com.bgu.project.finalprojectir.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bgu.project.finalprojectir.DeviceItemAdapter;
import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.classes.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {
    static FragmentManager fm;
    static DeviceItemAdapter adapter;
    static List<Device> deviceData;
    final static int RESULT_ADD_DEVICE = 1, RESULT_ADD_TASK = 2;
    final static int RESULT_OK = -1;

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);

        fm = getFragmentManager();
        //sha
        deviceData = new ArrayList<>();
        deviceData.add(new Device(R.drawable.ac_icon, "A/C Unit", "Sony"));
        deviceData.add(new Device(R.drawable.tv_icon, "Television", "Tadiran"));

        adapter = new DeviceItemAdapter(getActivity(),
                R.layout.list_item_row, deviceData);

        final ListView listView = (ListView) rootView
                .findViewById(R.id.devicePropertiesListView);
        listView.setAdapter(adapter);
        //need this for the long press on the list
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View myView, int pos,
                                    long arg3) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                switch (pos) {
                    case 0:
                        ft.replace(R.id.container, new ACRemoteFragment());
                        ft.addToBackStack("ACRemote");
                        ft.commit();
                        break;
                    case 1:
                        ft.replace(R.id.container, new TVRemoteFragment());
                        ft.addToBackStack("TVRemote");
                        ft.commit();
                        break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_device_context, menu);
    }

    @Override
    //What happens when you press long on a contact
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add_task:
                //TODO: Add task
                AddTaskFragment atFragment = new AddTaskFragment();
                // Show DialogFragment
                atFragment.setTargetFragment(this, RESULT_ADD_TASK);
                atFragment.show(fm, "Dialog Fragment");
                return true;
            case R.id.remove_device:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to remove device?")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        //TODO: Boaz, make a remove device from arduino REST request and then refresh page
                                        deviceData.remove(info.position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(
                                                getActivity(),
                                                "Device Removed Successfuly",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        Toast.makeText(getActivity(),
                                                "You chicken shit..",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_ADD_DEVICE:
                if (resultCode == RESULT_OK) {
                    // The user added a device
                    String type = data.getStringExtra("typeResult");
                    String brand = data.getStringExtra("brandResult");
                    int logo = type.equals("Television") ? R.drawable.tv_icon : R.drawable.ac_icon;
                    deviceData.add(new Device(logo,type,brand));
                    adapter.notifyDataSetChanged();
                }
                break;
            case RESULT_ADD_TASK:
                if (resultCode == RESULT_OK) {
                    // The user added a task
                    Toast.makeText(getActivity(),"Task added",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
