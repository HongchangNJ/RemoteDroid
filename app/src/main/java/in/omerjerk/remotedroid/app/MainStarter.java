package in.omerjerk.remotedroid.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by omerjerk on 9/10/15.
 */
public class MainStarter {

    private static final String TAG = "MainStarter";

    private static final String COMMAND = "su -c \"export CLASSPATH=/sdcard/Main.dex /system/bin/app_process " +
            "/sdcard in.omerjerk.remotedroid.app.Main\"\n";

    private Context context;

    public MainStarter(Context context) {
        this.context = context;
    }

    public void start() {
//        Shell.SU.run(String.format(COMMAND, getApkLocation()));
        try {
            Log.d(TAG, "===EXECUTING===" + COMMAND);
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.writeBytes(COMMAND);
            outputStream.flush();
            process.waitFor();
        } catch (IOException e) {
            Log.d(TAG, "MainStarter: " + e.getMessage());
            e.printStackTrace();
            Log.d(TAG, "MainStarter: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getApkLocation() {
        PackageManager pm = context.getPackageManager();

        for (ApplicationInfo app : pm.getInstalledApplications(0)) {
//            Log.d("PackageList", "package: " + app.packageName + ", sourceDir: " + app.sourceDir);
            if (app.packageName.equals(context.getPackageName())) {
                Log.d(TAG, app.sourceDir);
                return app.sourceDir;
            }
        }
        return null;
    }
}
