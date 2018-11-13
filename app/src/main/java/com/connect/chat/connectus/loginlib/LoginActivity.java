package com.connect.chat.connectus.loginlib;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.connect.chat.connectus.R;
import com.connect.chat.connectus.model.AnimatedViewPager;
import com.connect.chat.connectus.ui.adapter.AuthAdapter;
import com.connect.chat.connectus.utils.PermissionSettingUtils;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSION = 111;
    @BindViews(value = {R.id.logo, R.id.first,R.id.second,R.id.last})
    protected List<ImageView> sharedElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPermis();
        ButterKnife.bind(this);
        final AnimatedViewPager pager= ButterKnife.findById(this,R.id.pager);
        final ImageView background=ButterKnife.findById(this,R.id.scrolling_background);
        int[] screenSize=screenSize();

        for(ImageView element:sharedElements){
            @ColorRes int color=element.getId()!=R.id.logo?R.color.white_transparent:R.color.color_logo_log_in;
            DrawableCompat.setTint(element.getDrawable(), ContextCompat.getColor(this,color));
        }
        //load a very big image and resize it, so it fits our needs
        Glide.with(this)
                .load(R.drawable.busy)
                .asBitmap()
                .override(screenSize[0]*2,screenSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(background) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        background.setImageBitmap(resource);
                        background.post(()->{
                            //we need to scroll to the very left edge of the image
                            //fire the scale animation
                            background.scrollTo(-background.getWidth()/2,0);
                            ObjectAnimator xAnimator= ObjectAnimator.ofFloat(background, View.SCALE_X,4f,background.getScaleX());
                            ObjectAnimator yAnimator= ObjectAnimator.ofFloat(background, View.SCALE_Y,4f,background.getScaleY());
                            AnimatorSet set=new AnimatorSet();
                            set.playTogether(xAnimator,yAnimator);
                            set.setDuration(getResources().getInteger(R.integer.duration));
                            set.start();
                        });
                        pager.post(()->{
                            AuthAdapter adapter = new AuthAdapter(getSupportFragmentManager(), pager, background, sharedElements);
                            pager.setAdapter(adapter);
                        });
                    }
                });
    }

    private int[] screenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new int[]{size.x,size.y};
    }

    private void initPermis() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            PermissionSettingUtils.requestPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != PermissionSettingUtils.REQUEST_CODE) {
            return;
        }
        if (PermissionSettingUtils.isPermissionGranted(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }


}
