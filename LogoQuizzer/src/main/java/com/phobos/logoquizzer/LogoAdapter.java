package com.phobos.logoquizzer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Anthony on 2/25/14.
 */
public class LogoAdapter extends BaseAdapter
{
	private Context context;
	private List<Logo> logoList;

	public LogoAdapter(Context context,List<Logo>logos)
	{
		Log.i("MARTE", "Constructor LogoAdapter");
		this.context = context;
		this.logoList = logos;
		Log.i("MARTE", "Constructor LogoAdapter-Exit");

	}

	@Override
	public int getCount()
	{
		//Log.i("MARTE","Size:" + String.valueOf(logoList.size()));
		return logoList.size();
	}

	@Override
	public Object getItem(int position)
	{
		//Log.i("MARTE","GetItem:" + String.valueOf(logoList.get(position)));
		return logoList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		//Log.i("MARTE","GetItemID:" + logoList.indexOf(getItem(position)));
		return logoList.indexOf(getItem(position));
	}

	private class ViewHolder
	{
		ImageView ivImagen;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		//Log.i("MARTE","Test");
		ViewHolder holder;
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		//Log.i("MARTE","Test");
		if (convertView==null)
		{

			convertView = mInflater.inflate(R.layout.item,null);
			holder = new ViewHolder();
			holder.ivImagen = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		Logo currentLogo = (Logo) this.getItem(position);
//		Log.i("MARTE", "Imagen:"+ currentLogo.getImagen());

		int res= context.getResources().getIdentifier(currentLogo.getImagen(),"drawable",context.getPackageName());
//		Log.i("MARTE", "Res:"+ String.valueOf(res));
		//Log.i("MARTE", "PackageName:"+ context.getPackageName());


		holder.ivImagen.setBackgroundResource(res);
		holder.ivImagen.setScaleType(ImageView.ScaleType.FIT_XY);

		return convertView;
	}
}
