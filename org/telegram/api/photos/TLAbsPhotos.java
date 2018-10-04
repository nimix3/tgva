
package org.telegram.api.photos;

import org.telegram.api.photo.TLAbsPhoto;
import org.telegram.api.user.TLAbsUser;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

public abstract class TLAbsPhotos
extends TLObject {
    protected TLVector<TLAbsPhoto> photos;
    protected TLVector<TLAbsUser> users;

    protected TLAbsPhotos() {
    }

    public TLVector<TLAbsPhoto> getPhotos() {
        return this.photos;
    }

    public void setPhotos(TLVector<TLAbsPhoto> value) {
        this.photos = value;
    }

    public TLVector<TLAbsUser> getUsers() {
        return this.users;
    }

    public void setUsers(TLVector<TLAbsUser> value) {
        this.users = value;
    }
}

