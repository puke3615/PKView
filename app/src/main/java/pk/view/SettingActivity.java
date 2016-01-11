package pk.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @author zijiao
 * @version 2015/12/31
 * @Mark
 */
public class SettingActivity extends Activity {

    private EditText f, s;
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        type = getIntent().getIntExtra("index", -1);
        f = (EditText) findViewById(R.id.first);
        s = (EditText) findViewById(R.id.second);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("f", f.getText().toString());
        intent.putExtra("s", s.getText().toString());
        if (type != -1) {
            intent.putExtra("index", type);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
