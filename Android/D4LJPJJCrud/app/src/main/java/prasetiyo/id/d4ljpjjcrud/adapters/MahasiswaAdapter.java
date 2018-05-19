package prasetiyo.id.d4ljpjjcrud.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.List;

import prasetiyo.id.d4ljpjjcrud.R;
import prasetiyo.id.d4ljpjjcrud.models.Mahasiswa;
import prasetiyo.id.d4ljpjjcrud.utils.RecyclerViewClickListener;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private List<Mahasiswa> mahasiswaList;
    private RecyclerViewClickListener itemListener;

    public MahasiswaAdapter(RecyclerViewClickListener ctx, List<Mahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
        this.itemListener = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mhs_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MahasiswaAdapter.ViewHolder holder, int position) {
        Mahasiswa student = mahasiswaList.get(position);
        holder.nama.setText(student.getNama());
        holder.alamat.setText(student.getAlamat());
        holder.gender.setText(student.getGender());
        holder.bind(mahasiswaList.get(position), itemListener);
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama, alamat, gender;
        private ViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.tv_name);
            alamat = view.findViewById(R.id.tv_alamat);
            gender = view.findViewById(R.id.tv_gender);
        }

        private void bind(final Mahasiswa item, final RecyclerViewClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClickListener(item);
                }
            });
        }


    }
}
