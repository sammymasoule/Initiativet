package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

public class LoginFragment extends Fragment implements View.OnClickListener{

    //Button LoginBtn;
    EditText EmailInp,PassInp;
    TextView SignupBtn,FPassBtn, LoginBtn;
    RelativeLayout activity_main;
    private FirebaseAuth auth;
    private FirebaseAuthParser authParser;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_login, container, false);


            //Btns
            //LoginBtn = (Button) v.findViewById(R.id.loginbtn);

            //EditText
            EmailInp = (EditText) v.findViewById(R.id.login_email);
            PassInp = (EditText) v.findViewById(R.id.login_password);

            //Temporary hardcoded login info for lazy teachers/testers
            EmailInp.setText("test@mail.com");
            PassInp.setText("lamepassword");

            //TextView
            SignupBtn = (TextView) v.findViewById(R.id.signup);
            FPassBtn = (TextView) v.findViewById(R.id.forgotpass);
            LoginBtn = (TextView) v.findViewById(R.id.loginbtn);

            //Layout
            activity_main = (RelativeLayout) v.findViewById(R.id.activity_main);

            //ActionListener
            LoginBtn.setOnClickListener(this);
            FPassBtn.setOnClickListener(this);
            SignupBtn.setOnClickListener(this);

            //Firebase init
            FirebaseApp.initializeApp(getActivity());
            auth = FirebaseAuth.getInstance();

            //Check session
            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(getActivity(), FrameActivity.class));
                getActivity().finish();
                //startActivity(new Intent(getActivity(), TabActivity.class));
                //getActivity().finish();

            }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotpass:
                //startActivity(new Intent(getActivity(),ForgotPassActivity.class));
                //getActivity().finish();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ForgotPassFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.signup:
                //startActivity(new Intent(getActivity(),SignupActivity.class));
                //getActivity().finish();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SignupFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.loginbtn:
                //Method for hiding keyboard after submitting so the user can see snackbars easily
                // Check if no view has focus:

                LoginBtn.setEnabled(false);

                View view = getView();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //Logging in user with email and pass
                if (!EmailInp.getText().toString().isEmpty() && !PassInp.getText().toString().isEmpty()){
                    userlogin(EmailInp.getText().toString(), PassInp.getText().toString());
                }else{
                    //Snackbar for lightweight feedback
                    msgs("Email og/eller password må ikke være tom.");
                    LoginBtn.setEnabled(true);
                }
                break;
        }
    }

    public void msgs(String msg){
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    //login method
    private void userlogin(final String email, final String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    LoginBtn.setEnabled(true);
                    if(password.length() < 6) {
                        msgs("Password length must be over 6");
                    }else{
                        try{
                            msgs("Error");
                        }catch (Exception e){}
                    }
                }
                else{
                    startActivity(new Intent(getActivity(),FrameActivity.class));
                    getActivity().finish();
                }
            }
        });
    }
}
