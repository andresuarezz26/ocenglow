package com.example.animationexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	//Attributes******
	private static boolean isLeft=true;
	private ImageView _imagView;
	private Timer _timer;
	private int _index;
	private MyHandler handler;
	private int iteraciones=0;
	private SeekBar volumeControl = null;
	boolean isMoving=false;
	public static boolean  conectado = false;
	private static Thread workerThread;
	private static byte[] readBuffer;
	private static int readBufferPosition;
	private volatile static boolean stopWorker;
	private int REQUEST_ENABLE_BT = 2;
	// Bluetooth adapter
	private static BluetoothAdapter bltAdapter;
	// Devices
	public static BluetoothDevice bltDevice;
	// Socket
	public static BluetoothSocket bltSocket;
	// Input flow
	public static InputStream ins;
	// Output flow
	public static OutputStream ons;
	//Read the flwo
	public static InputStreamReader insr;
	public static BufferedReader buffereader;

	/**
	 *When Activity is created the Bluetooth connection starts and the program is waiting by Swipe Gestures
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Create Bluetooth conection
		crearConexionBT();
		//hide actionbar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();

		_imagView = (ImageView) findViewById(R.id.imageView1);

		//Listen swipe gestures events
		_imagView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

			//Left Swipe
			@Override
			public void onSwipeLeft() {
				if(!isMoving){
					isLeft=true;
					isMoving=true;
					enviarDatos("1");

					_index=0;
					_timer= new Timer();
					_timer.schedule(new TickClass(), 230, 50);
				}else{
				}
			}
			//Right swipe
			public void onSwipeRight() {
				if(!isMoving){
					isLeft=false;
					isMoving=true;
					enviarDatos("2");

					_index=32;
					_timer= new Timer();
					_timer.schedule(new TickClass(), 230, 50);
				}else{
				}
			}
		});
		//Start Handler
		handler = new MyHandler();
		//Start the SeekBar
		volumeControl = (SeekBar) findViewById(R.id.seek1);
		//Listen to SeekBarEvent
		volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			//Validate the speed 
			public void onStopTrackingTouch(SeekBar seekBar) {
				int level=0;
				if(progressChanged<=20){
					level=1;
					enviarDatos(level+2+"");
				}else
					if(progressChanged<=40){
						level=2;
						enviarDatos(level+2+"");
					}else
						if(progressChanged<=60){
							level=3;
							enviarDatos(level+2+"");
						}else
							if(progressChanged<=80){
								level=4;
								enviarDatos(level+2+"");
							}else{
								level=5;
								enviarDatos(level+2+"");
							}
				Toast.makeText(MainActivity.this,"Delay: "+level, 
						Toast.LENGTH_SHORT).show();

			}
		});




	}

	/**
	 * Exceution thread that control the animation
	 * @author usuario
	 *
	 */
	private class TickClass extends TimerTask {
		@Override
		public void run() {
			Log.e("score", "Enter to run method of TickClass ");
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(_index);
			if(isLeft){
				if (_index <= 30) {
					_index++;
					iteraciones++;

				} else {
					_index = 0;
					handler.sendEmptyMessage(_index);
					this.cancel();
					isMoving=false;

				}
			}else{
				if (_index <= 63) {
					_index++;
					iteraciones++;

				} else {
					_index = 32;
					handler.sendEmptyMessage(_index);
					this.cancel();
					isMoving=false;

				}

			}
		}
	}

	/**
	 * This class handdle the animation´s images
	 * @author usuario
	 *
	 */
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.e("score", "Inside Handler ");
			try {
				Bitmap bmp = BitmapFactory.decodeStream(MainActivity.this
						.getAssets().open("jelly"+ _index + ".png"));
				_imagView.setImageBitmap(bmp);

				Log.v("Loaing Image: ", _index + "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("Exception in Handler ", e.getMessage());
			}
		}
	}


	/**
	 * Create connection with bluetooth device
	 */
	private void crearConexionBT(){

		try{     
			bltSocket = MainActivity.bltDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			bltSocket.connect();     
			ins = MainActivity.bltSocket.getInputStream();     
			ons = MainActivity.bltSocket.getOutputStream(); 
			insr=new InputStreamReader(MainActivity.ins);
			buffereader=new BufferedReader(MainActivity.insr);
			mostrarMensaje("Connected");
			conectado=true;


		}   
		catch(Exception ex)   {     
			mostrarMensaje("Not connected");
		} 
	}


	/**
	 * Show messages through Toast Class
	 * @param theMsg
	 */
	private void mostrarMensaje(String theMsg) {
		Toast msg = Toast.makeText(getBaseContext(), theMsg, 50);
		msg.show();
	}

	/**
	 * Send data via Bluetooth
	 * @param message to send
	 */
	void enviarDatos(String mensaje) {
		try {
			if (conectado)
				ons.write(mensaje.getBytes());
			// mmOutputStream.write('A');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}