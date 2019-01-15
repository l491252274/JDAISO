package com.unlimited.oj.pojo;

public class DataChart
{
	private String label;
	private String value;
	private String unit;
	private String title;
	private String xlabel;
	private String ylabel;

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public DataChart()
	{

	}

	public DataChart(String title, String xLabel, String yLabel, String label, String value, String unit)
	{
		super();
		this.label = label;
		this.value = value;
		this.unit = unit;
		this.title = title;
		this.xlabel = xLabel;
		this.ylabel = yLabel;
	}

	public String getXlabel()
	{
		return xlabel;
	}

	public void setXlabel(String xlabel)
	{
		this.xlabel = xlabel;
	}

	public String getYlabel()
	{
		return ylabel;
	}

	public void setYlabel(String ylabel)
	{
		this.ylabel = ylabel;
	}

}
