package com.shop.ningbaoqi.performanceoptimization;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 实现图片叠加的自定义View
 */
public class MultiCardsView extends View {
    private ArrayList<SingleCard> cards = new ArrayList<>(5);
    private boolean enableOverdrawOpt = true;

    public MultiCardsView(Context context) {
        this(context, null, 0);
    }

    public MultiCardsView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public MultiCardsView(Context context, AttributeSet attributes, int defStyleAttr) {
        super(context, attributes, defStyleAttr);
    }

    public void addCards(SingleCard card) {
        cards.add(card);
    }

    /**
     * 设置是否取消过度绘制
     *
     * @param enableOverdrawOpt
     */
    public void enableOverdrawOpt(boolean enableOverdrawOpt) {
        this.enableOverdrawOpt = enableOverdrawOpt;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cards == null || canvas == null) {
            return;
        }
        Rect clip = canvas.getClipBounds();
        if (enableOverdrawOpt) {//根据enableOverdrawOpt来调用不同的绘制方法，对比效果
            drawCardsWithotOverDraw(canvas, cards.size() - 1);
        } else {
            drawCardsNormal(canvas, cards.size() - 1);
        }
    }

    /**
     * 普通绘制
     *
     * @param canvas
     * @param index
     */
    private void drawCardsNormal(Canvas canvas, int index) {
        if (canvas == null || index < 0 || index >= cards.size()) {
            return;
        }
        SingleCard card = cards.get(index);
        if (card != null) {
            drawCardsNormal(canvas, index - 1);
            card.draw(canvas);
        }
    }

    /**
     * 实现没有过度绘制的方法
     *
     * @param canvas
     * @param index
     */
    private void drawCardsWithotOverDraw(Canvas canvas, int index) {
        if (canvas == null || index < 0 || index >= cards.size()) {
            return;
        }
        SingleCard card = cards.get(index);
        if (card != null && !canvas.quickReject(card.area, Canvas.EdgeType.BW)) {//判断是否没和某个卡片相交，从而跳过那些非矩形区域内的绘制操作
            int saveCount = canvas.save(Canvas.CLIP_SAVE_FLAG);
            if (canvas.clipRect(card.area, Region.Op.DIFFERENCE)) {//只绘制可见区域
                drawCardsWithotOverDraw(canvas, index - 1);
            }
            canvas.restoreToCount(saveCount);
            saveCount = canvas.save(Canvas.CLIP_SAVE_FLAG);
            if (canvas.clipRect(card.area)) {//只绘制可见区域
                Rect clip = canvas.getClipBounds();
                card.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        } else {
            drawCardsWithotOverDraw(canvas, index - 1);
        }
    }
}
