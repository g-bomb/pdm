package pt.ipbeja.poluent_beach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.poluent_beach.data.Report;
import pt.ipbeja.poluent_beach.data.database.ReportDatabase;

public class ShowBeachRoom extends AppCompatActivity {

    private ShowBeachRoom.ReportAdapter adapter;
    private Button backButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_beach);

        this.recyclerView = findViewById(R.id.report_list);
        backButton = findViewById(R.id.button_back_list);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        this.adapter = new ShowBeachRoom.ReportAdapter();
        this.recyclerView.setLayoutManager(lm);
        this.recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    /**
     * Refresh Data from room
     */
    private void refreshData() {
        List<Report> reports = ReportDatabase.getInstance(getApplicationContext())
                .reportDao()
                .getAll();
        adapter.setData(reports);
    }

    /**
     * ReportAdapter gets Information from room to the RecyclerView
     */
    public class ReportAdapter extends RecyclerView.Adapter<ShowBeachRoom.ReportViewHolder> {

        private List<Report> data = new ArrayList<>();

        public void setData(List<Report> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ShowBeachRoom.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_beach_item, parent, false);
            return new ShowBeachRoom.ReportViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
            Report report = data.get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    /**
     * Permits Interaction with all the items in the RecycleView
     */
    public class ReportViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView description;
        private final TextView gps;
        private final ImageView praia;
        private Report report;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.report_name);
            description = itemView.findViewById(R.id.report_description);
            gps = itemView.findViewById(R.id.report_gps);
            praia = itemView.findViewById(R.id.beach_image);

            itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(ShowBeachRoom.this)
                        .setTitle(report.getName())
                        .setMessage(R.string.dialog_box_text2)
                        .setPositiveButton(R.string.dialog_box_text1, (dialog, which) -> {
                            ReportDatabase.getInstance(getApplicationContext())
                                    .reportDao()
                                    .delete(report);
                            refreshData();
                        })
                        .show();
                return true;
            });
        }

        public void bind(Report report) {
            this.report = report;
            name.setText(report.getName());
            description.setText(report.getDescription());
            gps.setText(report.getGps());
            Glide.with(getApplicationContext()).load(report.getFileLink()).into(praia);

        }
    }
}
