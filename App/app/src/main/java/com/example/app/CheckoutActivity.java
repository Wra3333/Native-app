package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;

import com.example.app.viewmodel.CheckoutViewModel;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.app.databinding.ActivityCheckoutBinding;

/**
 * Checkout implementation for the app
 */
public class CheckoutActivity extends AppCompatActivity {

  // Arbitrarily-picked constant integer you define to track a request for payment data activity.
  private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

  private CheckoutViewModel model;

  private ActivityCheckoutBinding layoutBinding;
  private View googlePayButton;

  /**
   * Initialize the Google Pay API on creation of the activity
   *
   * @see AppCompatActivity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try
    {
      this.getSupportActionBar().hide();
    }
    catch (NullPointerException e){}
    initializeUi();

    model = new ViewModelProvider(this).get(CheckoutViewModel.class);
    model.canUseGooglePay.observe(this, this::setGooglePayAvailable);
  }

  private void initializeUi() {

    // Use view binding to access the UI elements
    layoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
     setContentView(layoutBinding.getRoot());

    // The Google Pay button is a layout file – take the root view
    googlePayButton = layoutBinding.googlePayButton.getRoot();
    googlePayButton.setOnClickListener(this::requestPayment);
  }
  private void setGooglePayAvailable(boolean available) {
    if (available) {
      googlePayButton.setVisibility(View.VISIBLE);
    } else {
      Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
    }
  }

  public void requestPayment(View view) {
    googlePayButton.setClickable(false);
    long dummyPriceCents = 100;
    long shippingCostCents = 900;
    long totalPriceCents = dummyPriceCents + shippingCostCents;
    final Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);
    AutoResolveHelper.resolveTask(task, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
  }
  // Suppressing deprecation until `registerForActivityResult` can be used with the Google Pay API.
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      // value passed in AutoResolveHelper
      case LOAD_PAYMENT_DATA_REQUEST_CODE:
        switch (resultCode) {

          case AppCompatActivity.RESULT_OK:
            PaymentData paymentData = PaymentData.getFromIntent(data);
            handlePaymentSuccess(paymentData);
            break;

          case AppCompatActivity.RESULT_CANCELED:
            // The user cancelled the payment attempt
            break;

          case AutoResolveHelper.RESULT_ERROR:
            Status status = AutoResolveHelper.getStatusFromIntent(data);
            handleError(status);
            break;
        }

        // Re-enables the Google Pay payment button.
        googlePayButton.setClickable(true);
    }
  }

  private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
    final String paymentInfo = paymentData.toJson();

    try {
      JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
      // If the gateway is set to "example", no payment information is returned - instead, the
      // token will only consist of "examplePaymentMethodToken".

      final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
      final String token = tokenizationData.getString("token");
      final JSONObject info = paymentMethodData.getJSONObject("info");
      final String billingName = info.getJSONObject("billingAddress").getString("name");
      Toast.makeText(
          this, getString(R.string.payments_show_name, billingName),
          Toast.LENGTH_LONG).show();

      // Logging token string.
      Log.d("Google Pay token: ", token);

    } catch (JSONException e) {
      throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
    }
  }
  private void handleError(@Nullable Status status) {
    String errorString = "Unknown error.";
    if (status != null) {
      int statusCode = status.getStatusCode();
      errorString = String.format(Locale.getDefault(), "Error code: %d", statusCode);
    }

    Log.e("loadPaymentData failed", errorString);
  }
  int count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0,costt=0;

    public void counter1(View v) {
      TextView cost = findViewById(R.id.Cost);
      TextView text = findViewById(R.id.counter1);
      text.setText(++count1+"");
      costt+=260;
      cost.setText("Cумма заказа: "+costt+"₽");
    }
    public void counterMinus1(View v) {
      TextView cost = findViewById(R.id.Cost);
      TextView text = findViewById(R.id.counter1);
      if (count1>0){
        costt-=260;
      text.setText(--count1+"");
      cost.setText("Cумма заказа: "+costt+"₽");

      }
      else {
        return;
      }
    }
  public void counter2(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter2);
    text.setText(++count2+"");
    costt+=210;
    cost.setText("Cумма заказа: "+costt+"₽");
  }
  public void counterMinus2(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter2);
    if (count2>0){
      costt-=210;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count2+"");
    }
    else {
      return;
    }
  }
  public void counter3(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter3);
    text.setText(++count3+"");
    costt+=140;
    cost.setText("Cумма заказа: "+costt+"₽");
  }
  public void counterMinus3(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter3);
    if (count3>0){
      costt-=140;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count3+"");
    }
    else {
      return;
    }
  }
  public void counter4(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter4);
    text.setText(++count4+"");
    costt+=213;
    cost.setText("Cумма заказа: "+costt+"₽");
  }
  public void counterMinus4(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter4);
    if (count4>0){
      costt-=213;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count4+"");
    }
    else {
      return;
    }
  }
  public void counter5(View v) {
    TextView cost = findViewById(R.id.Cost);
    costt+=230;
    cost.setText("Cумма заказа: "+costt+"₽");
    TextView text = findViewById(R.id.counter5);
    text.setText(++count5+"");
  }
  public void counterMinus5(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter5);
    if (count5>0){
      costt-=230;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count5+"");
    }
    else {
      return;
    }
  }
  public void counter6(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter6);
    costt+=77;
    cost.setText("Cумма заказа: "+costt+"₽");
    text.setText(++count6+"");
  }
  public void counterMinus6(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter6);
    if (count6>0){
      costt-=77;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count6+"");
    }
    else {
      return;
    }
  }
  public void counter7(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter7);
    costt+=140;
    cost.setText("Cумма заказа: "+costt+"₽");
    text.setText(++count7+"");
  }
  public void counterMinus7(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter7);
    if (count7>0){
      costt-=140;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count7+"");
    }
    else {
      return;
    }
  }
  public void counter8(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter8);
    costt+=76;
    cost.setText("Cумма заказа: "+costt+"₽");
    text.setText(++count8+"");
  }
  public void counterMinus8(View v) {
    TextView cost = findViewById(R.id.Cost);
    TextView text = findViewById(R.id.counter8);
    if (count8>0){
      costt-=76;
      cost.setText("Cумма заказа: "+costt+"₽");
      text.setText(--count8+"");
    }
    else {
      return;
    }
  }
  public void tab1(View v){
    ImageButton tab1 = findViewById(R.id.button17);
    ImageButton tab2 = findViewById(R.id.button18);
    ImageButton tab3 = findViewById(R.id.button19);
    ImageButton tab4 = findViewById(R.id.button20);
    tab1.setBackgroundColor(getResources().getColor(R.color.purple_500));
    tab3.setBackgroundColor(getResources().getColor(R.color.white));
    tab4.setBackgroundColor(getResources().getColor(R.color.white));
    tab2.setBackgroundColor(getResources().getColor(R.color.white));
    CardView card1 = findViewById(R.id.card_view1);
    CardView card2 = findViewById(R.id.card_view2);
    CardView card3 = findViewById(R.id.card_view3);
    CardView card4 = findViewById(R.id.card_view4);
    CardView card5 = findViewById(R.id.card_view5);
    CardView card6 = findViewById(R.id.card_view6);
    CardView card7 = findViewById(R.id.card_view7);
    CardView card8 = findViewById(R.id.card_view8);
    card1.setVisibility(View.VISIBLE);
    card2.setVisibility(View.VISIBLE);
    card3.setVisibility(View.VISIBLE);
    card4.setVisibility(View.VISIBLE);
    card5.setVisibility(View.VISIBLE);
    card6.setVisibility(View.VISIBLE);
    card7.setVisibility(View.VISIBLE);
    card8.setVisibility(View.VISIBLE);
  }
  public void tab2(View v){
    ImageButton tab1 = findViewById(R.id.button17);
    ImageButton tab2 = findViewById(R.id.button18);
    ImageButton tab3 = findViewById(R.id.button19);
    ImageButton tab4 = findViewById(R.id.button20);
    tab1.setBackgroundColor(getResources().getColor(R.color.white));
    tab3.setBackgroundColor(getResources().getColor(R.color.white));
    tab4.setBackgroundColor(getResources().getColor(R.color.white));
    tab2.setBackgroundColor(getResources().getColor(R.color.purple_500));
    CardView card1 = findViewById(R.id.card_view1);
    CardView card2 = findViewById(R.id.card_view2);
    CardView card3 = findViewById(R.id.card_view3);
    CardView card4 = findViewById(R.id.card_view4);
    CardView card5 = findViewById(R.id.card_view5);
    CardView card6 = findViewById(R.id.card_view6);
    CardView card7 = findViewById(R.id.card_view7);
    CardView card8 = findViewById(R.id.card_view8);
    card1.setVisibility(View.VISIBLE);
    card2.setVisibility(View.VISIBLE);
    card3.setVisibility(View.VISIBLE);
    card4.setVisibility(View.VISIBLE);
    card5.setVisibility(View.VISIBLE);
    card6.setVisibility(View.GONE);
    card7.setVisibility(View.GONE);
    card8.setVisibility(View.GONE);
  }
  public void tab3(View v){
    ImageButton tab1 = findViewById(R.id.button17);
    ImageButton tab2 = findViewById(R.id.button18);
    ImageButton tab3 = findViewById(R.id.button19);
    ImageButton tab4 = findViewById(R.id.button20);
    tab1.setBackgroundColor(getResources().getColor(R.color.white));
    tab3.setBackgroundColor(getResources().getColor(R.color.purple_500));
    tab4.setBackgroundColor(getResources().getColor(R.color.white));
    tab2.setBackgroundColor(getResources().getColor(R.color.white));
    CardView card1 = findViewById(R.id.card_view1);
    CardView card2 = findViewById(R.id.card_view2);
    CardView card3 = findViewById(R.id.card_view3);
    CardView card4 = findViewById(R.id.card_view4);
    CardView card5 = findViewById(R.id.card_view5);
    CardView card6 = findViewById(R.id.card_view6);
    CardView card7 = findViewById(R.id.card_view7);
    CardView card8 = findViewById(R.id.card_view8);
    card1.setVisibility(View.GONE);
    card2.setVisibility(View.GONE);
    card3.setVisibility(View.GONE);
    card4.setVisibility(View.GONE);
    card5.setVisibility(View.GONE);
    card6.setVisibility(View.VISIBLE);
    card7.setVisibility(View.GONE);
    card8.setVisibility(View.VISIBLE);
  }
  public void tab4(View v){
    ImageButton tab1 = findViewById(R.id.button17);
    ImageButton tab2 = findViewById(R.id.button18);
    ImageButton tab3 = findViewById(R.id.button19);
    ImageButton tab4 = findViewById(R.id.button20);
    tab1.setBackgroundColor(getResources().getColor(R.color.white));
    tab3.setBackgroundColor(getResources().getColor(R.color.white));
    tab4.setBackgroundColor(getResources().getColor(R.color.purple_500));
    tab2.setBackgroundColor(getResources().getColor(R.color.white));
    CardView card1 = findViewById(R.id.card_view1);
    CardView card2 = findViewById(R.id.card_view2);
    CardView card3 = findViewById(R.id.card_view3);
    CardView card4 = findViewById(R.id.card_view4);
    CardView card5 = findViewById(R.id.card_view5);
    CardView card6 = findViewById(R.id.card_view6);
    CardView card7 = findViewById(R.id.card_view7);
    CardView card8 = findViewById(R.id.card_view8);
    card1.setVisibility(View.GONE);
    card2.setVisibility(View.GONE);
    card3.setVisibility(View.GONE);
    card4.setVisibility(View.GONE);
    card5.setVisibility(View.GONE);
    card6.setVisibility(View.GONE);
    card7.setVisibility(View.VISIBLE);
    card8.setVisibility(View.GONE);
  }
  public void dialowShow1(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                    "    Пшеничная булочка\n" +
                    "    Котлета из говядины\n" +
                    "    Красный лук\n" +
                    "    Листья салата\n" +
                    "    Соус сорчичный\n" +
                    "    \n520г, ккал 430\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
  public void dialowShow2(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                            "    Пшеничная булочка\n" +
                            "    2 Котлеты из говядины\n" +
                            "    Солёные огурчики\n" +
                            "    Хрустящий лук\n" +
                            "    Соус горчичный\n" +
                            "    Чеддер\n" +
                            "    \n580г, ккал 740\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
  public void dialowShow3(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                            "    Пшеничная булочка\n" +
                            "    Майонез\n" +
                            "    Котлета\n" +
                            "    Свежий томат\n" +
                            "    Хрустящий лук\n" +
                            "    Хрустящий салат Айсберг\n" +
                            "    Кетчуп томатный\n" +
                            "    \n450г, ккал 610\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
  public void dialowShow4(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                            "    Пшеничная булочка\n" +
                            "    2 Котлеты из свинины\n" +
                            "    Солёные огурчики\n" +
                            "    Свежий томат\n" +
                            "    Соус мерри\n" +
                            "    Кетчуп\n" +
                            "    Чеддер\n" +
                            "    \n580г, ккал 810\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
  public void dialowShow5(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                            "    Итальянская булочка\n" +
                            "    Котлета из телятины\n" +
                            "    Cыр моцарелла\n" +
                            "    Свежий томат\n" +
                            "    Микс салата\n" +
                            "    Соус песто\n" +
                            "    \n520г, ккал 560\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void dialowShow6(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                            "    \n0,5л, 1684 кКал \n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
  public void dialowShow7(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                    "Состав:\n" +
                            "    Булочка\n" +
                            "    Мясная сосиска\n" +
                            "    Помидор\n" +
                            "    Маринованный и свежий огурец\n" +
                            "    Лист салата\n" +
                            "    Соусы\n" +
                            "    \n210г, ккал 540\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();

  }

  public void dialowShow8(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
    builder.setTitle("Описание:")
            .setMessage(
                            "    \n0,5л , 212 ккал\n")
            .setCancelable(false)
            .setNegativeButton("ОК",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }
}
