package com.example.techquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.techquiz.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Wallet extends Fragment {

    User user;
public  Wallet(){
    //required empty contructor
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWalletBinding binding;
  FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentWalletBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();
        database.collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              user =documentSnapshot.toObject(User.class);
                binding.totalcoins.setText(String.valueOf(user.getCoins()));
            }
        });
        binding.paypalrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCoins()>50000)
                { String paypal=binding.paypal.getText().toString();
                    String uid=FirebaseAuth.getInstance().getUid();
                    WithdrawRequest request=new WithdrawRequest(paypal, user.getName(),uid );
                 database.collection("withdraw").document(uid)
                 .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(getContext(), "request sent successfuly.", Toast.LENGTH_SHORT).show();
                     }
                 });
                }else
                {
                    Toast.makeText(getContext(), "You need more coins to withdraw money.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }
}