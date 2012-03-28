package com.github.gwatanabe.garoonscraper;

import java.util.ArrayList;
import java.util.List;

public class Schedule
{
	private final String name;
	private final List<String> scheduleList = new ArrayList<String>();

	public Schedule( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return( name );
	}

	public List<String> getScheduleList()
	{
		return( scheduleList );
	}

	public void addSchedule( String schedule )
	{
		scheduleList.add( schedule );
	}

	public void addSchedule( List<String> addList )
	{
		scheduleList.addAll( addList );
	}
}
