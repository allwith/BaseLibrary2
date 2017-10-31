package com.radiogroup.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class KeyBoardUtil {
	public static void attach(final Activity activity, final KeyBoardListener keyBoardListener) {
		final ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
		final int statusBarHeight = getStatusHeight(activity);

		contentView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					public int fullDisplayHeight;
					boolean isKeyboardVisible;

					@Override
					public void onGlobalLayout() {
						ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
						View userRootView = contentView.getChildAt(0);
						// 计算userRootView的高度
						Rect r = new Rect();
						userRootView.getWindowVisibleDisplayFrame(r);
						int displayHeight = r.bottom - r.top;

						if (fullDisplayHeight == 0) {
							fullDisplayHeight = displayHeight;
							return;
						}
						int keyboardHeight = Math.abs(displayHeight - fullDisplayHeight);
						if (keyboardHeight == 0) {
							if (isKeyboardVisible) {
								isKeyboardVisible = false;
								if (keyBoardListener != null)
									keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
							}
							return;
						}


						// 当前变化由，非全屏到全屏导致，此时应该更新fullDisplayHeight
						if (keyboardHeight == statusBarHeight) {
							fullDisplayHeight = displayHeight;
							return;
						}

						if (keyBoardListener != null)
							keyBoardListener.keyboardHeight(keyboardHeight);

						if (!isKeyboardVisible) {
							isKeyboardVisible = true;
							if (keyBoardListener != null)
								keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
						}
					}
				});
	}

	public interface KeyBoardListener {
		void keyboardHeight(int keyboardHeight);

		void keyboardShowingChanged(boolean visible);

	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

}
