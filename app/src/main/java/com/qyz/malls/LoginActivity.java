package com.qyz.malls;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.qyz.malls.apicall.ApiInstanceClass;
import com.qyz.malls.db.LocalDatabase;
import com.qyz.malls.db.User;
import com.qyz.malls.db.UserDao;
import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Qzy/LoginActivity";
    EditText editText;
    EditText phoneNumber;
    ProgressBar progressBar;
    Button button,otpButton;
    String mVerificationId="";
    private FirebaseAuth mAuth;
    String number="";
    LoginActivity mActivity;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText=findViewById(R.id.code);
        button=findViewById(R.id.btn);
        otpButton=findViewById(R.id.get_otp);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        skip = findViewById(R.id.skip);
        mActivity = this;
        mVerificationId="";
        //  sendVerificationCode(number);
        phoneNumber=findViewById(R.id.number);
        phoneNumber.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number= "+91"+phoneNumber.getText().toString().trim();
                System.out.println("sree number "+number);
                if(number.isEmpty()){
                    phoneNumber.setText("Invalid Number");
                    phoneNumber.requestFocus();
                    return;
                }
                else{
                    sendVerificationCode(number);
                    phoneNumber.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                    otpButton.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    editText.setText("Invalid");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }


        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RestaurantHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential)
        {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verificaiton without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential.getSmsCode()+credential.getProvider());

            Log.e(TAG,"credential=-=-=>>><<>>>signInWithPhoneAuthCredential-->> ");
            String code=credential.getSmsCode();
            if(code!=null){
                editText.setText(code);
                verifyCode(code);
            }else{
                signInWithPhoneAuthCredential(credential);
            }

        }
        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.e(TAG, "onVerificationFailed", e);

            // Show a message and update the UI
            // ...
        }



        @Override
        public void onCodeSent(String verificationId,
            PhoneAuthProvider.ForceResendingToken token)
        {

            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.e(TAG, "onCodeSent:" + verificationId+"<<token>>"+token);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;

            //mResendToken = token;

            // ...
        }
    };





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendPostCred(mAuth,mActivity);
                            createNotificationChannel();
                            Intent intent=new Intent(mActivity, RestaurantHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public static void sendPostCred(FirebaseAuth mAuth, final Activity mActivity) {
        Log.d(TAG, "sendPostCred:");
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        params.put("partition",currentUser.getPhoneNumber());
        params.put("sort",currentUser.getUid());
//        params.put("passkey",FirebaseInstanceId.getInstance().getToken());
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        params.put("passkey",token);

                        // Log and toast
                        String msg =  "Instance ID: "+token;
                        Log.d(TAG, msg);
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();

                        System.out.println("sree id "+currentUser.getPhoneNumber()+" "+
                                currentUser.getProviderId()+"  "+params.get("passkey"));

                        Executor diskIO = new AppExecutors().getDiskIO();
                        diskIO.execute(new Runnable() {
                            @Override
                            public void run() {
                                UserDao userDao = LocalDatabase.getInstance(mActivity).userDao();
                                if(userDao.getUserCount()==0 || userDao.getGcmToken()==null){
                                    addToDatabase(userDao);
                                }else if((!userDao.getPhoneNumber().equalsIgnoreCase(currentUser.getPhoneNumber()) || !userDao.getGcmToken().equalsIgnoreCase(params.get("passkey")))){
                                    addToDatabase(userDao);
                                }
                            }

                            public void addToDatabase(UserDao userDao){
                                Log.d(TAG, "sendPostCred/run: Inserting into database for user:"+userDao.getPhoneNumber());
                                User user = new User();
                                user.setPhonenumber(currentUser.getPhoneNumber());
                                user.setUserid(currentUser.getUid());
                                user.setGcmToken(params.get("passKey"));
                                userDao.insertDetails(user);

                                //RequestBody body  = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(params)).toString());
                                RequestBody body = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                                ApiInstanceClass.getInstance().submitPostRequest(ApiInstanceClass.getBaseInterface(),body,null,"post_user_cred",null);
                            }
                        });
                    }
                });
    }

    private void createNotificationChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if(notificationManager.getNotificationChannel(channelId) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_HIGH));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            sendPostCred(mAuth,this);
            Intent intent = new Intent(this, RestaurantHomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
