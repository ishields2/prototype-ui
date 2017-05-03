package com.ianshields.mywebapp.client;

    import com.google.gwt.core.client.JavaScriptObject;

    class VideoData extends JavaScriptObject {                              
  // Overlay types always have protected, zero argument constructors.
  protected VideoData() {}                                              

      // JSNI methods to get video data.
  public final native String getTitle() /*-{ return this.title; }-*/; 
  public final native String getBody() /*-{ return this.body; }-*/; 
}