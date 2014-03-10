package com.phobos.logoquizzer;


import java.io.Serializable;

/**
 * Created by Anthony on 2/25/14.
 */
public class Logo implements Serializable
{
	private String id;
	private String fullImage;
	private String partialImage;
	private String description;
	private String value;

	public String getPartialImage()
	{
		return partialImage;
	}

	public void setPartialImage(String partialImage)
	{
		this.partialImage = partialImage;
	}

	public String getId()
	{
		return id;
	}

	public String getFullImage()
	{
		return fullImage;
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

	public void setFullImage(String fullImage)
	{
		this.fullImage = fullImage;
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
