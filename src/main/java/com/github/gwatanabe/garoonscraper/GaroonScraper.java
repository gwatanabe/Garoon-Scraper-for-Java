package com.github.gwatanabe.garoonscraper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.gwatanabe.garoonscraper.http.HTTPAccessResult;
import com.github.gwatanabe.garoonscraper.http.HTTPAccessor;

public class GaroonScraper
{
	public static Schedule getMySchedule( URL url, String account, String password ) throws IOException
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

	public static List<Schedule> getTargetUserSchedule( URL url, String account, String password, List<String> targetUserList ) throws IOException
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
