package pk.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pk.view.drag.DragActivity;
import pk.view.touch.TouchActivity;
import pk.view.widget.ProgressView;
import pk.view.widget.RandomView;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button button, touch, drag;
    private RandomView randomView;
    private ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        touch = (Button) findViewById(R.id.touch);
        touch.setOnClickListener(this);

        drag = (Button) findViewById(R.id.drag);
        drag.setOnClickListener(this);

        randomView = (RandomView) findViewById(R.id.randomView);
        initRandomView();

        progressView = (ProgressView) findViewById(R.id.progressView);
        initProgressView();
    }

    private void initProgressView() {
        progressView.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;
            @Override
            public void onClick(View v) {
                ValueAnimator a = ValueAnimator.ofFloat(0, 1);
                a.setDuration(4000);
                a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        progressView.setProgress((Float) animation.getAnimatedValue());
                    }
                });
                a.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressView.setProgress(1);
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
        });
    }

    private void initRandomView() {
        randomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomView.change();
            }
        });
        randomView.setData(new String[]{"234", "456dd1", "821d4", "1465665"});

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, PointActivity.class));
                break;
            case R.id.touch:
                startActivity(new Intent(this, TouchActivity.class));
                break;
            case R.id.drag:
                startActivity(new Intent(this, DragActivity.class));
                break;
        }
    }

}
