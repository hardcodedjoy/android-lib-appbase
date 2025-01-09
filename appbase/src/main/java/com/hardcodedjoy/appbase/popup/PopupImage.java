/*

MIT License

Copyright © 2025 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

package com.hardcodedjoy.appbase.popup;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupImage extends Popup {

    @Override
    public void onCancel() { }

    public PopupImage(String title, Bitmap image, String description) {

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_image, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        ImageView ivImage = findViewById(R.id.appbase_iv_image);
        TextView tvImageDescription = findViewById(R.id.appbase_tv_image_description);
        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);

        if(title != null) tvTitle.setText(title);
        if(image != null) ivImage.setImageBitmap(image);
        if(description != null) tvImageDescription.setText(description);
        btnCancel.setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss
        onCancel();
    }

    public PopupImage(Bitmap image, String description) { this(null, image, description); }

    public PopupImage(int titleStringId, Bitmap image, int descriptionStringId) {
        this(ContentView.getString(titleStringId), image,
                ContentView.getString(descriptionStringId));
    }

    public PopupImage(Bitmap image, int descriptionStringId) {
        this(null, image, ContentView.getString(descriptionStringId));
    }
}
