package com.example.admin.orderingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {

        if (quantity == 100) {

            return;

        }

        quantity = quantity + 1;

        displayQuantity(quantity);

    }

    public void decrement(View view) {

        if (quantity == 0) {

            return;

        }

        quantity = quantity - 1;

        displayQuantity(quantity);

    }


    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);

        Editable nameEditable = nameField.getText();

        String name = nameEditable.toString();


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox vanillaCheckBox = (CheckBox) findViewById(R.id.vanilla);

        boolean hasVanilla = vanillaCheckBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate, hasVanilla);


        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasVanilla);


        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_SUBJECT,

                getString(R.string.order_summary_email_subject, name));

        intent.putExtra(Intent.EXTRA_TEXT, message);



        if (intent.resolveActivity(getPackageManager()) != null) {

            startActivity(intent);

        }

    }


    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addVanilla) {


        int basePrice = 10;


        if (addWhippedCream) {

            basePrice = basePrice + 1;

        }


        if (addChocolate) {

            basePrice = basePrice + 2;

        }

        if (addVanilla) {

            basePrice = basePrice + 3;
        }


        return quantity * basePrice;

    }


    private String createOrderSummary(String name, int price, boolean addWhippedCream,

                                      boolean addChocolate, boolean addVanilla) {

        String priceMessage = getString(R.string.order_summary_name, name);

        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);

        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);

        priceMessage += "\n" + getString(R.string.order_summary_vanilla, addVanilla);

        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);

        priceMessage += "\n" + getString(R.string.order_summary_price,

                NumberFormat.getCurrencyInstance().format(price));

        priceMessage += "\n" + getString(R.string.thank_you);


        return priceMessage;

    }


    private void displayQuantity(int numberOfCoffees) {

        TextView quantityTextView = (TextView) findViewById(

                R.id.quantity_text_view);

        quantityTextView.setText("" + numberOfCoffees);

    }

}

