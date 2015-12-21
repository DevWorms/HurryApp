package com.salvador.devworms.hurryapp;

/**
 * Created by salvador on 20/12/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

/**
 * A placeholder fragment containing a simple view.
 */
public   class MainActivityFragment extends Fragment {

    private TextView textView;
    private ProfilePictureView image;
    private CallbackManager mCallbackManager;
    private Profile profile;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;


    public MainActivityFragment() {
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                //displayMessage(profile1);

            }
        };

        tokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setCompoundDrawables(null, null, null, null);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mFacebookCallback);
    }

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
          //displayMessage(profile);


        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
       displayMessage(profile);

    }

    @Override
    public void onStop() {
        super.onStop();
        profileTracker.stopTracking();
        tokenTracker.stopTracking();
    }

    private void displayMessage(Profile profile) {

            if (profile != null ) {
                Intent i = new Intent(getActivity(), MenuActivity.class);
                i.putExtra("nombre", profile.getName());
                i.putExtra("foto", profile.getId());
                startActivity(i);
                //image.setProfileId(profile.getId());
                Intent myIntent = new Intent(getActivity(), MenuActivity.class);
                getActivity().startActivity(myIntent);
                // stringBuilder.append("Logged In " + profile.getFirstName());
                //  Toast.makeText(getActivity(), "Inicio facebook" + profile.getFirstName(), Toast.LENGTH_SHORT).show();
                //image.setProfileId(profile.getId());

            } else {

            }

    }

}