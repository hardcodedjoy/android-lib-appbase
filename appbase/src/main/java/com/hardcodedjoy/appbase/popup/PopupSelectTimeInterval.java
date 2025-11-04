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

import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class PopupSelectTimeInterval extends Popup {

    private final int PROGRESS_MAX = 1000;

    private final TextView tvIntervalStart;
    private final TextView tvIntervalEnd;
    private final SeekBar sbIntervalStart;
    private final SeekBar sbIntervalEnd;

    private final long totalDurationMicros;


    abstract public void onOK(long startMicros, long endMicros);

    @Override
    public void onCancel() { }

    public PopupSelectTimeInterval(String title, long totalDurationMicros) {

        this.totalDurationMicros = totalDurationMicros;

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_sel_time_interval, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        tvIntervalStart = findViewById(R.id.appbase_tv_interval_start);
        tvIntervalEnd = findViewById(R.id.appbase_tv_interval_end);
        sbIntervalStart = findViewById(R.id.appbase_sb_interval_start);
        sbIntervalEnd = findViewById(R.id.appbase_sb_interval_end);

        if(title != null) { tvTitle.setText(title); }

        sbIntervalStart.setMax(PROGRESS_MAX);
        sbIntervalStart.setProgress(0);

        sbIntervalEnd.setMax(PROGRESS_MAX);
        sbIntervalEnd.setProgress(PROGRESS_MAX);

        tvUpdate(tvIntervalStart, 0, PROGRESS_MAX, totalDurationMicros);
        tvUpdate(tvIntervalEnd, PROGRESS_MAX, PROGRESS_MAX, totalDurationMicros);

        sbIntervalStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUpdate(tvIntervalStart, progress, PROGRESS_MAX, PopupSelectTimeInterval.this.totalDurationMicros);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbIntervalEnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUpdate(tvIntervalEnd, progress, PROGRESS_MAX, PopupSelectTimeInterval.this.totalDurationMicros);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ImageButton btnOk = findViewById(R.id.appbase_btn_ok);
        ImageButton btnCancel = findViewById(R.id.appbase_btn_cancel);
        btnOk.setOnClickListener(ocl);
        btnCancel.setOnClickListener(ocl);
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this); // dismiss
        int id = view.getId();
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) {
            float start = ((float)sbIntervalStart.getProgress()) / PROGRESS_MAX;
            float end = ((float)sbIntervalEnd.getProgress()) / PROGRESS_MAX;
            onOK((long)(totalDurationMicros*start + 0.5f), (long)(totalDurationMicros*end + 0.5f) );
        } else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) {
            onCancel();
        }
    }

    private void tvUpdate(final TextView textView, final int progress, int max, long totalDurationMicros) {
        float micros = totalDurationMicros;
        micros *= progress;
        micros /= max;
        long millis = (long)(micros / 1000);
        final String s = millisToHHMMSSmmm(millis);
        ContentView.runOnUiThread(() -> textView.setText(s));
    }

    static public String millisToHHMMSSmmm(long n) {

        int millis = (int)(n%1000);
        n = n/1000;
        int ss = (int)(n%60);
        n = n/60;
        int mm = (int)(n%60);
        n = n/60;
        int hh = (int)(n);

        String st = hh + ":";
        if(mm < 10) { st += "0"; }
        st += mm + ":";
        if(ss < 10) { st += "0"; }
        st += ss + ".";
        if(millis < 100) { st += "0"; }
        if(millis < 10) { st += "0"; }
        st += millis;

        return st;
    }
}