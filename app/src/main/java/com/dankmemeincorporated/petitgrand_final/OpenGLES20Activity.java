/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dankmemeincorporated.petitgrand_final;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.dankmemeincorporated.petitgrand_final.Controller.GameController;

public class OpenGLES20Activity extends Activity {

    private GLSurfaceView mGLView;
    public GameController gc;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity


        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    public void update(int l, int m, int r,int t){//une tentative infructueuse
        mGLView = new MyGLSurfaceView(this/*,l,m,r,t*/);
        setContentView(mGLView);

    }


    @Override
    protected void onPause() {
        super.onPause();

        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGLView.onResume();
    }
}