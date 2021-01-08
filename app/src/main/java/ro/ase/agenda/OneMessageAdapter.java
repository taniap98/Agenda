package ro.ase.agenda;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OneMessageAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> messages;
    private LayoutInflater layoutInflater;


    public OneMessageAdapter(@NonNull Context context, int resource, List<String> messages, LayoutInflater layoutInflater) {
        super(context, resource, messages);

        this.context = context;
        this.resource = resource;
        this.messages = messages;
        this.layoutInflater = layoutInflater;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        final String mess = messages.get(position);

        if(mess != null) {
            final TextView tv1 = view.findViewById(R.id.oneMess);
            tv1.setText(mess);

            tv1.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("Message", tv1.getText());
                    clipboard.setPrimaryClip(data);

                    Toast.makeText(getContext(), "Copied", Toast.LENGTH_LONG).show();
                    return true;
                };
            });
        }

        return view;
    }
}
