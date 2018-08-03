package com.shop.ningbaoqi.performanceoptimization;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 实现布局的Fragment
 */
public class OverDrawFragment extends Fragment {
    MultiCardsView multicardsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_overdraw, container, false);
        multicardsView = view.findViewById(R.id.cardView);
        multicardsView.enableOverdrawOpt(true);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int cardWidth = width / 3;
        int cardHeight = height / 3;
        int yOffset = 40;
        int xOffset = 40;
        for (int i = 0; i < cardResId.length(); i++) {
            SingleCard card = new SingleCard(new RectF(xOffset, yOffset, xOffset + cardWidth, yOffset + cardHeight));
            Bitmap bitmap = loadImageResource(cardResId[i], cardWidth, cardHeight);
            card.setBitmap(bitmap);
            multicardsView.addCards(card);
            xOffset += cardWidth / 3;
        }
        Button overdraw = view.findViewById(R.id.btn);
        overdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multicardsView.enableOverdrawOpt(false);
            }
        });
        Button perfectDraw = view.findViewById(R.id.prefecr);
        perfectDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multicardsView.enableOverdrawOpt(true);
            }
        });
        return view;
    }
}
