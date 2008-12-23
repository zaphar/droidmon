package com.marzhillstudios.android.monitor;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
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
        	List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(10);
        	content = "Running Tasks: " + tasks.size() + "\n"
   		 		+"Running services: " + services.size() + "\n"
   		 		;
        }
        catch (SecurityException e) {
        	content = "Exception: ["+e.toString()+"] encountered while getting process and task info";
		 		;
        }
        
        //Render our text view
        tv.setText(content);
        setContentView(tv);
    }
}