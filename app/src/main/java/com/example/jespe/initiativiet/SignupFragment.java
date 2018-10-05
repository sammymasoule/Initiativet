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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment implements View.OnClickListener {
    //Button RegisterBtn;
    TextView logmein,forgotpass, RegisterBtn;
    EditText EmailInp, PassInp;
    RelativeLayout SignUp_Activity;
    View vie2;
    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_signup, container, false);
        vie2 = v;
        //Buttons
        //RegisterBtn = (Button) v.findViewById(R.id.loginbtn);

        //TextViews
        logmein     = (TextView) v.findViewById(R.id.logmein);
        forgotpass  = (TextView) v.findViewById(R.id.forgotpass);
        RegisterBtn = (TextView) v.findViewById(R.id.loginbtn);

        //EditText
        EmailInp    = (EditText) v.findViewById(R.id.EmailInp);
        PassInp     = (EditText) v.findViewById(R.id.PassInp);

        //ActionListeners
        RegisterBtn.setOnClickListener(this);
        logmein.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

        //Firebase Init
        auth = FirebaseAuth.getInstance();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logmein:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new LoginFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.forgotpass:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ForgotPassFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.loginbtn:
                //Method for hiding keyboard after submitting so the user can see snackbars easily
                // Check if no view has focus:
                View view = (View) this.getView();//.getWindowToken();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (!EmailInp.getText().toString().isEmpty() && !PassInp.getText().toString().isEmpty()){
                    //Logging in user with email and pass
                    signUpUser(EmailInp.getText().toString(),PassInp.getText().toString());
                }else{
                    snacks("Tomt password eller email");
                }

                break;
        }
    }

    public void snacks(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void signUpUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //Error if pass is too
                            try{
                                snacks("Error");
                            }catch (Exception e){}
                        }
                        else{
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, new LoginFragment())
                                    .addToBackStack(null).commit();
                        }
                    }
                });
    }
}