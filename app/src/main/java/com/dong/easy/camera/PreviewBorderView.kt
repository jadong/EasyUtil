package com.dong.easy.camera

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.SurfaceHolder
import android.view.SurfaceView

import com.dong.easy.R

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/23.
 */
class PreviewBorderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback, Runnable {

    private val TAG = "PreviewBorderView"

    private var mScreenH: Int = 0
    private var mScreenW: Int = 0
    private var mCanvas: Canvas? = null
    private var mPaint: Paint? = null
    private var mPaintLine: Paint? = null
    private var mHolder: SurfaceHolder? = null
    private var mThread: Thread? = null
    /**
     * 自定义属性
     */
    private var tipTextSize: Float = 0.toFloat()
    private var tipTextColor: Int = 0
    private var tipText: String? = null

    private var rectF: RectF? = null //取景框

    init {
        initAttrs(context, attrs)
        init()
    }

    /**
     * 初始化自定义属性
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PreviewBorderView)
        try {
            tipTextSize = a.getDimension(R.styleable.PreviewBorderView_tipTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TIPS_TEXT_SIZE.toFloat(), resources.displayMetrics))
            tipTextColor = a.getColor(R.styleable.PreviewBorderView_tipTextColor, DEFAULT_TIPS_TEXT_COLOR)
            tipText = a.getString(R.styleable.PreviewBorderView_tipText)
            if (tipText == null) {
                tipText = DEFAULT_TIPS_TEXT
            }
        } finally {
            a.recycle()
        }


    }

    /**
     * 初始化绘图变量
     */
    private fun init() {
        this.mHolder = holder
        this.mHolder!!.addCallback(this)
        this.mHolder!!.setFormat(PixelFormat.TRANSPARENT)
        setZOrderOnTop(true)
        this.mPaint = Paint()
        this.mPaint!!.isAntiAlias = true
        this.mPaint!!.color = Color.WHITE
        this.mPaint!!.style = Paint.Style.FILL_AND_STROKE
        this.mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        this.mPaintLine = Paint()
        this.mPaintLine!!.color = tipTextColor
        this.mPaintLine!!.strokeWidth = 3.0f
        keepScreenOn = true
    }

    fun getSceneFrameSize(): RectF? {
        return rectF
    }

    /**
     * 绘制取景框
     */
    private fun draw() {
        try {
            this.mCanvas = this.mHolder!!.lockCanvas()
            this.mCanvas!!.drawARGB(100, 0, 0, 0)
            this.mScreenW = this.mScreenH * 4 / 3
            Log.i(TAG, "mScreenW:$mScreenW mScreenH:$mScreenH")
            rectF = RectF((this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat())
            this.mCanvas!!.drawRect(rectF, this.mPaint!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6 + 50).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6 + 50).toFloat(), (this.mScreenH * 1 / 6).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6 + 50).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6 - 50).toFloat(), (this.mScreenH * 1 / 6).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6 - 50).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6 + 50).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6 - 50).toFloat(), this.mPaintLine!!)
            this.mCanvas!!.drawLine((this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6 - 50).toFloat(), (this.mScreenH - this.mScreenH * 1 / 6).toFloat(), this.mPaintLine!!)

            mPaintLine!!.textSize = tipTextSize
            mPaintLine!!.isAntiAlias = true
            mPaintLine!!.isDither = true
            val length = mPaintLine!!.measureText(tipText)
            this.mCanvas!!.drawText(tipText!!, this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6 + this.mScreenH / 2 - length / 2, this.mScreenH * 1 / 6 - tipTextSize, mPaintLine!!)
            Log.i(TAG, "left:" + (this.mScreenW / 2 - this.mScreenH * 2 / 3 + this.mScreenH * 1 / 6))
            Log.i(TAG, "top:" + this.mScreenH * 1 / 6)
            Log.i(TAG, "right:" + (this.mScreenW / 2 + this.mScreenH * 2 / 3 - this.mScreenH * 1 / 6))
            Log.i(TAG, "bottom:" + (this.mScreenH - this.mScreenH * 1 / 6))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (this.mCanvas != null) {
                this.mHolder!!.unlockCanvasAndPost(this.mCanvas)
            }
        }
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        //获得宽高，开启子线程绘图
        this.mScreenW = width
        this.mScreenH = height
        this.mThread = Thread(this)
        this.mThread!!.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //停止线程
        try {
            mThread!!.interrupt()
            mThread = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun run() {
        //子线程绘图
        draw()
    }

    companion object {
        private val DEFAULT_TIPS_TEXT = "请将方框对准证件拍摄"
        private val DEFAULT_TIPS_TEXT_SIZE = 16
        private val DEFAULT_TIPS_TEXT_COLOR = Color.GREEN
    }
}
