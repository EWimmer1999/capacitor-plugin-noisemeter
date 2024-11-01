package io.capacitor.plugin.noisemeter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.PermissionState;

@CapacitorPlugin(
        name = "NoiseMeter",
        permissions = {
                @Permission(
                        strings = {Manifest.permission.RECORD_AUDIO},
                        alias = NoiseMeterPlugin.RECORD_AUDIO)
        }
)

public class NoiseMeterPlugin extends Plugin {

    static final String RECORD_AUDIO = "recordAudio";
    private AudioRecord audioRecord;
    private boolean isRecording = false;


    @PluginMethod()
    public void start(PluginCall call) {
        if (getPermissionState(RECORD_AUDIO) != PermissionState.GRANTED) {
            requestPermissionForAlias(RECORD_AUDIO, call, "pluginPermissionsCallback");
        } else {
            startRecording(call);
            call.resolve();
        }
    }


    @PermissionCallback
    private void pluginPermissionsCallback(PluginCall call) {
        if (getPermissionState(RECORD_AUDIO) == PermissionState.GRANTED) {
            startRecording(call);
        } else {
            call.reject("Permission is required to use the noise meter");
            Log.d("NoiseMeterPlugin", "Berechtigung zur Audioaufnahme wurde abgelehnt.");
        }
    }


    @PluginMethod()
    public void startRecording(PluginCall call) {
        int sampleRate = 44100;
        int bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        audioRecord.startRecording();
        isRecording = true;
    }


    @PluginMethod()
    public void getNoiseLevel(PluginCall call) {
        if (!isRecording || audioRecord == null) {
            call.reject("Recording is not started.");
            return;
        }

        short[] buffer = new short[1024];
        int read = audioRecord.read(buffer, 0, buffer.length);

        if (read > 0) {
            double sum = 0;
            for (short sound : buffer) {
                sum += sound * sound;
            }
            double amplitude = sum / read;
            double decibels = 20 * Math.log10(Math.sqrt(amplitude));

            JSObject ret = new JSObject();
            ret.put("decibels", decibels);
            call.resolve(ret);
        } else {
            call.reject("Could not read audio data.");
        }
    }

    @PluginMethod()
    public void stop(PluginCall call) {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        call.resolve();
    }
}
