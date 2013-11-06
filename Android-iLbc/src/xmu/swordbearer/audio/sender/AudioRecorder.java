package xmu.swordbearer.audio.sender;

import xmu.swordbearer.audio.AudioConfig;
import android.media.AudioRecord;
import android.util.Log;

public class AudioRecorder implements Runnable {

	String LOG = "Recorder ";

	private boolean isRecording = false;
	private AudioRecord audioRecord;

	private static final int BUFFER_FRAME_SIZE = 480;
	private int audioBufSize = 0;

	//
	private byte[] samples;// data
	// the size of audio read from recorder
	private int bufferRead = 0;
	// samples size
	private int bufferSize = 0;

	/*
	 * start recording
	 */
	public void startRecording() {
		bufferSize = BUFFER_FRAME_SIZE;

		audioBufSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLERATE,
				AudioConfig.RECORDER_CHANNEL_CONFIG, AudioConfig.AUDIO_FORMAT);
		if (audioBufSize == AudioRecord.ERROR_BAD_VALUE) {
			Log.e(LOG, "audioBufSize error");
			return;
		}
		samples = new byte[audioBufSize];
		// 初始化recorder
		if (null == audioRecord) {
			audioRecord = new AudioRecord(AudioConfig.AUDIO_RESOURCE,
					AudioConfig.SAMPLERATE,
					AudioConfig.RECORDER_CHANNEL_CONFIG,
					AudioConfig.AUDIO_FORMAT, audioBufSize);
		}
		new Thread(this).start();
	}

	/*
	 * stop
	 */
	public void stopRecording() {
		this.isRecording = false;
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void run() {
		// start encoder before recording
		AudioEncoder encoder = AudioEncoder.getInstance();
		encoder.startEncoding();
		System.out.println(LOG + "audioRecord startRecording()");
		audioRecord.startRecording();
		System.out.println(LOG + "start recording");

		this.isRecording = true;
		while (isRecording) {
			bufferRead = audioRecord.read(samples, 0, bufferSize);
			if (bufferRead > 0) {
				// add data to encoder
				encoder.addData(samples, bufferRead);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(LOG + "end recording");
		audioRecord.stop();
		encoder.stopEncoding();
	}
}
