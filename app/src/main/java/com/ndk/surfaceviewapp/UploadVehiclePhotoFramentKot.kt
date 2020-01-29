package com.ndk.surfaceviewapp


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.macom.maafinloancollection.R
import com.macom.maafinloancollection.utilities.Utility
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UploadVehiclePhotoFramentKot.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UploadVehiclePhotoFramentKot.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadVehiclePhotoFramentKot : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val MY_PERMISSIONS_REQUEST_USE_CAMERA: Int = 0x00AF
    internal lateinit var date: Date

    private lateinit var imgbtnBack: ImageButton
    private lateinit var imgbtn_More: ImageButton
    private lateinit var imgbtn_Receipt: ImageButton
    private lateinit var imgbtn_Search: ImageButton
    private lateinit var imgbtninfo: ImageButton
    private lateinit var imgbtnAdd: ImageButton

    private lateinit var buttonSubmit: Button
    private lateinit var imageView: ImageView
    private lateinit var txt_header: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_vehicle_photo, container, false)

        imageView = view.findViewById<ImageView>(R.id.imgPhoto)

        txt_header = view.findViewById<View>(R.id.txt_header) as TextView
        txt_header.text = "Upload  Vehicle Photo"
        imgbtnBack = view.findViewById<View>(R.id.imgbtn_Back) as ImageButton
        imgbtnBack.visibility = View.VISIBLE
        imgbtnAdd = view.findViewById<View>(R.id.imgbtn_Add) as ImageButton
        imgbtnAdd.visibility = View.GONE
        imgbtn_Search = view.findViewById<View>(R.id.imgbtn_Search) as ImageButton
        imgbtn_Search.visibility = View.GONE
        imgbtn_More = view.findViewById<View>(R.id.imgbtn_More) as ImageButton
        imgbtn_More.visibility = View.GONE
        imgbtn_Receipt = view.findViewById<View>(R.id.imgbtn_Receipt) as ImageButton
        imgbtn_Receipt.visibility = View.GONE
        buttonSubmit = view.findViewById<Button>(R.id.btnSubmit)

//        var dateSomeDay: Loc = Date("2020-1-02")
//
//        val current = LocalDateTime.now()
//
//        var calenderSomeDay:Calendar = Calendar.getInstance()
//
//        calenderSomeDay.time = dateSomeDay
//        val msDiff = Calendar.getInstance().timeInMillis - calenderSomeDay.getTimeInMillis()
//
//        val daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        try {
            date = sdf.parse("2020-1-02")
        } catch (e: ParseException) {
            e.printStackTrace()
        }


//        Calendar cal = Calendar.getInstance();


        val someday = Calendar.getInstance()
        someday.time = date


        val msDiff = Calendar.getInstance().timeInMillis - someday.timeInMillis
        val daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff)

        buttonSubmit.setOnClickListener { v ->

            if (daysDiff > 30) {
                Toast.makeText(context, "Upload successfull , you have to update the latest photo after 30 days", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Cant Upload now!" + " wait " + (30 - daysDiff) + " more days", Toast.LENGTH_SHORT).show()
            }
        }

        imgbtnBack.setOnClickListener { v ->

            activity!!.supportFragmentManager.popBackStack()
            Utility.hideKeyboard(view, context)


        }
        checkCameraPermission()
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, MY_PERMISSIONS_REQUEST_USE_CAMERA)
        return view


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //if (requestCode == CAMERA_REQUEST) {

        val photo = data.extras!!.get("data") as Bitmap
        //
        val photoEncoded = convertToBase64(photo)
        Log.d(this@UploadVehiclePhotoFramentKot.toString(), "onActivityResult: $photoEncoded")
        val decodedString = Base64.decode(photoEncoded, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        imageView.setImageBitmap(decodedByte)
        Log.d(this@UploadVehiclePhotoFramentKot.toString(), "onActivityResult: " + photoEncoded.length)


    }

    private fun convertToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()


        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

    override fun onRequestPermissionsResult(i: Int, strings: Array<String>, ints: IntArray) {
        when (i) {
            MY_PERMISSIONS_REQUEST_USE_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (ints.size > 0 && ints[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "permission was granted! Do your stuff")
                } else {
                    Log.d("TAG", "permission denied! Disable the function related with permission.")
                }
                return
            }
        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission not available requesting permission")
            ActivityCompat.requestPermissions((context as Activity?)!!,
                    arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_USE_CAMERA)
        } else {
            Log.d("TAG", "Permission has already granted")
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */


    fun newInstance(param1: String, param2: String) =
            UploadVehiclePhotoFramentKot().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
}



