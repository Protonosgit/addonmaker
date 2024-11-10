package com.protonos.addonmaker;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import im.delight.android.webview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class MetaconfigDialogFragmentActivity extends DialogFragment {
	
	private LinearLayout frame;
	private TextView headding;
	private ScrollView framescroll;
	private LinearLayout scrollcore;
	private LinearLayout ine1;
	private LinearLayout ine2;
	private LinearLayout ine3;
	private LinearLayout ine4;
	private LinearLayout ine5;
	private LinearLayout ine6;
	private Button savebtn;
	private TextView sh1;
	private EditText in1;
	private TextView sh2;
	private EditText in2;
	private TextView sh3;
	private EditText in3;
	private TextView sh4;
	private EditText in4;
	private TextView sh5;
	private EditText in5;
	private TextView sh6;
	private EditText in6;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.metaconfig_dialog_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		frame = _view.findViewById(R.id.frame);
		headding = _view.findViewById(R.id.headding);
		framescroll = _view.findViewById(R.id.framescroll);
		scrollcore = _view.findViewById(R.id.scrollcore);
		ine1 = _view.findViewById(R.id.ine1);
		ine2 = _view.findViewById(R.id.ine2);
		ine3 = _view.findViewById(R.id.ine3);
		ine4 = _view.findViewById(R.id.ine4);
		ine5 = _view.findViewById(R.id.ine5);
		ine6 = _view.findViewById(R.id.ine6);
		savebtn = _view.findViewById(R.id.savebtn);
		sh1 = _view.findViewById(R.id.sh1);
		in1 = _view.findViewById(R.id.in1);
		sh2 = _view.findViewById(R.id.sh2);
		in2 = _view.findViewById(R.id.in2);
		sh3 = _view.findViewById(R.id.sh3);
		in3 = _view.findViewById(R.id.in3);
		sh4 = _view.findViewById(R.id.sh4);
		in4 = _view.findViewById(R.id.in4);
		sh5 = _view.findViewById(R.id.sh5);
		in5 = _view.findViewById(R.id.in5);
		sh6 = _view.findViewById(R.id.sh6);
		in6 = _view.findViewById(R.id.in6);
		
		savebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
	}
	
	private void initializeLogic() {
		Dialog dialog = getDialog();
		  if (dialog != null) { 
			  int width = ViewGroup.LayoutParams.MATCH_PARENT;
			  int height = ViewGroup.LayoutParams.MATCH_PARENT; 
			  dialog.getWindow().setLayout(width, height);
			dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); 
			   }
		frame.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFFF5F5F5));
		frame.getLayoutParams().width = SketchwareUtil.getDisplayWidthPixels(getContext().getApplicationContext()) - 50;
		
		frame.getLayoutParams().height = SketchwareUtil.getDisplayHeightPixels(getContext().getApplicationContext()) - 50;
		ine1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
		ine2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
		ine3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
		ine4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
		ine5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
		ine6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, 0xFF000000, Color.TRANSPARENT));
	}
	
}