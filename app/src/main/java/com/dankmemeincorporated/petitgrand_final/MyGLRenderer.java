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

import com.dankmemeincorporated.petitgrand_final.Controller.GameController;
import com.dankmemeincorporated.petitgrand_final.modele.Arrow;
import com.dankmemeincorporated.petitgrand_final.modele.Equal;
import com.dankmemeincorporated.petitgrand_final.modele.Frame;
import com.dankmemeincorporated.petitgrand_final.modele.Hexagone;
import com.dankmemeincorporated.petitgrand_final.modele.Hourglass;
import com.dankmemeincorporated.petitgrand_final.modele.Minus;
import com.dankmemeincorporated.petitgrand_final.modele.Pentagone;
import com.dankmemeincorporated.petitgrand_final.modele.Plus;
import com.dankmemeincorporated.petitgrand_final.modele.Square;
import com.dankmemeincorporated.petitgrand_final.modele.Triangle;
import com.dankmemeincorporated.petitgrand_final.modele.Triforce;
import com.dankmemeincorporated.petitgrand_final.modele.Windows;

import java.util.LinkedList;

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
    private Square mSquare;
    private Pentagone mpenta;
    private Hexagone hexa;
    private Hourglass hourglass;
    private Windows window;
    private Triforce triforce;
    private Frame frame;

    public GameController gc=new GameController();

    private Plus plus;
    private Minus minus;
    private Equal equal;
    private Arrow arrow;

    private int left;
    private int middle;
    private int right;
    private int turn;

    private Pentagone penta2;



    private float[] displayA = new float[16];
    private float[] displayB = new float[16];
    private float[] a1 = new float[16];
    private float[] a2 = new float[16];

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

//    private float[] plusposition = {-0.45f,-0.55f};
//    private float[] minusposition = {0.45f,-0.55f};
//    private float[] equalposition = {0.0f,-0.55f};

    private float mAngle;

    public MyGLRenderer(/*int i, int i1, int i2,int t*/) {
        /*left=i;
        middle=i1;
        right=i2;
        turn=t;*/
//        gc = gameController;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare   = new Square();
        mpenta = new Pentagone();
        hexa = new Hexagone();
        hourglass = new Hourglass();
        window = new Windows();
        triforce=new Triforce();
        frame = new Frame();

        plus = new Plus();
        minus = new Minus();
        equal = new Equal();
        arrow = new Arrow();

        turn=1;

        penta2 = new Pentagone();

        gc.cheat();

//        gc = new GameController();

//        letsgo();


        Matrix.multiplyMM(displayA,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(displayB,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(a1,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(a2,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.translateM(displayA,0,-0.45f,0.0f,0);
        Matrix.translateM(a1,0,-0.45f,0.57f,0);
        Matrix.translateM(displayB,0,0.45f,0.0f,0);
        Matrix.translateM(a2,0,0.45f,0.57f,0);

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

        float [] begin = new float[16];

        Matrix.multiplyMM(begin,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.translateM(begin,0,0.6f,0.9f,0f);
        //Matrix.multiplyMM();


        // Draw square
        mTriangle.draw(begin);

        for (int i = 1 ; i<7 ; i++){

            float[] next = new float[16];
            float decalX = i*(0.10f+((float)i/100));
            float decalY = i*0.01f;

            Matrix.multiplyMM(next,0,mProjectionMatrix,0,mViewMatrix,0);
            Matrix.translateM(next,0,0.6f-decalX,0.9f-decalY,0);

            switch(i){
                case 1:
                    mSquare.draw(next);
                    break;
                case 2:
                    mpenta.draw(next);
                    break;
                case 3:
                    hexa.draw(next);
                    break;
                case 4:
                    hourglass.draw(next);
                    break;
                case 5:
                    window.draw(next);
                    break;
                case 6:
                    triforce.draw(next);
                    break;
            }
        }

//        float[] displayA = new float[16];
//        float[] displayB = new float[16];
        float[] p = new float[16];
        float[] m = new float[16];
        float[] e = new float[16];
        float[] a = new float[16];
//        float[] a1 = new float[16];
//        float[] a2 = new float[16];

        Matrix.multiplyMM(displayA,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(displayB,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(p,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(m,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(e,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(a,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(a1,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.multiplyMM(a2,0,mProjectionMatrix,0,mViewMatrix,0);
        Matrix.translateM(displayA,0,-0.45f,0.0f,0);
        Matrix.translateM(a1,0,-0.45f,0.57f,0);
        Matrix.translateM(m,0,-0.45f,-0.55f,0);
        Matrix.translateM(displayB,0,0.45f,0.0f,0);
        Matrix.translateM(a2,0,0.45f,0.57f,0);
        Matrix.translateM(p,0,0.45f,-0.55f,0);
        Matrix.translateM(e,0,0.0f,-0.55f,0);
        Matrix.translateM(a,0,0.0f,-0.80f,0);

        frame.draw(displayA);
        frame.draw(displayB);
        if(gc.getWinner()==0){
            minus.draw(m);
            plus.draw(p);
            equal.draw(e);
        }

        arrow.draw(a);
        if ((gc.getTurn() == 1)) {
            arrow.draw(a1);
            if(gc.getWinner()==1){
                mpenta.draw(a1);
            }
        } else if(gc.getTurn()==2) {
            arrow.draw(a2);
            if(gc.getWinner()==2){
                mpenta.draw(a2);
            }
        }else{
            System.out.println("Turn not regcognized.");
        }


        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
//        mTriangle.draw(mMVPMatrix);
//
//
//        mpenta.draw(mMVPMatrix);
//        hexa.draw(mMVPMatrix);
//        hourglass.draw(mMVPMatrix);
//        window.draw(mMVPMatrix);
//        triforce.draw(mMVPMatrix);
        Matrix.multiplyMM(scratch,0,mProjectionMatrix,0,mViewMatrix,0);
        frame.draw(scratch);
//        System.out.println("left :"+left);
        displayCarte(gc.getLeft(),displayB);
//        System.out.println("middle :"+middle);
        displayCarte(gc.getMid(),scratch);
//        System.out.println("right :"+right);
        displayCarte(gc.getRight(),displayA);

//        displayCarte(2,displayA);
//        displayCarte(5,displayB);
//        hexa.draw(displayA);


//        mpenta.draw(scratch);
//        penta2.draw(displayB);


    }

    public void displayCarte(int forme, float[] matr){
//        System.out.println("forme : "+forme+" et matr :"+matr.toString());
        if(forme!=0){
            switch (forme){
                case 1:
                    mTriangle.draw(matr);
                    break;
                case 2:
                    mSquare.draw(matr);
                    break;
                case 3:
                    mpenta.draw(matr);
                    break;
                case 4:
                    hexa.draw(matr);
                    break;
                case 5:
                    hourglass.draw(matr);
                    break;
                case 6:
                    window.draw(matr);
                    break;
                case 7:
                    triforce.draw(matr);
                    break;
            }
        }
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

//    public float[] getPosition(int symbole) {
//        if(symbole>0){
//            return plusposition;
//        }else if(symbole<0){
//            return minusposition;
//        }else if(symbole==0){//au cas ou
//            return equalposition;
//        }
//        return new float[16];
//    }

    public void setPosition(float v, float v1) {

    }
    public void setBgColor(float r, float g, float b){
        GLES20.glClearColor(r, g, b, 1.0f);
    }

    public void afficher(int forme, int cadre){

    }

    public void dosomethinggoddammit() {
//        float[] scratch = new float[16];
//        Matrix.multiplyMM(scratch,0,mProjectionMatrix,0,mViewMatrix,0);
//
//        mpenta.draw(scratch);
//        System.out.println("COUCOU !");

//        displayCarte(2,displayA);
//        displayCarte(5,displayB);
//        hexa.draw(a2);


        left=1;
        middle=5;
        right=7;

    }

    public void switchTurn(){
        turn=turn%2+1;
        if(turn==2){
        arrow.draw(a2);}else{
            arrow.draw(a1);
        }
    }

    public void truc(){
        left=(left+1)%8;
        middle=(middle+1)%8;
        right=(right+1)%8;
    }


    //Je vais essayer de mettre le GameController ici parce que flute !


////    private int turn;
//    private LinkedList<Integer> stackA;
//    private LC stackB;
//    private LC stackMid;
//    private LC stackRev;
//
//    public void letsgo(){
//        turn = 1;
//
//        distribute();
//
//    }
//
//    public void distribute(){
//        for(int i = 0; i<31;i++){
//            Log.d("message","test_plus="+Boolean.toString(true));
//            System.out.println(i+": tout va bien");
//            stackA.add(getFreeCard());
//            stackB.add(getFreeCard());
//        }
//        stackMid.add(getFreeCard());
//    }
//
//    private int cards[]={8,8,8,8,8,8,8};
//    public int getFreeCard(){
//        boolean done = false;
//        while(!done) {
//            int nb = ((int) Math.random() % 7);
//            if (cards[nb] > 0) {
//                cards[nb]--;
//                return nb+1;
//            }
//        }
//        return 0;
//    }
//
//
//    public void bet(int bt){
//        LC stack = (turn==1)? stackA : stackB;
//
//        if(
//                (bt==1&&stack.getHead().getValue()>stackMid.getHead().getValue())||
//                        (bt==0&&stack.getHead().getValue()==stackMid.getHead().getValue())||
//                        (bt==-1&&stack.getHead().getValue()<stackMid.getHead().getValue())){
//            continuer();
//        }else{
//            rempiler();
//        }
//    }
//
//    public void rempiler(){
//        if(turn==1) {
//            stackRev.getTail().setNext(stackA.getHead());
//            stackA.setHead(stackRev.getHead());
//            turn=2;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
//        }
//        if(turn==2) {
//            stackRev.getTail().setNext(stackB.getHead());
//            stackB.setHead(stackRev.getHead());
//            turn=1;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
//        }
//    }
//    public void continuer(){
//        if(turn==1) {
//            stackRev.getTail().setNext(stackA.getHead());
//            stackRev.setTail(stackRev.getTail().getNext());
//            if(stackA.getHead().getNext()!=null){
//                stackA.setHead(stackA.getHead().getNext());
//                stackRev.getTail().setNext(null);
//            }else{
//                win(1);
//            }
//        }
//        if(turn==2) {
//            stackRev.getTail().setNext(stackB.getHead());
//            stackRev.setTail(stackRev.getTail().getNext());
//            if(stackA.getHead().getNext()!=null) {
//                stackB.setHead(stackB.getHead().getNext());
//                stackRev.getTail().setNext(null);
//            }else{
//                win(2);
//            }
//        }
//    }
//
//    public void stop(){
//        stackMid.getTail().setNext(stackRev.getHead());
//        stackMid.setTail(stackRev.getTail());
//        stackRev.setHead(new Maillon(0));
//        stackRev.setTail(new Maillon(0));
//    }
//
//    public void win(int who){
//        System.out.println("Joueur "+who+" a gagné !");
//    }
//
//    public int getLeft(){return stackA.getHead().getValue();}
//    public int getRight(){return stackB.getHead().getValue();}
//    public int getMid(){
//        if(stackRev.getHead().getValue()==0){
//            return stackMid.getTail().getValue();
//        }
//        return stackRev.getTail().getValue();
//    }
//TODO afficher le stackrev.getlast() ou stackmid.getlast si l'autre est vide

}