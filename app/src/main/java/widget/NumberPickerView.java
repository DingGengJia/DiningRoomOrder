package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.gavin.diningroomorder.R;

import bean.Meal;

/**
 * Created by gavin on 1/5/16.
 */
public class NumberPickerView extends LinearLayout implements View.OnClickListener {
    private static final String LOG_TAG = "NumberPicker";
    NumberPicker numberPicker = null;
    INumberPickerView delegate;
    Meal meal;

    public NumberPickerView(Context context) {
        super(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface INumberPickerView {

        public void submit(Meal meal);

        public void cancle();
    }

    public void setDelegate(INumberPickerView delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Toast.makeText(getContext(), "number picker value change", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.number_picker_cancel);
        btn_cancel.setOnClickListener(this);

        Button btn_confirm = (Button) findViewById(R.id.number_picker_confirm);
        btn_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "number picker on click");
        switch (v.getId()) {
            case R.id.number_picker_cancel:
                Log.d(LOG_TAG, "cancel number pick");
                if(delegate!=null)
                {
                    delegate.cancle();
                }
                break;
            case R.id.number_picker_confirm:
                Log.d(LOG_TAG, "confirm number pick");
                if(delegate!=null)
                {
                    if(meal.getOrderCount() != numberPicker.getValue()) {
                        Log.d(LOG_TAG, "data changed");
                        delegate.submit(meal);
                        meal.setOrderCount(numberPicker.getValue());
                    }
                    else
                    {
                        Log.d(LOG_TAG, "data no change");
                        delegate.cancle();
                    }
                }
                break;
        }
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
        numberPicker.setValue(Integer.valueOf(this.meal.getOrderCount()));
    }

}
