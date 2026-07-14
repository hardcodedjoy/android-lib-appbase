/*

MIT License

Copyright © 2026 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.ImageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.PopupMenuUpDownDelete;

import java.util.ArrayList;

@SuppressLint("ViewConstructor")
abstract public class CvListEditor<T> extends CvTSLL {

    private final ArrayList<T> list;
    private final LinearLayout llList;

    abstract public void onEditItemAt(int index);

    public CvListEditor(int iconResId, String title, ArrayList<T> list) {
        this.list = list;
        setTitleIcon(iconResId);
        setTitle(title);
        LinearLayout ll = findViewById(com.hardcodedjoy.appbase.R.id.appbase_ll_content);
        inflate(getActivity(), R.layout.appbase_cv_list_editor, ll);
        llList = findViewById(R.id.ll_list);
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view -> onAddNewItem());
        uiRefreshList();
    }

    private void uiRefreshList() {

        llList.removeAllViews();

        Button button;
        ImageButton btnRowMenu;
        LinearLayout ll;
        LayoutParams params;
        int margin = DisplayUnit.dpToPx(1.25f);
        int padding = DisplayUnit.dpToPx(3.0f);
        int textColor = ThemeUtil.getColor(getActivity(), android.R.attr.textColor);

        int n = list.size();

        for(int i=0; i<n; i++) {

            final int index = i;
            T item = list.get(i);
            String buttonText = item.toString();
            button = new Button(getActivity());
            button.setText(buttonText);
            button.setAllCaps(false);
            params = new LayoutParams(
                    DisplayUnit.dpToPx(220f),
                    LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            button.setLayoutParams(params);

            button.setOnClickListener(view -> onEditItemAt(index));

            // add row menu button:
            btnRowMenu = new ImageButton(getActivity());
            params = new LayoutParams(
                    DisplayUnit.dpToPx(40f),
                    LayoutParams.MATCH_PARENT);
            params.setMargins(margin, margin, margin, margin);
            btnRowMenu.setLayoutParams(params);
            btnRowMenu.setImageResource(com.hardcodedjoy.appbase.R.drawable.ic_menu_1);
            btnRowMenu.setBackgroundResource(com.hardcodedjoy.appbase.R.drawable.btn_3);
            btnRowMenu.setPadding(padding, padding, padding, padding);
            ImageUtil.setTint(btnRowMenu, textColor);

            ll = new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            String menuTitle = item.toString();
            PopupMenuUpDownDelete<T> mmudd = new PopupMenuUpDownDelete<T>(
                    menuTitle, list, item, this::uiRefreshList);
            mmudd.enableDismissByOutsideClick();
            btnRowMenu.setOnClickListener(view -> mmudd.show());
            ll.addView(btnRowMenu);
            ll.addView(button);
            llList.addView(ll);
        }
    }

    private void onAddNewItem() { onEditItemAt(-1); }

    public void insertNewItem(T item, int indexToReplace) {
        if(indexToReplace == -1) {
            // don't replace, just add:
            list.add(item);
        } else {
            list.remove(indexToReplace);
            list.add(indexToReplace, item);
        }
        uiRefreshList();
    }
}