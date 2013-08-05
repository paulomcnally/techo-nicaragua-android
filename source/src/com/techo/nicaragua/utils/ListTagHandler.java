package com.techo.nicaragua.utils;

import org.xml.sax.XMLReader;

import android.text.Editable;
import android.text.Html.TagHandler;

public class ListTagHandler implements TagHandler {
    boolean first = true;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        // TODO Auto-generated method stub
        if (tag.equals("li")) {
            char lastChar = 0;
            if (output.length() > 0)
                lastChar = output.charAt(output.length() - 1);
            if (first) {
                if (lastChar == '\n')
                    output.append("•  ");
                else
                    output.append("\n•  ");
                first = false;
            } else {
                first = true;
            }
        }
    }
}