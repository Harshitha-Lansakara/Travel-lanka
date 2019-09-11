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
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

public class TrainBook extends AppCompatActivity {

    String[] locations={"select from here","Colombo to galle","Galle to colombo","Colombo to Anuradhapura",
            "Anuradhapura to Colombo","Badulla to Colombo","Colombo to Badulla"
            ,"Kandy to Colombo","Colombo to kandy"};

    Spinner spin;
    TextView classone,classtwo,totalamounttext,test;
    ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private Button process;
    private EditText firsttickets,secondtickets,name,tpno,email,date,month,time;
    private FirebaseAuth auth;
    private static final String TAG = "";
    private final static int PAYHERE_REQUEST = 11010;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_book);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        spin=(Spinner)findViewById(R.id.trainselect);
        classone=(TextView)findViewById(R.id.price1st);
        classtwo=(TextView)findViewById(R.id.price2nd);
        process=(Button)findViewById(R.id.proceed);
        firsttickets=(EditText)findViewById(R.id.firstclasstickets);
        secondtickets=(EditText)findViewById(R.id.secondclasstickets);
        name=(EditText)findViewById(R.id.name);
        tpno=(EditText)findViewById(R.id.mobilenum);
        date=(EditText)findViewById(R.id.datee);
        month=(EditText)findViewById(R.id.monthh);
        time=(EditText)findViewById(R.id.timee);
        totalamounttext=(TextView) findViewById(R.id.totaltxt);
        email=(EditText)findViewById(R.id.etemail);
        Button totalval = (Button)findViewById(R.id.totalval);





        totalval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classoneprice=classone.getText().toString();
                String classtwoprice=classtwo.getText().toString();
                String nooffirstticket =firsttickets.getText().toString();
                String noofsecodtickets=secondtickets.getText().toString();
                if (nooffirstticket.isEmpty() || classoneprice.isEmpty()|| classtwoprice.isEmpty()||noofsecodtickets.isEmpty()){

                }
                else {
                    int classtwopriceint=Integer.parseInt(classtwoprice);
                    int noofticketssecondint=Integer.parseInt(noofsecodtickets);
                    int classonepriceint = Integer.parseInt(classoneprice);
                    int noofticketsint = Integer.parseInt(nooffirstticket);
                    int totalamountoftickets = ((classonepriceint * noofticketsint)+(classtwopriceint*noofticketssecondint));
                    String totalstring=String.valueOf(totalamountoftickets);
                    totalamounttext.setText(totalstring);


                }
            }
        });

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();



                String classoneprice=classone.getText().toString();
                String classtwoprice=classtwo.getText().toString();
                String nooffirstticket =firsttickets.getText().toString();
                String noofsecodtickets=secondtickets.getText().toString();
                String firstticket =firsttickets.getText().toString().trim();
                String secondticket =secondtickets.getText().toString().trim();
                String names =name.getText().toString().trim();
                String contact =tpno.getText().toString().trim();
                String dates =date.getText().toString().trim();
                String months =month.getText().toString().trim();
                String times =time.getText().toString().trim();
                String text = spin.getSelectedItem().toString().trim();
                String emailadd =email.getText().toString().trim();
                int classtwopriceint=Integer.parseInt(classtwoprice);
                int noofticketssecondint=Integer.parseInt(noofsecodtickets);
                int classonepriceint = Integer.parseInt(classoneprice);
                int noofticketsint = Integer.parseInt(nooffirstticket);
                int totalamountoftickets = ((classonepriceint * noofticketsint)+(classtwopriceint*noofticketssecondint));
                String totalstring=String.valueOf(totalamountoftickets);

                saveBookingDetails();
                InitRequest req = new InitRequest();
                req.setMerchantId("1212419");
                req.setMerchantSecret("travellanka");// Your Merchant ID
                req.setAmount(totalamountoftickets); // Amount which the customer should pay
                req.setCurrency("LKR"); // Currency
                req.setOrderId(text); // Unique ID for your payment transaction
                req.setItemsDescription("ticket details   :"+"no of first class tickets :  "+firstticket+" "+"no of second class tickets :  "+secondticket);
                req.setCustom1("");
                req.getCustomer().setFirstName(names);
                req.getCustomer().setLastName(names);
                req.getCustomer().setPhone(contact);
                req.getCustomer().setEmail(emailadd);
                req.getCustomer().getAddress().setAddress("");
                req.getCustomer().getAddress().setCity("");
                req.getCustomer().getAddress().setCountry("");
                req.getCustomer().getDeliveryAddress().setAddress("");
                req.getCustomer().getDeliveryAddress().setCity("");
                req.getCustomer().getDeliveryAddress().setCountry("");
                req.getItems().add(new Item(null, "", 1));

                Intent intent = new Intent(TrainBook.this, PHMainActivity.class);
                intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
                PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
                startActivityForResult(intent, PAYHERE_REQUEST);
            }
        });




        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,locations);
        spin.setAdapter(adapter);


        final String galletocol1st="380";
        final String galletocol2nd="200";
        final String anuratocol1st="620";
        final String anuratocol2nd="390";
        final String badullatocol1st="1100";
        final String badullatocol2nd="700";
        final String kandytocol1st="390";
        final String kandytocol2nd="290";

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        break;

                    case 1:
                        classone.setText(galletocol1st);
                        classtwo.setText(galletocol2nd);

                        break;
                    case 2:
                        classone.setText(galletocol1st);
                        classtwo.setText(galletocol2nd);
                        break;
                    case 3:
                        classone.setText(anuratocol1st);
                        classtwo.setText(anuratocol2nd);
                        break;
                    case 4:
                        classone.setText(anuratocol1st);
                        classtwo.setText(anuratocol2nd);
                        break;
                    case 5:
                        classone.setText(badullatocol1st);
                        classtwo.setText(badullatocol2nd);
                        break;
                    case 6:
                        classone.setText(badullatocol1st);
                        classtwo.setText(badullatocol2nd);
                        break;
                    case 7:
                        classone.setText(kandytocol1st);
                        classtwo.setText(kandytocol2nd);
                        break;
                    case 8:
                        classone.setText(kandytocol1st);
                        classtwo.setText(kandytocol2nd);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void saveBookingDetails(){

        String firstticket =firsttickets.getText().toString().trim();
        String secondticket =secondtickets.getText().toString().trim();
        String names =name.getText().toString().trim();
        String contact =tpno.getText().toString().trim();
        String dates =date.getText().toString().trim();
        String months =month.getText().toString().trim();
        String times =time.getText().toString().trim();
        String text = spin.getSelectedItem().toString().trim();
        String  total = totalamounttext.getText().toString().trim();
        String  emailadd = email.getText().toString().trim();




        UserInformation userInformation =new UserInformation(firstticket,secondticket,names,contact,dates,months,times,text,total,emailadd);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this,"information saved!",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO process response
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            String msg;
            if (response.isSuccess()) {
                msg = "Activity result:" + response.getData().toString();
                Log.d(TAG, msg);
            } else {
                msg = "Result:" + response.toString();
            }
            test.setText(msg);
            Log.d(TAG, msg);
        }
    }

    private void sendMessage() {
        final String  total = totalamounttext.getText().toString().trim();
        final String firstticket =firsttickets.getText().toString().trim();
        final String secondticket =secondtickets.getText().toString().trim();
        final String text = spin.getSelectedItem().toString().trim();

        final String emailadd= email.getText().toString().trim();
        final ProgressDialog dialog = new ProgressDialog(TrainBook.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("travellankaapp@gmail.com", "kavisac123");
                    sender.sendMail("TrainCONNECT",
                            "<html><body><h1><center>Thank you for using travel lanka train ticket booking</center></h1>" +
                                    "this is your virtual ticket! " +
                                    "you have orderd  "+firstticket+" first class tickets and "+secondticket+
                                    " second class tickets"+ "of the train "+text+" and yo have paid " +
                                    total+" successfully!!</html></body>",
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
