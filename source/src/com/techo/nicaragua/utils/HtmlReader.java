package com.techo.nicaragua.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

public class HtmlReader {
	private static String log = "Html";

	private Context myContext;


	public HtmlReader(Context context) {
		super();

	}
	
	public static Spanned get(Context context, int raw_id) {
		
		InputStream stream = context.getResources().openRawResource(raw_id);
		String content = null;
		try {
			content = CharStreams.toString(new InputStreamReader(stream,
					Charsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Closeables.closeQuietly(stream);
		return Html.fromHtml(content, null, new ListTagHandler());
	}


}
