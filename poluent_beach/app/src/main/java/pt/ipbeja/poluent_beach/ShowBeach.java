package pt.ipbeja.poluent_beach;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.poluent_beach.data.Report;

/**
 *
 * RecycleView to Show Reports from Firebase
 *
 * @author Tiago Azevedo 17427
 * @author Bruno Guerra 16247
 *
 * IPBEJA - PDM 29/01/2020
 */
public class ShowBeach extends AppCompatActivity {

    private ReportAdapter adapter;
    private Button backButton;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_beach);

        RecyclerView list = findViewById(R.id.report_list);
        backButton = findViewById(R.id.button_back_list);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        this.adapter = new ReportAdapter();

        list.setLayoutManager(lm);
        list.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reports")
                .addSnapshotListener(this, (value, error) -> {
                    if (error == null) {
                        List<Report> reports = value.toObjects(Report.class);
                        adapter.setData(reports);
                    }
                });
        refreshInfo();
    }

    /**
     * Refresh Infomation from Firestore
     */
    private void refreshInfo()
    {
        this.list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reports")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            this.list.add(document.getId());
                        }
                    }
                });
    }

    /**
     * ReportAdapter gets Information from Firestore to the RecyclerView
     */
    public class ReportAdapter extends RecyclerView.Adapter<ReportViewHolder> {

        private List<Report> data = new ArrayList<>();

        public void setData(List<Report> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_beach_item, parent, false);
            return new ReportViewHolder(view);
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
        private final ImageView image;
        private final TextView data;
        private Report report;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.report_name);
            description = itemView.findViewById(R.id.report_description);
            gps = itemView.findViewById(R.id.report_gps);
            image = itemView.findViewById(R.id.beach_image);
            data = itemView.findViewById(R.id.report_date);

            itemView.setOnClickListener(v -> {
                String stringId = returnID(getAdapterPosition());
                Intent position = new Intent(v.getContext(), BeachHistory.class);
                position.putExtra("name", report.getName());
                position.putExtra("description", report.getDescription());
                position.putExtra("gps", report.getGps());
                position.putExtra("photo", report.getFileLink());
                position.putExtra("id", stringId);
                position.putExtra("data", report.getCurrentData());
                v.getContext().startActivity(position);
            });
        }

        public void bind(Report report) {
            this.report = report;
            name.setText(report.getName());
            description.setText(report.getDescription());
            gps.setText(report.getGps());
            Glide.with(getApplicationContext()).load(report.getFileLink()).into(image);
            data.setText(report.getCurrentData());
        }

    }

    private String returnID(int adapterPosition) {
        return this.list.get(adapterPosition);
    }
}
