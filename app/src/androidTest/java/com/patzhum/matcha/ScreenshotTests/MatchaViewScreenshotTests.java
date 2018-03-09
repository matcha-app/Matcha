package com.patzhum.matcha.ScreenshotTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;
import com.patzhum.matcha.R;
import com.patzhum.matcha.RenderResource;
import com.patzhum.matcha.render.MatchaView;
import com.patzhum.matcha.render.core.RenderUtil;
import com.patzhum.matcha.render.layouts.MatchaLinearLayout;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by patri on 2018-03-05.
 */

public class MatchaViewScreenshotTests{

    @Test
    public void renderTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String json = RenderResource.getFileContents("MatchaViewScreenshotTests#renderTest.json");

        View view = RenderUtil.Companion.renderView(context, MatchaView.class, json);
        ViewHelpers.setupView(view).setExactWidthPx(1080).setExactHeightPx(1920).layout();
        Screenshot.snap(view)
                .record();
    }
}
