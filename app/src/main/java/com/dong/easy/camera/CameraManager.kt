package com.dong.easy.camera

import android.graphics.ImageFormat
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder

import java.io.IOException

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/23.
 */
class CameraManager {
    private var camera: Camera? = null
    private var parameters: Camera.Parameters? = null
    private var autoFocusManager: AutoFocusManager? = null
    private val requestedCameraId = -1

    private var initialized: Boolean = false
    private var previewing: Boolean = false

    /**
     * 打开摄像头
     *
     * @param cameraId 摄像头id
     * @return Camera
     */
    private fun open(cId: Int): Camera? {
        var cameraId = cId
        val numCameras = Camera.getNumberOfCameras()
        if (numCameras == 0) {
            Log.e(TAG, "No cameras!")
            return null
        }
        val explicitRequest = cameraId >= 0
        if (!explicitRequest) {
            // Select a camera if no explicit camera requested
            var index = 0
            while (index < numCameras) {
                val cameraInfo = Camera.CameraInfo()
                Camera.getCameraInfo(index, cameraInfo)
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    break
                }
                index++
            }
            cameraId = index
        }
        val camera: Camera?
        camera = when {
            cameraId < numCameras -> {
                Log.e(TAG, "Opening camera #" + cameraId)
                Camera.open(cameraId)
            }
            explicitRequest -> {
                Log.e(TAG, "Requested camera does not exist: " + cameraId)
                null
            }
            else -> {
                Log.e(TAG, "No camera facing back; returning camera #0")
                Camera.open(0)
            }
        }
        return camera
    }

    /**
     * 打开camera
     *
     * @param holder SurfaceHolder
     * @throws IOException IOException
     */
    @Synchronized
    @Throws(IOException::class)
    fun openDriver(holder: SurfaceHolder) {
        var theCamera = camera
        if (theCamera == null) {
            theCamera = open(requestedCameraId)
            if (theCamera == null) {
                throw IOException()
            }
            camera = theCamera
        }
        theCamera.setPreviewDisplay(holder)

        if (!initialized) {
            initialized = true
            parameters = camera!!.parameters
            val previewSizes = parameters!!.supportedPreviewSizes

            var w = 800
            var h = 600
            for (size in previewSizes) {
                Log.i(TAG, "previewSizes width:" + size.width + " | height:" + size.height)
                if (size.width - w <= 100) {
                    w = size.width
                    h = size.height
                    break
                }
            }

            parameters!!.setPreviewSize(w, h)
            parameters!!.pictureFormat = ImageFormat.JPEG
            parameters!!.jpegQuality = 100
            parameters!!.setPictureSize(800, 600)
            theCamera.parameters = parameters
        }
    }

    /**
     * camera是否打开
     *
     * @return camera是否打开
     */
    val isOpen: Boolean
        @Synchronized get() = camera != null

    /**
     * 关闭camera
     */
    @Synchronized
    fun closeDriver() {
        if (camera != null) {
            camera!!.release()
            camera = null
        }
    }

    /**
     * 开始预览
     */
    @Synchronized
    fun startPreview() {
        val theCamera = camera
        if (theCamera != null && !previewing) {
            theCamera.startPreview()
            previewing = true
            autoFocusManager = AutoFocusManager(theCamera)
        }
    }

    fun setPreviewCallback(cb: Camera.PreviewCallback) {
        if (camera != null) {
            camera!!.setPreviewCallback(cb)
        }
    }

    /**
     * 关闭预览
     */
    @Synchronized
    fun stopPreview() {
        if (autoFocusManager != null) {
            autoFocusManager!!.stop()
            autoFocusManager = null
        }
        if (camera != null && previewing) {
            camera!!.stopPreview()
            previewing = false
        }
    }

    /**
     * 打开闪光灯
     */
    @Synchronized
    fun openLight() {
        if (camera != null) {
            parameters = camera!!.parameters
            parameters!!.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera!!.parameters = parameters
        }
    }

    /**
     * 关闭闪光灯
     */
    @Synchronized
    fun offLight() {
        if (camera != null) {
            parameters = camera!!.parameters
            parameters!!.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera!!.parameters = parameters
        }
    }

    /**
     * 拍照
     *
     * @param shutter ShutterCallback
     * @param raw     PictureCallback
     * @param jpeg    PictureCallback
     */
    @Synchronized
    fun takePicture(shutter: Camera.ShutterCallback?, raw: Camera.PictureCallback?, jpeg: Camera.PictureCallback?) {
        camera!!.takePicture(shutter, raw, jpeg)
    }

    companion object {
        private val TAG = CameraManager::class.java.name
    }
}
