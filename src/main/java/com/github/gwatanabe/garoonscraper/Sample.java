package com.github.gwatanabe.garoonscraper;

import java.io.IOException;
import java.util.List;

public class Sample
{
	/**
	 * EntryPoint
	 * 
	 * @param args Startup args
	 * @throws IOException 
	 */
	public static void main( String[] args ) throws IOException
	{
		if( args.length >= 3 )
		{
			GaroonScraper garoonScraper = new GaroonScraper( args );

			List<Schedule> list = garoonScraper.execute();
			for( Schedule schedule: list )
			{
				Sample.print( schedule );
			}
		}
		else
		{
			Sample.usage();
		}
	}

	private static void print( Schedule schedule )
	{
		System.out.println( "## " + schedule.getName() );
		for( String s: schedule.getScheduleList() )
		{
			System.out.println( s );
		}

		System.out.println( "##" );
	}

	private static void usage()
	{
		
	}
}
