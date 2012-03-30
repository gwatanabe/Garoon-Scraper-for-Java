package com.github.gwatanabe.garoonscraper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author watanabe
 *
 */
public class TodayScheduleScraper
{
	private static final SimpleDateFormat formater = new SimpleDateFormat( "'&amp;bdate='yyyy-MM-dd" );
	private static String todayString = formater.format( new Date() );

	public static List<String> scraping( List<String> targetList )
	{
		List<String> resultList = new ArrayList<String>();

		for( String line: targetList )
		{
			String scrapedString = TodayScheduleScraper.scraping( line );
			if( scrapedString != null )
			{
				resultList.add( scrapedString );
			}
		}

		return( resultList );
	}

	public static String scraping( String line )
	{
		// ここの処理は面倒なので適当
		if( line.indexOf( "id=\"notification\"" ) == -1 && line.indexOf( todayString ) > 0 )
		{
			String[] lines = line.split( "span class=\"\">" );
			if( lines.length > 1 )
			{
				String[] tmps = lines[ 1 ].split( "</span>" );
				if( tmps.length > 1 )
				{
					return( tmps[ 0 ] );
				}
				else
					System.out.println( "001:" + lines[ 1 ] );
			}
			else
			{
				String[] tmps = line.split( "text\" border=\"0\">" );
				if( tmps.length > 1 )
				{
					String[] tmps2 = tmps[ 1 ].split( "<img" );
					if( tmps2.length > 1 )
					{
						return( tmps2[ 0 ] );
					}
				}
				else
				{
					String[] tmps2 = line.split( todayString + "\">" );
					if( tmps2.length > 1 )
					{
						String[] tmps3 = tmps2[ 1 ].split( "</a>" );
						if( tmps3.length > 0 )
						{
							return( tmps3[ 0 ] );
						}
					}
				}
			}
		}

		return( null );
	}
}
