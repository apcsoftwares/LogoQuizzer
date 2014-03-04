package com.phobos.logoquizzer;

import java.io.Serializable;

/**
 * Created by Anthony on 2/25/14.
 */
public class Logo implements Serializable
{
	private String id;
	private String imagen;
	private String description;
	private String value;

	public String getId()
	{
		return id;
	}

	public String getImagen()
	{
		return imagen;
	}

	public String getDescription()
	{
		return description;
	}

	public String getValue()
	{
		return value;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setImagen(String imagen)
	{
		this.imagen = imagen;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
