package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

/**
 * @描述: 公共titlebar
 * @创建人：zhangpeisen
 * @创建时间：2017/10/12 上午10:38
 * @修改人：zhangpeisen
 * @修改时间：2017/10/12 上午10:38
 * @修改备注：
 * @throws
 */
public class NormalTopBar extends RelativeLayout {

    private String leftText, titleText, rightText;

    private int leftImageId, rightImageId, titleImageId;

    private int leftTextColor, titleTextColor, rightTextColor;


    private float leftTextSize, titleTextSize, rightTextSize;

    public String getLeftText() {
        return leftTextView.getText().toString();
    }

    public void setLeftText(String leftText) {
        leftTextView.setText(leftText);
    }

    public String getTitleText() {
        return titleTextView.getText().toString();
    }

    public void setTitleText(String titleText) {
        titleTextView.setText(titleText);
    }

    public String getRightText() {
        return rightTextView.getText().toString();
    }

    public void setRightText(String rightText) {
        rightTextView.setText(rightText);
    }

    public void setLeftImageId(int leftImageId) {
        leftImage.setImageResource(leftImageId);
    }

    public void setRightImageId(int rightImageId) {
        rightImage.setImageResource(rightImageId);
    }


    public void setLeftTextColor(int leftTextColor) {
        leftTextView.setTextColor(leftTextColor);
    }


    public void setTitleTextColor(int titleTextColor) {
        titleTextView.setTextColor(titleTextColor);
    }


    public void setRightTextColor(int rightTextColor) {
        rightTextView.setTextColor(rightTextColor);
    }


    public void setLeftTextSize(float leftTextSize) {
        leftTextView.setTextSize(leftTextSize);
    }


    public void setTitleTextSize(float titleTextSize) {
        titleTextView.setTextSize(titleTextSize);
    }


    public void setRightTextSize(float rightTextSize) {
        rightTextView.setTextSize(rightTextSize);
    }

    private TextView leftTextView, rightTextView;


    private ImageView leftImage;
    private ImageView rightImage;
    private ImageView titleImage;

    public ImageView getLeftImage() {
        return leftImage;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public ImageView getTitleImage() {
        return titleImage;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    private TextView titleTextView;

    private LayoutParams leftImageParams, leftTextParams, titleParams, titleImageParams, rightTextParams, rightImageParams;

    private normalTopClickListener mClickListener;


    public NormalTopBar(Context context) {
        this(context, null);
    }

    public NormalTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NormalTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTypeArray(context, attrs);
        addAllView(context);
        addOnClick();
    }

    private void addOnClick() {

        leftTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onLeftClick(view);
            }
        });

        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onLeftClick(view);
            }
        });

        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onRightClick(view);
            }
        });

        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onRightClick(view);
            }
        });

        titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onTitleClick(view);
            }
        });

        titleImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onTitleClick(view);
            }
        });


    }

    private void addAllView(Context context) {
        leftTextView = new TextView(context);
        rightTextView = new TextView(context);
        titleTextView = new TextView(context);
        leftImage = new ImageView(context);
        rightImage = new ImageView(context);
        titleImage = new ImageView(context);

        leftImage.setId(R.id.leftimageid);
        leftImage.setImageResource(leftImageId);
        leftImage.setAdjustViewBounds(true);
        leftImage.setScaleType(ImageView.ScaleType.CENTER);
        leftTextView.setText(leftText);
        leftTextView.setTextSize(leftTextSize);
        leftTextView.setTextColor(leftTextColor);

        titleTextView.setId(R.id.titleTextViewid);
        titleTextView.setText(titleText);
        titleTextView.setTextSize(titleTextSize);
        titleTextView.setTextColor(titleTextColor);
        titleTextView.setGravity(Gravity.CENTER);//一定要设置textview内容的位置

        titleImage.setBackgroundResource(titleImageId);

        rightTextView.setText(rightText);
        rightTextView.setTextSize(rightTextSize);
        rightTextView.setTextColor(rightTextColor);

        rightImage.setId(R.id.rightimageid);
        rightImage.setImageResource(rightImageId);


        //为组建设置相应的布局
        if (leftImageId != 0 && leftText != null) {
            leftImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftImageParams.addRule(ALIGN_PARENT_LEFT, TRUE);
            leftImageParams.addRule(CENTER_VERTICAL, TRUE);
            addView(leftImage, leftImageParams);

            leftTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            leftTextParams.addRule(RelativeLayout.RIGHT_OF, R.id.leftimageid);
            leftTextParams.addRule(CENTER_VERTICAL, TRUE);
            leftTextView.setGravity(Gravity.CENTER_VERTICAL);
            addView(leftTextView, leftTextParams);

        } else if (leftImageId != 0 && leftText == null) {

            leftImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftImageParams.addRule(ALIGN_PARENT_LEFT, TRUE);
            leftImageParams.addRule(CENTER_VERTICAL, TRUE);
            addView(leftImage, leftImageParams);
        } else {

            leftTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            leftTextParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
            leftTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
            leftTextView.setGravity(Gravity.CENTER_VERTICAL);
            leftTextView.setPadding(17, 10, 15, 5);
            addView(leftTextView, leftTextParams);

        }


        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        titleParams.addRule(TEXT_ALIGNMENT_CENTER);
        addView(titleTextView, titleParams);

        titleImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleImageParams.addRule(RelativeLayout.RIGHT_OF, R.id.titleTextViewid);
        titleImageParams.addRule(CENTER_VERTICAL, TRUE);
        addView(titleImage, titleImageParams);

        if (rightImageId != 0 && rightText != null) {
            rightTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rightTextParams.addRule(RelativeLayout.LEFT_OF, R.id.rightimageid);
            rightTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
            rightTextView.setGravity(Gravity.CENTER_VERTICAL);
            addView(rightTextView, rightTextParams);
            rightImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightImageParams.addRule(CENTER_VERTICAL, TRUE);
            rightImageParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
            addView(rightImage, rightImageParams);
        } else if (rightImageId != 0 && rightText == null) {
            rightImageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightImageParams.addRule(CENTER_VERTICAL, TRUE);
            rightImageParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
            addView(rightImage, rightImageParams);
        } else {
            rightTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rightTextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
            rightTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
            rightTextView.setGravity(Gravity.CENTER_VERTICAL);
            rightTextView.setPadding(15, 5, 15, 5);
            addView(rightTextView, rightTextParams);
        }

    }


    private void getTypeArray(Context context, AttributeSet attrs) {
        //将attrs.xml中定义的属性存储到TypeArray中
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.NormalTopBar);

        leftText = typeArray.getString(R.styleable.NormalTopBar_leftText);
        leftTextColor = typeArray.getColor(R.styleable.NormalTopBar_leftTextColor, Color.BLACK);
        leftTextSize = 15;
        leftImageId = typeArray.getResourceId(R.styleable.NormalTopBar_leftImageSrc, 0);
        titleText = typeArray.getString(R.styleable.NormalTopBar_top_titleText);
        titleTextColor = typeArray.getColor(R.styleable.NormalTopBar_top_titleTextColor, Color.BLACK);
        titleTextSize = typeArray.getDimension(R.styleable.NormalTopBar_top_titleTextSize, 20);
        titleImageId = typeArray.getResourceId(R.styleable.NormalTopBar_top_titleImageSrc, 0);
        rightText = typeArray.getString(R.styleable.NormalTopBar_lefttextview);
        rightTextColor = typeArray.getColor(R.styleable.NormalTopBar_rightTextColor, Color.BLACK);
        rightTextSize = 15;
        rightImageId = typeArray.getResourceId(R.styleable.NormalTopBar_rightImageSrc, 0);

        typeArray.recycle();//获取完所有属性后需要调用recycle来避免重新创建发生的错误

    }

    /**
     * 提供给调用者的点击事件接口
     */
    public interface normalTopClickListener {

        void onLeftClick(View view);

        void onRightClick(View view);

        void onTitleClick(View view);

    }


    public void setTopClickListener(normalTopClickListener mListener) {

        this.mClickListener = mListener;

    }

}
