package com.ianshields.mywebapp.client;

    import com.google.gwt.core.client.JavaScriptObject;

    class ImageData extends JavaScriptObject {                              
  // Overlay types always have protected, zero argument constructors.
  protected ImageData() {}                                              

      // JSNI methods to get video data.
  public final native String getTitle() /*-{ return this.title; }-*/; 
  public final native String getUrl() /*-{ return this.url; }-*/; 
  
 


}
