        package com.example.android.justjava;


        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.NumberFormat;
        import java.util.jar.Attributes;

        /**
         * This app displays an order form to order coffee.
         */
        public class MainActivity extends AppCompatActivity {

            int quantity = 2;
            int whippedCream = 1;
            int chocolate = 2;
            int priceCoffe = 5;
            boolean hasWhippedCream;
            boolean hasChocolate;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }

            /**
             * This method is called when the plus button is clicked.
             */
            public void increment(View view) {
                if (quantity == 100) {
                    // pop up toast messages
                    Toast toast = Toast.makeText(this, "You can't have more then 100 coffee", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                quantity = quantity + 1;
                displayQuantity(quantity);
            }

            /**
             * This method is called when the minus button is clicked.
             */
            public void decrement(View view) {
                if(quantity == 1){
                    // pop up toast messages
                    Toast toast = Toast.makeText(this, "You can't have less then 1 coffee", Toast.LENGTH_SHORT);
                    toast.show();
                return;
                }
                quantity = quantity - 1;
                displayQuantity(quantity);
            }

            /**
             * This method is called when the order button is clicked.
             */
            public void submitOrder(View view) {
                // Figure out if the user wants whipped cream topping
                CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
                hasWhippedCream = whippedCreamCheckBox.isChecked();

                // Figure out if the user wants chocolate topping
                CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
                hasChocolate = chocolateCheckBox.isChecked();
        //Name
               EditText editName = (EditText) findViewById(R.id.edit_text_name);
                String editNameNew = editName.getText().toString();

                // Calculate the price
                int price = calculatePrice();

                // Display the order summary on the screen
                String message = createOrderSummary(price, hasWhippedCream, hasChocolate, editNameNew);

    //Send email - intent!!!
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + editNameNew);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

            /**
             * Calculates the price of the order.
             * @return total price
             */
            private int calculatePrice() {
                if (hasChocolate && hasWhippedCream) {
                    return quantity * (priceCoffe + whippedCream + chocolate);
                } else if (hasChocolate) {
                return quantity * (priceCoffe + chocolate);
                }else if (hasWhippedCream){
                    return quantity * (priceCoffe + whippedCream);
                } else {
                    return quantity * priceCoffe;
                }
            }
            /**
             * Create summary of the order.
             *
             * @param price           of the order
             * @param addWhippedCream is whether or not to add whipped cream to the coffee
             * @param addChocolate    is whether or not to add chocolate to the coffee
             * @param addName
             * @return text summary
             */
            @SuppressLint("StringFormatMatches")
            private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String addName) {
                String priceMessage = getString(R.string.order_summary_name, addName);
                priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
                priceMessage += "\n"  + getString(R.string.order_summary_chocolate, addChocolate);
                priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
                priceMessage += "\n" + getString(R.string.order_summary_price, price);
                priceMessage += "\n" + getString(R.string.thank_you);
                return priceMessage;
            }

            /**
             * This method displays the given quantity value on the screen.
             */
            private void displayQuantity(int numberOfCoffees) {
                TextView quantityTextView = (TextView) findViewById(
                        R.id.quantity_text_view);
                quantityTextView.setText("" + numberOfCoffees);
            }

        }
