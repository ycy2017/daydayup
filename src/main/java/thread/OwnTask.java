package thread;

import com.google.common.collect.Lists;
import thread.process.APIThread;

import java.util.List;

public class OwnTask {


    public static void main(String[] args) {


        List<Entity> list = Lists.newLinkedList();
        for (Long i = 1L; i <= 25; i++) {
            list.add(new Entity(i));
        }
//        API api = new API();
//        api.invoke(list);
        //17513
        APIThread apiThread = new APIThread();
        apiThread.invoke(list);

    }

}
