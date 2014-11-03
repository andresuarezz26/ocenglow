package com.example.animationexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {

	private static BluetoothAdapter bltAdapter=BluetoothAdapter.getDefaultAdapter();
	private static Set<BluetoothDevice>pairedDevices;
	/**
	 * Used when activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_activity);

		//Bluetooth devices list
		ListView lstData=(ListView) findViewById(R.id.lstData);
		ArrayList <String> dispositivos=new ArrayList<String>();
		pairedDevices = bltAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice dispositivo : bltAdapter.getBondedDevices())
			{
				dispositivos.add(dispositivo.getName()+"\n"+dispositivo.getAddress());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dispositivos );
			lstData.setAdapter(arrayAdapter); 


		}
		//Listen the event on Bluetooth device
		lstData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				//Get item selected
				String info=(String) parent.getItemAtPosition(position);
				String[]arreglo=info.split("\n");
				//Get device address
				String dir_dispositivo=arreglo[1];
				//Get device selected
				for (BluetoothDevice dispositivo : bltAdapter.getBondedDevices())
				{
					if(dispositivo.getAddress().equals(dir_dispositivo)){
						MainActivity.bltDevice=dispositivo;
						try{     
							Toast.makeText(BluetoothActivity.this, dispositivo.getAddress(), Toast.LENGTH_LONG).show();

							MainActivity.bltSocket = MainActivity.bltDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
							MainActivity.bltSocket.connect();     
							MainActivity.ins = MainActivity.bltSocket.getInputStream();     
							MainActivity.ons = MainActivity.bltSocket.getOutputStream(); 
							MainActivity.insr=new InputStreamReader(MainActivity.ins);
							MainActivity.buffereader=new BufferedReader(MainActivity.insr);
							Toast.makeText(BluetoothActivity.this, "Connected", Toast.LENGTH_LONG).show();
							MainActivity.conectado=true;
							//MainActivity.enEsperaDeInstrucciones();


							Intent enabler = new Intent(BluetoothActivity.this,MainActivity.class);
							startActivity(enabler);
						}   
						catch(Exception ex)   {     
							Toast.makeText(BluetoothActivity.this, "Not connected", Toast.LENGTH_LONG).show();
						} 
					}
				}
			}

		});
	}
	/**
	 * When user press back go to MainActivity
	 */
	@Override
	public void onBackPressed() {
		//Ir a la actividad principal si le da atrás
		Intent i = new Intent(BluetoothActivity.this,MainActivity.class);
		startActivity(i);
		finish();
	}






}
