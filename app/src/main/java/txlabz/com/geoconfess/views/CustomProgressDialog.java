package txlabz.com.geoconfess.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import txlabz.com.geoconfess.R;

public class CustomProgressDialog extends ProgressDialog {
	// private AnimationDrawable animation;
	private ProgressBar pageProgressBar;
	private String color = "";

	public static ProgressDialog ctor(Context context) {
		CustomProgressDialog dialog = new CustomProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}

	public static ProgressDialog ctor(Context context, String color) {
		CustomProgressDialog dialog = new CustomProgressDialog(context);
		dialog.color = color;
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_custom_progress_dialog);
		pageProgressBar = (ProgressBar) findViewById(R.id.pageLoadingIndicator);
	}

	@Override
	public void show() {
		super.show();
		pageProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		pageProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
		pageProgressBar.setVisibility(View.GONE);
	}
}
