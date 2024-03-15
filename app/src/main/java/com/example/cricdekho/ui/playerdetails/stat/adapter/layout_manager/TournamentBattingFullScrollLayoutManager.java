package com.example.cricdekho.ui.playerdetails.stat.adapter.layout_manager;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TournamentBattingFullScrollLayoutManager extends LinearLayoutManager {
    private int offset;
    private int maxOffset;

    public ScrollListenerTournamentBatting scrollListener;

    public TournamentBattingFullScrollLayoutManager(Context context) {
        super(context);
    }

    public TournamentBattingFullScrollLayoutManager(Context context, ScrollListenerTournamentBatting scrollListener) {
        super(context);
        this.scrollListener = scrollListener;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        int n = getChildCount();
        offset = 0;
        maxOffset = 0;
        int ownWidth = getWidth();
        for(int i=0; i<n; ++i) {
            View view = getChildAt(i);
            int x = view.getRight();
            if(x>ownWidth) maxOffset = Math.max(maxOffset,x-ownWidth);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(dx<0) {
            if(-dx>offset) dx = -offset;
        }
        else
        if(dx>0) {
            if(dx+offset>maxOffset) dx = maxOffset-offset;
        }
        offsetChildrenHorizontal(-dx);
        offset += dx;
        scrollListener.scrollTournamentBattingBy(offset);
        return dx;
    }


    public interface ScrollListenerTournamentBatting {
        void scrollTournamentBattingBy(int dx);
    }

}