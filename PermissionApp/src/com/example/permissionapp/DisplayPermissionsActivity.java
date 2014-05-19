package com.example.permissionapp;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DisplayPermissionsActivity extends ActionBarActivity {
//	private ListView listview;
//	private ArrayAdapter<String> adapter;
//	private ArrayList<String> listNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_permissions);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_permissions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final ArrayAdapter<String> adapter;
			final ArrayList<String> listNames;
			
			View rootView = inflater.inflate(
					R.layout.fragment_display_permissions, container, false);
			
			//Make sure this cast doesn't cause problems		
			List<String> appNames = ((DisplayPermissionsActivity) getActivity()).getAppNames(); 
			List<ArrayList<String>> appPermissions = ((DisplayPermissionsActivity) getActivity()).getPermissions(); 
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appNames);
			ListView listView = (ListView)rootView.findViewById(R.id.list2);
		    listView.setAdapter(adapter);
			
			return rootView;
		}
	}
	
	public List<String> getAppNames(){
		List<String> appNames = new ArrayList<String>();
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo applicationInfo : packages) {
			appNames.add("App: " + applicationInfo.loadLabel(pm));
		}
		return appNames;
	}
	
	
	//Taken from evilone from stackoverflow
	//(http://stackoverflow.com/questions/7937794/how-to-get-installed-applications-permissions)
	public List<ArrayList<String>> getPermissions(){
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		List<ArrayList<String>> appPermissions = new ArrayList<ArrayList<String>>();
		for (ApplicationInfo applicationInfo : packages) {
			ArrayList<String> permissions = new ArrayList<String>();
		   try {
		      PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

		      //Get Permissions
		      String[] requestedPermissions = packageInfo.requestedPermissions;

		      if(requestedPermissions != null) {
		         for (int i = 0; i < requestedPermissions.length; i++) {
		        	 permissions.add(requestedPermissions[i]);
		         }
		      }
		   } catch (NameNotFoundException e) {
		      e.printStackTrace();
		   }
		   appPermissions.add(permissions);
		}
		return appPermissions;
	}
	
}
