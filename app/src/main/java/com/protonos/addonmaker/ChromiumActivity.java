package com.protonos.addonmaker;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import im.delight.android.webview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class ChromiumActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	
	private double iter = 0;
	private String cb = "";
	private boolean countfiles = false;
	
	private ArrayList<HashMap<String, Object>> project_files = new ArrayList<>();
	private ArrayList<String> file_list = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ScrollView vscroll1;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear3;
	private TextView textview2;
	private LinearLayout linear5;
	private TextView textview3;
	private ListView listview1;
	private LinearLayout linear7;
	private TextView textview6;
	private LinearLayout linear6;
	private Button button3;
	private Button button1;
	private Button button2;
	private CheckBox checkbox1;
	
	private TimerTask timer;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private AlertDialog.Builder addfile;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chromium);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		textview2 = findViewById(R.id.textview2);
		linear5 = findViewById(R.id.linear5);
		textview3 = findViewById(R.id.textview3);
		listview1 = findViewById(R.id.listview1);
		linear7 = findViewById(R.id.linear7);
		textview6 = findViewById(R.id.textview6);
		linear6 = findViewById(R.id.linear6);
		button3 = findViewById(R.id.button3);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		checkbox1 = findViewById(R.id.checkbox1);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		dialog = new AlertDialog.Builder(this);
		addfile = new AlertDialog.Builder(this);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MetaconfigDialogFragmentActivity dialog = new MetaconfigDialogFragmentActivity();
				dialog.show(getSupportFragmentManager(),"1");
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final LinearLayout lin1 = new LinearLayout(ChromiumActivity. this);
				final Button btn1 = new Button(ChromiumActivity. this);
				final Button btn2 = new Button(ChromiumActivity. this);
				final Button btn3 = new Button(ChromiumActivity. this);
				addfile.setView(lin1);
				lin1.addView(btn1);
				lin1.addView(btn2);
				lin1.addView(btn3);
				lin1.setOrientation(LinearLayout.VERTICAL);
				addfile.setTitle("Pick an option");
				btn1.setText("Pick file");
				btn2.setText("Pick image");
				btn3.setText("Logic");
				btn1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						startActivityForResult(fp, REQ_CD_FP);
					}
				});
				btn2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						SketchwareUtil.showMessage(getApplicationContext(), "Use pick file instead");
					}
				});
				btn3.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						iter = 1;
						while(true) {
							if (FileUtil.isExistFile(getIntent().getStringExtra("path").concat("/blockly".concat(String.valueOf((long)(iter)).concat(".logic"))))) {
								iter++;
							}
							else {
								FileUtil.writeFile(getIntent().getStringExtra("path").concat("/blockly".concat(String.valueOf((long)(iter)).concat(".logic"))), "{}");
								break;
							}
						}
						_updateFiles();
					}
				});
				AlertDialog dialogf = addfile.create();
				dialogf.show();
				dialogf.getWindow().setGravity(Gravity.CENTER);
			}
		});
	}
	
	private void initializeLogic() {
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				FileUtil.copyFile(_filePath.get((int)(0)), getIntent().getStringExtra("path").concat("/".concat(Uri.parse(_filePath.get((int)(0))).getLastPathSegment())));
				_updateFiles();
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_updateFiles();
	}
	public void _updateFiles() {
		file_list.clear();
		project_files.clear();
		iter = 0;
		FileUtil.listDir(getIntent().getStringExtra("path"), file_list);
		for(int _repeat13 = 0; _repeat13 < (int)(file_list.size()); _repeat13++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("path", file_list.get((int)(iter)));
				project_files.add(_item);
			}
			
			project_files.get((int)iter).put("type", "file");
			if (FileUtil.isDirectory(file_list.get((int)(iter)))) {
				project_files.get((int)iter).put("type", "dir");
			}
			else {
				String someFilepath = file_list.get((int)(iter));
				String extension = someFilepath.substring(someFilepath.lastIndexOf("."));
				if (extension.equals(".logic")) {
					project_files.get((int)iter).put("type", "logic");
				}
				if (extension.equals(".png") || (extension.equals(".jpg") || (extension.equals(".webp") || extension.equals(".jpeg")))) {
					project_files.get((int)iter).put("type", "image");
				}
			}
			iter++;
		}
		listview1.setAdapter(new Listview1Adapter(project_files));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.defiler, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			
			textview1.setText(Uri.parse(_data.get((int)_position).get("path").toString()).getLastPathSegment());
			if (_data.get((int)_position).get("type").toString().equals("logic")) {
				imageview3.setImageResource(R.drawable.logic);
			}
			if (_data.get((int)_position).get("type").toString().equals("dir")) {
				linear1.setVisibility(View.GONE);
			}
			if (_data.get((int)_position).get("type").toString().equals("image")) {
				imageview3.setImageResource(R.drawable.ic_panorama_black);
			}
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("type").toString().equals("logic")) {
						intent.putExtra("path", _data.get((int)_position).get("path").toString());
						intent.setClass(getApplicationContext(), EditorActivity.class);
						startActivity(intent);
					}
					if (_data.get((int)_position).get("type").toString().equals("dir")) {
						
					}
					if (_data.get((int)_position).get("type").toString().equals("image")) {
						
					}
				}
			});
			imageview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					dialog.setTitle("Delete");
					dialog.setMessage("This action can not be undone!");
					dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							FileUtil.deleteFile(_data.get((int)_position).get("path").toString());
							_updateFiles();
						}
					});
					dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			});
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}