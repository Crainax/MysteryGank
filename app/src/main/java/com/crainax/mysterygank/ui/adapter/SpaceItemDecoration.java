package com.crainax.mysterygank.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.ui.adapter <br/>
 * Description: <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/6/11 <br/>
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceInMargin;

    public SpaceItemDecoration(int spaceInMargin) {
        this.spaceInMargin = spaceInMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = spaceInMargin;

    }
}
