package com.github.gwatanabe.garoonscraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.gwatanabe.garoonscraper.http.HTTPAccessResult;
import com.github.gwatanabe.garoonscraper.http.HTTPAccessor;

public class GaroonScraper
{
	private URL url;
	private String account;
	private String password;
	private List<String> argsTargetUsers = new ArrayList<String>();

	public GaroonScraper( String[] args ) throws MalformedURLException
	{
		parseArgs( args );
	}

	private boolean parseArgs( String[] args ) throws MalformedURLException
	{
		boolean result = false;

		if( args.length >= 3 )
		{
			// 引数の分解
			url = new URL( args[ 0 ] );
			account = args[ 1 ];
			password = args[ 2 ];

			if( args.length > 3 )
			{
				for( int i = 3; i < args.length; i++ )
				{
					argsTargetUsers.add( args[ i ] );
				}
			}

			result = true;
		}

		return( result );
	}

	public List<Schedule> execute() throws IOException
	{
		List<Schedule> resultList = new ArrayList<Schedule>();

		switch( argsTargetUsers.size() )
		{
			// Schedule Myself.
			case	0 :
			{
				Schedule schedule = executeMySchedule( url, account, password );

				resultList.add( schedule );
				break;
			}

			default:
			{
				List<Schedule> list = executeOtherSchedule( url, account, password, argsTargetUsers);

				resultList.addAll( list );
				break;
			}
		}

		return( resultList );
	}

	public Schedule executeMySchedule( URL url, String account, String password ) throws IOException
	{
		Schedule schedule = new Schedule( account );

		HTTPAccessor postAccessor = new HTTPAccessor( url, true );
		HTTPAccessResult result = postAccessor.connect( "_system=1&page=index&_account=" + account + "&_password=" + password );

		for( String line: result.html )
		{
			String scrapedString = TodayScheduleScraper.scraping( line );
			if( scrapedString != null )
			{
				schedule.addSchedule( scrapedString );
			}
		}

		return( schedule );
	}

	public List<Schedule> executeOtherSchedule( URL url, String account, String password, List<String> targetUserList ) throws IOException
	{
		List<Schedule> resultList = new ArrayList<Schedule>();

		String urlString = url.toExternalForm();
		String[] urlStrings = urlString.split( "index" );
		if( urlStrings.length > 0 )
		{
			// まずはログイン
			HTTPAccessor loginAccessor = new HTTPAccessor( url, true );
			HTTPAccessResult loginResult = loginAccessor.connect( "_system=1&page=index&_account=" + account + "&_password=" + password );

			// 指定されたユーザー分繰り返す
			for( String target: targetUserList )
			{
				Schedule schedule = new Schedule( target );

				URL targetURL = new URL( urlStrings[ 0 ] + "schedule/index?search_text=" + target + "&gid=search" );

				HTTPAccessor getAccessor = new HTTPAccessor( targetURL, false );
				HTTPAccessResult getResult = getAccessor.connect( loginResult.manager );

				schedule.addSchedule( TodayScheduleScraper.scraping( getResult.html ) );
			}
		}

		return( resultList );
	}
}
