package pk.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecord = !isRecord;
                changeButton();
                mPoint.setIsRecord(isRecord);
            }
        });
        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText edit = new EditText(PointActivity.this);
                new AlertDialog.Builder(PointActivity.this)
                        .setTitle("设置时间")
                        .setView(edit)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s = edit.getText().toString().trim();
                                try {
                                    int time = Integer.valueOf(s);
                                    mPoint.setTime(time);
                                } catch (Exception e) {
                                }
                            }
                        })
                        .show();

                return false;
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
