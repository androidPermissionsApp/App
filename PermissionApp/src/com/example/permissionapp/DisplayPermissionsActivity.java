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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_permissions);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	//	List<String> testL = new ArrayList<String>();
	//	testL.add("testString");
	//	testL.add("secondTestThing");
	//	testL.add("last string");
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	//	        android.R.layout.simple_list_item_1, testL);
	//	ListView listView = (ListView) findViewById(R.id.listview);
	//	listView.setAdapter(adapter);
		
		
		
		//List<String> appPermissions = getPermissions(); 
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		//        android.R.layout.simple_list_item_1, appPermissions);
		//ListView listView = (ListView) findViewById(R.id.listview);
		//listView.setAdapter(adapter);
		
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
			View rootView = inflater.inflate(
					R.layout.fragment_display_permissions, container, false);
			return rootView;
		}
	}
	//Taken from evilone from stackoverflow
	//(http://stackoverflow.com/questions/7937794/how-to-get-installed-applications-permissions)
	public List<String> getPermissions(){
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		List<String> appPermissions = new ArrayList<String>();
		for (ApplicationInfo applicationInfo : packages) {
		   //Log.d("test", "App: " + applicationInfo.name + " Package: " + applicationInfo.packageName);
			appPermissions.add("App: " + applicationInfo.name + " Package: " + applicationInfo.packageName);
		   try {
		      PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

		      //Get Permissions
		      String[] requestedPermissions = packageInfo.requestedPermissions;

		      if(requestedPermissions != null) {
		         for (int i = 0; i < requestedPermissions.length; i++) {
		            //Log.d("test", requestedPermissions[i]);
		        	 appPermissions.add(requestedPermissions[i]);
		         }
		      }
		   } catch (NameNotFoundException e) {
		      e.printStackTrace();
		   }
		}
		return appPermissions;
	}
	
}
