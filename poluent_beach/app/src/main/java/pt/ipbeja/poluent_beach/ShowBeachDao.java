package pt.ipbeja.poluent_beach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.poluent_beach.data.Report;
import pt.ipbeja.poluent_beach.data.database.ReportDatabase;

public class ShowBeachDao extends AppCompatActivity {

    private ShowBeachDao.ReportAdapter adapter;
    private Button backButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_beach);

       this.recyclerView = findViewById(R.id.report_list);
        backButton = findViewById(R.id.button_back_list);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        this.adapter = new ShowBeachDao.ReportAdapter();
        this.recyclerView.setLayoutManager(lm);
        this.recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> startActivity(new Intent(ShowBeachDao.this, MainActivity.class)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    private void refreshData() {
        List<Report> reports = ReportDatabase.getInstance(getApplicationContext())
                .reportDao()
                .getAll();
        adapter.setData(reports);
    }

    public class ReportAdapter extends RecyclerView.Adapter<ShowBeachDao.ReportViewHolder> {

        private List<Report> data = new ArrayList<>();

        public void setData(List<Report> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ShowBeachDao.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_beach_item, parent, false);
            return new ShowBeachDao.ReportViewHolder(view);
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
                new AlertDialog.Builder(ShowBeachDao.this)
                        .setTitle("Apagar")
                        .setMessage("Pretende apagar " + report.getName() + "?")
                        .setPositiveButton("Apagar", (dialog, which) -> {
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
