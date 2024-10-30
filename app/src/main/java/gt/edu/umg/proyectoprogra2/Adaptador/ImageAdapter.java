package gt.edu.umg.proyectoprogra2.Adaptador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gt.edu.umg.proyectoprogra2.Modelo.ImageItem;
import gt.edu.umg.proyectoprogra2.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<ImageItem> imageList;
    private double latitude;
    private double longitude;

    public ImageAdapter(List<ImageItem> imageList, double latitude, double longitude) {
        this.imageList = imageList;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewLocation;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
        }
    }

    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageViewHolder holder, int position) {
        byte[] imageBytes = imageList.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageView.setImageBitmap(bitmap);

        String locationUrl = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
        holder.textViewLocation.setText(locationUrl);

        holder.textViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationUrl));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}