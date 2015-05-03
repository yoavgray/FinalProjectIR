package com.bgu.project.finalprojectir.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bgu.project.finalprojectir.classes.DeviceItemAdapter;
import com.bgu.project.finalprojectir.InfoFromArduino;
import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.Utils;
import com.bgu.project.finalprojectir.classes.Device;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.tasks.AbstractRestTask;
import com.bgu.project.finalprojectir.tasks.RestActionTask;

import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {
    public static final String TAG = "DeviceFragment";
    static FragmentManager fm;
    static DeviceItemAdapter adapter;
    static List<Device> deviceData;
    final static int RESULT_ADD_DEVICE = 1;
    final static int RESULT_OK = -1;
    static String ip;
    public static final boolean useREST = true; // for debug

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
        setHasOptionsMenu(true);
        ip = getArguments().getString("ip");
        fm = getFragmentManager();
        //sha
        deviceData = new ArrayList<>();
        if(!useREST) {
            deviceData.add(new Device(R.drawable.tv_icon, DeviceType.TV, "LG"));
            deviceData.add(new Device(R.drawable.ac_icon, DeviceType.AC, "Tornado"));
        } else {
            GetDevices getDevices = new GetDevices();
            getDevices.execute((Void) null);
        }

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
                Device device = deviceData.get(pos);
                if (device.getDeviceType().equals(DeviceType.AC)){
                    ft.replace(R.id.container, new ACRemoteFragment());
                    ft.addToBackStack("ACRemote");
                    ft.commit();
                }else if(device.getDeviceType().equals(DeviceType.TV)){
                    TVRemoteFragment tvRemoteFragment = new TVRemoteFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("ip",ip);
                    bundle.putString("brand",device.getBrand());
                    tvRemoteFragment.setArguments(bundle);
                    ft.replace(R.id.container, tvRemoteFragment);
                    ft.addToBackStack("TVRemote");
                    ft.commit();
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
    //What happens when you press long on a list item
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.remove_device:
                Log.d("Hey!","Remove Device!");
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to remove device?")
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        Device remove = deviceData.remove(info.position);
                                        removeDevice(remove);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_ADD_DEVICE:
            if (resultCode == RESULT_OK) {
                // The user added a device
                DeviceType type =  DeviceType.fromString(data.getStringExtra("typeResult"));
                String brand = data.getStringExtra("brandResult");
                int logo = type.equals(DeviceType.TV) ? R.drawable.tv_icon : R.drawable.ac_icon;
                Device device = new Device(logo, type, brand);
                deviceData.add(device);
                addDevice(device);
                adapter.notifyDataSetChanged();
            }
            break;
        }
    }

    private void addDevice(Device device) {
        new ChangeDevice(device,"add").execute();
    }

    private void removeDevice(Device remove) {
        new ChangeDevice(remove,"remove").execute();
    }

    private class GetDevices extends AbstractRestTask<Void, Void, ResponseEntity<InfoFromArduino[]>> {

        public GetDevices() {
            super(TAG, "get devices");
        }

        @Override
        protected ResponseEntity<InfoFromArduino[]> doInBackground(Void... params) {
            try {
                URI uri = Utils.getURIForGetDevices(ip);
                return restTemplate.getForEntity(uri, InfoFromArduino[].class);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<InfoFromArduino[]> infoFromArduino) {
            super.onPostExecute(infoFromArduino);
            if (infoFromArduino != null) {
                for (InfoFromArduino fromArduino : infoFromArduino.getBody()) {
                    DeviceType deviceType = DeviceType.fromString(fromArduino.getType());
                    if (deviceType.equals(DeviceType.TV)) {
                        deviceData.add(new Device(R.drawable.tv_icon, deviceType, fromArduino.getBrand()));
                    } else if (deviceType.equals(DeviceType.AC)) {
                        deviceData.add(new Device(R.drawable.ac_icon, deviceType, fromArduino.getBrand()));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class ChangeDevice extends RestActionTask {

        private Device device;

        public ChangeDevice(Device device,String action) {
            super(TAG,"change device",Utils.getURIForAddOrRemoveDevice(ip,action,device.getDeviceType(),device.getBrand()));
            this.device = device;
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
                Toast.makeText(getActivity(), "Removed device " + device.getDeviceType() +"-"+ device.getBrand() +" Successfully", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
