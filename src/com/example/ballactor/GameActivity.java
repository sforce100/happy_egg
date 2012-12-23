package com.example.ballactor;



import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
 
public class GameActivity extends AndroidApplication {    
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initialize(new MyGame(), false); 
    } 
}


    

