package com.ndk.surfaceviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback  {

Button btn_submit;
    Camera mCamera;
    SurfaceView mPreview;
    ImageView img;
    RecyclerView recycler_images;
    ImagesAdapter imagesAdapter;
    ImagesCaptured imagesCaptured;
    CustomerList customerList;

    private List<ImagesCaptured> ImageList;
    private LinearLayoutManager layoutManager;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_land_loan);
        btn_submit=findViewById(R.id.btn_submit);
        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        recycler_images=findViewById(R.id.recycler_image);
        requestPermission();
        getLoanCustomerDetails_Read("0431351301600001");
        mCamera = Camera.open();
        ImageList = new ArrayList<ImagesCaptured>();
        imagesAdapter = new ImagesAdapter(this, ImageList);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AssetLoanActivity.class);
                startActivity(i);
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
        imagesCaptured =new ImagesCaptured();

          Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap!=null)
            {
                imagesCaptured.setImage(bitmap);
                ImageList.add(imagesCaptured);
            }
            //Toast.makeText(MainActivity.this, ""+ImageList.size(), Toast.LENGTH_SHORT).show();
            if(ImageList.size()<=4&&ImageList.size()>0)
            {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recycler_images.setLayoutManager(mLayoutManager);
                recycler_images.setItemAnimator(new DefaultItemAnimator());
                recycler_images.setAdapter(imagesAdapter);
                imagesAdapter.notifyDataSetChanged();
            }
            else
                {
                Toast.makeText(this, "Maximum 4 Images", Toast.LENGTH_SHORT).show();

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
    private void getLoanCustomerDetails_Read(String loanid) {
        GetDataServices dataService=RetrofitClientInstance.getRetrofitLOSAPIInstance().create(GetDataServices.class);
        Call<CustomerList> call=dataService.getLoanList(loanid);
        call.enqueue(new Callback<CustomerList>() {

            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                if(response.body()!= null)
                {
                    if(response.body().getCusTGUARANT()!= null)
                    {
                        customerList= new CustomerList();
                        customerList=response.body();
                        Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                        //Status status=new Status();
                        //status.
                    }

                }else
                {
                    Toast.makeText(MainActivity.this, "null response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mCamera.startPreview();
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                   // Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            /*showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });*/
                        }
                    }
                }
                break;
        }
    }


}

