package com.androidquery.callback;

import android.graphics.Bitmap;

import com.androidquery.AQuery;

public class ImageOptions {

	public boolean memCache = true;
	public boolean fileCache = true;
	public Bitmap preset;
	public int policy;

	public int targetWidth;
	public int fallback;
	public int animation;
	public float ratio;
	public int round;
	public float anchor = AQuery.ANCHOR_DYNAMIC;

	public float getAnchor() {
		return anchor;
	}

	public int getAnimation() {
		return animation;
	}

	public int getFallback() {
		return fallback;
	}

	public int getPolicy() {
		return policy;
	}

	public Bitmap getPreset() {
		return preset;
	}

	public float getRatio() {
		return ratio;
	}

	public int getRound() {
		return round;
	}

	public int getTargetWidth() {
		return targetWidth;
	}

	public ImageOptions setAnchor(float anchor) {
		this.anchor = anchor;
		return this;
	}

	public ImageOptions setAnimation(int animation) {
		this.animation = animation;
		return this;
	}

	public ImageOptions setFallback(int fallback) {
		this.fallback = fallback;
		return this;
	}

	public ImageOptions setFileCache(boolean fileCache) {
		this.fileCache = fileCache;
		return this;
	}

	public ImageOptions setMemCache(boolean memCache) {
		this.memCache = memCache;
		return this;
	}

	public ImageOptions setPolicy(int policy) {
		this.policy = policy;
		return this;
	}

	public ImageOptions setPreset(Bitmap preset) {
		this.preset = preset;
		return this;
	}

	public ImageOptions setRatio(float ratio) {
		this.ratio = ratio;
		return this;
	}

	public ImageOptions setRound(int round) {
		this.round = round;
		return this;
	}

	public ImageOptions setTargetWidth(int targetWidth) {
		this.targetWidth = targetWidth;
		return this;
	}

}
