package com.patzhum.matcha;

import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by patri on 2018-03-09.
 */

public class RenderResource {
    public static String getFileContents(String filename) {
        InputStream inputStream = null;
        try {
            inputStream = InstrumentationRegistry.getInstrumentation().getContext().getAssets().open("render/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining());
    }
}
