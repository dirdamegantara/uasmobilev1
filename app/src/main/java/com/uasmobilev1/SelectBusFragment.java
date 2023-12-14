package com.uasmobilev1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class SelectBusFragment extends Fragment {

    private ImageView[][] imageViews = new ImageView[4][9];
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewRoute;
    private TextView textViewBusInfo;
    private TextView textViewPrice;
    private TextView textViewSeatInfo;
    Button btnbooking;
    ImageView btnBack;

    private String routeid;
    private String date;
    private String time;
    private String route;
    private String busInfo;
    private String price;
    private String seatInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_bus, container, false);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                String imageViewId = String.format("%c%d", 'a' + i, j + 1);
                int imageViewResourceId = getResources().getIdentifier(imageViewId, "id", requireActivity().getPackageName());
                imageViews[i][j] = view.findViewById(imageViewResourceId);

                // Set the initial tag based on the drawable resource ID
                int drawableResourceId = getDrawableResourceId(imageViews[i][j]);
                if (drawableResourceId == R.drawable.available_img) {
                    imageViews[i][j].setTag(R.drawable.available_img);
                } else if (drawableResourceId == R.drawable.booked_img) {
                    imageViews[i][j].setTag(R.drawable.booked_img);
                } else if (drawableResourceId == R.drawable.your_seat_img) {
                    imageViews[i][j].setTag(R.drawable.your_seat_img);
                }

                setupImageViewClickListener(i, j);
            }
        }

        // Find your views
        btnbooking = view.findViewById(R.id.buttonBooking);
        btnBack = view.findViewById(R.id.backarrow);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewTime = view.findViewById(R.id.textViewTime);
        textViewRoute = view.findViewById(R.id.textViewRoute);
        textViewBusInfo = view.findViewById(R.id.textViewBusInfo);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        textViewSeatInfo = view.findViewById(R.id.textViewSeatInfo);

        // Get data from arguments
        Bundle args = getArguments();
        if (args != null) {
            routeid = args.getString("routeid", "");
            date = args.getString("date", "");
            time = args.getString("time", "");
            route = args.getString("route", "");
            busInfo = args.getString("busInfo", "");
            price = args.getString("price", "");

            // Set data to views
            updateViews(date, time, route, busInfo, price, seatInfo);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }

    private void updateViews(String date, String time, String route, String busInfo, String price, String seatInfo) {
        textViewDate.setText(date);
        textViewTime.setText(time);
        textViewRoute.setText(route);
        textViewBusInfo.setText(busInfo);
        textViewPrice.setText(price);
        textViewSeatInfo.setText(seatInfo);
    }

    private void setupImageViewClickListener(final int row, final int column) {
        final ImageView imageView = imageViews[row][column];

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(row, column);
                date = textViewDate.getText().toString();
                time = textViewTime.getText().toString();
                route = textViewRoute.getText().toString();
                busInfo = textViewBusInfo.getText().toString();
                price = textViewPrice.getText().toString();
                seatInfo = textViewSeatInfo.getText().toString();

            }
        });
    }

    private void handleImageViewClick(int row, int column) {
        ImageView clickedImageView = imageViews[row][column];
        int clickedSeatTag = (Integer) clickedImageView.getTag();

        // Handle seat states more efficiently using tags
        if (clickedSeatTag == R.drawable.available_img) {
            // Selecting a new seat, remove previous "your seat"
            removePreviousYourSeat();
            clickedImageView.setImageResource(R.drawable.your_seat_img);
            clickedImageView.setTag(R.drawable.your_seat_img);
            btnbooking.setVisibility(View.VISIBLE);

            // Get seat information
            seatInfo = String.format("%c%d", 'A' + row, column + 1);

            // Update the views immediately
            updateViews(date, time, route, busInfo, price, seatInfo);
        } else if (clickedSeatTag == R.drawable.booked_img) {
            Toast.makeText(requireContext(), "Kursi terisi", Toast.LENGTH_SHORT).show();
            // Do nothing for booked seats
            // Optionally, you can show a message or perform some action to inform the user that the seat is already booked.
        } else if (clickedSeatTag == R.drawable.your_seat_img) {
            // Deselecting the current "your seat"
            clickedImageView.setImageResource(R.drawable.available_img);
            clickedImageView.setTag(R.drawable.available_img);
            btnbooking.setVisibility(View.GONE);

            // Clear seat information
            seatInfo = "";

            // Update the views immediately
            updateViews(date, time, route, busInfo, price, seatInfo);
        }

        btnbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new PaymentFragment instance
                PaymentFragment paymentFragment = new PaymentFragment();

                // Pass ticket data as arguments
                Bundle args = new Bundle();
                args.putString("routeid", routeid);
                args.putString("date", date);
                args.putString("time", time);
                args.putString("route", route);
                args.putString("busInfo", busInfo);
                args.putString("price", price);
                args.putString("seatInfo", seatInfo);
                paymentFragment.setArguments(args);

                // Open the PaymentFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, paymentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }




    // Helper method to find the ImageView by its ID
    private ImageView findImageViewById(String imageviewId) {
        int imageviewResourceId = getResources().getIdentifier(imageviewId, "id", requireActivity().getPackageName());
        return getView().findViewById(imageviewResourceId);
    }

    private int getDrawableResourceId(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();

            // Check against your actual drawable resource IDs
            if (bitmap.sameAs(((BitmapDrawable) getResources().getDrawable(R.drawable.available_img)).getBitmap())) {
                return R.drawable.available_img;
            } else if (bitmap.sameAs(((BitmapDrawable) getResources().getDrawable(R.drawable.booked_img)).getBitmap())) {
                return R.drawable.booked_img;
            } else if (bitmap.sameAs(((BitmapDrawable) getResources().getDrawable(R.drawable.your_seat_img)).getBitmap())) {
                return R.drawable.your_seat_img;
            } else {
                return -1; // Handle other cases if needed
            }
        } else {
            return -1; // Handle other types of drawables if needed
        }
    }


    private void removePreviousYourSeat() {
        for (ImageView[] row : imageViews) {
            for (ImageView imageView : row) {
                if ((Integer) imageView.getTag() == R.drawable.your_seat_img) {
                    imageView.setImageResource(R.drawable.available_img);
                    imageView.setTag(R.drawable.available_img);
                }
            }
        }
    }


}
