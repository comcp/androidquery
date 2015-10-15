package com.androidquery.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.IXProgress;

public class Progress implements Runnable {
	private static final int defBtyes = 10000;
	private static final int REFRESH_FREQUENCY = 20;// 刷新频率
	private Activity act;
	private int bytes;
	private int count = REFRESH_FREQUENCY;
	private int current;
	private ProgressBar pb;
	private ProgressDialog pd;
	private boolean unknown;
	private String url;
	private View view;
	private IXProgress xp;

	public Progress(Object p) {
		if (p instanceof IXProgress) {
			xp = (IXProgress) p;
		} else if (p instanceof ProgressBar) {
			pb = (ProgressBar) p;
		} else if (p instanceof ProgressDialog) {
			pd = (ProgressDialog) p;
		} else if (p instanceof Activity) {
			act = (Activity) p;
		} else if (p instanceof View) {
			view = (View) p;
		}
	}

	private void dismiss(String url) {

		if (pd != null) {
			AQuery aq = new AQuery(pd.getContext());
			aq.dismiss(pd);
		}

		if (act != null) {
			act.setProgressBarIndeterminateVisibility(false);
			act.setProgressBarVisibility(false);
		}

		if (pb != null) {
			pb.setTag(AQuery.TAG_URL, url);
			pb.setVisibility(View.VISIBLE);
		}

		View pv = pb;
		if (pv == null) {
			pv = view;
		}

		if (pv != null) {

			Object tag = pv.getTag(AQuery.TAG_URL);
			if (tag == null || tag.equals(url)) {
				pv.setTag(AQuery.TAG_URL, null);

				if (pb != null && pb.isIndeterminate()) {
					pv.setVisibility(View.GONE);
				}
			}
		}

	}

	public void done() {

		if (pb != null) {
			pb.setProgress(pb.getMax());
		}
		if (pd != null) {
			pd.setProgress(pd.getMax());
		}

		if (act != null) {
			act.setProgress(bytes);
		}
		if (xp != null)
			xp.onProgress(bytes, bytes);

	}

	public void hide(String url) {

		if (AQUtility.isUIThread()) {
			dismiss(url);
		} else {
			this.url = url;
			AQUtility.post(this);
		}

	}

	public void increment(int delta) {
		if (unknown) {
			current++;
		} else {
			current += delta;
		}
		if (current != bytes)// 他俩相等的时候继续向下执行
			if (count-- > 0) {
				// 频率
				return;
			} else
				count = REFRESH_FREQUENCY;

		int p = unknown ? current : (defBtyes * current) / bytes;
		if (p > 9999) {
			p = 9999;
		}
		if (pb != null) {
			pb.setProgress(unknown ? 1 : delta);
		}

		if (pd != null) {
			pd.incrementProgressBy(unknown ? 1 : delta);
		}

		if (act != null) {

			act.setProgress(p);
		}
		if (xp != null)
			xp.onProgress(current, bytes);

	}

	public void reset() {

		if (pb != null) {
			pb.setProgress(0);
			pb.setMax(defBtyes);
		}
		if (xp != null)
			xp.onProgress(0, this.bytes);
		if (pd != null) {
			pd.setProgress(0);
			pd.setMax(defBtyes);
		}

		if (act != null) {
			act.setProgress(0);
		}

		unknown = false;
		current = 0;
		bytes = defBtyes;

	}

	@Override
	public void run() {
		dismiss(url);
	}

	public void setBytes(int bytes) {

		if (bytes <= 0) {
			unknown = true;
			bytes = defBtyes;
		}

		this.bytes = bytes;

		if (pb != null) {
			pb.setProgress(0);
			pb.setMax(bytes);
		}
		if (pd != null) {
			pd.setProgress(0);
			pd.setMax(bytes);
		}

	}

	public void show(String url) {

		reset();

		if (pd != null) {
			AQuery aq = new AQuery(pd.getContext());
			aq.show(pd);
		}

		if (act != null) {
			act.setProgressBarIndeterminateVisibility(true);
			act.setProgressBarVisibility(true);
		}

		if (pb != null) {
			pb.setTag(AQuery.TAG_URL, url);
			pb.setVisibility(View.VISIBLE);
		}

		if (view != null) {
			view.setTag(AQuery.TAG_URL, url);
			view.setVisibility(View.VISIBLE);
		}

	}

	private void showProgress(Object p, String url, boolean show) {

		if (p != null) {

			if (p instanceof View) {

				View pv = (View) p;

				ProgressBar pbar = null;

				if (p instanceof ProgressBar) {
					pbar = (ProgressBar) p;
				}

				if (show) {
					pv.setTag(AQuery.TAG_URL, url);
					pv.setVisibility(View.VISIBLE);
					if (pbar != null) {
						pbar.setProgress(0);
						pbar.setMax(100);
					}

				} else {
					Object tag = pv.getTag(AQuery.TAG_URL);
					if (tag == null || tag.equals(url)) {
						pv.setTag(AQuery.TAG_URL, null);

						if (pbar != null && pbar.isIndeterminate()) {
							pv.setVisibility(View.GONE);
						}
					}
				}
			} else if (p instanceof Dialog) {

				Dialog pd = (Dialog) p;

				AQuery aq = new AQuery(pd.getContext());

				if (show) {
					aq.show(pd);
				} else {
					aq.dismiss(pd);
				}

			} else if (p instanceof Activity) {

				Activity act = (Activity) p;

				act.setProgressBarIndeterminateVisibility(show);
				act.setProgressBarVisibility(show);

				if (show) {
					act.setProgress(0);
				}
			}
		}

	}

}
