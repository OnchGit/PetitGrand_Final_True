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
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLES20Activity extends Activity {

    private GLSurfaceView mGLView;
//    public GameController gc;

//    private

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
//        gc = new GameController();
//        System.out.println(gc.toString());
//        System.out.println(gc.getLeft());
//        System.out.println(gc.getMid());
//        System.out.println(gc.getRight());

        mGLView = new MyGLSurfaceView(this,3,5,6/*gc.getLeft(),gc.getMid(),gc.getRight()*/);
        setContentView(mGLView);
//        update(gc.getLeft(),gc.getMid(),gc.getRight());
    }

    public void update(int l, int m, int r){
        mGLView = new MyGLSurfaceView(this,l,m,r);
        setContentView(mGLView);

    }

//    public static void call(int l, int m , int r){
//        update(l,m,r);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
}