package com.phobos.logoquizzer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class LogoListActivity extends Activity
{

	public static final String ITEM_TAG = "item";
	public static final String ID_TAG = "id";
	public static final String FULL_IMAGE_TAG = "fullImage";
	public static final String DESCRIPTION_TAG = "description";
	public static final String VALUE_TAG = "value";
	public static final String PARTIAL_IMAGE_TAG = "partialImage";
	private XmlPullParser xmlPullParser;
	public static final String EXTRA_LOGO = "com.phobos.logoquizzer.LOGO" 
	//private XmlPullParserFactory xmlPullParserFactory;
	ArrayList<Logo> logos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logolist);

		if (savedInstanceState == null)
		{
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		//getFragmentManager().findFragmentByTag("YourFragmentTag")
		//Log.i("Marte", "Findviewbyid:"+String.valueOf(getFragmentManager().findFragmentById(R.layout.fragment_logolist).getView().findViewById(R.id.gridLogos)));

		loadXML();
		initializeViews();

		Utils utils = new Utils(this);
		utils.savePlayed();

	}

	private void initializeViews()
	{
		final GridView gridLogos = (GridView) findViewById(R.id.gridLogos);

		gridLogos.setAdapter(new LogoAdapter(LogoListActivity.this, logos));

		gridLogos.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(parent.getContext(), AnswerActivity.class);
				intent.putExtra(EXTRA_LOGO, logos.get(position));
				startActivity(intent);
			}
		});

	}

	private void loadXML()
	{
		try
		{
			XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParser = Xml.newPullParser();
			InputStream in_s = this.getResources().openRawResource(R.raw.logos_info);
			//xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			xmlPullParser.setInput(in_s, null);

			parseXML(xmlPullParser);
		}
		catch (XmlPullParserException e)
		{
			Log.i("MARTE-PARSER", e.getMessage());
		}
		catch (IOException e)
		{
			Log.i("MARTE-IO", e.getMessage());
		}
	}

	private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		int eventType = parser.getEventType();
		Logo currentLogo = null;

		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			String name;
			switch (eventType)
			{
				case XmlPullParser.START_DOCUMENT:
					logos = new ArrayList();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals(ITEM_TAG))
					{
						currentLogo = new Logo();
					}
					else if (currentLogo != null)
					{
						if (name.equals(ID_TAG))
						{
							currentLogo.setId(parser.nextText());
						}
						else if (name.equals(FULL_IMAGE_TAG))
						{
							currentLogo.setFullImage(parser.nextText());
						}
						else if (name.equals(PARTIAL_IMAGE_TAG))
						{
							currentLogo.setPartialImage(parser.nextText());
						}
						else if (name.equals(DESCRIPTION_TAG))
						{
							currentLogo.setDescription(parser.nextText());
						}
						else if (name.equals(VALUE_TAG))
						{
							currentLogo.setValue(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase(ITEM_TAG) && currentLogo != null)
					{
						logos.add(currentLogo);
					}
					break;
			}
			eventType = parser.next();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logolist, menu);
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
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_logolist, container, false);
			return rootView;

		}
	}

}
