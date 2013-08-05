package com.techo.nicaragua.socialnetworks;

import com.actionbarsherlock.app.SherlockFragment;
import com.techo.nicaragua.R;
import com.techo.nicaragua.utils.Connection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.os.Message;

public class SocialNetworksFragment extends SherlockFragment {
	private String log = "WebView";
	private static Connection connection;
	
	int mNum;
	  
    public static SocialNetworksFragment newInstance(int num) {
    	SocialNetworksFragment fragment = new SocialNetworksFragment();
        
       // Supply num input as an argument.
       Bundle args = new Bundle();
       args.putInt("num", num);
       fragment.setArguments(args);

       return fragment;
   }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
       Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.item_webview, null);
       TextView error = (TextView) view.findViewById(R.id.textViewWebViewError);
       final WebView webview = (WebView) view.findViewById(R.id.webview); 
       if( connection.isConnected() ){
    	   error.setVisibility(View.GONE);
    	   webview.getSettings().setJavaScriptEnabled(true);
           webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
           
           
           webview.setWebViewClient(new WebViewClient() {

               @Override
               public boolean shouldOverrideUrlLoading(WebView view, String url) {
                       Log.i(log, "shouldOverrideUrlLoading: " + url);
                       webview.loadUrl(url);
                       return true;
               }

               @Override
               public void onLoadResource(WebView view, String url) {
            	   Log.i(log, "onLoadResource: " + url);
               }

               @Override
               public void onPageStarted(WebView view, String url, Bitmap favicon) {
                       super.onPageStarted(view, url, favicon);
                       Log.i(log, "onPageStarted: " + url);
                       //if (!progressBar.isShowing()) {
                       //        progressBar.show();
                       //}
               }

               @Override
               public void onFormResubmission(WebView view, Message dontResend,
                               Message resend) {
                       super.onFormResubmission(view, dontResend, resend);
                       Log.i(log, "onFormResubmission");
                       //if (!progressBar.isShowing()) {
                      //         progressBar.show();
                       //}
               }

               @Override
               public void onPageFinished(WebView view, String url) {
                       super.onPageFinished(view, url);
                       Log.i(log, "onPageFinished: " + url);
                       
               }

               public void onReceivedError(WebView view, int errorCode,
                               String description, String failingUrl) {
                       Log.e(log, "Error: " + description);

                       //toasterror.setIcon("device_access_network_wifi");
                       //toasterror.setMessage(description);
                       //toasterror.show();
                       //back(true);
               }
       });
           
           switch( mNum ){
           case 0:
        	   webview.loadUrl(getString(R.string.social_network_url_facebook));
        	   break;
           case 1:
        	   webview.loadUrl(getString(R.string.social_network_url_twitter));
        	   break;
           case 2:
        	   webview.loadUrl(getString(R.string.social_network_url_youtube));
        	   break;
           }
              
           
       }
       else{
    	   webview.setVisibility(View.GONE);
    	   error.setText(getString(R.string.social_network_require));
       }
       return view;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       connection = new Connection( getActivity() );
       mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

}
