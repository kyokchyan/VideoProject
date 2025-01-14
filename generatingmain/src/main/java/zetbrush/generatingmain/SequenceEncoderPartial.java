/*
package zetbrush.com.generatingmain;

import org.jcodec.codecs.h264.H264Encoder;
import org.jcodec.codecs.h264.H264Utils;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.Brand;
import org.jcodec.containers.mp4.MP4Packet;
import org.jcodec.containers.mp4.TrackType;
import org.jcodec.containers.mp4.muxer.FramesMP4MuxerTrack;
import org.jcodec.containers.mp4.muxer.MP4Muxer;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.Transform;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class SequenceEncoderPartial {
    private SeekableByteChannel ch;
    private SeekableByteChannel chm;
    private Picture toEncode;
    private Transform transform;
    private H264Encoder encoder;
    private ArrayList<ByteBuffer> spsList;
    private ArrayList<ByteBuffer> ppsList;
    private FramesMP4MuxerTrack outTrack;
    private ByteBuffer _out;
    private int frameNo;
    private MP4Muxer muxer;
    private static int framesec =1;

    public static void setFrameDuration(int sec){
       framesec = sec;
    }

    public SequenceEncoderPartial(File out) throws IOException {
        this.ch = NIOUtils.writableFileChannel(out);

        muxer = new MP4Muxer(ch, Brand.MOV);

        outTrack = muxer.addTrackForCompressed(TrackType.VIDEO, 10);

        // Allocate a buffer big enough to hold output frames
        _out = ByteBuffer.allocate(1920 * 1080 * 6);

        // Create an instance of encoder
        encoder = new H264Encoder();

        // Transform to convert between RGB and YUV
        transform = ColorUtil.getTransform(ColorSpace.RGB, encoder.getSupportedColorSpaces()[0]);

        // Encoder extra data ( SPS, PPS ) to be stored in a special place of
        // MP4
        spsList = new ArrayList<ByteBuffer>();
        ppsList = new ArrayList<ByteBuffer>();

    }


    public void encodeNativeFrameForPartialEffect(Picture pic) throws IOException {


        if (toEncode == null) {
            toEncode = Picture.create(pic.getWidth(), pic.getHeight(), encoder.getSupportedColorSpaces()[0]);
        }

        // Perform conversion
        transform.transform(pic, toEncode);

        // Encode image into H.264 frame, the result is stored in '_out' buffer
        _out.clear();
        ByteBuffer result = encoder.encodeFrame(toEncode, _out);

        // Based on the frame above form correct MP4 packet
        spsList.clear();
        ppsList.clear();
        H264Utils.wipePS(result, spsList, ppsList);
        H264Utils.encodeMOVPacket(result);

        // Add packet to video track


        outTrack.addFrame(new MP4Packet(
                result,
                frameNo, /
 framesec,
                25,
                1,
                frameNo,
                true,
                null,
                frameNo,
                0));

        frameNo++;

    }

    public void finish() throws IOException {
        // Push saved SPS/PPS to a special storage in MP4

      //  org.jcodec.containers.mp4.boxes.SampleEntry smpentry = H264Utils.createMOVSampleEntry(spsList, ppsList);

        outTrack.addSampleEntry(H264Utils.createMOVSampleEntry(spsList, ppsList));

        // Write MP4 header and finalize recording

        muxer.writeHeader();

        NIOUtils.closeQuietly(ch);
     }
    }
*/
