package ro.ase.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<AgendaDetails> {
    private Context context;
    private int resource;
    private List<AgendaDetails> agendaDetails;
    private LayoutInflater layoutInflater;


    public DetailsAdapter(@NonNull Context context, int resource, List<AgendaDetails> agendaDetails, LayoutInflater layoutInflater) {
        super(context, resource, agendaDetails);

        this.context = context;
        this.resource = resource;
        this.agendaDetails = agendaDetails;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        final AgendaDetails detail = agendaDetails.get(position);

        if(detail != null) {
            final CheckedTextView ctv = view.findViewById(R.id.ctvId);
            ctv.setText(Integer.toString(detail.getId()));
            ctv.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(ctv.isChecked()){
                        ctv.setChecked(false);
                        if(CallDetails.checkedMessages.contains(detail.getId()))
                            CallDetails.checkedMessages.remove((Integer)detail.getId());
                    } else {
                        ctv.setChecked(true);
                        CallDetails.checkedMessages.add(detail.getId());

                    }
                }
            });

            TextView tv2 = view.findViewById(R.id.tvMessage);
            tv2.setText(detail.getMessage());

            TextView tv3 = view.findViewById(R.id.tvDate);
            tv3.setText(detail.getLastMessage().toString());
        }

        return view;
    }
}
