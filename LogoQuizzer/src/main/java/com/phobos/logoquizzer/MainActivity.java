package com.phobos.logoquizzer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity
{
	Button btnContinueGame;
	Utils utils;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id., new PlaceholderFragment()).commit();
		}*/
		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

		utils = new Utils(this);
		utils.InitializeSounds();

		btnContinueGame = ((Button) findViewById(R.id.btnContinueGame));

		ContinueGameVisibility();

		btnContinueGame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				utils.PlaySound(Utils.SoundType.CLICK);
				Intent intent = new Intent(v.getContext(), LogoListActivity.class);
				startActivity(intent);
			}
		});

	}

	public void ContinueGameVisibility()
	{
		if (!utils.isGamePlayed())
		{
			btnContinueGame.setVisibility(View.GONE);
		}
		else
		{
			btnContinueGame.setVisibility(View.VISIBLE);
		}

	}

	public void settingsClick(View v)
	{
		utils.PlaySound(Utils.SoundType.CLICK);
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void btnNewGameClick(View v)
	{
		utils.PlaySound(Utils.SoundType.CLICK);
		if (utils.isGamePlayed())
		{
			if (!utils.showConfirmDialog()) return;
		}
		utils.resetAllProgress();
		Intent intent = new Intent(this, LogoListActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			settingsClick(findViewById(R.id.action_settings));
			return true;
		}
		if (id == R.id.action_resetgame)
		{
			utils.resetAllProgress();
			ContinueGameVisibility();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		ContinueGameVisibility();

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
			return inflater.inflate(R.layout.fragment_main, container, false);
		}
	}

}
