package xmu.swordbearer.audio.ui;

import xmu.swordbearer.audio.AudioWrapper;
import xmu.swordbearer.audio.NetConfig;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	String LOG = "MainActivity";
	private AudioWrapper audioWrapper;

	// View
	private Button btnStartRecord;
	private Button btnStopRecord;
	private Button btnStartListen;
	private Button btnStopListen;
	private Button btnExit;
	private EditText ipEditText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		audioWrapper = AudioWrapper.getInstance();

		initView();
	}

	private void initView() {
		btnStartRecord = (Button) findViewById(R.id.startRecord);
		btnStartListen = (Button) findViewById(R.id.startListen);
		btnStopRecord = (Button) findViewById(R.id.stopRecord);
		btnStopListen = (Button) findViewById(R.id.stopListen);
		ipEditText = (EditText) findViewById(R.id.edittext_ip);

		//
		btnStopRecord.setEnabled(false);
		btnStopListen.setEnabled(false);

		btnExit = (Button) findViewById(R.id.btnExit);
		btnStartRecord.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String ipString = ipEditText.getText().toString().trim();
				NetConfig.setServerHost(ipString);
				btnStartRecord.setEnabled(false);
				btnStopRecord.setEnabled(true);
				audioWrapper.startRecord();
			}
		});

		btnStopRecord.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				btnStartRecord.setEnabled(true);
				btnStopRecord.setEnabled(false);
				audioWrapper.stopRecord();
			}
		});
		btnStartListen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				btnStartListen.setEnabled(false);
				btnStopListen.setEnabled(true);
				audioWrapper.startListen();
			}
		});
		btnStopListen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				btnStartListen.setEnabled(true);
				btnStopListen.setEnabled(false);
				audioWrapper.stopListen();
			}
		});
		btnExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				audioWrapper.stopListen();
				audioWrapper.stopRecord();
				System.exit(0);
			}
		});
	}
}