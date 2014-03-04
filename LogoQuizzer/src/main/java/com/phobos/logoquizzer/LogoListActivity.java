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


public class LogoListActivity extends Activity {

	GridView gridLogos;
	private XmlPullParser xmlPullParser;
	private XmlPullParserFactory xmlPullParserFactory;
	ArrayList<Logo> logos = null;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logolist);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
		//getFragmentManager().findFragmentByTag("YourFragmentTag")
		//Log.i("Marte", "Findviewbyid:"+String.valueOf(getFragmentManager().findFragmentById(R.layout.fragment_logolist).getView().findViewById(R.id.gridLogos)));

		//Log.i("MARTE",String.valueOf(findViewById(R.id.gridLogos)));
		//gridLogos = (GridView) getFragmentManager().findFragmentByTag("PlaceholderFragment").getView().findViewById(R.id.gridLogos);
		initialize();

    }

	private void initialize()
	{

		try
		{
			xmlPullParserFactory = XmlPullParserFactory.newInstance();
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



		gridLogos = (GridView) findViewById(R.id.gridLogos);
		gridLogos.setAdapter(new LogoAdapter(LogoListActivity.this, logos));


		gridLogos.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				Log.i("MARTE", "Logo: " + logos.get(position).getDescription());
				Intent intent = new Intent(parent.getContext(),AnswerActivity.class);
				intent.putExtra("Logo", logos.get(position));
				startActivity(intent);
			}
		});

	}

	private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		int eventType = parser.getEventType();
		Logo currentLogo = null;

		while (eventType != XmlPullParser.END_DOCUMENT){
			String name = null;
			switch (eventType){
				case XmlPullParser.START_DOCUMENT:
					logos = new ArrayList();
					//Log.i("MARTE","New ArrayList");

					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					//Log.i("MARTE","Start Tag:" + name);

					if (name.equals("item"))
					{
						//Log.i("MARTE","New Logo!");

						currentLogo = new Logo();
					}
					else if (currentLogo != null)
					{
						//Log.i("MARTE","nodo:" + name);

						if (name.equals("id") ){
							currentLogo.setId(parser.nextText());
						} else if (name.equals("imagen")){
							currentLogo.setImagen(parser.nextText());
						} else if (name.equals("description")){
							currentLogo.setDescription(parser.nextText());
						} else if (name.equals("value")){
							currentLogo.setValue(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					//Log.i("MARTE",name);

					if (name.equalsIgnoreCase("item") && currentLogo != null){
						//Log.i("MARTE","fin item");
						logos.add(currentLogo);
					}
					break;
			}
			eventType = parser.next();
		}

	}



		@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logolist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_logolist, container, false);
			return rootView;

        }
    }

}
