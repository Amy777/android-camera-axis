package com.myapps;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Home extends Activity {
	private static Dialog dialog_about;
	private static Activity activity;
	private ListView L;
	private ArrayList<Camera> camList;
	
	
	private String FILE = "camera.ser";
	private String messageRemove = "Etes vous sur de vouloir supprimer cette camera ?";

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";

	
	public Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	private void updateListView(boolean init) {

		List<Map<String, ?>> printCamList = new LinkedList<Map<String, ?>>();
		for (int i = 0; i < camList.size(); i++) {
			printCamList.add(createItem(camList.get(i).id, camList.get(i).ip));
		}
		
		/* Affichage de la liste */
		L = (ListView) findViewById(R.id.lv);
		L.setAdapter(new SimpleAdapter(this, printCamList, R.layout.list_item,  new String[] { ITEM_TITLE, ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));  
		if (init = true)
			L.setTextFilterEnabled(true);

	}


	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		activity = this;

		try {
			FileInputStream fichier = activity.getApplication().openFileInput(
					FILE);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			Log.i(getString(R.string.logTag), "lecture cameras effectuee");
			camList = (ArrayList<Camera>) ois.readObject();
			updateListView(false);
		} catch (java.io.IOException e) {
			Log.i(getString(R.string.logTag), "file not found");
			camList = new ArrayList<Camera>();
			updateListView(true);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		L.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {

				AlertDialog alert_reset;
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage(messageRemove)
						.setCancelable(false)
						.setPositiveButton("Oui",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										camList.remove(position);
										updateListView(false);
									}
								})
						.setNegativeButton("Non",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				alert_reset = builder.create();
				alert_reset.show();
				return true;
			}
		});

		L.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = new Intent(activity.getApplicationContext(),
						Video.class);
				Bundle objetbunble = new Bundle();
				objetbunble.putSerializable(getString(R.string.camTag), camList.get(position));
				intent.putExtras(objetbunble);
				startActivity(intent);
			}
		});

	}

	protected void onDestroy() {
		try {
			FileOutputStream fichier = activity.getApplicationContext()
					.openFileOutput(FILE, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(camList);
			oos.flush();
			oos.close();
			fichier.close();
			Log.i(getString(R.string.logTag), "camera save");
		} catch (java.io.IOException e) {
			Log.i(getString(R.string.logTag), "file not save");
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
			Bundle extras = intent.getExtras();
			Camera tmp = (Camera) extras.getSerializable(getString(R.string.camTag));
			Log.i(getString(R.string.logTag), "camera " + tmp.id + " recuperer");

			camList.add(tmp);
			Log.i(getString(R.string.logTag), "camera ajouter");

			updateListView(false);
		}
	}

	/* Affichage du menu */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_option_about:
			dialog_about = new Dialog(activity);
			dialog_about.setContentView(R.layout.dialog_about);
			dialog_about.setTitle("A Propos");

			TextView text = (TextView) dialog_about.findViewById(R.id.about);
			text.setText(activity.getResources().getString(R.string.about));
			ImageView image = (ImageView) dialog_about
					.findViewById(R.id.about_image);
			image.setImageResource(R.drawable.ic_fave1);
			Button close = (Button) dialog_about.findViewById(R.id.aboutClose);
			close.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_about.cancel();
				}
			});

			dialog_about.show();
			return true;
		case R.id.menu_quitter:
			Log.i(getString(R.string.logTag), "Exit");
			activity.finish();
			return true;
		case R.id.menu_ajouter:
			Intent intent = new Intent(this, AddCam.class);
			startActivityForResult(intent, 1);
			return true;
		case R.id.menu_4vue:
			Intent intent1 = new Intent(this, MultiVideo.class);
			Bundle objetbunble = new Bundle();
			objetbunble.putSerializable(getString(R.string.camListTag), camList);
			intent1.putExtras(objetbunble);
			Log.i(getString(R.string.logTag), "Start 4 vues");
			startActivity(intent1);
		}
		return false;
	}
}