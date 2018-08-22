package thread;

import com.google.common.collect.Lists;
import thread.future.Res;
import thread.process.APIThread;

import java.util.List;
import java.util.concurrent.Callable;

public class API implements Callable<Res> {

    APIThread process;
    Entity entity;

    public API() {
        this.process = new APIThread();
    }


    public API(Entity entity) {
        this.process = new APIThread();
        this.entity = entity;
    }

    public static void main(String[] args) {


    }

    public void invoke(List<Entity> list) {

        Long start = System.currentTimeMillis();

        List<Res> ResList = Lists.newLinkedList();
        for (Entity entity : list) {
            Res res = getHttp(entity);
            ResList.add(res);
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(ResList);
    }


    public Res getHttp(Entity entity) {


        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String key = entity.id * 100 + "key";
        Res res = new Res(entity.id, key);

        return res;
    }


    public Res getHttp( ) {


        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String key = entity.id * 100 + "key";
        Res res = new Res(entity.id, key);

        return res;
    }


    @Override
    public Res call() throws Exception {

//        getHttp();

        return getHttp();
    }
}
