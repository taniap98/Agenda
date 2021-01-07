package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CardProfileFragment extends Fragment {
   private Profile profile;
   public CardProfileFragment(Profile prof){
       this.profile = prof;
   }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_profile, container, false);
        Button btn = (Button) view.findViewById(R.id.btnShowContact);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowContact.class);
                intent.putExtra("fullName", profile.getName());
                intent.putExtra("phone", profile.getPhone());
                intent.putExtra("email", profile.getEmail());
                intent.putExtra("birthday", profile.getDate().toString());
                startActivity(intent);
            }
        };
        btn.setOnClickListener(listener);
        return view;
    }

//    public void setText(String name, String category){
//
//        TextView textView = (TextView) mView.findViewById(R.id.tvName);
//        textView.setText(name);
//        TextView textView1 = (TextView) mView.findViewById(R.id.tvCategory);
//        textView1.setText(category);
//
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.tvName);
        textView.setText(profile.getName());
        TextView textView1 = (TextView) view.findViewById(R.id.tvCategory);
        textView1.setText(profile.getCategory().toString());

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}