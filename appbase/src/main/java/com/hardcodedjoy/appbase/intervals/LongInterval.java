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

package com.hardcodedjoy.appbase.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongInterval {

    private long start;
    private long end;

    public LongInterval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(long start) { this.start = start; }
    public long getStart() { return start; }

    public void setEnd(long end) { this.end = end; }
    public long getEnd() { return end; }

    public long getDuration() { return end - start; }

    public boolean contains(long pos) {
        if(pos < start) { return false; }
        return pos < end;
    }

    public LongInterval intersection(LongInterval otherInterval) {
        long start = Math.max(this.start, otherInterval.start);
        long end = Math.min(this.end, otherInterval.end);
        if(end < start) { return null; }
        return new LongInterval(start, end);
    }

    public LongInterval[] split(long pos) {
        if(isBefore(pos)) { return new LongInterval[] { this }; }
        if(isAfter(pos)) { return new LongInterval[] { this }; }
        return new LongInterval[] {
                new LongInterval(start, pos),
                new LongInterval(pos, end)
        };
    }

    public LongInterval[] remove(LongInterval intervalToRemove) {
        if (intervalToRemove.getEnd() <= start || intervalToRemove.getStart() >= end) {
            // Case 1: No overlap
            return new LongInterval[] { this };
        }

        // Store the list of resulting intervals
        List<LongInterval> result = new ArrayList<>();

        if (intervalToRemove.getStart() > start) {
            // Case 2: Partial overlap on the end
            result.add(new LongInterval(start, intervalToRemove.getStart()));
        }

        if (intervalToRemove.getEnd() < end) {
            // Case 3: Partial overlap on the start
            result.add(new LongInterval(intervalToRemove.getEnd(), end));
        }

        // Return the results as an array
        return result.toArray(new LongInterval[0]);
    }

    public boolean isBefore(LongInterval referenceInterval) {
        return (end <= referenceInterval.getStart());
    }

    public boolean isBefore(long pos) { return end <= pos; }

    public boolean isAfter(LongInterval referenceInterval) {
        return (start >= referenceInterval.getEnd());
    }

    public boolean isAfter(long pos) { return start >= pos; }

    public void shift(long l) {
        start += l;
        end += l;
    }

    static public ArrayList<LongInterval> remove(LongInterval from,
                                                 LongInterval... toRemove) {
        ArrayList<LongInterval> result = new ArrayList<>();
        result.add(from); // Start with the original interval

        for(LongInterval sub : toRemove) {
            ArrayList<LongInterval> newResult = new ArrayList<>();
            for(LongInterval current : result) {
                // Subtract 'sub' from the 'current' interval
                LongInterval[] fragments = current.remove(sub);
                newResult.addAll(Arrays.asList(fragments));
            }
            result = newResult;
            if(result.isEmpty()) { break; } // nothing left to subtract from
        }
        return result;
    }

    static public ArrayList<LongInterval> remove(LongInterval from,
                                                 ArrayList<LongInterval> intervals) {
        return remove(from, intervals.toArray(new LongInterval[]{}));
    }
}