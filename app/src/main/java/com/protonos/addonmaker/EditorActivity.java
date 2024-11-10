package com.protonos.addonmaker;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.regex.*;
import org.json.*;
import android.content.Context;

public class EditorActivity extends AppCompatActivity implements AdvancedWebView.Listener {
	
	private String code = "";
	
	private LinearLayout linear1;
	private AdvancedWebView webview2;
	private Button button1;
	private Button button2;
	private ProgressBar progressbar1;
	private Button button4;
	private Button button5;
	
	private AlertDialog.Builder dialog;
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.editor);
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
        webview2 = (AdvancedWebView) findViewById(R.id.webview2);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		progressbar1 = findViewById(R.id.progressbar1);
		button4 = findViewById(R.id.button4);
		button5 = findViewById(R.id.button5);
		dialog = new AlertDialog.Builder(this);
		
		webview2.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview2.evaluateJavascript("getCode()", new ValueCallback<String>() {
					    @Override
					    public void onReceiveValue(String value) {
						        code = value.substring((int)(1), (int)(value.length() - 1));
						dialog.setTitle("Code");
						dialog.setMessage(code.replaceAll("\\\\n", "\n").trim()
						     );
						dialog.create().show();
						    }
				});
				
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview2.evaluateJavascript("getBlocks()", new ValueCallback<String>() {
					    @Override
					    public void onReceiveValue(String value) {
						        FileUtil.writeFile(getIntent().getStringExtra("path"), value);
						SketchwareUtil.showMessage(getApplicationContext(), "Saved");
						    }
				});
				
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview2.evaluateJavascript("undoAction(false)", new ValueCallback<String>() {
					    @Override
					    public void onReceiveValue(String value) {
						         
						    }
				});
				
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview2.evaluateJavascript("undoAction(true)", new ValueCallback<String>() {
					    @Override
					    public void onReceiveValue(String value) {
						         
						    }
				});
				
			}
		});
	}
	
	private void initializeLogic() {
		JavaScriptInterface jsInterface = new JavaScriptInterface(this);
		webview2.addJavascriptInterface(jsInterface, "AndroidInterface");
		
		webview2.setListener(EditorActivity.this, EditorActivity.this);
		webview2.getSettings().setJavaScriptEnabled(true);
		webview2.setMixedContentAllowed(false);
		webview2.addPermittedHostname("file:///android_asset/index.html");
		webview2.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview2.clearCache(true);
		webview2.clearHistory();
		webview2.loadUrl("/android_asset/index.html");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		webview2.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		webview2.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		webview2.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		webview2.onDestroy();
	}
    public class JavaScriptInterface {
		private Context mContext;
		
		public JavaScriptInterface(Context context) {
				mContext = context;
		}
		@JavascriptInterface
		public void showToast(String message) {
				SketchwareUtil.showMessage(getApplicationContext(), message);
		}
	}
	
	
    public void onPageStarted(String _url, Bitmap _favicon) {
	}
	
	
    public void onPageFinished(String _url) {
		if (FileUtil.isExistFile(getIntent().getStringExtra("path"))) {
			webview2.evaluateJavascript("loadBlocks('".concat(FileUtil.readFile(getIntent().getStringExtra("path")).concat("')")), new ValueCallback<String>() {
				    @Override
				    public void onReceiveValue(String value) {
					        progressbar1.setVisibility(View.INVISIBLE);
					    }
			});
			
		}
		else {
			progressbar1.setVisibility(View.INVISIBLE);
		}
	}
	
	
    public void onPageError(int _code, String _description, String _url) {
	}
	
	
    public void onDownloadRequested(String _url, String _filename, String _mimeType, long _contentLength, String _contentDisposition, String _userAgent) {
		SketchwareUtil.showMessage(getApplicationContext(), "⚠️WARNING forbidden action detected");
	}
	
	
    public void onExternalPageRequest(String _url) {
		if (false) {
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(_url));
			startActivity(intent);
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