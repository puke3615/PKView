package pk.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pk.view.widget.RandomView;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button button;
    private RandomView randomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        randomView = (RandomView) findViewById(R.id.randomView);
        initRandomView();
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
        randomView.change();
    }

}
