package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String state = "operand1";
    String op = "";
    double operand1 = 0;
    double operand2 = 0;
    double end_result = 0;

    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_dot;
    Button btn_redu;
    Button btn_clean;
    Button btn_plus;
    Button btn_minus;
    Button btn_mul;
    Button btn_divide;
    Button btn_result;
    EditText result;
    EditText Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_dot = findViewById(R.id.btn_dot);
        btn_redu = findViewById(R.id.btn_redu);
        btn_clean = findViewById(R.id.btn_clean);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_mul = findViewById(R.id.btn_mul);
        btn_divide = findViewById(R.id.btn_divide);
        btn_result = findViewById(R.id.btn_result);
        result = findViewById(R.id.text_view_result);
        Result = findViewById(R.id.text_view_result_old);
    }

    public void Digit_button(View v) {
        //result.setText("0");
        Button btn_clicked = (Button) v;
        String str = result.getText().toString();

        switch(state) {
            case "op":
                str = "";
                state = "operand2";
                break;

            case "result":
                str = "";
                state = "operand1";
                break;
        }

        if (str.equals("0") && btn_clicked != btn_dot) {
            str = "";
        }
        if (btn_clicked == btn_dot) {
            if (!str.contains(".")) {
                str += ".";
            }
        } else {
                str = str + btn_clicked.getText();
        }
        result.setText(str);
    }

    public void Operator_button(View v) {

        switch(state) {
            case "operand1":
                operand1 = Double.parseDouble(result.getText().toString());
                break;

            case "operand2":
                operand1 = end_result;
                break;

            case "result":
                operand1 = end_result;
                break;
        }

        state = "op";

        Button btn = (Button) v;

        if(btn==btn_plus) op = "+";
        if(btn==btn_minus) op = "-";
        if(btn==btn_mul) op = "*";
        if(btn==btn_divide) op = "/";
    }

    public void equal_button(View v) {

        switch(state) {
            case "operand1":
                operand1 = Double.parseDouble(result.getText().toString());
                end_result = operand1;
                state = "result";
                return;

            case "operand2":
                operand2 = Double.parseDouble(result.getText().toString());
                break;

            case "op":
                operand2 = operand1;
                break;

            case "result":
                operand1 = end_result;
                break;
        }

        switch(op) {
            case "+":
                end_result = operand1 + operand2;
                break;
            case "-":
                end_result = operand1 - operand2;
                break;
            case "*":
                end_result = operand1 * operand2;
                break;
            case "/":
                end_result = operand1 / operand2;
                break;
        }

        String strResult = String.valueOf(end_result);
        if(strResult.endsWith(".0")) {
            strResult = strResult.substring(0, strResult.length()-2);
        }
        result.setText(strResult);
        Result.setText(strResult);

        state = "result";

    }

    public void redu(View v){
        String str = result.getText().toString();

        str = str.substring(0, str.length()-1);

        if(state.equals("result")) {
            str = "0";
        }

        if(str.isEmpty() || str.equals("-")) {
            str = "0";
        }

        result.setText(str);
    }

    public void clean(View v){
        state = "operand1";
        op = "";
        operand1 = 0;
        operand2 = 0;
        end_result = 0;
        result.setText("0");
    }


    //Big Mul:
    public static String bigMul(String num1, String num2){
        String tempnum1 = num1;
        String tempnum2 = num2;

        // Check condition if one string is negative
        if(num1.charAt(0) == '-' && num2.charAt(0)!='-'){
            num1 = num1.substring(1);
        }
        else if(num1.charAt(0) != '-' && num2.charAt(0) == '-'){
            num2 = num2.substring(1);
        }
        else if(num1.charAt(0) == '-' && num2.charAt(0) == '-'){
            num1 = num1.substring(1);
            num2 = num2.substring(1);
        }
        String s1 = new StringBuffer(num1).reverse().toString();
        String s2 = new StringBuffer(num2).reverse().toString();

        int[] m = new int[s1.length()+s2.length()];
        // Go from right to left in num1
        for(int i = 0; i<s1.length(); i++){
            // Go from right to left in num2
            for(int j = 0; j<s2.length(); j++){
                m[i+j] = m[i+j]+(s1.charAt(i)-'0')*(s2.charAt(j)-'0');
            }
        }


        String product = new String();
        // Multiply with current digit of first number
        // and add result to previously stored product
        // at curren+t position.
        for(int i = 0; i<m.length; i++){
            int digit = m[i]%10;
            int carry = m[i]/10;
            if(i+1<m.length)
            {
                m[i+1] = m[i+1] + carry;
            }
            product = digit+product;
        }

        // ignore '0's from the right
        while(product.length()>1 && product.charAt(0) == '0'){
            product = product.substring(1);
        }
        // Check condition if one string is negative
        if(tempnum1.charAt(0) == '-' && tempnum2.charAt(0)!='-'){
            product = new StringBuffer(product).insert(0,'-').toString();
        }
        else if(tempnum1.charAt(0) != '-' && tempnum2.charAt(0) == '-'){
            product = new StringBuffer(product).insert(0,'-').toString();
        }
        else if(tempnum1.charAt(0) == '-' && tempnum2.charAt(0) == '-'){
            product = product;
        }

        if(product.equals("-0")){
            product="0";
        }

        return product;
    }

}
