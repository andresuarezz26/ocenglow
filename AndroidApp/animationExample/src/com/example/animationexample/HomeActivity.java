package com.example.animationexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class HomeActivity extends Activity {
	//Bluetooth Adapter
	private static BluetoothAdapter bltAdapter;
	/**
	 * Created when Activity start
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Hide Action Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();
		setContentView(R.layout.activityhome);

	}
	/**
	 * Event of the button
	 * @param v
	 */
	public void onEntrar(View v){

		//Start Pairing
		Intent i= new Intent(HomeActivity.this,BluetoothActivity.class);
		startActivity(i);
		finish();


	}
}
