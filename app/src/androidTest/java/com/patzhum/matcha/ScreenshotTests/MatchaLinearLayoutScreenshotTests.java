package com.patzhum.matcha.ScreenshotTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.View;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;
import com.patzhum.matcha.RenderResource;
import com.patzhum.matcha.render.core.RenderUtil;
import com.patzhum.matcha.render.layouts.MatchaLinearLayout;

import org.junit.Test;

/**
 * Created by patri on 2018-03-09.
 */

public class MatchaLinearLayoutScreenshotTests {
    @Test
    public void basicTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String s = RenderResource.getFileContents("MatchaLinearLayoutScreenshotTests#basicTest.json");

        View view = RenderUtil.Companion.renderView(context, MatchaLinearLayout.class, s);
        ViewHelpers.setupView(view).setExactWidthPx(1080).setExactHeightPx(1920).layout();
        Screenshot.snap(view)
                .record();
    }
    @Test
    public void horizontalTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String s = RenderResource.getFileContents("MatchaLinearLayoutScreenshotTests#horizontalTest.json");

        View view = RenderUtil.Companion.renderView(context, MatchaLinearLayout.class, s);
        ViewHelpers.setupView(view).setExactWidthPx(1080).setExactHeightPx(1920).layout();
        Screenshot.snap(view)
                .record();
    }
    @Test
    public void complexTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String s = RenderResource.getFileContents("MatchaLinearLayoutScreenshotTests#complexTest.json");

        View view = RenderUtil.Companion.renderView(context, MatchaLinearLayout.class, s);
        ViewHelpers.setupView(view).setExactWidthPx(1080).setExactHeightPx(1920).layout();
        Screenshot.snap(view)
                .record();
    }
}
