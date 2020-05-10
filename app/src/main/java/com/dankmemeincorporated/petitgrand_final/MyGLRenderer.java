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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Square   mSquare;
    private Pentagone mpenta;
    private Hexagone hexa;
    private Hourglass hourglass;
    private Windows window;
    private Triforce triforce;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mMaaaatrix= new float[16];


    private float mAngle;
    private float[] pos = {0.6f, 0.8f};
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);



        mTriangle = new Triangle(/*pos*/);
        mSquare   = new Square();
        mpenta = new Pentagone();
        hexa = new Hexagone();
        hourglass = new Hourglass();
        window = new Windows();
        triforce=new Triforce();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        //mSquare.draw(mMVPMatrix);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
        Matrix.setIdentityM(mModelMatrix,0);

        Matrix.translateM(mModelMatrix, 0, pos[0], pos[1], 0);
        //Matrix.translateM(mModelMatrix,mMVPMatrix,0,pos[0],pos[1],0.0f);

        Matrix.multiplyMM(mMVPMatrix,0,mMVPMatrix,0,mModelMatrix,0);

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);



        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(mModelMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0);


        // Draw triangle
        //mTriangle.set_position(pos);

        mTriangle.draw(mModelMatrix);

        Matrix.setIdentityM(mMaaaatrix,0);
        Matrix.translateM(mMaaaatrix,0,pos[0],pos[1],0f);

        for (int i = 1; i < 7; ++i) {
            float dist = -0.15f;

//            if (i > 2){dist = -0.30f;}
//            if (i == 5){dist = -0.40f;}
//            if (i > 5){dist = -0.60f;}

            Matrix.translateM(mModelMatrix, 0, dist, 0.0f, 0.0f);
            Matrix.multiplyMM(mMaaaatrix, 0, mMVPMatrix, 0, mModelMatrix, 0);

            switch (i) {
                case 1:
                    mSquare.draw(mMaaaatrix);
                    break;
                case 2:
                    mpenta.draw(mMaaaatrix);
                    break;
                case 3:
                    hexa.draw(mMaaaatrix);
                    break;
                case 4:
                    hourglass.draw(mMaaaatrix);
                    break;
                case 5:
                    window.draw(mMaaaatrix);
                    break;
                case 6:
                    triforce.draw(mMaaaatrix);
                    break;
            }        }

        //mTriangle.draw(mModelMatrix);

//        pos = new float[]{0.1f, 0.1f};
//        Matrix.translateM(mModelMatrix, 0, pos[0], pos[1], 0);
//        //Matrix.translateM(mModelMatrix,mMVPMatrix,0,pos[0],pos[1],0.0f);
//
//        Matrix.multiplyMM(mMVPMatrix,0,mMVPMatrix,0,mModelMatrix,0);



//        mpenta.draw(scratch);
//        hexa.draw(scratch);
//        hourglass.draw(scratch);
//        window.draw(scratch);
        //triforce.draw(mMVPMatrix);


    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

    public void setPosition(float x, float y) {
        /*mSquarePosition[0] += x;
        mSquarePosition[1] += y;*/
        pos[0] = x;
        pos[1] = y;

    }

    public float[] getPosition() {
        return pos;
    }

}