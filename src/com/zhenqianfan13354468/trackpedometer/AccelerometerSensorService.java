package com.zhenqianfan13354468.trackpedometer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class AccelerometerSensorService extends Service {

	// Accelerometer������
	private SensorManager accelerometerSM;
	private AccelerometerSensorListener accelerometerSD;
	Sensor accelerometerSensor;

	
	
	public static boolean isRun;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		new Thread(new Runnable() {
			public void run() {
				initAccelerometerSensor();
			}
		}).start();
	}

	private void initAccelerometerSensor() {
		isRun = false;

		// ��ȡϵͳ����SENSOR_SERVICE)����һ��SensorManager ����������������
		accelerometerSM = (SensorManager) getSystemService(SENSOR_SERVICE);

		// ͨ��SensorManager�����ȡ��Ӧ������������
		if (accelerometerSM != null) {
			accelerometerSensor = accelerometerSM
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}

		// ע����Ӧ��SensorService
		if (accelerometerSensor != null) {
			accelerometerSD = new AccelerometerSensorListener(this);
			// �������ʻ���SENSOR_DELAY_UI��SENSOR_DELAY_FASTEST��SENSOR_DELAY_GAME�ȣ�
			// ���ݲ�ͬӦ�ã���Ҫ�ķ�Ӧ���ʲ�ͬ���������ʵ������趨
			accelerometerSM.registerListener(accelerometerSD,
					accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
		}

		if (accelerometerSM != null && accelerometerSensor != null
				&& accelerometerSD != null) {
			isRun = true;
		} 
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (accelerometerSD != null) {
			accelerometerSM.unregisterListener(accelerometerSD);
		}
		isRun = false;
	}
}
