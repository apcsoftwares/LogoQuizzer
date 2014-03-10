package com.phobos.logoquizzer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;


public class AnswerActivity extends Activity
{
	private int res;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);

		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		final ImageView imageHidden = (ImageView) findViewById(R.id.imageHidden);
		final ImageView imageFull = (ImageView) findViewById(R.id.imageFull);
		final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

		final EditText editTextResult = (EditText) findViewById(R.id.editTextResult);
		//final Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

		final Logo logo = (Logo) getIntent().getSerializableExtra("Logo");

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		res = getResources().getIdentifier(logo.getPartialImage(), "drawable", getPackageName());
		imageHidden.setBackgroundResource(res);
		res = getResources().getIdentifier(logo.getFullImage(), "drawable", getPackageName());
		imageFull.setBackgroundResource(res);

		editTextResult.setOnKeyListener(new View.OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
				{
					Toast toast;
					if (logo.getValue().toLowerCase().equals(editTextResult.getText().toString().toLowerCase()))
					{
						//Log.i("MARTE", "Entro");
						toast = Toast.makeText(getApplicationContext(), "Correcto!", Toast.LENGTH_SHORT);
						imageSwitcher.showNext();
						editTextResult.setEnabled(false);
						getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
					}
					else
					{
						//Log.i("MARTE", "No entro");
						toast = Toast.makeText(getApplicationContext(), "Mal!", Toast.LENGTH_SHORT);
					}
					toast.show();
				}
				return true;

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.answer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_answer, container, false);
			return rootView;
		}
	}

}
