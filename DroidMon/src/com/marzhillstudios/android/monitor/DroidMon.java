package com.marzhillstudios.android.monitor;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StatFs;
import android.widget.TextView;

public class DroidMon extends Activity {
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        TextView tv = new TextView(this);
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        am.getMemoryInfo(mi);
        String content = "Available Memory: " + mi.availMem + "\n";
        try {
        	List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(ActivityManager.RECENT_WITH_EXCLUDED);
        	List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(ActivityManager.RECENT_WITH_EXCLUDED);
        	content += "Running Tasks: " + tasks.size() + "\n"
   		 		+"Running services: " + services.size() + "\n"
   		 		;
        }
        catch (SecurityException e) {
        	content += "Exception: ["+e.toString()+"] encountered while getting process and task info";
		 		;
        }
        try {
	        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
	        NetworkInfo[] ni = cm.getAllNetworkInfo();
	        content += "Networks: "+ni.length+"\n";
	        for (int n=0; n<ni.length; n++) {
	        	content +="Network "+ni[n].getTypeName() + " is "+ ni[n].getDetailedState().name() + "\n";
	        }
	        StatFs fs = new StatFs("/data");
	        content += "Disk Free blocks: " + fs.getFreeBlocks() + "\n";
	        content += "Disk Total Blocks: " + fs.getBlockCount() + "\n";
	        
	        LocationManager lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
	        List<String> providers = lm.getProviders(true);
	        for (String p : providers) {
	        	Location loc = lm.getLastKnownLocation(p);
	        	double altitude = 0; double longitude =0; 
	        	double latitude = 0;
	        	float bearing = 0;
	        	if (loc != null) {
			        	if (loc.hasAltitude())
		        		altitude = loc.getAltitude();
		        	latitude = loc.getLatitude();
		        	longitude = loc.getLongitude();
		        	if (loc.hasBearing())
		        		bearing = loc.getBearing();
		        	content += "Provider ["+p+"] has alt:"+altitude+", lat:"+latitude+", long:"+longitude+" at bearing:"+bearing;
	        	} else {
	        		content += "Provider ["+p+"] has no location info";
	        	}
	        }
        }
        catch (Exception e) {
        	content += "Encountered exception: "+e;
        }
        //Render our text view
        tv.setText(content);
        setContentView(tv);
    }
}