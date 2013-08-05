package com.techo.nicaragua.manual;

import android.support.v4.app.FragmentPagerAdapter;


public class ImageViewAdapter extends FragmentPagerAdapter {
	private int count = 0;
    public ImageViewAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    
    public void setCount( int i){
    	count = i;
    }
    
    public void setUrls(String[] urls){
    	ImageFragment.setUrls(urls);
    }

    @Override
    public int getCount() {
        return count;
    }
    
    

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return ImageFragment.newInstance(position);
    }
}
