
package ir.telegramp;

import ir.telegramp.UpdateWrapper;
import java.util.List;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.update.TLAbsUpdate;
import org.telegram.api.updates.TLUpdatesState;
import org.telegram.api.updates.difference.TLAbsDifference;
import org.telegram.api.user.TLAbsUser;

public interface IUpdatesHandler {
    public void getDifferences();

    public void onTLAbsDifference(TLAbsDifference var1);

    public void onTLChannelDifferences(List<TLAbsUser> var1, List<TLAbsMessage> var2, List<TLAbsUpdate> var3, List<TLAbsChat> var4);

    public void updateStateModification(TLUpdatesState var1);

    public boolean checkSeq(int var1, int var2, int var3);

    public void processUpdate(UpdateWrapper var1);

    public void onTLUpdatesTooLong();

    public void onUsers(List<TLAbsUser> var1);

    public void onChats(List<TLAbsChat> var1);
}

