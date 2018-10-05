package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFragment extends Fragment implements View.OnClickListener {

    //Button ResetBtn;
    EditText EmailInp;
    TextView BackBtn, ResetBtn;
    RelativeLayout activity_forgot;
    private FirebaseAuth auth;
    View vie;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_forgot_pass, container, false);
        vie = v;
        //Buttons
        //ResetBtn = (Button) v.findViewById(R.id.ResetBtn);

        //EditText
        EmailInp = (EditText) v.findViewById(R.id.EmailInp);

        //TextView
        BackBtn = (TextView) v.findViewById(R.id.BackBtn);
        ResetBtn = (TextView) v.findViewById(R.id.ResetBtn);

        //Layout
        activity_forgot = (RelativeLayout) v.findViewById(R.id.activity_forgot_password);

        //Firebase init
        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();

        BackBtn.setOnClickListener(this);
        ResetBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ResetBtn:
                //Method for hiding keyboard after submitting so the user can see snackbars easily
                // Check if no view has focus:
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //Resetting pass
                try {
                    resetPassword(EmailInp.getText().toString());
                } catch (Exception e) {
                    snack("Error: "+e.getMessage().replace("String","Input"));
                }
                break;
            case R.id.BackBtn:
                //startActivity(new Intent(ForgotPassActivity.this,MainActivity.class));
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new LoginFragment())
                        .addToBackStack(null)
                        .commit();

                break;
        }
    }

    public void snack(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        if(task.isSuccessful()){
                            snack("Please check your mail for reset link: "+email);

                            //Returning user to mainpage after 3.5 seconds, so that the user has a chance to reade the snackbar info.
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    // if you are redirecting from a fragment then use getActivity() as the context.
                                    //startActivity(new Intent(ForgotPassActivity.this, MainActivity.class));
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.fragmentContainer, new LoginFragment())
                                            .addToBackStack(null)
                                            .commit();
                                }
                            };

                            //Running the runnable after 3.5 seconds delay.
                            Handler h = new Handler();
                            h.postDelayed(r, 3500);
                        }
                        else{
                            try {
                                snack("Failed");
                            }catch (Exception e){}
                        }
                    }
                });
    }
}
