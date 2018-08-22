package thread.process;

import com.google.common.collect.Lists;
import thread.API;
import thread.Entity;
import thread.future.Res;

import java.util.List;
import java.util.concurrent.*;

/**
 * 最大线程数20
 * 等待队列,无限
 */
public class APIThread implements Callable<Res> {

    //访问外部的多线程调用
    public  ExecutorService rs  ;

    //访问队列
    BlockingQueue<Runnable> workQueue ;

    public APIThread(){
        //无界队列
        this.workQueue=new LinkedBlockingQueue<Runnable>();
//        int cpuThread = 20;
//        //访问线程
//        this.rs = new ThreadPoolExecutor(cpuThread, cpuThread,
//                0L, TimeUnit.MILLISECONDS,
//                this.workQueue );
        this.rs = Executors.newFixedThreadPool(25);
    }


    public void invoke(List<Entity> list) {

        Long start = System.currentTimeMillis();

        List<Res> ResList = Lists.newLinkedList();
        List<Future<Res>> futureList = Lists.newLinkedList();
        for (Entity entity : list) {
            API api  = new API(entity);
            Future<Res> future = this.rs.submit(api);
            futureList.add(future);
        }
        for(Future<Res> futureRes:futureList) {
            Res res = null;
            try {
                res = futureRes.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (res != null) {
                ResList.add(res);
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(ResList);

    }


    @Override
    public Res call() throws Exception {



        return null;
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


}
