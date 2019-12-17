package com.eminent.a2019.websock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import tech.gusavila92.websocketclient.WebSocketClient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eminent.a2019.websock.databinding.ActivityMainBinding;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNMembershipResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNSpaceResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNUserResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    PNConfiguration pnConfiguration;
    PubNub pubnub;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //createWebSocketClient();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-3a3650a8-1450-11ea-9ae1-a6989f9d21fe");
        pnConfiguration.setPublishKey("pub-c-245fc8c9-041d-42fd-a6ca-0c966c4aeaf7");
        pnConfiguration.setSecure(false);
        pubnub = new PubNub(pnConfiguration);
        arrayList = new ArrayList<>();
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                }
                else {
                    // Message has been received on channel stored in
                    // message.getSubscription()

                }

                Log.i("getMessage",(message.getMessage().toString()));
                Log.i("getTimetoken",String.valueOf(message.getTimetoken().intValue()));
                arrayList.add(message.getMessage().toString());

                //Log.i("final message ",msg);
                //Toast.makeText(MainActivity.this, message.toString(), Toast.LENGTH_SHORT).show();

            /*
                log the following items with your favorite logger
                    - message.getMessage()
                    - message.getSubscription()
                    - message.getTimeToken()
            */
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }

            @Override
            public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {

            }

            @Override
            public void user(PubNub pubnub, PNUserResult pnUserResult) {

            }

            @Override
            public void space(PubNub pubnub, PNSpaceResult pnSpaceResult) {

            }

            @Override
            public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {

            }

            @Override
            public void messageAction(PubNub pubnub, PNMessageActionResult pnMessageActionResult) {

            }
        });
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter.notifyDataSetChanged();
        binding.lvID.setAdapter(arrayAdapter);

        pubnub.subscribe().channels(Arrays.asList("awesomeChannel")).execute();

    }

    public void send(View view) {

        pubnub.publish().channel("awesomeChannel").message(binding.editText.getText().toString()).async(new PNCallback<PNPublishResult>() {
            @Override
            public void onResponse(PNPublishResult result, PNStatus status) {
                // Check whether request successfully completed or not.
                if (!status.isError()) {
                    Log.i("onResponse","Sent");
                    Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                    // Message successfully published to specified channel.
                    arrayAdapter.notifyDataSetChanged();

                }
                // Request processing failed.
                else {


                }
            }
        });

    }



}
