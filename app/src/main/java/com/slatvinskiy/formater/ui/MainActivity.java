package com.slatvinskiy.formater.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.slatvinskiy.formater.App;
import com.slatvinskiy.formater.R;
import com.slatvinskiy.formater.base.MvpView;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements MvpView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etInput)
    EditText etInput;

    @BindView(R.id.tvOutput)
    TextView tvOutput;

    private CompositeDisposable subscriptions;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        subscriptions = new CompositeDisposable();

        setSupportActionBar(toolbar);

        etInput.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (etInput.getRight() - etInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    etInput.setText("");
                    return true;
                }
            }
            return false;
        });

        subscriptions.add(RxTextView.textChanges(etInput)
                .debounce(200, TimeUnit.MILLISECONDS)
                .flatMap(new Function<CharSequence, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull CharSequence charSequence) throws Exception {
                        return presenter.processText(charSequence.toString());
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> tvOutput.setText(text)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_string_1) {
            etInput.setText(getResources().getString(R.string.test_string_1));
            return true;
        }else if (id == R.id.action_string_2) {
            etInput.setText(getResources().getString(R.string.test_string_2));
            return true;
        }else if (id == R.id.action_string_3) {
        etInput.setText(getResources().getString(R.string.test_string_3));
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        subscriptions.clear();
    }
}
