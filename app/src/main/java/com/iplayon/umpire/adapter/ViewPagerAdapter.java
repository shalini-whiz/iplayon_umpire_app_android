package com.iplayon.umpire.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplayon.umpire.R;


/**
 * Created by shalinibr on 9/8/17.
 */


public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private Integer[] images = {
               /* R.drawable.landingscreen_1, R.drawable.landingscreen_2, R.drawable.landingscreen_3,
                R.drawable.landingscreen_4, R.drawable.landingscreen_5, R.drawable.landingscreen_6,
                R.drawable.landingscreen_7, R.drawable.landingscreen_8, R.drawable.landingscreen_9
                */
        };



        public ViewPagerAdapter(Context context) {
                this.context = context;
        }

        @Override
        public int getCount() {
                return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

                return view == object;
        }


        static class ViewHolder {
                ImageView imageView;

        }

        public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inSampleSize = 1;
                options.inScaled = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return BitmapFactory.decodeResource(resources, resImage, options);
        }



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
                try {
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                        View layout = (View) layoutInflater.inflate(R.layout.pager_layout, null);
                        ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
                        ///imageView.setImageBitmap(getBitmapFromResources(context.getResources(),images[position]));


                        /*System.out.println("position .. "+position+
                        " ..density.. "+context.getResources().getDisplayMetrics().density);
                        if(position == 0){
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_1);
                               imageView.setImageBitmap(bitmap);
                        }
                        else if(position == 1)
                        {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_2);
                                imageView.setImageBitmap(bitmap);
                        }else if(position == 2)
                        {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_3);
                                imageView.setImageBitmap(bitmap);
                        }else if(position == 3)
                        {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_4);
                               imageView.setImageBitmap(bitmap);
                        }
                        else if(position == 4)
                        {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_5);
                                imageView.setImageBitmap(bitmap);
                        }
                        else if(position == 5) {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_6);
                                imageView.setImageBitmap(bitmap);

                        }else if(position == 6) {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_7);
                                imageView.setImageBitmap(bitmap);

                        } else if(position == 7) {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_8);
                               imageView.setImageBitmap(bitmap);


                        } else if(position == 8) {
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_9);
                                imageView.setImageBitmap(bitmap);

                        }else{
                                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.landingscreen_1);
                                imageView.setImageBitmap(bitmap);
                        }*/



                        ViewPager vp = (ViewPager) container;
                        vp.addView(layout,0);
                        return layout;


                } catch (Exception e) {
                        return null;
                }


        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView((View) object);

        }






}
