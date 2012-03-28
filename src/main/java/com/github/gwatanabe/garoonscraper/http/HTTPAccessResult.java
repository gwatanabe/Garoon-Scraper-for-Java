package com.github.gwatanabe.garoonscraper.http;
import java.net.CookieManager;
import java.util.List;


public class HTTPAccessResult
{
	public final List<String> html;
	public final CookieManager manager;

	public HTTPAccessResult( List<String> html, CookieManager manager )
	{
		this.html = html;
		this.manager = manager;
	}
}
