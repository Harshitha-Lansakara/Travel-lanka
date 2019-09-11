package com.example.travellanka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;

public class Yalapackages extends AppCompatActivity {

    String[] locations={"select from here","Family package","Dynamic duo","Solo Run",
            "With the Gang"};

    Spinner spin;
    ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private static final String TAG = "";
    private final static int PAYHERE_REQUEST = 11010;
    private FirebaseAuth auth;
    EditText depdate,nname,tpno,eemail;

    TextView contents,duration,amount,capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yalapackages);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        spin=(Spinner)findViewById(R.id.trainselect);

        contents=findViewById(R.id.description);
        duration=findViewById(R.id.packagedays);
        capacity=findViewById(R.id.packagecapacity);
        amount=findViewById(R.id.price);

        nname=(EditText)findViewById(R.id.name);
        tpno=(EditText)findViewById(R.id.mobilenum);
        depdate=(EditText)findViewById(R.id.date);
        eemail=(EditText)findViewById(R.id.email);


        Button process=findViewById(R.id.proceed);




        //family package details
        final String fdescription="enjoy your stay in galle with your family/ hotel details/food details transport details etc";
        final String fduration="4 days 3 nights";
        final String fcapacity="4-5 heads";
        final String famount="100000";

        //dynamic duo details
        final String ddescription="enjoy your stay in galle with your family/ hotel details/food details transport details etc";
        final String dduration="3 days 2 nights";
        final String dcapacity="2 heads max";
        final String damount="65000";

        //solo run
        final String sdescription="enjoy your stay in galle with your family/ hotel details/food details transport details etc";
        final String sduration="2 days 1 night";
        final String scapacity="1 head only";
        final String samount="35000";


        //with the gang
        final String gdescription="enjoy your stay in galle with your family/ hotel details/food details transport details etc";
        final String gduration="5 days 4  nights";
        final String gcapacity="8-10 heads";
        final String gamount="200000";


        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,locations);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        break;

                    case 1:
                        contents.setText(fdescription);
                        duration.setText(fduration);
                        capacity.setText(fcapacity);
                        amount.setText(famount);

                        break;
                    case 2:
                        contents.setText(ddescription);
                        duration.setText(dduration);
                        capacity.setText(dcapacity);
                        amount.setText(damount);
                        break;
                    case 3:
                        contents.setText(sdescription);
                        duration.setText(sduration);
                        capacity.setText(scapacity);
                        amount.setText(samount);
                        break;
                    case 4:
                        contents.setText(gdescription);
                        duration.setText(gduration);
                        capacity.setText(gcapacity);
                        amount.setText(gamount);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBookingDetails();
                sendMessage();

                String amountt=amount.getText().toString();
                String text = spin.getSelectedItem().toString().trim();
                String names=nname.getText().toString();
                String contact =tpno.getText().toString();
                String eeemail=eemail.getText().toString();
                int aamount = Integer.parseInt(amountt);

                InitRequest req = new InitRequest();
                req.setMerchantId("1212419");
                req.setMerchantSecret("travellanka");// Your Merchant ID
                req.setAmount(aamount); // Amount which the customer should pay
                req.setCurrency("LKR"); // Currency
                req.setOrderId(text); // Unique ID for your payment transaction
                req.setItemsDescription("ticket details   :");
                req.setCustom1("");
                req.getCustomer().setFirstName(names);
                req.getCustomer().setLastName(names);
                req.getCustomer().setPhone(contact);
                req.getCustomer().setEmail(eeemail);
                req.getCustomer().getAddress().setAddress("");
                req.getCustomer().getAddress().setCity("");
                req.getCustomer().getAddress().setCountry("");
                req.getCustomer().getDeliveryAddress().setAddress("");
                req.getCustomer().getDeliveryAddress().setCity("");
                req.getCustomer().getDeliveryAddress().setCountry("");
                req.getItems().add(new Item(null, "", 1));

                Intent intent = new Intent(Yalapackages.this, PHMainActivity.class);
                intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
                PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
                startActivityForResult(intent, PAYHERE_REQUEST);
            }
        });
    }

    private void saveBookingDetails(){

        String name =nname.getText().toString().trim();
        String departuredate =depdate.getText().toString().trim();
        String telephoneno =tpno.getText().toString().trim();
        String text = spin.getSelectedItem().toString().trim();
        String  emailadd = eemail.getText().toString().trim();




        UserInformation2 userInformation =new UserInformation2(text,emailadd,name,departuredate,telephoneno);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this,"information saved!",Toast.LENGTH_SHORT).show();
    }

    private void sendMessage() {
        final String  datedep = depdate.getText().toString().trim();

        final String text = spin.getSelectedItem().toString().trim();

        final String emailadd= eemail.getText().toString().trim();
        final ProgressDialog dialog = new ProgressDialog(Yalapackages.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("travellankaapp@gmail.com", "kavisac123");
                    sender.sendMail("TrainCONNECT",
                            "<html><body><h1><center>Thank you for selecting  travel lanka to enjoy your stay in sri lanka </center></h1>" +
                                    "this is your virtual ticket! " +
                                    "you have selected  "+text+" we will pick you up on "+datedep+" at  katunayaka air port",
                            "travellankaapp@gmail.com",
                            emailadd);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
