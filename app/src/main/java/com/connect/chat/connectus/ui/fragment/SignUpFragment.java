package com.connect.chat.connectus.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.ViewGroup;
import android.widget.Toast;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.loginlib.AuthFragment;
import com.connect.chat.connectus.model.Rotate;
import com.connect.chat.connectus.model.TextSizeTransition;
import com.connect.chat.connectus.model.TextWatcherAdapter;
import com.connect.chat.connectus.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpFragment extends AuthFragment implements View.OnClickListener {
    public static final String TAG = "SignUpFragment";
    @BindViews(value = {R.id.email_input_edit,
            R.id.password_input_edit,
            R.id.confirm_password_edit})
    protected List<TextInputEditText> views;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {

            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_sign_up));
            caption.setText(getString(R.string.sign_up_label));
            for (TextInputEditText editText : views) {
                if (editText.getId() == R.id.password_input_edit) {
                    final TextInputLayout inputLayout = ButterKnife.findById(view, R.id.password_input);
                    final TextInputLayout confirmLayout = ButterKnife.findById(view, R.id.confirm_password);
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    inputLayout.setTypeface(boldTypeface);
                    confirmLayout.setTypeface(boldTypeface);
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
            caption.setVerticalText(true);
            foldStuff();
            caption.setTranslationX(getTextPadding());
        }
        caption.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("SignUpFragment", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("SignUpFragment", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public int authLayout() {
        return R.layout.sign_up_fragment;
    }

    @Override
    public void clearFocus() {
        for (View view : views) view.clearFocus();
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!checkInput()) {
            return;
        }
        Utils.getIns().showLoadding(getContext());
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils.getIns().hideLoadding();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Đăng kí thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Đăng kí thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
    private boolean checkInput() {
        if (views.get(0).getText().toString().equals("") || views.get(1).getText().toString().equals("") || views.get(2).getText().toString().equals("")) {
            Utils.showMessenger("Bạn chưa nhập đầy đủ thông tin bắt buộc", getContext());
            return false;
        }
        if (!views.get(1).getText().toString().equals(views.get(2).getText().toString())) {
            Utils.showMessenger("Mật khẩu không giống nhau", getContext());
            Log.d("SignUpFragment", "checkInput: " + views.get(1).getText().toString() + "/" + views.get(2).getText().toString());
            return false;
        }
        return true;
    }

    @Override
    public void fold() {
        lock = false;
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
        set.addListener(new Transition.TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                caption.setTranslationX(getTextPadding());
                caption.setRotation(0);
                caption.setVerticalText(true);
                caption.requestLayout();
                caption.setClickable(false);

            }
        });
        TransitionManager.beginDelayedTransition(parent, set);
        foldStuff();
        caption.setTranslationX(-caption.getWidth() / 8 + getTextPadding());
    }

    private void foldStuff() {
        caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.getTextSize() / 2f);
        caption.setTextColor(Color.WHITE);
        ConstraintLayout.LayoutParams params = getParams();
        params.rightToRight = ConstraintLayout.LayoutParams.UNSET;
        params.verticalBias = 0.5f;
        caption.setLayoutParams(params);
    }

    private float getTextPadding() {
        return getResources().getDimension(R.dimen.folded_label_padding) / 2.1f;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caption:
                try {
                    createAccount(views.get(0).getText().toString(), views.get(1).getText().toString());
                } catch (RuntimeException e) {}
                break;
            default:
                break;

        }
    }
}
