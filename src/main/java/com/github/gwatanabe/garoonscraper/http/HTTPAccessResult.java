package com.github.gwatanabe.garoonscraper.http;
import java.net.CookieManager;
import java.util.List;

/**
 * HTTP connect result Object.
 * 
 * @author watanabe
 */
public class HTTPAccessResult
{
	/** http response code */
	public final int responseCode;
	/** html string */
	public final List<String> html;
	/** httpcookie */
	public final CookieManager manager;

	/**
	 * Constractor.
	 * 
	 * @param responseCode http response code
	 * @param html html string
	 * @param manager httpcookie
	 */
	public HTTPAccessResult( int responseCode, List<String> html, CookieManager manager )
	{
		this.responseCode = responseCode;
		this.html = html;
		this.manager = manager;
	}

	/**
	 * http response code return.
	 * 
	 * @return int response code
	 */
	public int getResponseCode()
	{
		return( responseCode );
	}

	/**
	 * html strings return.
	 * 
	 * @return List<String> html strings
	 */
	public List<String> getHtml()
	{
		return( html );
	}

	/**
	 * cookie manager return.
	 * 
	 * @return CookieManager cookie manager
	 */
	public CookieManager getCookieManager()
	{
		return( manager );
	}
}
