/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

@SuppressLint("ViewConstructor")
public class GifLoadingLayout extends LoadingLayout {

	static final String LOG_TAG = "PullToRefresh-GifLoadingLayout";

	protected final ImageView mLoadingView;
	protected final View mProgressView;
	private int mPrevIndex = -1;
    private List<CharSequence> mDropdownList;

	public GifLoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);

		mLoadingView = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_loading_view);
		mProgressView = mInnerLayout.findViewById(R.id.pull_to_refresh_progress_view);

		if (attrs.hasValue(R.styleable.PullToRefresh_ptrGifResource)) {
			int gif_resId = attrs.getResourceId(R.styleable.PullToRefresh_ptrGifResource, -1);
			if(-1 != gif_resId) {
				try {
					GifDrawable gifFromResource = new GifDrawable(getResources(), gif_resId);
					mLoadingView.setImageDrawable(gifFromResource);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrGifDropdown)) {
            CharSequence[] entries = attrs.getTextArray(R.styleable.PullToRefresh_ptrGifDropdown);
            mDropdownList = Arrays.asList(entries);
        }
    }

	@Override
	public void hideAllViews() {
		super.hideAllViews();
		if (View.VISIBLE == mLoadingView.getVisibility()) {
			mLoadingView.setVisibility(View.INVISIBLE);
		}
		if (View.VISIBLE == mProgressView.getVisibility()) {
			mProgressView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void showInvisibleViews() {
		super.showInvisibleViews();
		if (View.INVISIBLE == mLoadingView.getVisibility()) {
			mLoadingView.setVisibility(View.VISIBLE);
		}
		if (View.INVISIBLE == mProgressView.getVisibility()) {
			mProgressView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_empty;
	}

	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {

	}

	@Override
	protected void onPullImpl(float scaleOfLayout) {
        int length = mDropdownList.size();
		int index = (int) (scaleOfLayout / 1f * length);
		if (index == mPrevIndex) {
			return;
		} else {
			if (index >= length) {
				index = length - 1;
			}
			int res = getResources().getIdentifier(mDropdownList.get(index).toString(), "mipmap", getContext().getPackageName());
			mHeaderImage.setImageResource(res);
			mPrevIndex = index;
		}
	}

	@Override
	protected void pullToRefreshImpl() {
		if(null != mProgressView) {
			mProgressView.setVisibility(View.VISIBLE);
		}
		if(null != mLoadingView) {
			mLoadingView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void refreshingImpl() {
		if(null != mProgressView) {
			mProgressView.setVisibility(View.INVISIBLE);
		}
		if(null != mLoadingView) {
			mLoadingView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void releaseToRefreshImpl() {

	}

	@Override
	protected void resetImpl() {
		if(null != mProgressView) {
			mProgressView.setVisibility(View.VISIBLE);
		}
		if(null != mLoadingView) {
			mLoadingView.setVisibility(View.GONE);
		}
	}
}
