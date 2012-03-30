package com.github.gwatanabe.garoonscraper;

import java.util.ArrayList;
import java.util.List;

/**
 * スケジュール。
 * スケジュールを取得する際に利用した名前（name）とそのスケジュールのリストを保持している。
 * 
 * @author watanabe
 */
public class Schedule
{
	/** スケジュールを取得する際に利用した名前 */
	private final String name;
	/** スケジュールリスト */
	private final List<String> scheduleList = new ArrayList<String>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name 取得する際に利用した名前
	 */
	public Schedule( String name )
	{
		this.name = name;
	}

	/**
	 * 取得する際に利用した名前を取得。
	 * 
	 * @return String search word
	 */
	public String getName()
	{
		return( name );
	}

	/**
	 * スケジュールリストを取得。
	 * 
	 * @return List<String> スケジュールリスト
	 */
	public List<String> getScheduleList()
	{
		return( scheduleList );
	}

	/**
	 * スケジュールを１件追加する。
	 * 
	 * @param schedule 追加するスケジュール
	 */
	public void addSchedule( String schedule )
	{
		scheduleList.add( schedule );
	}

	/**
	 * スケジュールをリスト件数分追加する。
	 * 
	 * @param addList 追加するスケジュールリスト
	 */
	public void addSchedule( List<String> addList )
	{
		scheduleList.addAll( addList );
	}
}
