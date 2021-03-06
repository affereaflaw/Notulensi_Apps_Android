package com.example.affereaflaw.notulensi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {


    public EditFragment() {
        // Required empty public constructor
    }

    public static class notulensi extends RecyclerView.ViewHolder {
        static View viewNotulensi;

        public notulensi(View itemView) {
            super(itemView);
            viewNotulensi = itemView;
        }

        public void setJudul(String judul) {
            final TextView txtJudul = (TextView) viewNotulensi.findViewById(R.id.judul);
            txtJudul.setText(judul);
        }

        public void setTanggal(String tanggal) {
            final TextView txtTanggal = (TextView) viewNotulensi.findViewById(R.id.tanggal);
            txtTanggal.setText(tanggal);
        }

        public void setNotulen(String notulen) {
            final TextView txtNotulen = (TextView) viewNotulensi.findViewById(R.id.notulen);
            txtNotulen.setText(notulen);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit, container, false);

        SharedPreferences mPrefs = getActivity().getSharedPreferences("tag", 0);
        String kode = mPrefs.getString("label", "0");
        final RecyclerView rcmeView = (RecyclerView) view.findViewById(R.id.rcmeView);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notulensi");
        Query query = databaseReference.orderByChild("id").equalTo(kode);
        databaseReference.keepSynced(true);
        //rvPasien.setHasFixedSize(true);
        rcmeView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerAdapter<NotulensiListGetSet, notulensi> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NotulensiListGetSet, notulensi>(
                NotulensiListGetSet.class,
                R.layout.notulensi_row,
                notulensi.class,
                query
        ) {
            @Override
            protected void populateViewHolder(notulensi viewHolder, NotulensiListGetSet model, int position) {
                final String key = getRef(position).getKey();
                viewHolder.setJudul(model.getJudul());
                viewHolder.setTanggal(model.getTanggal());
                viewHolder.setNotulen(model.getNotulen());
                viewHolder.viewNotulensi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(),NotulensiEdit.class);
                        i.putExtra("kode",key);
                        startActivity(i);
                    }
                });
            }
        };
        rcmeView.setAdapter(firebaseRecyclerAdapter);
        return view;
    }

}
