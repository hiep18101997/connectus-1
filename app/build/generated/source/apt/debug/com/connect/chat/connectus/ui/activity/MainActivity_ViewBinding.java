// Generated code from Butter Knife. Do not modify!
package com.connect.chat.connectus.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.connect.chat.connectus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296321;

  private View view2131296320;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_online, "field 'btnOnline' and method 'onClick'");
    target.btnOnline = Utils.castView(view, R.id.btn_online, "field 'btnOnline'", Button.class);
    view2131296321 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_offline, "field 'btnOffline' and method 'onClick'");
    target.btnOffline = Utils.castView(view, R.id.btn_offline, "field 'btnOffline'", Button.class);
    view2131296320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnOnline = null;
    target.btnOffline = null;

    view2131296321.setOnClickListener(null);
    view2131296321 = null;
    view2131296320.setOnClickListener(null);
    view2131296320 = null;
  }
}
