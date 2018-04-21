package com.connect.chat.connectus.ui.activity;


import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.data.fixtures.MessagesFixtures;
import com.connect.chat.connectus.data.model.Message;
import com.connect.chat.connectus.presenter.MessagesPresenter;
import com.connect.chat.connectus.presenter.impl.MessagesPresenterImpl;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessagesActivity extends BaseActivity<MessagesPresenter> implements MessagesView, MessageInput.InputListener, MessagesListAdapter.SelectionListener, MessagesListAdapter.OnLoadMoreListener {
    private MessagesList messagesList;
    private static final int TOTAL_MESSAGES_COUNT = 100;

    private final String senderId = "0";
    private ImageLoader imageLoader;
    private MessagesListAdapter<Message> messagesAdapter;

    private Menu menu;
    private int selectionCount;
    private Date lastLoadedDate;
    @Override
    public int getContentViewId() {
        return R.layout.activity_messages;
    }

    @Override
    public void initializeComponents() {
        messagesList = findViewById(R.id.messagesList);
        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.get().load(url).into(imageView);
            }
        };
        initAdapter();
        MessageInput input =  findViewById(R.id.input);
        input.setInputListener(this);
    }

    private void initAdapter() {
        messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);
        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {

                    }
                });
        messagesList.setAdapter(messagesAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
    }
    private void loadMessages() {
        new Handler().postDelayed(new Runnable() { //imitation of internet connection
            @Override
            public void run() {
                ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
                lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
                messagesAdapter.addToEnd(messages, false);
            }
        }, 1000);
    }
    @Override
    public MessagesPresenter createPresenter() {
        return new MessagesPresenterImpl(this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onSelectionChanged(int count) {

    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages();
        }
    }
    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        return new MessagesListAdapter.Formatter<Message>() {
            @Override
            public String format(Message message) {
                String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                        .format(message.getCreatedAt());

                String text = message.getText();
                if (text == null) text = "[attachment]";

                return String.format(Locale.getDefault(), "%s: %s (%s)",
                        message.getUser().getName(), text, createdAt);
            }
        };
    }
}
