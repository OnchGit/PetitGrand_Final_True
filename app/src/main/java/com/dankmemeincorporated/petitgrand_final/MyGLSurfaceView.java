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

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.dankmemeincorporated.petitgrand_final.Controller.GameController;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;
//    public static GameController gc=new GameController();

    public MyGLSurfaceView(Context context/*,int l ,int m ,int r,int t*/) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(/*l,m,r,t*/);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        // MotionEvent reports input details from the touch screen
//        // and other input controls. In this case, you are only
//        // interested in events where the touch position changed.
//
//        float x = e.getX();
//        float y = e.getY();
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;
//
//                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -1 ;
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -1 ;
//                }
//
//                mRenderer.setAngle(
//                        mRenderer.getAngle() +
//                        ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
//                requestRender();
//        }
//
//        mPreviousX = x;
//        mPreviousY = y;
//        return true;
//    }

    private boolean conditionp = false;
    private boolean conditione = false;
    private boolean conditionm = false;
    private boolean conditions = false;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Les coordonnées du point touché sur l'écran
        float x = e.getX();
        float y = e.getY();

        // la taille de l'écran en pixels
        float screen_x = getWidth();
        float screen_y = getHeight();



        // Des messages si nécessaires */
        Log.d("message", "x"+Float.toString(x));
        Log.d("message", "y"+Float.toString(y));
        Log.d("message", "screen_x="+Float.toString(screen_x));
        Log.d("message", "screen_y="+Float.toString(screen_y));


        /* accès aux paramètres du rendu (cf MyGLRenderer.java)
        soit la position courante du centre du carré
         */
//        float[] posp = mRenderer.getPosition(1);
//        float[] posm = mRenderer.getPosition(-1);
//        float[] pose = mRenderer.getPosition(0);

        /* Conversion des coordonnées pixel en coordonnées OpenGL
        Attention l'axe x est inversé par rapport à OpenGLSL
        On suppose que l'écran correspond à un carré d'arête 2 centré en 0
         */

        float x_opengl = 20.0f*x/getWidth() - 10.0f;
        float y_opengl = -20.0f*y/getHeight() + 10.0f;

        Log.d("message","x_opengl="+Float.toString(x_opengl));
        Log.d("message","y_opengl="+Float.toString(y_opengl));

        /* Le carré représenté a une arête de 2 (oui il va falloir changer cette valeur en dur !!)
        /* On teste si le point touché appartient au carré ou pas car on ne doit le déplacer que si ce point est dans le carré
        */

        boolean test_plus = ((x_opengl > -7.77f && (x_opengl <-5.88f) && (y_opengl > -6.27f) && (y_opengl < -4.80f)));
        boolean test_minus = ((x_opengl > 5.15f) && (x_opengl < 7.92f) && (y_opengl < -5.17f) && (y_opengl > -6.08f));
        boolean test_equal = ((x_opengl > -1.26f) && (x_opengl < 1.335f) && (y_opengl > -6.38f) && (y_opengl < -4.85f));
        boolean test_stop = ((x_opengl < 0.96f) && (x_opengl > -0.58f) && (y_opengl > -8.65f) && (y_opengl < -7.33f));

        Log.d("message","test_plus="+Boolean.toString(test_plus));
        Log.d("message","conditionp="+Boolean.toString(conditionp));
        System.out.println("touched : "+ x_opengl+", "+y_opengl+".");

        if (conditionp || test_plus) {
            System.out.println("plus");

            switch (e.getAction()) {
                /* Lorsqu'on touche l'écran on mémorise juste le point */
                case MotionEvent.ACTION_DOWN:
                    conditionp=true;
                    break;
                case MotionEvent.ACTION_UP:
//                    System.out.println("AU SECOURS !");
//                    mRenderer = new MyGLRenderer(6,6,6);
//                    setRenderer(mRenderer);
//                    OpenGLES20Activity.update(3,6,5);
//                    mRenderer.setBgColor(1.0f,0.0f,0.0f);
//                    mRenderer.dosomethinggoddammit();

                    if(mRenderer.gc.getWinner()==0){mRenderer.gc.bet(1);}
                    requestRender(); // équivalent de glutPostRedisplay pour lancer le dessin avec les modifications.
                    conditionp=false;

            }
        }
        if (conditionm || test_minus) {
            System.out.println("minus");

            switch (e.getAction()) {
                /* Lorsqu'on touche l'écran on mémorise juste le point */
                case MotionEvent.ACTION_DOWN:
                    conditionm=true;
                    break;
                case MotionEvent.ACTION_UP:
//                    mRenderer.setBgColor(0.0f,1.0f,0.0f);
//                    mRenderer.truc();
                    if(mRenderer.gc.getWinner()==0){mRenderer.gc.bet(-1);}
                    requestRender(); // équivalent de glutPostRedisplay pour lancer le dessin avec les modifications.
                    conditionm=false;

            }
        }
        if (conditione || test_equal) {
            System.out.println("equal");

            switch (e.getAction()) {
                /* Lorsqu'on touche l'écran on mémorise juste le point */
                case MotionEvent.ACTION_DOWN:
                    conditione=true;
                    break;
                case MotionEvent.ACTION_UP:
//                    mRenderer.setBgColor(0.0f,0.0f,1.0f);
                    if(mRenderer.gc.getWinner()==0){mRenderer.gc.bet(0);}
                    requestRender(); // équivalent de glutPostRedisplay pour lancer le dessin avec les modifications.
                    conditione=false;

            }
        }

        if (conditions || test_stop) {
            System.out.println("stop");

            switch (e.getAction()) {
                /* Lorsqu'on touche l'écran on mémorise juste le point */
                case MotionEvent.ACTION_DOWN:
                    conditions=true;
                    break;
                case MotionEvent.ACTION_UP:
//                    mRenderer.setBgColor(0.5f,0.5f,1.0f);
//                    mRenderer.switchTurn();
                    if(mRenderer.gc.getWinner()==0){mRenderer.gc.stop();}else{
                        mRenderer.gc=new GameController();//après avoir gagné 
                    }
                    requestRender(); // équivalent de glutPostRedisplay pour lancer le dessin avec les modifications.
                    conditions=false;

            }
        }

        return true;
    }

}
