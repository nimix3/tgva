
package org.telegram.api.webpage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.api.document.TLAbsDocument;
import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.webpage.TLAbsWebPage;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;

public class TLWebPage
extends TLAbsWebPage {
    public static final int CLASS_ID = -897446185;
    private static final int FLAG_TYPE = 1;
    private static final int FLAG_SITENAME = 2;
    private static final int FLAG_TITLE = 4;
    private static final int FLAG_DESCRIPTION = 8;
    private static final int FLAG_PHOTO = 16;
    private static final int FLAG_URL = 32;
    private static final int FLAG_SIZE = 64;
    private static final int FLAG_DURATION = 128;
    private static final int FLAG_AUTHOR = 256;
    private static final int FLAG_DOCUMENT = 512;
    private int flags;
    private long id;
    private String url;
    private String display_url;
    private String type;
    private String site_name;
    private String title;
    private String description;
    private TLAbsPhoto photo;
    private String embed_url;
    private String embed_type;
    private int embed_width;
    private int embed_height;
    private int duration;
    private String author;
    private TLAbsDocument document;

    @Override
    public int getClassId() {
        return -897446185;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay_url() {
        return this.display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite_name() {
        return this.site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TLAbsPhoto getPhoto() {
        return this.photo;
    }

    public void setPhoto(TLAbsPhoto photo) {
        this.photo = photo;
    }

    public String getEmbed_url() {
        return this.embed_url;
    }

    public void setEmbed_url(String embed_url) {
        this.embed_url = embed_url;
    }

    public String getEmbed_type() {
        return this.embed_type;
    }

    public void setEmbed_type(String embed_type) {
        this.embed_type = embed_type;
    }

    public int getEmbed_width() {
        return this.embed_width;
    }

    public void setEmbed_width(int embed_width) {
        this.embed_width = embed_width;
    }

    public int getEmbed_height() {
        return this.embed_height;
    }

    public void setEmbed_height(int embed_height) {
        this.embed_height = embed_height;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TLAbsDocument getDocument() {
        return this.document;
    }

    public void setDocument(TLAbsDocument document) {
        this.document = document;
    }

    @Override
    public void serializeBody(OutputStream stream) throws IOException {
        StreamingUtils.writeInt(this.flags, stream);
        StreamingUtils.writeLong(this.id, stream);
        StreamingUtils.writeTLString(this.url, stream);
        StreamingUtils.writeTLString(this.display_url, stream);
        if ((this.flags & 1) != 0) {
            StreamingUtils.writeTLString(this.type, stream);
        }
        if ((this.flags & 2) != 0) {
            StreamingUtils.writeTLString(this.site_name, stream);
        }
        if ((this.flags & 4) != 0) {
            StreamingUtils.writeTLString(this.title, stream);
        }
        if ((this.flags & 8) != 0) {
            StreamingUtils.writeTLString(this.description, stream);
        }
        if ((this.flags & 16) != 0) {
            StreamingUtils.writeTLObject(this.photo, stream);
        }
        if ((this.flags & 32) != 0) {
            StreamingUtils.writeTLString(this.embed_url, stream);
            StreamingUtils.writeTLString(this.embed_type, stream);
        }
        if ((this.flags & 64) != 0) {
            StreamingUtils.writeInt(this.embed_width, stream);
            StreamingUtils.writeInt(this.embed_height, stream);
        }
        if ((this.flags & 128) != 0) {
            StreamingUtils.writeInt(this.duration, stream);
        }
        if ((this.flags & 256) != 0) {
            StreamingUtils.writeTLString(this.author, stream);
        }
        if ((this.flags & 512) != 0) {
            StreamingUtils.writeTLObject(this.document, stream);
        }
    }

    @Override
    public void deserializeBody(InputStream stream, TLContext context) throws IOException {
        this.flags = StreamingUtils.readInt(stream);
        this.id = StreamingUtils.readLong(stream);
        this.url = StreamingUtils.readTLString(stream);
        this.display_url = StreamingUtils.readTLString(stream);
        if ((this.flags & 1) != 0) {
            this.type = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 2) != 0) {
            this.site_name = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 4) != 0) {
            this.title = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 8) != 0) {
            this.description = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 16) != 0) {
            this.photo = (TLAbsPhoto)StreamingUtils.readTLObject(stream, context);
        }
        if ((this.flags & 32) != 0) {
            this.embed_url = StreamingUtils.readTLString(stream);
            this.embed_type = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 64) != 0) {
            this.embed_width = StreamingUtils.readInt(stream);
            this.embed_height = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 128) != 0) {
            this.duration = StreamingUtils.readInt(stream);
        }
        if ((this.flags & 256) != 0) {
            this.author = StreamingUtils.readTLString(stream);
        }
        if ((this.flags & 512) != 0) {
            this.document = (TLAbsDocument)StreamingUtils.readTLObject(stream, context);
        }
    }

    @Override
    public String toString() {
        return "webpage.TLWebPage#ca820ed7";
    }
}

