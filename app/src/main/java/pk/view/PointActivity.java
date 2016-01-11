package pk.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import pk.view.R;
import pk.view.widget.PointView;

public class PointActivity extends Activity {

    private PointView mPoint;
    private Button mButton;
    private boolean isRecord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        mPoint = (PointView) findViewById(R.id.point);
        mButton = (Button) findViewById(R.id.button);
        changeButton();
        mPoint.setTime(5000);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecord = !isRecord;
                changeButton();
                mPoint.setIsRecord(isRecord);
            }
        });
    }

    private void changeButton() {
        if (isRecord) {
            mButton.setText("显示");
            mPoint.clear();
        } else {
            mButton.setText("记录");
        }
    }

}
