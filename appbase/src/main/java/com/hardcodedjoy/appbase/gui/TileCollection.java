/*

MIT License

Copyright © 2024 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.hardcodedjoy.appbase.gui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TileCollection {

    private LayoutInflater inflater;
    private boolean portraitNotLandscape;
    private float rowHeightDp;
    private LinearLayout llContainer; // where rows will be added
    private LinearLayout llRow;
    private int idRowPortrait;
    private int idRowLandscape;
    private int itemsPerRow;
    private int indexInRow;

    public void setInflater(LayoutInflater inflater) { this.inflater = inflater; }

    public void setPortraitNotLandscape(boolean portraitNotLandscape) {
        this.portraitNotLandscape = portraitNotLandscape;
    }

    public void setRowHeightDp(float dp) { this.rowHeightDp = dp; }

    public void setContainerLayout(LinearLayout llContainer) { this.llContainer = llContainer; }

    public void setRowLayoutId(int id) {
        this.idRowPortrait = id;
        this.idRowLandscape = id;
    }

    public void setRowLayoutPortraitId(int id) { this.idRowPortrait = id; }
    public void setRowLayoutLandscapeId(int id) { this.idRowLandscape = id; }

    public LinearLayout inflateNewRow() {
        LinearLayout llRow;
        if(portraitNotLandscape) {
            llRow = (LinearLayout) inflater.inflate(idRowPortrait, null);
        } else {
            llRow = (LinearLayout) inflater.inflate(idRowLandscape, null);
        }

        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DisplayUnit.dpToPx(rowHeightDp));
        llRow.setLayoutParams(params);

        this.itemsPerRow = llRow.getChildCount();

        return llRow;
    }

    public int itemCountPerRow() { return itemsPerRow; }

    public void addView(View view) {
        if(indexInRow == 0) { llRow = inflateNewRow(); }

        View oldView = llRow.getChildAt(indexInRow);
        view.setLayoutParams(oldView.getLayoutParams());
        llRow.removeView(oldView);
        llRow.addView(view, indexInRow);

        indexInRow++;
        if(indexInRow == itemCountPerRow()) { llContainer.addView(llRow); indexInRow = 0; }
    }

    public void closeLastIncompleteRow() {
        if(indexInRow > 0) {
            // we have an incomplete row

            // remove unused cells:
            for( ; indexInRow<itemCountPerRow(); indexInRow++) {
                ViewGroup tile = ((ViewGroup) llRow.getChildAt(indexInRow));
                tile.removeAllViews();
                tile.setBackgroundColor(0x00000000);
            }
            llContainer.addView(llRow);

            indexInRow = 0;
        }
    }
}