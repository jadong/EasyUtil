package com.dong.easy.scan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.widget.RelativeLayout
import com.dong.easy.R
import com.dong.easy.camera.CameraManager
import com.dong.easy.constant.AppConstant
import kotlinx.android.synthetic.main.activity_scan.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/23.
 */
class ScanActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private val TAG = "ScanActivity"
    private var cameraManager: CameraManager? = null
    private var hasSurface: Boolean = false
    private var toggleLight: Boolean = false
    private var safeToTakePicture: Boolean = true
    private var previewWidth = 0
    private var previewHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        initLayoutParams()
    }

    /**
     * 重置surface宽高比例为3:4，不重置的话图形会拉伸变形
     */
    private fun initLayoutParams() {
        take.setOnClickListener {
            if (safeToTakePicture) {
                safeToTakePicture = false
                cameraManager?.takePicture(null, null, pictureCallback)
            }
        }
        light.setOnClickListener {
            if (!toggleLight) {
                toggleLight = true
                cameraManager!!.openLight()
            } else {
                toggleLight = false
                cameraManager!!.offLight()
            }
        }

        //重置宽高，3:4
        val widthPixels = getScreenWidth(this)
        previewHeight = getScreenHeight(this)

        previewWidth = previewHeight * 4 / 3
        val surfaceViewParams = surfaceView.layoutParams as RelativeLayout.LayoutParams
        surfaceViewParams.width = previewWidth
        surfaceViewParams.height = previewHeight
        surfaceView!!.layoutParams = surfaceViewParams

        val borderViewParams = previewBorderView.layoutParams as RelativeLayout.LayoutParams
        borderViewParams.width = previewWidth
        borderViewParams.height = previewHeight
        previewBorderView.layoutParams = borderViewParams

        val linearLayoutParams = linearLayout.layoutParams as RelativeLayout.LayoutParams
        linearLayoutParams.width = widthPixels - previewWidth
        linearLayoutParams.height = previewHeight
        linearLayout.layoutParams = linearLayoutParams


        Log.i(TAG, "Screen width:" + previewWidth)
        Log.i(TAG, "Screen height:" + previewHeight)

    }


    override fun onResume() {
        super.onResume()
        //初始化camera
        cameraManager = CameraManager()
        val surfaceHolder = surfaceView.holder

        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder)
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (!hasSurface) {
            hasSurface = true
            initCamera(holder)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false
    }

    /**
     * 初始camera
     *
     * @param surfaceHolder SurfaceHolder
     */
    private fun initCamera(surfaceHolder: SurfaceHolder?) {
        if (cameraManager == null) {
            return
        }
        if (surfaceHolder == null) {
            throw IllegalStateException("No SurfaceHolder provided")
        }
        if (cameraManager!!.isOpen) {
            return
        }
        try {
            // 打开Camera硬件设备
            cameraManager!!.openDriver(surfaceHolder)
            // 创建一个handler来打开预览，并抛出一个运行时异常
            cameraManager!!.startPreview()

            cameraManager!!.setPreviewCallback(Camera.PreviewCallback { data, camera ->
                //                    final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //                    int height = bitmap.getHeight();
                //                    int width = bitmap.getWidth();
                //                    final Bitmap bitmap1 = Bitmap.createBitmap(bitmap, (width - height) / 2, height / 6, height, height * 2 / 3);
                //                    Log.e("TAG", "width:" + width + " height:" + height);
                //                    Log.e("TAG", "x:" + (width - height) / 2 + " y:" + height / 6 + " width:" + height + " height:" + height * 2 / 3);
                // 创建一个位于SD卡上的文件
            })

        } catch (ioe: Exception) {
            Log.e(TAG, ioe.message)
        }

    }


    override fun onPause() {
        //停止camera，是否资源操作
        cameraManager!!.stopPreview()
        cameraManager!!.closeDriver()
        if (!hasSurface) {
            val surfaceHolder = surfaceView.holder
            surfaceHolder.removeCallback(this)
        }
        super.onPause()
    }

    /**
     * 拍照回调
     */
    private var pictureCallback: Camera.PictureCallback = Camera.PictureCallback { data, _ ->
        // 根据拍照所得的数据创建位图
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        val height = bitmap.height
        val width = bitmap.width
        val sceneFrameSize = previewBorderView.getSceneFrameSize()

        val x:Int = (width * (sceneFrameSize?.left ?: 0f) / previewWidth).toInt()
        val y:Int = (height * (sceneFrameSize?.top ?: 0f) / previewHeight).toInt()

        val bitmap1 = Bitmap.createBitmap(bitmap, x, y, height, height * 2 / 3)
        Log.i(TAG, "width:$width height:$height")
        Log.i(TAG, "x:" + (width - height) / 2 + " y:" + height / 6 + " width:" + height + " height:" + height * 2 / 3)
        Log.i(TAG, "sceneFrameSize:" + sceneFrameSize)

        val path = File(AppConstant.DOWNLOAD_PATH)
        if (!path.exists()) {
            path.mkdirs()
        }

        val fileName = "test.jpg"

        val file = File(path, fileName)

        var outStream: FileOutputStream? = null
        try {
            // 打开指定文件对应的输出流
            outStream = FileOutputStream(file)
            // 把位图输出到指定文件中
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (outStream != null) {
                try {
                    outStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        safeToTakePicture = true
    }

    /**
     * 获得屏幕宽度，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    private fun getScreenWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }


    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕除去通知栏的高度
     */
    private fun getScreenHeight(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels - getStatusBarHeight(context)
    }

    /**
     * 获取通知栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val obj = clazz.newInstance()
            val field = clazz.getField("status_bar_height")
            val temp = Integer.parseInt(field.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(temp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }
}
