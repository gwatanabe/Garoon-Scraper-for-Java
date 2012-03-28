package com.github.gwatanabe.garoonscraper.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * HTTPAccess rapper class.
 * 
 * @author watanabe
 */
public class HTTPAccessor
{
	/** HTTP Access target URL */
	private final URL targetURL;
	/** HTTP method. post is true. get is false. */
	private final boolean isPost;

	/**
	 * Constractor.
	 * 
	 * @param targetURL Access target URL
	 * @param isPost HTTP method.
	 */
	public HTTPAccessor( URL targetURL, boolean isPost )
	{
		this.targetURL = targetURL;
		this.isPost = isPost;
	}

	/**
	 * Constractor. only get method.
	 * 
	 * @param targetURL Access target URL
	 */
	public HTTPAccessor( URL targetURL )
	{
		this( targetURL, false );
	}

	/**
	 * HTTP connect and new Cookie.
	 * 
	 * @param parameter post parameter. get is nouse.
	 * @return HTTPAccessResult "http access result object" is return.
	 * @throws IOException
	 */
	public HTTPAccessResult connect( String parameter ) throws IOException
	{
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy( CookiePolicy.ACCEPT_ALL );

		return( connect( parameter, manager ) );
	}

	/**
	 * HTTP connect. no parameter pattern.
	 * 
	 * @param manager Cookie.
	 * @return HTTPAccessResult "http access result object" is return.
	 * @throws IOException
	 */
	public HTTPAccessResult connect( CookieManager manager ) throws IOException
	{
		return( connect( "", manager ) );
	}

	/**
	 * HTTP connect.
	 * 
	 * @param parameter post parameter. get is nouse.
	 * @param manager Cookie.
	 * @return HTTPAccessResult "http access result object" is return.
	 * @throws IOException
	 */
	public HTTPAccessResult connect( String parameter, CookieManager manager ) throws IOException
	{
		CookieHandler.setDefault( manager );

		HttpURLConnection http = (HttpURLConnection)targetURL.openConnection();

		http.setDoOutput( isPost );
		if( isPost )
		{
			write( http, parameter );
		}

		List<String> result = read( http );

		return( new HTTPAccessResult( result, manager ) );
	}

	/**
	 * 
	 * 
	 * @param http
	 * @param parameter
	 * @throws IOException
	 */
	private void write( HttpURLConnection http, String parameter ) throws IOException
	{
		OutputStream os = null;
		PrintStream ps = null;

		try
		{
			os = http.getOutputStream();
			ps = new PrintStream( os );
			// パラメータ送信
			ps.print( parameter );
		}
		finally
		{
			if( ps != null )
			{
				ps.close();
				ps = null;
			}

			if( os != null )
			{
				try
				{
					os.close();
				}
				catch( IOException ignore )
				{
					ignore.printStackTrace();
				}
				os = null;
			}
		}
	}

	private List<String> read( HttpURLConnection http ) throws IOException
	{
		List<String> result = new ArrayList<String>();

		InputStream is = null;
		BufferedReader reader = null;

		try
		{
			// 応答内容取得
			is = http.getInputStream();
			reader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );

			for( String line = reader.readLine(); line != null; line = reader.readLine() )
			{
				result.add( line );
			}
		}
		finally
		{
			if( is != null )
			{
				try
				{
					is.close();
				}
				catch( IOException ignore )
				{
					ignore.printStackTrace();
				}
				is = null;
			}
	
			if( reader != null )
			{
				try
				{
					reader.close();
				}
				catch( IOException ignore )
				{
					ignore.printStackTrace();
				}
				reader = null;
			}
		}

		return( result );
	}
}
