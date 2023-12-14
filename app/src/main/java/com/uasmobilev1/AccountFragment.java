package com.uasmobilev1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    Button logoutButton;
    TextView username, displayname;
    FirebaseUser user;

    public AccountFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        logoutButton = view.findViewById(R.id.logoutButton);
        username = view.findViewById(R.id.username);
        displayname = view.findViewById(R.id.displayname);
        ShapeableImageView profilePicture = view.findViewById(R.id.profilepicture);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            username.setText("NULL");
        } else {
            String displayName = user.getDisplayName();
            String email = user.getEmail();

            // Set the display name to the TextView
            if (displayName != null && !displayName.isEmpty()) {
                displayname.setText(displayName);
                username.setText(email);
            } else {
                // If the display name is not set, use the email as a fallback
                displayname.setText(email);
                username.setVisibility(View.INVISIBLE);
            }

            // Load the user's avatar into the ShapeableImageView
            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null) {
                // You may want to use a library like Glide or Picasso to load the image efficiently
                // Example using Glide:
                Glide.with(requireContext())
                        .load(photoUrl)
                        .into(profilePicture);
            } else {
                // If the user doesn't have a photo, you can set a default image
                profilePicture.setImageResource(R.drawable.default_avatar);
            }
        }

        // You need to load the user's avatar image into the ShapeableImageView here
        // Example: profilePicture.setImageResource(R.drawable.user_avatar);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                signOut();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
                Toast.makeText(requireContext(), "Anda Logout dari Aplikasi", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void signOut() {
        // Firebase sign-out
        FirebaseAuth.getInstance().signOut();

        // Google sign-out
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Handle sign-out result
                if (task.isSuccessful()) {
                    // Revoking access
                    mGoogleSignInClient.revokeAccess().addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Handle the result of revokeAccess
                        }
                    });
                }
            }
        });
    }


}