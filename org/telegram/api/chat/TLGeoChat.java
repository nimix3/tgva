
package org.telegram.api.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.chat.photo.TLAbsChatPhoto;
import org.telegram.api.geo.point.TLAbsGeoPoint;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLGeoChat
extends TLAbsChat {
    public static final int CLASS_ID = 1978329690;
    private String title;
    private int date;
    private long accessHash;
    private String address;
    private String venue;
    private TLAbsGeoPoint geo;
    private TLAbsChatPhoto photo;
    private int participantsCount;
    private boolean checkedIn;
    private int version;

    @Override
    public int getClassId() {
        return 1978329690;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public TLAbsGeoPoint getGeo() {
        return this.geo;
    }

    public void setGeo(TLAbsGeoPoint geo) {
        this.geo = geo;
    }

    public TLAbsChatPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsChatPhoto photo) {
        this.photo = photo;
    }

    public int getParticipantsCount() {
        return this.participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public boolean isCheckedIn() {
        return this.checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.id, stream);
        StreamingUtils.writeLong(this.accessHash, stream);
        StreamingUtils.writeTLString(this.title, stream);
        StreamingUtils.writeTLString(this.address, stream);
        StreamingUtils.writeTLString(this.venue, stream);
        StreamingUtils.writeTLObject(this.geo, stream);
        StreamingUtils.writeTLObject(this.photo, stream);
        StreamingUtils.writeInt(this.participantsCount, stream);
        StreamingUtils.writeInt(this.date, stream);
        StreamingUtils.writeTLBool(this.checkedIn, stream);
        StreamingUtils.writeInt(this.version, stream);
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.id = StreamingUtils.readInt(stream);
        this.accessHash = StreamingUtils.readLong(stream);
        this.title = StreamingUtils.readTLString(stream);
        this.address = StreamingUtils.readTLString(stream);
        this.venue = StreamingUtils.readTLString(stream);
        this.geo = (TLAbsGeoPoint)StreamingUtils.readTLObject(stream, context);
        this.photo = (TLAbsChatPhoto)StreamingUtils.readTLObject(stream, context);
        this.participantsCount = StreamingUtils.readInt(stream);
        this.date = StreamingUtils.readInt(stream);
        this.checkedIn = StreamingUtils.readTLBool(stream);
        this.version = StreamingUtils.readInt(stream);
    }

    @Override
    public String toString() {
        return "geoChat#75eaea5a";
    }
}

