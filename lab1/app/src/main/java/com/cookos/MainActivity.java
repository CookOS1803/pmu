package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;

public class MainActivity extends AppCompatActivity {

    private TextView expressionField;

    static {
        License.iConfirmNonCommercialUse("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionField = findViewById(R.id.expression);

        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            var oldText = expressionField.getText();
            var newText = oldText.subSequence(0, oldText.length() - 1);

            expressionField.setText(newText);
        });

        Button clearAll = findViewById(R.id.clearAll);
        clearAll.setOnClickListener(v -> {
            expressionField.setText("");
        });

        initButton(R.id.zero,  "0");
        initButton(R.id.one,   "1");
        initButton(R.id.two,   "2");
        initButton(R.id.three, "3");
        initButton(R.id.four,  "4");
        initButton(R.id.five,  "5");
        initButton(R.id.six,   "6");
        initButton(R.id.seven, "7");
        initButton(R.id.eight, "8");
        initButton(R.id.nine,  "9");

        initButton(R.id.plus,     "+");
        initButton(R.id.minus,    "-");
        initButton(R.id.multiply, "*");
        initButton(R.id.divide,   "/");

        Button equals = findViewById(R.id.equals);
        equals.setOnClickListener(v -> {
            var e = new Expression(expressionField.getText().toString());

            expressionField.setText(Double.toString(e.calculate()));
        });
    }

    private void initButton(int id, String text) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> {
            expressionField.append(text);
        });
    }

}