package com.udemy.cursoandroid.gestaogados.Helper;

import static com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper.SetToastMessageAndShow;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NfcHelper {
    private static final String ERROR_READ = "No Nfc Tag Detected";
    private static final String ERROR_WRITE = "Error trying to write to Nfc tag, try again";
    private static final String SUCCESS_WRITE = "Nfc tag wrote successful";

    Context context;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] intentFilter;
    Tag myTag;

    public NfcHelper(Context context) {
        this.context = context;
        this.InitializeNfc();
    }

    private void InitializeNfc()
    {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        intentFilter = new IntentFilter[2];
        intentFilter[0] = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        intentFilter[0].addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter[1] = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilter[1].addCategory(Intent.CATEGORY_DEFAULT);
    }

    public boolean SaveTagContent(String content) {
        try {
            if (myTag == null) {
                SetToastMessageAndShow(ERROR_READ, context);
            } else {
                Write(content, myTag);
                SetToastMessageAndShow(SUCCESS_WRITE, context);
                return true;
            }

        } catch (IOException e) {
            SetToastMessageAndShow(ERROR_WRITE, context);
            e.printStackTrace();

        } catch (FormatException e) {
            SetToastMessageAndShow(ERROR_WRITE, context);
            e.printStackTrace();
        }

        return false;
    }

    private void Write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records  = { CreateRecord(text) };
        NdefMessage message = new NdefMessage(records);

        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private NdefRecord CreateRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = lang.length();
        int textLength = text.length();
        byte[] payload = new byte[1 + langLength + textLength];

        // Set status byte (see Ndef for actual bits)
        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNdef = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNdef;
    }


    public String ReadFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
        {
            Parcelable[] rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsg != null)
            {
                msgs = new NdefMessage[rawMsg.length];
                for (int i=0; i < rawMsg.length; i++)
                {
                    msgs[i] = (NdefMessage) rawMsg[i];
                }
            }
            return BuildTagViews(msgs);
        }
        return "";
    }

    private String BuildTagViews(NdefMessage[] msgs)
    {
        if (msgs == null || msgs.length == 0)
            return "";

        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 63;

        try
        {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return text;
    }

    public void setMyTag(Tag myTag) {
        this.myTag = myTag;
    }

    public void foregroundDispatch(Activity activity, boolean status) {
        if (status) {
            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilter, null);
        } else {
            nfcAdapter.disableForegroundDispatch(activity);
        }
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public NfcAdapter getNfcAdapter() {
        return nfcAdapter;
    }

    public IntentFilter[] getIntentFilter() {
        return intentFilter;
    }
}
