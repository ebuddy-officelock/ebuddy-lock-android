package org.thoughtcrime.securesms.giph.net;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class GiphyGifLoader extends GiphyLoader {

  public GiphyGifLoader(@NonNull Context context, @Nullable String searchString) {
    super(context, searchString);
  }

  @Override
  protected String getTrendingUrl() {
    return "https://api.giphy.com/v1/gifs/trending?api_key=u70MUA7wD3J8j4fQUnNLb9JtfWPqfTl7&offset=%d&limit=" + PAGE_SIZE;
  }

  @Override
  protected String getSearchUrl() {
    return "https://api.giphy.com/v1/gifs/search?api_key=u70MUA7wD3J8j4fQUnNLb9JtfWPqfTl7&offset=%d&limit=" + PAGE_SIZE + "&q=%s";
  }
}
