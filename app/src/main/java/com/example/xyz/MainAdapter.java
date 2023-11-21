package com.example.xyz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModelo, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModelo> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModelo model) {
        holder.nombre.setText(model.getNombre());
        holder.categoria.setText(model.getCategoria());
        holder.precio.setText("$"+model.getPrecio());
        holder.stock.setText("Stock: "+model.getStock());

        Glide.with(holder.img.getContext())
                .load(model.getImgUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.editar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.actualizar_articulos))
                        .setExpanded(true, 1800)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText nombre = view.findViewById(R.id.nombreAC);
                EditText categoria = view.findViewById(R.id.categoriaAC);
                EditText precio = view.findViewById(R.id.precioAC);
                EditText stock = view.findViewById(R.id.stockAC);
                EditText imgUrl = view.findViewById(R.id.imgUrlAC);

                Button actualizar = view.findViewById(R.id.btnActualizar);

                nombre.setText(model.getNombre());
                categoria.setText(model.getCategoria());
                precio.setText(model.getPrecio());
                stock.setText(model.getStock());
                imgUrl.setText(model.getImgUrl());

                dialogPlus.show();

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Nombre", nombre.getText().toString());
                        map.put("Categoria", categoria.getText().toString());
                        map.put("Precio", precio.getText().toString());
                        map.put("Stock", stock.getText().toString());
                        map.put("ImgUrl", imgUrl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("AlmacenamientoXYZ")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(),"Actualizacion Correcta", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nombre.getContext(), "Error en la Actualizacion", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("¿Estás seguro de eliminar?");
                builder.setMessage("Eliminado");

                builder.setPositiveButton("Eliminado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("AlmacenamientoXYZ")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nombre.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_articulos, parent, false );
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView nombre, categoria, precio, stock;
        Button editar, eliminar;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            nombre = itemView.findViewById(R.id.txtNombreArticulo);
            categoria = itemView.findViewById(R.id.txtCategoriaArticulo);
            precio = itemView.findViewById(R.id.txtPrecioArticulo);
            stock = itemView.findViewById(R.id.txtStockArticulo);

            editar = itemView.findViewById(R.id.btnEditar);
            eliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
