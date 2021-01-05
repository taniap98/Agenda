package ro.ase.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter<Profile> {
    private Context context;
    private int resource;
    private List<Profile> profileList;
    private LayoutInflater layoutInflater;

    public ProfileAdapter(@NonNull Context context, int resource, List<Profile> vehicleList, LayoutInflater layoutInflater) {
        super(context, resource, vehicleList);

        this.context = context;
        this.resource = resource;
        this.profileList = vehicleList;
        this.layoutInflater = layoutInflater;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        Profile profile = profileList.get(position);

        if(profile != null) {
            TextView tv1 = view.findViewById(R.id.lastName);
            tv1.setText(profile.getLastName());

            TextView tv2 = view.findViewById(R.id.firstName);
            tv2.setText(profile.getFirstName());

            TextView tv3 = view.findViewById(R.id.phone);
            tv3.setText(profile.getPhone());

            TextView tv4 = view.findViewById(R.id.email);
            tv4.setText(profile.getEmail());

            TextView tv5 = view.findViewById(R.id.category);
            tv5.setText(profile.getCategory().toString());

            TextView tv6 = view.findViewById(R.id.date);
            tv6.setText(profile.getDate());
        }

        return view;
    }
}
