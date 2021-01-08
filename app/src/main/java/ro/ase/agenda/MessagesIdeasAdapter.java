package ro.ase.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MessagesIdeasAdapter extends ArrayAdapter<HolidayMessages> {
    private Context context;
    private int resource;
    private List<HolidayMessages> holMessages;
    private LayoutInflater layoutInflater;


    public MessagesIdeasAdapter(@NonNull Context context, int resource, List<HolidayMessages> holMessages, LayoutInflater layoutInflater) {
        super(context, resource, holMessages);

        this.context = context;
        this.resource = resource;
        this.holMessages = holMessages;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        final HolidayMessages mess = holMessages.get(position);

        if(mess != null) {
            TextView tv1 = view.findViewById(R.id.tvHoliday);
            tv1.setText(mess.getHoliday());

            TextView tv2 = view.findViewById(R.id.tvCategory);
            tv2.setText(mess.getCategory());

            final ListView lv = view.findViewById(R.id.lvMessages);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this.context, R.layout.lv_one_message, mess.getMessages());
            lv.setAdapter(arrayAdapter);

        }

        return view;
    }
}
