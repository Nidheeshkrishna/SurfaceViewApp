package com.ndk.surfaceviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class








AssetLoanActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback  {
    Button btn_submit;
    Camera mCamera;
    SurfaceView mPreview;
    ImageView img;
    RecyclerView recycler_images;
    ImageAdapterAsset imagesAdapter;
    ImgesAssetLoan imagesCaptured;

    private List<ImgesAssetLoan> ImageList;
    private LinearLayoutManager layoutManager;
    private CustomerList customerlist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_loan);
        btn_submit=findViewById(R.id.btn_submit);
        mPreview = (SurfaceView)findViewById(R.id.preview_asset);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        recycler_images=findViewById(R.id.recycler_image);
        mCamera = Camera.open();
        ImageList = new ArrayList<ImgesAssetLoan>();
        imagesAdapter = new ImageAdapterAsset(this, ImageList);
        //getLoanCustomerDetails_Read()
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(AssetLoanActivity.this,AssetLoanActivity.class);
               // startActivity(i);
                //sumbmitMethod()
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
        ImageList.clear();
        Log.d("CAMERA","Destroy");
    }

    public void onCancelClick(View v) {
        finish();
    }

    public void onSnapClick(View v) {
        mCamera.takePicture(this, null, null, this);
    }

    @Override
    public void onShutter() {
        //Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //Here, we chose internal storage
        // try {
           /* FileOutputStream out = openFileOutput("picture.jpg", Activity.MODE_PRIVATE);
            out.write(data);
            out.flush();
            out.close();*/

        //createDirectoryAndSaveFile(bitmap,"img");
        //ArrayList<Bitmap> images=new ArrayList<>();
        //images.add(bitmap);
        //img.setImageBitmap(bitmap);
        imagesCaptured =new ImgesAssetLoan();

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if(bitmap!=null)
        {
            imagesCaptured.setImage(bitmap);
            ImageList.add(imagesCaptured);
        }
        //Toast.makeText(MainActivity.this, ""+ImageList.size(), Toast.LENGTH_SHORT).show();
        if(ImageList.size()<=2&&ImageList.size()>0)
        {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recycler_images.setLayoutManager(mLayoutManager);
            recycler_images.setItemAnimator(new DefaultItemAnimator());
            recycler_images.setAdapter(imagesAdapter);
            imagesAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this, "Allowed 2 Images", Toast.LENGTH_SHORT).show();

        }

        //} catch (FileNotFoundException e) {
        //  e.printStackTrace();
        //} catch (IOException e) {
        //e.printStackTrace();
        //}
        camera.startPreview();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW","surfaceDestroyed");
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/DirName/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/DirName/", fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        mCamera.startPreview();
    }

}
