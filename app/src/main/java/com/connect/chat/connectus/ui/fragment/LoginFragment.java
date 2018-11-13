package com.connect.chat.connectus.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.loginlib.AuthFragment;
import com.connect.chat.connectus.model.Rotate;
import com.connect.chat.connectus.model.TextSizeTransition;
import com.connect.chat.connectus.model.TextWatcherAdapter;
import com.connect.chat.connectus.ui.activity.HomeActivity;
import com.connect.chat.connectus.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class LoginFragment extends AuthFragment {

    private static final String TAG = "LogInFragment";
    @BindViews(value = {R.id.email_input_edit, R.id.password_input_edit})
    protected List<TextInputEditText> views;
    private FirebaseAuth mAuth;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (view != null) {
            caption.setText(getString(R.string.log_in_label));
            caption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login(views.get(0).getText().toString(), views.get(1).getText().toString());
                }
            });
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_log_in));
            for (TextInputEditText editText : views) {
                if (editText.getId() == R.id.password_input_edit) {
                    final TextInputLayout inputLayout = ButterKnife.findById(view, R.id.password_input);
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    inputLayout.setTypeface(boldTypeface);
                    editText.addTextChangedListener(new TextWatcherAdapter() {
                        @Override
                        public void afterTextChanged(Editable editable) {
                            inputLayout.setPasswordVisibilityToggleEnabled(editable.length() > 0);
                        }
                    });
                }
                editText.setOnFocusChangeListener((temp, hasFocus) -> {
                    if (!hasFocus) {
                        boolean isEnabled = editText.getText().length() > 0;
                        editText.setSelected(isEnabled);
                    }
                });
            }
        }
    }

    @Override
    public int authLayout() {
        return R.layout.login_fragment;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fold() {
        lock = false;
        caption.setClickable(false);
        Rotate transition = new Rotate();
        transition.setEndAngle(-90f);
        transition.addTarget(caption);
        TransitionSet set = new TransitionSet();
        set.setDuration(getResources().getInteger(R.integer.duration));
        ChangeBounds changeBounds = new ChangeBounds();
        set.addTransition(changeBounds);
        set.addTransition(transition);
        TextSizeTransition sizeTransition = new TextSizeTransition();
        sizeTransition.addTarget(caption);
        set.addTransition(sizeTransition);
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        final float padding = getResources().getDimension(R.dimen.folded_label_padding) / 2;
        set.addListener(new Transition.TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                caption.setTranslationX(-padding);
                caption.setRotation(0);
                caption.setVerticalText(true);
                caption.requestLayout();
                caption.setClickable(false);

            }
        });
        TransitionManager.beginDelayedTransition(parent, set);
        caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.getTextSize() / 2);
        caption.setTextColor(Color.WHITE);
        ConstraintLayout.LayoutParams params = getParams();
        params.leftToLeft = ConstraintLayout.LayoutParams.UNSET;
        params.verticalBias = 0.5f;
        caption.setLayoutParams(params);
        caption.setTranslationX(caption.getWidth() / 8 - padding);
    }

    @Override
    public void clearFocus() {
        for (View view : views) view.clearFocus();
    }

    public void login(String email, String password) {
        if (!checkInput()) {
            return;
        }
        Utils.getIns().showLoadding(getContext());
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Utils.getIns().hideLoadding();
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getActivity(), R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            Utils.showMessenger("Đăng nhập thất bại", getContext());
                        } else {
                            Utils.getIns().hideLoadding();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                    }

                });
    }

    private boolean checkInput() {
        if (views.get(0).getText().toString().equals("") || views.get(1).getText().toString().equals("")) {
            Utils.showMessenger("Bạn chưa nhập đầy đủ thông tin bắt buộc", getContext());
            return false;
        }
        return true;
    }

}
