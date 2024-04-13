package com.example.myapplication.ui.theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.ImageEntity;

import java.util.List;


public class ImageAdapter extends ArrayAdapter<ImageEntity> {

    private final int resourceId;

    public ImageAdapter(Context context, int textViewResourceId,
                        List<ImageEntity> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageEntity imageEntity = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageImage = view.findViewById(R.id.image_img);
            viewHolder.imageName = view.findViewById(R.id.image_name);
            viewHolder.imageLocation = view.findViewById(R.id.image_location);
            view.setTag(viewHolder);  //将ViewHolder储存在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();  //重新获取ViewHolder
        }
        viewHolder.imageImage.setImageBitmap(imageEntity.getImage());
        viewHolder.imageName.setText(imageEntity.getName());
        viewHolder.imageLocation.setText(imageEntity.getLocation());
        return view;
    }

    class ViewHolder {
        ImageView imageImage;
        TextView imageName, imageLocation;
    }

}
