package pk.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import pk.view.widget.ProgressView;

/**
 * @author zijiao
 * @version 2015/12/31
 * @Mark
 */
public class ProgressActivity extends Activity implements View.OnClickListener {

    private static final int[] ids = {R.id.progressView1,
            R.id.progressView2,
            R.id.progressView3,
            R.id.progressView4,};
    private ProgressView[] ps = new ProgressView[ids.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        for (ProgressView p : ps) {
            p.setOnClickListener(this);
            p.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(ProgressActivity.this, SettingActivity.class);
                    intent.putExtra("index", Integer.valueOf(v.getTag().toString()));
                    startActivityForResult(intent, 0);
                    return false;
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0 && data != null) {
            setColor(data.getIntExtra("index", -1), data.getStringExtra("f"), data.getStringExtra("s"));
        }
    }

    private void setColor(int index, String f, String s) {
        if (index < 0 || index >= ps.length) {
            return;
        }
        ProgressView p = ps[index];
        if (!TextUtils.isEmpty(f)) {
            if (!f.startsWith("#")) {
                f = "#" + f;
            }
            p.setFirstColor(Color.parseColor(f));
        }
        if (!TextUtils.isEmpty(s)) {
            if (!s.startsWith("#")) {
                s = "#" + s;
            }
            p.setSecondColor(Color.parseColor(s));
        }
    }

    private void initView() {
        setContentView(R.layout.layout_progress);
        for (int i = 0; i < ids.length; i ++) {
            ps[i] = (ProgressView) findViewById(ids[i]);
            ps[i].setTag(i);
        }
    }

    @Override
    public void onClick(View v) {
        final ProgressView p = (ProgressView) v;
        ValueAnimator a = ValueAnimator.ofFloat(0, 1);
        a.setDuration(6000);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p.setProgress((Float) animation.getAnimatedValue());
            }
        });
        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                p.setProgress(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        a.start();
    }
}
