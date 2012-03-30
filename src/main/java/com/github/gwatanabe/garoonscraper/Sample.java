package com.github.gwatanabe.garoonscraper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
			// 引数の分解
			URL url = new URL( args[ 0 ] );
			String account = args[ 1 ];
			String password = args[ 2 ];

			List<String> targetUserList = new ArrayList<String>();
			if( args.length > 3 )
			{
				for( int i = 3; i < args.length; i++ )
				{
					targetUserList.add( args[ i ] );
				}
			}

			List<Schedule> scheduleList = new ArrayList<Schedule>();
			switch( targetUserList.size() )
			{
				// Schedule Myself.
				case	0 :
				{
					Schedule schedule = GaroonScraper.getMySchedule( url, account, password );

					scheduleList.add( schedule );
					break;
				}

				default:
				{
					List<Schedule> list = GaroonScraper.getTargetUserSchedule( url, account, password, targetUserList );

					scheduleList.addAll( list );
					break;
				}
			}

			for( Schedule schedule: scheduleList )
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
