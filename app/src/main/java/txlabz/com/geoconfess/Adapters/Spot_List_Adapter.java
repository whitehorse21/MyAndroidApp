package txlabz.com.geoconfess.Adapters;



import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arslan on 1/15/2016.
 */

import txlabz.com.geoconfess.R;

public class Spot_List_Adapter extends RecyclerView.Adapter<Spot_List_Adapter.ViewHolder> {

    private ArrayList<HashMap<String,String>> mItems;
    ArrayList<ArrayList<HashMap<String,String>>>actorlistmain;
    Context context;

    public Spot_List_Adapter(ArrayList<HashMap<String,String>> items) {
        mItems = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v;
        if (i == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spot_list_item, viewGroup, false);
            context=viewGroup.getContext();
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spot_list_item, viewGroup, false);
            context=viewGroup.getContext();

        }


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {


        if(i%2==0) {

            viewHolder.mainlayout.setBackgroundColor(context.getResources().getColor(R.color.whitegrey));
            viewHolder.edit.setImageResource(R.drawable.pencilgray);
            viewHolder.delet.setImageResource(R.drawable.bingray);
            viewHolder.spotname.setTextColor(context.getResources().getColor(R.color.colorGreyText));
            viewHolder.spottime.setTextColor(context.getResources().getColor(R.color.colorGreyText));

        }
        else
        {
            viewHolder.mainlayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            viewHolder.edit.setImageResource(R.drawable.pencilwhite);
            viewHolder.delet.setImageResource(R.drawable.binwhite);
            viewHolder.spotname.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.spottime.setTextColor(context.getResources().getColor(R.color.white));

        }


//        viewHolder.mainlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(context, PdfViewer.class);
////                intent.putExtra("pdf", mItems.get(i).get("pdf"));
////                context.startActivity(intent);
////
//////                Intent intent = new Intent(Intent.ACTION_VIEW);
//////
//////                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + mItems.get(i).get("pdf")), "text/html");
//////
//////                context.startActivity(intent);
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(mItems.get(i).get("pdf")), "application/pdf");
//                try{
//                    view.getContext().startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    //user does not have a pdf viewer installed
//                }
//
//
//            }
//        });
////
////
//        Ion.with(context)
//                .load(mItems.get(i).get("image"))
//                .withBitmap()
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.loading)
//                .intoImageView(viewHolder.imageView2);
//
//        viewHolder.textView2.setText(mItems.get(i).get("title"));
//        viewHolder.textView3.setText(mItems.get(i).get("author"));

    }

    @Override
    public int getItemViewType(int position) {


        return 0;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainlayout;
        private ImageView edit,delet;
        TextView spotname,spottime;
        ViewHolder(View v) {
            super(v);

            mainlayout=(RelativeLayout)v.findViewById(R.id.mainlayout);
            edit=(ImageView)v.findViewById(R.id.edit);
            delet=(ImageView)v.findViewById(R.id.delet);
            spotname=(TextView)v.findViewById(R.id.spotname);
            spottime=(TextView)v.findViewById(R.id.spottime);
        }
    }

}
