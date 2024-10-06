package br.com.renan.ex002;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
*@author:<renan santos carvalho>
*/
public class MainActivity extends AppCompatActivity {

    private EditText etValueA;
    private EditText etValueB;
    private EditText etValueC;
    private TextView x1Result;
    private TextView x2Result;
    private TextView otherResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etValueA = findViewById(R.id.etValueA);
        etValueB = findViewById(R.id.etValueB);
        etValueC = findViewById(R.id.etValueC);
        Button calculateButton = findViewById(R.id.calculateButton);
        x1Result = findViewById(R.id.x1Result);
        x2Result = findViewById(R.id.x2Result);
        otherResults = findViewById(R.id.otherResults);

        calculateButton.setOnClickListener(e -> calculate());
    }

    private void calculate() {
        double a = Double.parseDouble(etValueA.getText().toString());
        double b = Double.parseDouble(etValueB.getText().toString());
        double c = Double.parseDouble(etValueC.getText().toString());

        x1Result.setText("");
        x2Result.setText("");
        otherResults.setText("");

        if(a == 0) {
            otherResults.setText(R.string.invalidInput);
            return;
        }

        CalculationResult result = calculateSecondDegreeEquation(a, b, c);

        String temp;
        if(result.delta > 0) {
            temp = getString(R.string.x1Result) + " " + result.x1;
            x1Result.setText(temp);
            temp = getString(R.string.x2Result) + " " + result.x2;
            x2Result.setText(temp);
            temp = getString(R.string.deltaAbove0) + " " + result.delta;
            otherResults.setText(temp);
        }
        else if(result.delta < 0) {
            temp = getString(R.string.deltaNegative) + " " + result.delta;
            otherResults.setText(temp);
        }
        else {
            temp = getString(R.string.deltaIs0) + " " + result.xd0;
            otherResults.setText(temp);
        }
    }

    private CalculationResult calculateSecondDegreeEquation(double a, double b, double c) {
        double delta = Math.pow(b, 2) - 4*a*c;
        if (delta < 0) {
            return new CalculationResult(delta);
        }

        double x1 = (-b + Math.sqrt(delta))/2*a;
        double x2 = (-b - Math.sqrt(delta))/2*a;

        return delta == 0 ? new CalculationResult(delta) : new CalculationResult(x1, x2, delta);

    }

    private static class CalculationResult {
        final Double x1;
        final Double x2;
        final Double delta;
        final Double xd0;

        public CalculationResult(Double x1, Double x2, Double delta) {
            this.x1 = x1;
            this.x2 = x2;
            this.delta = delta;
            this.xd0 = null;
        }

        public CalculationResult(Double delta, Double xd0) {
            this.x1 = null;
            this.x2 = null;
            this.delta = delta;
            this.xd0 = xd0;
        }

        public CalculationResult(Double delta) {
            this.delta = delta;
            this.x1 = null;
            this.x2 = null;
            this.xd0 = null;
        }
    }
}
