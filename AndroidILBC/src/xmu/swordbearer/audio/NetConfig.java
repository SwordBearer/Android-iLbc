package xmu.swordbearer.audio;

import android.util.Log;

/*
 * UDP configure
 */
public class NetConfig {
	public static String SERVER_HOST = "192.168.1.102";// server ip
	public static final int SERVER_PORT = 5656;// server port
	public static final int CLIENT_PORT = 5757;// client port

	public static void setServerHost(String ip) {
		Log.e("NetConfig", "重新设置的IP是" + ip);
		SERVER_HOST = ip;
	}
}
